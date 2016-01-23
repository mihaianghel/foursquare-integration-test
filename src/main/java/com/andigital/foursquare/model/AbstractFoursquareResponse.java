package com.andigital.foursquare.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AbstractFoursquareResponse {

    private Meta meta;

    private Response response;

}
