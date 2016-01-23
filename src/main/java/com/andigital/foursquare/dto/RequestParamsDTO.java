package com.andigital.foursquare.dto;

import com.andigital.foursquare.util.Operation;

public class RequestParamsDTO {

	private String location;

	private Integer radius;

	private Integer limit;

	private Operation operation;

	public RequestParamsDTO(String location, Integer radius, Integer limit, Operation operation) {
		this.location = location;
		this.radius = radius;
		this.limit = limit;
		this.operation = operation;
	}

	public String getLocation() {
		return location;
	}

	public Integer getRadius() {
		return radius;
	}

	public Integer getLimit() {
		return limit;
	}

	public Operation getOperation() {
		return operation;
	}
}
