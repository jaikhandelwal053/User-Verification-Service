package com.user.verification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.user.verification.models.User;
import com.user.verification.repository.UserRepository;
import com.user.verification.responses.PageInfo;
import com.user.verification.responses.UserResponse;
import com.user.verification.responses.UserWithPageInfoResponse;
import com.user.verification.strategy.SortingMethod;
import com.user.verification.strategy.SortingStrategy;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private SortingMethod sortingMethod;

	public List<UserResponse> createUsers(int size) {
		for (int i = 0; i < size; i++) {
			userInfoService.fetchAndSaveUser();
		}
		List<User> users = userRepository.findAll(PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "dateCreated")))
				.getContent();

		return users.stream().map(this::createUserResponse).collect(Collectors.toList());
	}

	public List<UserResponse> getUsers(String sortType, String sortOrder, int limit, int offset) {
		Sort sort = Sort.unsorted();
		if ("Name".equalsIgnoreCase(sortType)) {
			sort = Sort.by("name");
		} else if ("Age".equalsIgnoreCase(sortType)) {
			sort = Sort.by("age");
		}
		List<User> users = userRepository.findAll(sort);

		SortingStrategy sortingStrategy = sortingMethod.getStrategy(sortType, sortOrder);
		if (sortingStrategy != null) {
			users = sortingStrategy.sortingby(users);
		}
		
		users = users.stream().skip(offset).limit(limit).collect(Collectors.toList());

		return users.stream().map(this::createUserResponse).collect(Collectors.toList());

	}

	public List<UserWithPageInfoResponse> getAllUsers() {

		List<User> users = userRepository.findAll();
		long totalUsers = users.size();

		List<UserWithPageInfoResponse> userResponses = finalDataResponse(totalUsers, users);
		return userResponses;
	}

	private List<UserWithPageInfoResponse> finalDataResponse(long totalUsers, List<User> users) {
		List<UserWithPageInfoResponse> userResponses = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			UserResponse ur = createUserResponse(user);
			PageInfo pageInfo = new PageInfo(i < users.size() - 1, i > 0, totalUsers);
			userResponses.add(new UserWithPageInfoResponse(ur, pageInfo));
		}
		return userResponses;
	}

	private UserResponse createUserResponse(User user) {
		return new UserResponse(user.getName(), user.getAge(), user.getDob(), user.getGender(), user.getNationality(),
				user.getVerificationStatus());
	}

}
