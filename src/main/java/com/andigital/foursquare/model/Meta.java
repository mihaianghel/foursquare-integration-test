package com.andigital.foursquare.model;

public class Meta extends AbstractModel{
	
	private int statusCode;
	
	private String error;

	public Meta(int statusCode, String error) {
		this.statusCode = statusCode;
		this.error = error;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getError() {
		return error;
	}

}
