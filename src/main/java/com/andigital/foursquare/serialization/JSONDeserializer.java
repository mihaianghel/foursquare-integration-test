package com.andigital.foursquare.serialization;

import java.util.List;
import com.google.gson.JsonObject;

/**
 * Interface for Json deserializers
 */
public interface JSONDeserializer<T> {
	
	JsonObject deserialise();
	
	T unmarshallMeta();
	
	List<T> unmarshallResponse();

}
