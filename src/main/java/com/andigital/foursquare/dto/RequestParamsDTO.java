package com.andigital.foursquare.dto;

import com.andigital.foursquare.util.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RequestParamsDTO {

	private String location;

	private Integer radius;

	private Integer limit;

	private Operation operation;
}
