package com.matiasgonsal.parametermanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ParameterManager {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	private String parameter_category;
	private String parameter_key;
	private String parameter_value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getParameter_category() {
		return parameter_category;
	}

	public void setParameter_category(String parameter_category) {
		this.parameter_category = parameter_category;
	}

	public String getParameter_key() {
		return parameter_key;
	}

	public void setParameter_key(String parameter_key) {
		this.parameter_key = parameter_key;
	}

	public String getParameter_value() {
		return parameter_value;
	}

	public void setParameter_value(String parameter_value) {
		this.parameter_value = parameter_value;
	}

}
