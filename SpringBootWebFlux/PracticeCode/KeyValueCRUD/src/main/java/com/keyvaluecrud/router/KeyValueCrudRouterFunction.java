package com.keyvaluecrud.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.keyvaluecrud.handler.KeyValueCrudHandlerFunction;
import com.keyvaluecrud.constants.KeyValueCrudConstants;

@Configuration
public class KeyValueCrudRouterFunction {
	
	@Autowired
	KeyValueCrudHandlerFunction keyValueCrudHandlerFunction;
	
	@Bean
	public RouterFunction<ServerResponse> ketValueCrudServiceRoute (){
		return RouterFunctions
				.route(POST(KeyValueCrudConstants.POST_CREATE_PARAMETERS)
					.and(accept(MediaType.APPLICATION_JSON)), 
					keyValueCrudHandlerFunction::createParameters)
				.andRoute(GET(KeyValueCrudConstants.GET_PARAMETERS)
					.and(accept(MediaType.APPLICATION_JSON)), 
					keyValueCrudHandlerFunction::getParameters)
				.andRoute(GET(KeyValueCrudConstants.GET_PARAMETERS_BY_ID)
						.and(accept(MediaType.APPLICATION_JSON)), 
						keyValueCrudHandlerFunction::getParametersById)
				.andRoute(DELETE(KeyValueCrudConstants.DELETE_PARAMETERS)
						.and(accept(MediaType.APPLICATION_JSON)), 
						keyValueCrudHandlerFunction::deleteParameters)
				.andRoute(DELETE(KeyValueCrudConstants.DELETE_PARAMETERS_BY_ID)
						.and(accept(MediaType.APPLICATION_JSON)), 
						keyValueCrudHandlerFunction::deleteParametersById);
	}

}
