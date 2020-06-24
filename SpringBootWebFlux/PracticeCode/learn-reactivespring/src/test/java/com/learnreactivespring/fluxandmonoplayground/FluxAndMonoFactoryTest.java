package com.learnreactivespring.fluxandmonoplayground;

import java.util.List;
import java.util.Arrays;


import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class FluxAndMonoFactoryTest {
	
	List<String> names = Arrays.asList("Mike", "Anna", "George", "Jack");
	
	@Test
	public void fluxUsingIterable () {
		
		Flux<String> namesFlux = Flux.fromIterable(names).log();
		namesFlux.subscribe(System.out::println);
		
	}
	
	@Test
	public void fluxUsingStream () {
		
		Flux<String> namesFlux = Flux.fromStream(names.stream()).log();
		namesFlux.subscribe(System.out::println);
		
	}

}
