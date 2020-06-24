package com.prametermanager.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.prametermanager.handler.ParameterManagerHandler;

@Configuration
public class ParameterManagerRouterFunction {

	@Autowired
	ParameterManagerHandler parameterManagerHandler;
	
	/*@Bean
	public RouterFunction<ServerResponse> parameterManagerServiceRoute (){
		return RouterFunctions.route(GET("/v1/parameterManager/getParameters")
					.and(accept(MediaType.APPLICATION_JSON)), 
					parameterManagerHandler::getAllParameters);
	}*/
}
