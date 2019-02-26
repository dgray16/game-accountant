package com.avid.web.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;

@AllArgsConstructor
public class ServerRequestConverter {

    ObjectMapper objectMapper;

    @SneakyThrows
    public <T> T mapQueryParams(ServerRequest serverRequest, Class dtoType) {
        MultiValueMap<String, String> queryParams = serverRequest.queryParams();
        String json = objectMapper.writeValueAsString(queryParams.toSingleValueMap());
        return (T) objectMapper.readValue(json, dtoType);
    }

}
