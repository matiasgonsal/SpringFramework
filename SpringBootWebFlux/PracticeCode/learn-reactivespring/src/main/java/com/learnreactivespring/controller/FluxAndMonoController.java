package com.learnreactivespring.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FluxAndMonoController {
	
	@GetMapping ("/flux")
	public Flux<Integer> returnNumericFlux (){
		return Flux.range(1, 4).delayElements(Duration.ofSeconds(2)).log();
	}
	
	@GetMapping (value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Integer> returnFluxStream (){
		return Flux.range(1, 10).delayElements(Duration.ofSeconds(2)).log();
	}
	
	@GetMapping ("/mono")
	public Mono<String> returnMono (){
		return Mono.just("Hello, this is a Mono Api from WebFlux");
	}
}
