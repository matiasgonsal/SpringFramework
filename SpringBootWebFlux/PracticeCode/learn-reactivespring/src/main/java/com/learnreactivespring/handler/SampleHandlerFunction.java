package com.learnreactivespring.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SampleHandlerFunction {
	
	@Autowired
	ItemReactiveRepository itemReactiveRepository;
	
	private static Mono<ServerResponse> notFound = ServerResponse.notFound().build();
	
	public Mono<ServerResponse> getItems (ServerRequest serverRequest){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(
						Flux.just("Item 1", "Item 2", "Item 3").log(),
						String.class
				);
	}
	
	public Mono<ServerResponse> getAllItems (ServerRequest serverRequest){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(itemReactiveRepository.findAll(), Item.class);
	}
	
	public Mono<ServerResponse> getItemById (ServerRequest serverRequest) {
		
		String id = serverRequest.pathVariable("id");
		Mono <Item> itemMono = itemReactiveRepository.findById(id);
		
		return itemMono.flatMap((item) -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(item))
				.switchIfEmpty(notFound);
		
	}
	
	public Mono<ServerResponse> createItem (ServerRequest serverRequest) {
		
		return ServerResponse.created(null)
				.contentType(MediaType.APPLICATION_JSON)
				.body(serverRequest.bodyToMono(Item.class).flatMap(item -> itemReactiveRepository.save(item)), Item.class);
	}

}
