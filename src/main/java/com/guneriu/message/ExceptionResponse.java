package com.guneriu.message;

import lombok.Data;

/**
 * Created by ugur on 14.05.2016.
 */
@Data
public class ExceptionResponse {

    private String error;

    public boolean isTooManyRequestError() {
        return error.contains("limit");
    }
}
