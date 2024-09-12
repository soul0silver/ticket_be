package com.userservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
    private String email;
    private String password;
    private String confirmpassword;
    private String token;
}
