package com.andigital.foursquare.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
public class Venue {

    private String name;

    private Contact contact;

    private Stats stats;

    private Location location;

    @AllArgsConstructor
    @Getter
    private class Contact {

        private String formattedPhone;
    }

    @AllArgsConstructor
    @Getter
    private class Stats {

        private int checkinsCount;
    }

    @AllArgsConstructor
    @Getter
    public class Location {

        private Collection<String> formattedAddress;
    }

    public String getName() {
        return name;
    }

    public String getFormattedPhone() {
        return contact.getFormattedPhone();
    }

    public int getCheckinsCount() {
        return stats.getCheckinsCount();
    }

    public Collection<String> getAddress() {
        return location.getFormattedAddress();
    }

}
