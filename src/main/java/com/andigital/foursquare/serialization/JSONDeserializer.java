package com.andigital.foursquare.serialization;

import java.util.List;
import com.google.gson.JsonObject;

/**
 * Interface for Json deserializers
 */
public interface JSONDeserializer<T> {
	
	/**
	 * Deserialize a json string
	 * @param json object to deserialize
	 * @return JSON representation as {@link JsonObject}
	 */
	JsonObject deserialise(String json);
	
	/**
	 * Unmarshall 'meta' section of the response
	 * @param meta section represented as {@link JsonObject}
	 * @return object of generic type T
	 */
	T unmarshallMeta(JsonObject meta);
	
	/**
	 * Unmarshall 'response' section of the response
	 * @param response section represented as {@link JsonObject}
	 * @return list of objects of generic type T
	 */
	List<T> unmarshallResponse(JsonObject response);

}
