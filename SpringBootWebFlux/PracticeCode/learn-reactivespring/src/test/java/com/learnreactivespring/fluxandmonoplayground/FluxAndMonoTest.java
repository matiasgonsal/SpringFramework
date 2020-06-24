package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
	
	@Test
	public void fluxTest() {
		
		Flux<String> stringFlux = Flux.just("Customer 1", "Customer 2", "Customer 3")
						.concatWith(Flux.error(new RuntimeException("An exception occurred")))
						.log();
		
		stringFlux
			.subscribe(System.out::println, //On-NextEvent
					   e -> System.out.println(e.getMessage()), //On-Error Event
					   () -> System.out.println("OnComplete Excecution")); //On-Complete Event
			
	}
	
	@Test
	public void fluxTestElements_WithoutError (){
		
		Flux<String> stringFlux = Flux.just("Customer 1", "Customer 2", "Customer 3")
						.log();
		
		StepVerifier.create(stringFlux)
				.expectNext("Customer 1")
				.expectNext("Customer 2")
				.expectNext("Customer 3")
				.verifyComplete();
	}
	
	@Test
	public void fluxTestElements_WithError (){
		
		Flux<String> stringFlux = Flux.just("Customer 1", "Customer 2", "Customer 3")
						.concatWith(Flux.error(new RuntimeException("An exception occurred")))
						.log();
		
		StepVerifier.create(stringFlux)
				.expectNext("Customer 1")
				.expectNext("Customer 2")
				.expectNext("Customer 3")
				.expectError(RuntimeException.class)
				.verify();
				
	}
	
	@Test
	public void monoTest () {
		Mono<String> monoString = Mono.just("Customer")
					.log();
		
		StepVerifier.create(monoString)
				.expectNext("Customer")
				.verifyComplete();
		
	}
}
