package com.bgnc.Uber_Clone_Backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ApiError<E> {
    private int status;

    private Exception<E> exception;

}
