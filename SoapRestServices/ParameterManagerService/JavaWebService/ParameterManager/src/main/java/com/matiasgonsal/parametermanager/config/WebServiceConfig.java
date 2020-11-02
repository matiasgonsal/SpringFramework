package com.matiasgonsal.parametermanager.config;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.dom.WSConstants;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.matiasgonsal.parametermanager.ParameterManagerImpl;

@Configuration
public class WebServiceConfig {
	
	@Autowired
	private Bus serviceBus;
	
	@Autowired
	private ParameterManagerImpl parameterManagerImpl;
	
	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(serviceBus, parameterManagerImpl);
		endpoint.publish("/ParameterManager");
		
		//Implement WS-Security:
		Map<String, Object> wssProperties = new HashMap<>();
		wssProperties.put(ConfigurationConstants.ACTION, ConfigurationConstants.USERNAME_TOKEN);
		wssProperties.put(ConfigurationConstants.PASSWORD_TYPE, WSConstants.PASSWORD_TEXT);
		wssProperties.put(ConfigurationConstants.PW_CALLBACK_CLASS, UTPasswordCallback.class.getName());
		
		WSS4JInInterceptor wssInterceptor = new WSS4JInInterceptor();
		wssInterceptor.setProperties(wssProperties);
		
		endpoint.getInInterceptors().add(wssInterceptor);
		return endpoint;
	}
	
	@Bean
	public LoggingFeature loggingFeature() {
		LoggingFeature loggingFeature = new LoggingFeature();
	    return loggingFeature;
	    
	}

}
