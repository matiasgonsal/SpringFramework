package com.keyvaluecrud.couchbase.test;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.keyvaluecrud.document.ParameterDocument;
import com.keyvaluecrud.repository.KeyValueCrudRepository;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext
public class CouchBaseTest {
	
	@Autowired
	KeyValueCrudRepository keyValueCrudRepository;
	
	
	@BeforeEach
	public void setUpCouchbaseBucket () {
		
		List<ParameterDocument> parameterDocument = Arrays.asList(
				new ParameterDocument("parameterKey1", "1"),
				new ParameterDocument("parameterKey2", "2"));
		
		Flux<ParameterDocument> parameterDocumentFlux = Flux.fromIterable(parameterDocument);
		parameterDocumentFlux
			.flatMap(parameterItem -> keyValueCrudRepository.save(parameterItem).log())
			.subscribe();
	}
	
	@AfterEach
	public void cleanCouchBaseBucket() {
		keyValueCrudRepository.deleteAll()
			.subscribe();
	}
	
	
	@Test
	public void getAllParameterDocuments () {
		
		Flux <ParameterDocument> parameterDocument = keyValueCrudRepository.findAll();
		StepVerifier.create(parameterDocument.log())
			.expectSubscription()
			.expectNextCount(2)
			.verifyComplete();
		
	}

}
