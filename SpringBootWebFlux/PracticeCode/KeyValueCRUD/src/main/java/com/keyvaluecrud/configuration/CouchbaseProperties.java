package com.keyvaluecrud.configuration;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@Data
public class CouchbaseProperties {
	
	private List<String> bootstrapHosts;
    private String bucketName;
    private String bucketPassword;
 
    public CouchbaseProperties(
      @Value("${spring.couchbase.bootstrap-hosts}") List<String> bootstrapHosts, 
      @Value("${spring.couchbase.bucket.name}") String bucketName, 
      @Value("${spring.couchbase.bucket.password}") String bucketPassword) {
        this.bootstrapHosts = Collections.unmodifiableList(bootstrapHosts);
        this.bucketName = bucketName;
        this.bucketPassword = bucketPassword;
    }

}
