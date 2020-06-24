package com.learnreactivespring.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.learnreactivespring.constants.ItemConstants;
import com.learnreactivespring.handler.SampleHandlerFunction;


@Configuration
public class RouterFunctionConfig {
	
	@Autowired
	private SampleHandlerFunction sampleHandlerFunction;
	
	
	@Bean
	public RouterFunction<ServerResponse> serviceRoutes (){
		return RouterFunctions.route(GET("/items").and(accept(MediaType.APPLICATION_JSON)), sampleHandlerFunction::getItems)
				.andRoute(GET(ItemConstants.GET_ALL_ITEMS).and(accept(MediaType.APPLICATION_JSON)), sampleHandlerFunction::getAllItems)
				.andRoute(GET(ItemConstants.GET_ITEM_BY_ID).and(accept(MediaType.APPLICATION_JSON)), sampleHandlerFunction::getItemById)
				.andRoute(POST(ItemConstants.POST_ITEM).and(accept(MediaType.APPLICATION_JSON)), sampleHandlerFunction::createItem);
	}
	
	
}
