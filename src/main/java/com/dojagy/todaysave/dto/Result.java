package com.dojagy.todaysave.dto;

public record Result<T>(
    String status,
    String message,
    T data
) {
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAIL = "fail";
    public static final String STATUS_ERROR = "error";

    public static <T> Result<T> SUCCESS (String message) {
        return buildResponse(STATUS_SUCCESS, message, null);
    }

    public static <T>Result<T> SUCCESS (String message, T data) {
        return buildResponse(STATUS_SUCCESS, message, data);
    }

    public static <T>Result<T> FAILURE (String message) {
        return buildResponse(STATUS_FAIL, message, null);
    }

    public static <T>Result<T> FAILURE (String message, T data) {
        return buildResponse(STATUS_FAIL, message, data);
    }

    public static <T>Result<T> ERROR (String message) {
        return buildResponse(STATUS_ERROR, message, null);
    }

    public static <T>Result<T> ERROR (String message, T data) {
        return buildResponse(STATUS_ERROR, message, data);
    }

    private static <T> Result<T> buildResponse(String status, String message, T data) {
        return new Result<>(status, message, data);
    }
}
