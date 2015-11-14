package com.andigital.foursquare.serialization.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.model.ExploreResponseModelObject;
import com.andigital.foursquare.model.Meta;
import com.andigital.foursquare.serialization.JSONDeserializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static com.andigital.foursquare.util.Constants.*;

@Component
public class JSONDeserializerImpl implements JSONDeserializer<AbstractModel> {

	@Override
	public JsonObject deserialise(String json) {
		if (StringUtils.isNotBlank(json)) {
			final JsonElement jelement = new JsonParser().parse(json);
		    return jelement.getAsJsonObject();
		} else {
			return null;
		}
	}

	@Override
	public AbstractModel unmarshallMeta(JsonObject json) {
		if (json != null) {
			JsonObject meta = json.getAsJsonObject(META);
			int statusCode = meta.get(CODE).getAsInt();
			final String error = meta.get(ERROR) != null ? meta.get(ERROR).getAsString() : null;
			return new Meta(statusCode, error);
		} else {
			return null;
		}
	}

	@Override
	public List<AbstractModel> unmarshallResponse(JsonObject json) {
		if (json != null) {
			JsonObject response = json.getAsJsonObject(RESPONSE);
			final JsonArray groups = response.getAsJsonArray(GROUPS);
			final Iterator<JsonElement> venuesIterator = groups.iterator();
			final List<AbstractModel> result = new LinkedList<>();
			
			while (venuesIterator.hasNext()) {
				final JsonObject group = venuesIterator.next().getAsJsonObject();
				final String type = group.get(TYPE).getAsString();
				if (RECOMMENDED_PLACES.equals(type)) {
					final JsonArray items = group.getAsJsonArray(ITEMS);
					final Iterator<JsonElement> itemsiterator = items.iterator();
					
					while (itemsiterator.hasNext()) {
						final JsonObject item = itemsiterator.next().getAsJsonObject();
						final JsonObject venue = item.getAsJsonObject(VENUE);
						//get the name of the location
						final String name = venue.get(NAME).getAsString();
						//get address of the location
						final List<String> address = getAddress(venue);
						//get contact number
						final String contactNumber = getContactNumber(venue);
						//get checkin count
						final String checkins = getCheckinCount(venue);
						
						ExploreResponseModelObject venueResponse = new ExploreResponseModelObject(name, contactNumber, address, checkins);
						result.add(venueResponse);
					}
				}
			}
			return result;
		} else {
			return null;
		}
	}

	private List<String> getAddress(final JsonObject venue) {
		final JsonArray addressLines = venue.getAsJsonObject(LOCATION).get(FORMATTED_ADDRESS).getAsJsonArray();
		final List<String> address = new ArrayList<>(addressLines.size());
		final Iterator<JsonElement> addressIterator = addressLines.iterator();
		while (addressIterator.hasNext()) {
			address.add(addressIterator.next().getAsString());
		}
		return address;
	}
	
	private String getContactNumber(final JsonObject venue) {
		String contactNumber = "-";
		final JsonObject contact = venue.getAsJsonObject(CONTACT);
		if (contact != null) {
			 final JsonElement phoneNumber = contact.get(FORMATTED_PHONE);
			 if (phoneNumber != null) {
				 contactNumber = phoneNumber.getAsString();
			 }
		}
		return contactNumber;
	}
	
	private String getCheckinCount(final JsonObject venue) {
		String checkinCount = "-";
		final JsonObject stats = venue.getAsJsonObject(STATS);
		if (stats != null) {
			 final JsonElement checkins = stats.get(CHECKIN_COUNT);
			 if (checkins != null) {
				 checkinCount = checkins.getAsString();
			 }
		}
		return checkinCount;
	}
}
