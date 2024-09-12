package com.event_management.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private int code;
    private String message;
    private Object data;

    public static BaseResponse of(int code, String message, Object data){
        return new BaseResponse(code, message, data);
    }
}
