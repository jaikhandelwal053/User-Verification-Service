package com.user.verification.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.user.verification.models.User;
import com.user.verification.repository.UserRepository;
import com.user.verification.responses.GenderResponse;
import com.user.verification.responses.NationalityResponse;
import com.user.verification.responses.RandomUserResponse;

import reactor.util.retry.Retry;

@Service
public class UserInfoService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Qualifier("randomUserWebClient")
	private WebClient randomUserWebClient;

	@Autowired
	@Qualifier("nationalizeWebClient")
	private WebClient nationalizeWebClient;

	@Autowired
	@Qualifier("genderizeWebClient")
	private WebClient genderizeWebClient;

	public void fetchAndSaveUser() {

//		For randomUserWebClient
		RandomUserResponse randomUserResponse = randomUserWebClient.get().retrieve()
				.bodyToMono(RandomUserResponse.class)
				.retryWhen(Retry.backoff(3, Duration.ofSeconds(2)).filter(this::isRetryableException)).block();

		RandomUserResponse.UserData userresponse = randomUserResponse.getResults().get(0);

		String name = userresponse.getName().getFirst() + " " + userresponse.getName().getLast();
		String nationality = userresponse.getNat();
		String gender = userresponse.getGender();
		int age = userresponse.getDob().getAge();
		String dob = userresponse.getDob().getDate();

//		For nationalizeWebClient
		CompletableFuture<NationalityResponse> nationalityFuture = CompletableFuture
				.supplyAsync(() -> nationalizeWebClient.get()
						.uri(uriBuilder -> uriBuilder.path("/").queryParam("name", userresponse.getName().getFirst())
								.build())
						.retrieve().bodyToMono(NationalityResponse.class)
						.retryWhen(Retry.backoff(3, Duration.ofSeconds(2)).filter(this::isRetryableException)).block());

//		For genderizeWebClient
		CompletableFuture<GenderResponse> genderFuture = CompletableFuture.supplyAsync(() -> genderizeWebClient.get()
				.uri(uriBuilder -> uriBuilder.path("/").queryParam("name", userresponse.getName().getFirst()).build())
				.retrieve().bodyToMono(GenderResponse.class)
				.retryWhen(Retry.backoff(3, Duration.ofSeconds(2)).filter(this::isRetryableException)).block());

		CompletableFuture.allOf(nationalityFuture, genderFuture).join();

//		Check and set verified
		boolean isVerified = false;
		try {
			NationalityResponse nationalityResponse = nationalityFuture.get();
			GenderResponse genderResponse = genderFuture.get();

			isVerified = nationalityResponse.getCountry().stream()
					.anyMatch(country -> country.getCountryId().equals(nationality))
					&& genderResponse.getGender().equals(gender);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		String verificationStatus = isVerified ? "VERIFIED" : "TO_BE_VERIFIED";

		
//		Set data for database 
		User user = new User();
		user.setName(name);
		user.setAge(age);
		user.setGender(gender);
		user.setDob(dob);
		user.setNationality(nationality);
		user.setVerificationStatus(verificationStatus);
		user.setDateCreated(LocalDateTime.now());
		user.setDateModified(LocalDateTime.now());

		userRepository.save(user);
	}

	private boolean isRetryableException(Throwable throwable) {
		if (throwable instanceof WebClientResponseException) {
			WebClientResponseException ex = (WebClientResponseException) throwable;
			return ex.getStatusCode().is5xxServerError() || ex.getStatusCode().value() == 429;
		}
		return false;
	}
}
