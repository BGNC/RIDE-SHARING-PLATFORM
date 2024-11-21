package com.bgnc.Uber_Clone_Backend.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
@Setter
@JsonInclude(value = NON_NULL)
public class RootEntity<T> {
    private T data;
    private String errorMessage;
    private Integer status;


    public static <T>  RootEntity<T> ok (T data) {
        RootEntity<T> rootEntity = new RootEntity<T>();
        rootEntity.setData(data);
        rootEntity.setErrorMessage(null);
        rootEntity.setStatus(HttpStatus.OK.value());
        return rootEntity;
    }

    public static <T>  RootEntity<T> error (String errorMessage) {
        RootEntity<T> rootEntity = new RootEntity<T>();
        rootEntity.setData(null);
        rootEntity.setErrorMessage(errorMessage);
        rootEntity.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return rootEntity;
    }
}