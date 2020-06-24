package com.keyvaluecrud.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.keyvaluecrud.document.ParameterDocument;
import com.keyvaluecrud.repository.KeyValueCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class KeyValueCrudHandlerFunction {
	
	@Autowired
	KeyValueCrudRepository keyValueCrudRepository;
	
	private static Mono<ServerResponse> notFound = ServerResponse.notFound().build();
	
	public Mono<ServerResponse> createParameters (ServerRequest serverRequest){
		Flux<ParameterDocument> parameterDocumentRequest = 
				serverRequest.bodyToFlux(ParameterDocument.class);
		
				
		return ServerResponse.created(null)
				.contentType(MediaType.APPLICATION_JSON)
				.body(parameterDocumentRequest.flatMap(parameter -> keyValueCrudRepository.save(parameter).log()), ParameterDocument.class);
	}
	
	public Mono<ServerResponse> getParameters (ServerRequest serverRequest){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(keyValueCrudRepository.findAll(), ParameterDocument.class);
	}
	
	public Mono<ServerResponse> getParametersById (ServerRequest serverRequest){
		
		String id = serverRequest.pathVariable("id");
		Mono <ParameterDocument> parameterDocument = keyValueCrudRepository.findById(id);
		
		return parameterDocument.flatMap(document -> ServerResponse.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.bodyValue(document))
						 .switchIfEmpty(notFound);
	}
	
	public Mono<ServerResponse> deleteParameters (ServerRequest serverRequest){
		return ServerResponse.noContent()
				.build(keyValueCrudRepository.deleteAll());
	}
	
	public Mono<ServerResponse> deleteParametersById (ServerRequest serverRequest){
		String id = serverRequest.pathVariable("id");
		Mono <ParameterDocument> parameterDocument = keyValueCrudRepository.findById(id);
		
		return parameterDocument.flatMap(document -> ServerResponse
							.noContent().build(keyValueCrudRepository.deleteById(id)))
				.switchIfEmpty(notFound);
				
	}
}
