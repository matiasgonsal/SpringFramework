package com.prametermanager.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.prametermanager.domain.ParameterDocument;

import reactor.core.publisher.Mono;


@Component
public class ParameterManagerHandler {

	WebClient webClient = WebClient.create("http://localhost:8080");
			
	
	public Mono <ServerResponse> getAllParameters (ServerRequest serverReuest){
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(webClient.get().uri("/v1/getParameters")
						.retrieve()
						.bodyToMono(ParameterDocument.class), ParameterDocument.class);
		
	}
	
	
}
