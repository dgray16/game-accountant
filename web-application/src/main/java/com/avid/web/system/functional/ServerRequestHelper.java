package com.avid.web.system.functional;

import com.avid.web.system.exception.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@AllArgsConstructor
public class ServerRequestHelper {

    ObjectMapper objectMapper;
    Validator validator;

    @SneakyThrows
    public <T> T mapQueryParams(ServerRequest serverRequest, Class dtoType) {
        MultiValueMap<String, String> queryParams = serverRequest.queryParams();
        String json = objectMapper.writeValueAsString(queryParams.toSingleValueMap());
        return (T) objectMapper.readValue(json, dtoType);
    }

    @SneakyThrows
    public <T> T mapQueryParamsWithValidation(ServerRequest serverRequest, Class dtoType) {
        T dto = mapQueryParams(serverRequest, dtoType);

        Set<ConstraintViolation<T>> validationErrors = validator.validate(dto);

        if (CollectionUtils.isNotEmpty(validationErrors)) {
            ConstraintViolation<T> fistError = Iterables.getFirst(validationErrors, null);
            String message = String.format("%s : %s", fistError.getPropertyPath().toString(), fistError.getMessage());
            throw new BadRequestException(message);
        }

        return dto;
    }

}
