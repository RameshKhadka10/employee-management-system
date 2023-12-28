package com.ramesh.employeemanagementsystem.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class RestResponse {

    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("data")
    private Object data;

    public RestResponse(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static SuccessResponseBuilder success() {
        return new SuccessResponseBuilder();
    }

    public static ErrorResponseBuilder error() {
        return new ErrorResponseBuilder();
    }

    public static class SuccessResponseBuilder {
        public RestResponse build(Object data) {
            return new RestResponse(ResponseType.SUCCESS.httpStatus.value(), data);
        }
    }

    public static class ErrorResponseBuilder {
        public RestResponse build(int statusCode, Object data) {
            return new RestResponse(statusCode, data);
        }
    }

    public enum ResponseType {
        SUCCESS(0, HttpStatus.OK),
        INVALID_JWT(1, HttpStatus.UNAUTHORIZED);

        private final int code;
        private final HttpStatus httpStatus;

        ResponseType(int code, HttpStatus httpStatus) {
            this.code = code;
            this.httpStatus = httpStatus;
        }

        public int getCode() {
            return code;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }

}
