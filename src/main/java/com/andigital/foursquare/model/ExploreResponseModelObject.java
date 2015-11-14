package com.andigital.foursquare.model;

import java.util.List;

public class ExploreResponseModelObject extends AbstractModel{
	
	private String name;
	
	private String contactNumber;
	
	private List<String> address;
	
	private String checkins;

	public ExploreResponseModelObject(String name, String contactNumber, List<String> address, String checkins) {
		this.name = name;
		this.contactNumber = contactNumber;
		this.address = address;
		this.checkins = checkins;
	}

	public String getName() {
		return name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public List<String> getAddress() {
		return address;
	}

	public String getCheckins() {
		return checkins;
	}
}
