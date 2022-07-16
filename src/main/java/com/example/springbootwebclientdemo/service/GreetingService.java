package com.example.springbootwebclientdemo.service;

import com.example.springbootwebclientdemo.model.Greeting;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
@Slf4j
public class GreetingService {

  @Autowired
  private WebClient webClient;

  private String name = "Your name";

  public Greeting getGreeting() {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/greeting")
            .queryParam("name", name)
            .build())
        .retrieve()
        .bodyToMono(Greeting.class)
        .block();
  }

  public Mono<Greeting> getGreetingAsyncCall() {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/greeting")
            .queryParam("name", name).build())
        .retrieve()
        .bodyToMono(Greeting.class);
  }

  public Greeting getGreetingWithRetry() {
    return webClient.get()
        .uri("broken_url")
        .retrieve()
        .bodyToMono(Greeting.class)
        .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)))
        .block();
  }

  public Greeting getGreetingWithErrorHandling() {
    return webClient.get()
        .uri("/hello")
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, err -> Mono.error(new RuntimeException("API Not Found")))
        .onStatus(HttpStatus::is5xxServerError, err -> Mono.error(new RuntimeException("Server is not Responding")))
        .bodyToMono(Greeting.class)
        .block();
  }

  public Greeting getGreetingWithFallBack() {
    return webClient.get()
        .uri("/hi")
        .retrieve()
        .bodyToMono(Greeting.class)
        .doOnError(err -> log.error("An error occurred ", err.getMessage()))
        .onErrorResume(err -> Mono.just(new Greeting()))
        .block();
  }
}
