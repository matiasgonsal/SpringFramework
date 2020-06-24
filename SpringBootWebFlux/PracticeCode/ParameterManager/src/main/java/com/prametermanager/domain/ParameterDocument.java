package com.prametermanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterDocument {
	
	private String parameterKey;
	private String parameterValue;
	private String parameterDefaultValue;

}
