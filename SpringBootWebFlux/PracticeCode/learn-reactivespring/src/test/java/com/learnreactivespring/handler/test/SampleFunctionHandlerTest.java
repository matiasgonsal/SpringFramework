package com.learnreactivespring.handler.test;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.learnreactivespring.constants.ItemConstants;
import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext
public class SampleFunctionHandlerTest {
	
	@Autowired
	WebTestClient webTestClient;
	
	@Autowired
	ItemReactiveRepository itemReactiveRepository;
	
	
	@BeforeEach
	public void setUpItems () {
		
		List<Item> itemList = Arrays.asList(new Item(null, "Samsung TV", 400.20),
				new Item(null, "LG TV", 620.00),
				new Item("123", "MacBook Pro", 840.70));
		
		itemReactiveRepository.deleteAll()
			.thenMany(Flux.fromIterable(itemList))
			.flatMap(item -> itemReactiveRepository.save(item))
			.blockLast();
	}
	
	
	@Test
	public void getItems_test () {
		
		Flux<String> items = webTestClient.get().uri("/items")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.returnResult(String.class)
				.getResponseBody();
		
		StepVerifier.create(items.log())
				.expectSubscription()
				.expectNext("Item 1Item 2Item 3")
				.verifyComplete();
		
	}
	
	@Test
	public void getAllItems_test () {
		Flux<Item> itemsFlux = webTestClient.get().uri(ItemConstants.GET_ALL_ITEMS)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.returnResult(Item.class)
				.getResponseBody();
		
		StepVerifier.create(itemsFlux.log("Item from Handler Test: "))
			.expectSubscription()
			.expectNextCount(3)
			.verifyComplete();
		
	}
	

}
