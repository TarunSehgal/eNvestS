package com.envest.services.components.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Category {
	private List<String> category;
	private String id;
	
	public Category(List<String> category, String id)
	{
		this.category = category;
		this.id = id;
	}
	
	   public List<String> getCategorys() {
	        return category;
	    }
	   	   
	   public String getId() {
	        return id;
	    }
}
