package com.craftsoft.main.handler;

import com.craftsoft.main.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*
     * TODO
     * We can implement around here other code errors
     * */
    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<Object> handleGenericException(Exception e) {
        // Log the error or do something with it
        ApiResponse<String> response = new ApiResponse<>("error",
                "An unexpected error occurred: " + e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
