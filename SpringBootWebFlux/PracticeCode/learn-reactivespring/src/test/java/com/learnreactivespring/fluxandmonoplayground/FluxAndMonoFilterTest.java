package com.learnreactivespring.fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxAndMonoFilterTest {

	List<String> names = Arrays.asList("Mike", "Anna", "George", "Jack");
	
	@Test
	public void filterTest() {
		Flux<String> namesFlux = Flux.fromIterable(names);
		
		namesFlux
			.filter(name -> name.startsWith("A"))
			.log()
			.subscribe(name -> System.out.println(name));
		
	}
}
