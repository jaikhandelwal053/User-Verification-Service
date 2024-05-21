package com.user.verification.configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	@Value("${api.randomuser.url}")
	private String randomUserApiUrl;

	@Value("${api.nationalize.url}")
	private String nationalizeApiUrl;

	@Value("${api.genderize.url}")
	private String genderizeApiUrl;

	@Bean(name = "randomUserWebClient")
	public WebClient api1WebClient() {
		return WebClient.builder().baseUrl(randomUserApiUrl)
				.clientConnector(new ReactorClientHttpConnector(HttpClient.create()
						.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000).responseTimeout(Duration.ofMillis(2000))
						.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(2000, TimeUnit.MILLISECONDS))
								.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS)))))
				.build();
	}

	@Bean(name = "nationalizeWebClient")
	public WebClient api2WebClient() {
		return WebClient.builder().baseUrl(nationalizeApiUrl)
				.clientConnector(new ReactorClientHttpConnector(HttpClient.create()
						.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000).responseTimeout(Duration.ofMillis(1000))
						.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(1000, TimeUnit.MILLISECONDS))
								.addHandlerLast(new WriteTimeoutHandler(1000, TimeUnit.MILLISECONDS)))))
				.build();
	}

	@Bean(name = "genderizeWebClient")
	public WebClient api3WebClient() {
		return WebClient.builder().baseUrl(genderizeApiUrl)
				.clientConnector(new ReactorClientHttpConnector(HttpClient.create()
						.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000).responseTimeout(Duration.ofMillis(1000))
						.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(1000, TimeUnit.MILLISECONDS))
								.addHandlerLast(new WriteTimeoutHandler(1000, TimeUnit.MILLISECONDS)))))
				.build();
	}
}