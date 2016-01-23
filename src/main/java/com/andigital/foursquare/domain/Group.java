package com.andigital.foursquare.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class Group {

    private Collection<Item> items;
}
