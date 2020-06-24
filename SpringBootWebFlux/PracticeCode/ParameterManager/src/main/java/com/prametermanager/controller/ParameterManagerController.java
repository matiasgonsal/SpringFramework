package com.prametermanager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.prametermanager.domain.ParameterDocument;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ParameterManagerController {
	
	WebClient webClient = WebClient.create("http://localhost:8080");
	
	@GetMapping("/v1/parameterManager/getParameters")
	Flux <ParameterDocument> getAllParameters (){
		return webClient.get().uri("/v1/getParameters")
				.retrieve()
				.bodyToFlux(ParameterDocument.class);
	}
	
	@GetMapping ("/v1/parameterManager/getParameters/{id}")
	Mono <ParameterDocument> getParameterById (@PathVariable("id") String id){
		
		return webClient.get().uri("v1/getParameters/{id}", id)
				.retrieve()
				.bodyToMono(ParameterDocument.class);
	}
	
	@PostMapping ("/v1/parameterManager/getParameters")
	Flux <ParameterDocument> returnParameters (@RequestBody List<ParameterDocument> parameterRequest){
		
		Flux<ParameterDocument> requestedParameters = Flux.fromIterable(parameterRequest);
		
		return requestedParameters.flatMap(document -> 
				webClient.get().uri("v1/getParameters/{id}", document.getParameterKey())
				.exchange()
				.flatMap(clientResponse -> {
					if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
						if (document.getParameterDefaultValue() != null && !document.getParameterDefaultValue().isEmpty())
							document.setParameterValue(document.getParameterDefaultValue());						
						return Mono.just(document);
					}
					else {
						return clientResponse.bodyToMono(ParameterDocument.class);
					}
				}));
	}
	
	@PostMapping ("/v1/parameterManager/createParameters")
	Flux <ParameterDocument> createParameters (@RequestBody List<ParameterDocument> parameterRequest){
		Flux<ParameterDocument> requestedParameters = Flux.fromIterable(parameterRequest);
		
		return webClient.post().uri("v1/createParameters")
			.contentType(MediaType.APPLICATION_JSON)
			.body(requestedParameters, ParameterDocument.class)
			.retrieve()
			.bodyToFlux(ParameterDocument.class)
			.log();
	}
	

}
