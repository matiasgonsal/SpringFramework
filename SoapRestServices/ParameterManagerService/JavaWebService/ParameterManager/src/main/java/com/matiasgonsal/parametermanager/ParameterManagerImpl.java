package com.matiasgonsal.parametermanager;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.contract.servicecomponents.parametermanager.v1_0.ParameterManagerPortType;
import com.company.schema.servicecomponents.parametermanager.v1_0.GetParameterRequest;
import com.company.schema.servicecomponents.parametermanager.v1_0.GetParameterRequest.Parameter;
import com.company.schema.servicecomponents.parametermanager.v1_0.GetParameterResponse;
import com.company.schema.servicecomponents.parametermanager.v1_0.ObjectFactory;
import com.company.schema.servicecomponents.parametermanager.v1_0.PutParameterRequest;
import com.company.schema.servicecomponents.parametermanager.v1_0.PutParameterResponse;
import com.matiasgonsal.parametermanager.model.ParameterManager;
import com.matiasgonsal.parametermanager.respositories.ParameterManagerRepository;

@Features(features = "org.apache.cxf.ext.logging.LoggingFeature")
@Service
public class ParameterManagerImpl implements ParameterManagerPortType {

	@Autowired
	private ParameterManagerRepository parameterManagerRepo;
	
	@Override
	public GetParameterResponse queryParameterManager(GetParameterRequest parameter) {
		ObjectFactory responseFactory = new ObjectFactory();
		GetParameterResponse getParameterServiceResponse = 
				responseFactory.createGetParameterResponse();
		/*
		 System.out.println("This service version is using regular for each iteration");
		 for (Parameter element : parameter.getParameter()) {
			GetParameterResponse.Parameter responseParameter = responseFactory.createGetParameterResponseParameter();
			
			String result = parameterManagerRepo.
					findValueByCategoryKey(element.getCategory(), element.getKey());
		
			responseParameter.setCategory(element.getCategory());
			responseParameter.setKey(element.getKey());
			responseParameter.setValue(result);
				
			if (result == null) {
				responseParameter.setValue(element.getDefaultValue());
			}
			getParameterServiceResponse.getParameter().add(responseParameter);
		}
		return getParameterServiceResponse;
		*/
		
		//Same implementation with streams
		System.out.println("This service version is using java streams API");
		List<GetParameterResponse.Parameter> responseParameters = parameter.getParameter().stream()
			.map(requestParameter -> {
				GetParameterResponse.Parameter responseParameter = responseFactory.createGetParameterResponseParameter();
				ParameterManager result = parameterManagerRepo.
						findValueByCategoryKey(requestParameter.getCategory(), requestParameter.getKey());
				
				responseParameter.setCategory(requestParameter.getCategory());
				responseParameter.setKey(requestParameter.getKey());
				
				if (result != null) {
					responseParameter.setValue(result.getParameter_value());
					return responseParameter;
				}
				
				responseParameter.setValue(requestParameter.getDefaultValue());
				return responseParameter;
				
			}).collect(Collectors.toList());
		
		getParameterServiceResponse.getParameter().addAll(responseParameters);
		return getParameterServiceResponse;
	}

	@Override
	public PutParameterResponse createParameterManager(PutParameterRequest parameter) {
		ObjectFactory responseFactory = new ObjectFactory();
		
		ParameterManager parameterManageEntity = new ParameterManager();
		
		parameterManageEntity.setParameter_category(parameter.getCategory());
		parameterManageEntity.setParameter_key(parameter.getKey());
		parameterManageEntity.setParameter_value(parameter.getValue());
		
		
		ParameterManager result = parameterManagerRepo.findValueByCategoryKey(
				parameterManageEntity.getParameter_category(), parameterManageEntity.getParameter_key());
		
		//Setting id when the entry exists to perform an upsert.
		if (result != null) {
			parameterManageEntity.setId(result.getId());
		}
		
		parameterManagerRepo.save(parameterManageEntity);
		
		PutParameterResponse putParameterResponse = responseFactory.createPutParameterResponse();
		putParameterResponse.setCreated(true);
		return putParameterResponse;
	}

}
