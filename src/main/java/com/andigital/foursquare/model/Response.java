package com.andigital.foursquare.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class Response {

    private Collection<Group> groups;
}