package com.linagora.openup.cqrs.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    private final ObjectMapper objectMapper;

    public JsonTransformer() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public String render(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }
}
