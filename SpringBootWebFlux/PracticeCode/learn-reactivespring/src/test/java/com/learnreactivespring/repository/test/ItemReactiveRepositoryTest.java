package com.learnreactivespring.repository.test;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
public class ItemReactiveRepositoryTest {
	
	@Autowired
	ItemReactiveRepository itemReactiveRepository;
	
	@BeforeEach
	public void setUpItems () {
		List<Item> itemList = Arrays.asList(new Item(null, "Samsung TV", 400.20),
				new Item(null, "LG TV", 620.00),
				new Item("123", "MacBook Pro", 840.70));
		
		itemReactiveRepository.deleteAll()
			.thenMany(Flux.fromIterable(itemList))
			.flatMap((item) -> itemReactiveRepository.save(item))
			.doOnNext((item) -> {
				System.out.println("The next Item was inserted: ");
				System.out.println("id: " + item.getId());
				System.out.println("descpription: " + item.getDescription());
				System.out.println("price: " + item.getPrice());
			})
			.blockLast();
	}
	
	@Test
	public void getAllItems(){
		StepVerifier.create(itemReactiveRepository.findAll().log())
				.expectSubscription()
				.expectNextMatches(item -> item.getDescription().equals("Samsung TV"))
				.expectNextMatches(item -> item.getDescription().equals("LG TV"))
				.expectNextMatches(item -> item.getDescription().equals("MacBook Pro"))
				.verifyComplete();
	}
	
	@Test
	public void getItemById () {
		StepVerifier.create(itemReactiveRepository.findById("123").log())
				.expectNextMatches(item -> item.getDescription().equals("MacBook Pro"))
				.verifyComplete();
	}
	
	@Test
	public void getItemByDescription () {
		StepVerifier.create(itemReactiveRepository.getItemByDescription("MacBook Pro").log())
				.expectNextMatches(item -> item.getId().equals("123"))
				.verifyComplete();
	}
	
	@Test
	public void updateItem () {
		itemReactiveRepository.findById("123")
			.map(item -> {
				item.setPrice(1000.00);
				return item;
			})
			.flatMap((item) -> itemReactiveRepository.save(item))
			.block();
			
		
		StepVerifier.create(itemReactiveRepository.findById("123").log())
			.expectSubscription()
			.expectNextMatches(item -> item.getPrice() == 1000.00)
			.verifyComplete();
	}
	
	

}
