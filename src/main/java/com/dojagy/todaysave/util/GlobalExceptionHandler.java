package com.dojagy.todaysave.util;

import com.dojagy.todaysave.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 1. 발생한 모든 필드 에러를 가져옵니다.
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // 2. 우리가 만든 Result 레코드를 사용하여 응답 본문을 생성합니다.
        //    여러 에러 중 첫 번째 에러 메시지를 대표로 사용하거나, 모든 에러를 data에 담을 수 있습니다.
        //    여기서는 data에 모든 에러 맵을 담아보겠습니다.
        String firstErrorMessage = errors.values().iterator().next(); // 첫 번째 에러 메시지-

        return Result.FAILURE(firstErrorMessage);
    }
}
