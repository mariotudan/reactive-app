package com.example.reactiveapp.model;

/**
 * Created by mario on 24.6.2017..
 */

public class ResponseModel {
    private Boolean success;
    private String message;

    public ResponseModel(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
