package com.learnreactivespring.fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FluxAndMonoTransformTest {
	
	List<String> names = Arrays.asList("Mike", "Anna", "George", "Jack");
	
	@Test
	public void fluxTransformTest() {
		Flux<String> fluxNames = Flux.fromIterable(names)
						.map(name -> name.toUpperCase())
						.log();
		
		fluxNames.subscribe(System.out::println);
						
	}
	
	@Test
	public void fluxTransformUsingFlatMap() {
		Flux<String> fluxNames = Flux.fromIterable(Arrays.asList("A", "B", "C", "D"))
						.flatMap(name -> {
							return Flux.fromIterable(convertToList(name));
						})
						.log();
		
		fluxNames.subscribe();
						
	}
	
	@Test
	public void fluxTransformUsingFlatMap_parallel() {
		Flux<String> fluxNames = Flux.fromIterable(Arrays.asList("A", "B", "C", "D"))
						.window(2)
						.flatMap((names) -> 
							names.map(this::convertToList).subscribeOn(Schedulers.parallel()))
							.flatMap(name -> Flux.fromIterable(name))
						.log();
						
		
		StepVerifier.create(fluxNames)
					.expectNextCount(8)
					.verifyComplete();
						
	}
	
	@Test
	public void fluxTransformUsingFlatMap_parallel_inOrder() {
		Flux<String> fluxNames = Flux.fromIterable(Arrays.asList("A", "B", "C", "D"))
						.window(2)
						.flatMapSequential((names) -> 
							names.map(this::convertToList).subscribeOn(Schedulers.parallel()))
							.flatMap(name -> Flux.fromIterable(name))
						.log();
						
		
		StepVerifier.create(fluxNames)
		.expectNextCount(8)
		.verifyComplete();
						
	}
	
	
	public List<String> convertToList (String name){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Arrays.asList(name, "NewValue");
	}

}
