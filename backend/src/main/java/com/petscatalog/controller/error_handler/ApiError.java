package com.petscatalog.controller.error_handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tlegen on 11.01.2018.
 */
@Getter
@Setter
public class ApiError {
    private HttpStatus status;
    private String message;
    private int errorCode;

    public ApiError(HttpStatus status, String message, int errorCode) {
        super();
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }

}
