package com.andigital.foursquare.serialization.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.model.Meta;
import com.andigital.foursquare.serialization.JSONDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static com.andigital.foursquare.util.Constants.*;

@Component
public class JSONDeserializerImpl implements JSONDeserializer<AbstractModel> {

	@Override
	public JsonObject deserialise(String json) {
		if (StringUtils.isNotBlank(json)) {
			JsonElement jelement = new JsonParser().parse(json);
		    return jelement.getAsJsonObject();
		} else {
			return null;
		}
	}

	@Override
	public AbstractModel unmarshallMeta(JsonObject meta) {
		if (meta != null) {
			int statusCode = meta.get(CODE).getAsInt();
			String error = meta.get(ERROR) != null ? meta.get(ERROR).getAsString() : null;
			return new Meta(statusCode, error);
		} else {
			return null;
		}
	}

	@Override
	public List<AbstractModel> unmarshallResponse(JsonObject response) {
		// TODO Auto-generated method stub
		return null;
	}
}
