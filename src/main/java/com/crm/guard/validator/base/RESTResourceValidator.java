package com.crm.guard.validator.base;


import com.crm.guard.exception.BadRequestException;
import com.crm.guard.exception.ResourceNotFoundException;

public final class RESTResourceValidator {
	
	public static <T> T checkNotNull(T entity) {
		if(entity == null) {
			throw new ResourceNotFoundException("Entity not found");
		}
		return entity;
	}
	
	public static <T> T checkRequestResourceNotNull(T entity) {
		if(entity == null) {
			throw new BadRequestException("Bad request");
		}
		return entity;
	}
	
}
