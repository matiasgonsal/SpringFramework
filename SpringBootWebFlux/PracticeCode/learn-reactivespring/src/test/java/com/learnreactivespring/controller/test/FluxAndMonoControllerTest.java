package com.learnreactivespring.controller.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@WebFluxTest
@AutoConfigureWebTestClient (timeout = "10000")// 10 seconds
public class FluxAndMonoControllerTest {
	
	@Autowired
	WebTestClient webTestClient;
	
	@Test
	public void flux_test () {
		Flux<Integer> fluxTest = webTestClient.get().uri("/flux")
				.accept(MediaType.APPLICATION_JSON)
				.exchange() //This is the method calling the endpoint
				.expectStatus().isOk()
				.returnResult(Integer.class)
				.getResponseBody();
		
		StepVerifier.create(fluxTest.log())
				.expectSubscription()
				.expectNext(1, 2, 3, 4)
				.verifyComplete();
	}
	
	@Test
	public void mono_test () {
		Flux<String> monoTest = webTestClient.get().uri("/mono")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.returnResult(String.class)
			.getResponseBody();
		
		StepVerifier.create(monoTest.log())
			.expectSubscription()
			.expectNext("Hello, this is a Mono Api from WebFlux")
			.verifyComplete();
	}
	
	@Test
	public void mono_test2 () {
		Flux<String> monoTest = webTestClient.get().uri("/mono2")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.returnResult(String.class)
			.getResponseBody();
		
		StepVerifier.create(monoTest.log())
			.expectSubscription()
			.expectNext("Hello, this is a Mono Api from WebFlux")
			.verifyComplete();
	}

}
