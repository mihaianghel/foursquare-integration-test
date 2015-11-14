package com.andigital.foursquare.model;

public class RequestModelObject {
	
	private String location;
	
	private Integer radius;
	
	private Integer limit;

	public RequestModelObject(String location, Integer radius, Integer limit) {
		super();
		this.location = location;
		this.radius = radius;
		this.limit = limit;
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
	
}
