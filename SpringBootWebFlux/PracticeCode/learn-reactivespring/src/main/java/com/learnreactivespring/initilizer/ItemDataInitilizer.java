package com.learnreactivespring.initilizer;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReactiveRepository;

import reactor.core.publisher.Flux;

@Component
public class ItemDataInitilizer implements CommandLineRunner{

	@Autowired
	ItemReactiveRepository itemReactiveRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		List<Item> itemList = Arrays.asList(new Item(null, "Samsung TV", 400.20),
				new Item(null, "LG TV", 620.00),
				new Item("123", "MacBook Pro", 840.70));
		
		itemReactiveRepository.deleteAll()
			.thenMany(Flux.fromIterable(itemList))
			.flatMap(item -> itemReactiveRepository.save(item))
			.subscribe(item -> {
				System.out.println("New item inserted: " + item);
			}
			);
	}

}
