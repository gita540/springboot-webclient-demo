package com.example.springbootwebclientdemo;


import com.example.springbootwebclientdemo.service.GreetingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@AllArgsConstructor
public class SpringbootWebclientDemoApplication implements CommandLineRunner {

private final GreetingService greetingService;
  public static void main(String[] args) {
    SpringApplication.run(SpringbootWebclientDemoApplication.class, args);
  }

  //Non-blocking call
  @Override
  public void run(String... args) throws Exception {
    greetingService.getGreetingAsyncCall()
        .subscribe(greeting -> log.info("Get Greeting async : {} , {} ", greeting.getId(), greeting.getContent()));
  }

//  //For blocking call
//  @Override
//  public void run(String... args) throws Exception {
//    log.info("Greeting service response:  {}", greetingService.getGreeting().getContent());
//  }

  //Error Handling
//  @Override
//  public void run(String... args) throws Exception {
//        log.info("Get Greeting async : {} ", greetingService.getGreetingWithErrorHandling());
//  }

  //Fallback
//  @Override
//  public void run(String... args) throws Exception {
//    log.info("Get Greeting async : {} ", greetingService.getGreetingWithFallBack());
//  }

//  //Retry
//  @Override
//  public void run(String... args) throws Exception {
//    log.info("Get Greeting async : {} ", greetingService.getGreetingWithRetry());
//  }
}
