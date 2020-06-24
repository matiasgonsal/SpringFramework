package com.keyvaluecrud.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.keyvaluecrud.document.ParameterDocument;

@Repository
@ViewIndexed(designDoc = "parameterDocument")
public interface KeyValueCrudRepository extends ReactiveCouchbaseRepository<ParameterDocument, String>{
	
}
