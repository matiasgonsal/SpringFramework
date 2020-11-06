package com.matiasgonsal.parametermanager;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.company.schema.servicecomponents.parametermanager.v1_0.GetParameterRequest;
import com.company.schema.servicecomponents.parametermanager.v1_0.GetParameterResponse;
import com.company.schema.servicecomponents.parametermanager.v1_0.ObjectFactory;

@SpringBootTest
class ParameterManagerApplicationTests {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ParameterManagerImpl parameterManagerService;

	@Test
	void contextLoads() {
		logger.info("Context was loaded... Running JUnit Testing...");
	}
	
	@Test
	void queryParameterManager() {
		ObjectFactory responseFactory = new ObjectFactory();
		GetParameterRequest.Parameter parameter = responseFactory.createGetParameterRequestParameter();
		parameter.setCategory("LEGACY_SERVICE");
		parameter.setKey("siebel");
		parameter.setDefaultValue("default");
		
		GetParameterRequest getParameterRequest = responseFactory.createGetParameterRequest();
		getParameterRequest.getParameter().add(parameter);
		
		GetParameterResponse response = parameterManagerService.queryParameterManager(getParameterRequest);
		
		//if the response is not "default" means that we received a parameter response from the db.
		assertNotEquals(response.getParameter().get(0).getValue(), "default");
	}

}
