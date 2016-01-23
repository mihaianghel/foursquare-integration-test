package com.andigital.foursquare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class ExploreResponseDTO extends ResponseDTO {

    private String name;

    private String contactNumber;

    private Collection<String> address;

    private int checkins;
}
