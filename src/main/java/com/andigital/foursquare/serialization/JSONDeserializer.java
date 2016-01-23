package com.andigital.foursquare.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json deserializers
 */
public class JSONDeserializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSONDeserializer.class);

    private static final Gson GSON = new Gson();

    /**
     * Deserialise Json
     * @param json string representation of the json
     * @param clazz return type
     * @return type T object
     */
    public static <T> T fromString(final String json, final Class<T> clazz) {
        if (StringUtils.isNotBlank(json)) {
            try {
                final JsonElement jsonElement = new JsonParser().parse(json);
                return GSON.fromJson(jsonElement, clazz);
            } catch (JsonSyntaxException e) {
                LOGGER.error("Failed to parse string", e);
            }
        }
        return null;
    }

}
