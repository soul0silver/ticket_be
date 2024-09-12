package com.event_management.exception;

public class BusinessException extends RuntimeException{
    private String ex;
    public BusinessException(String ex){
        this.ex = ex;
    }

}
