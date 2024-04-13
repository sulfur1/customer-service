package com.iprody08.customerservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJson(Object object) throws Exception {
        return OBJECT_MAPPER.writeValueAsString(object);
    }
}
