package com.event_management.controller;

import com.event_management.DTO.response.BaseResponse;
import com.event_management.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleException(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({BusinessException.class})
    ResponseEntity<?> handleBusinessEx(BusinessException ex){
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));
    }
}
