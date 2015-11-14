package com.andigital.foursquare.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.service.FoursquareService;

/**
 * Controller for testing request from broswer
 */
@RestController
public class Controller {
	
	@Autowired
	private FoursquareService foursquareService;
	
	@RequestMapping(value = {"/venues/explore"}, method = RequestMethod.GET, produces = "application/json")
	public Collection<AbstractModel> exploreVenues(
			@RequestParam(value = "location", required = true) String place,
			@RequestParam(value = "radius", required = false) Integer radius,
			@RequestParam(value = "limit", required = false) Integer limit,
			HttpServletRequest request) {
		
		return foursquareService.execute(place, radius, limit);
		
	}

}
