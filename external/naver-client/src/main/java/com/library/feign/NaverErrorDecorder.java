package com.library.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.NaverErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NaverErrorDecorder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    public NaverErrorDecorder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String s, Response response) {
        try {
            String body = new String(response.body().asInputStream().readAllBytes(),  StandardCharsets.UTF_8);
            NaverErrorResponse naverErrorResponse = objectMapper.readValue(body, NaverErrorResponse.class);
            throw new RuntimeException(naverErrorResponse.getErrorMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
