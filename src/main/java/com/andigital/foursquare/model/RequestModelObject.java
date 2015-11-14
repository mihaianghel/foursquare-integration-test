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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((limit == null) ? 0 : limit.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((radius == null) ? 0 : radius.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestModelObject other = (RequestModelObject) obj;
		if (limit == null) {
			if (other.limit != null)
				return false;
		} else if (!limit.equals(other.limit))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (radius == null) {
			if (other.radius != null)
				return false;
		} else if (!radius.equals(other.radius))
			return false;
		return true;
	}
}
