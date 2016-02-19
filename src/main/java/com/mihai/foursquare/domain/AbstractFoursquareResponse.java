package com.mihai.foursquare.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AbstractFoursquareResponse {

    private Meta meta;

    private Response response;

}
