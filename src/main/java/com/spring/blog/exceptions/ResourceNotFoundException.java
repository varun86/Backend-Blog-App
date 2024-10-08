package com.spring.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException  extends RuntimeException{

	String resourceNamee;
	String fieldName;
	long fieldValue;
	
	
	public ResourceNotFoundException(String resourceNamee, String fieldName, long fieldValue) {
		super(String.format("%s not found  with %s :%s", resourceNamee,fieldName,fieldValue));
		this.resourceNamee = resourceNamee;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
}
