package com.jmgits.sample.audit.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jmgits.sample.audit.exception.GeneralException;

import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.USE_ANNOTATIONS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public final class JsonUtils {

    private static final TypeReference<HashMap<String, Object>> TYPE_REF = new TypeReference<HashMap<String, Object>>() { };
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new JavaTimeModule());

        MAPPER.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.configure(USE_ANNOTATIONS, false);

        MAPPER.setSerializationInclusion(ALWAYS);
    }

    private JsonUtils() {
    }

    public static <T> T read(String value, Class<T> clazz) {
        try {
            return MAPPER.readValue(value, clazz);
        } catch (Exception e) {
            throw new GeneralException(String.format("Could not read value '%s' as '%s'", value, clazz.getSimpleName()), e);
        }
    }

    public static <T> T read(String value, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(value, typeReference);
        } catch (Exception e) {
            throw new GeneralException(String.format("Could not read value '%s' as '%s'", value, typeReference), e);
        }
    }

    public static Map<String, Object> readAsMap(String value) {

        return JsonUtils.read(value, TYPE_REF);
    }

    public static String write(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (Exception e) {
            throw new GeneralException(String.format("Could not write value '%s'", value.toString()), e);
        }
    }
}
