package com.userservice.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormReg {
    private String username;
    private String email;
    private String password;
    private int gender;
    private String phone;
    private Date birthday;
    private String school;
    private String major;
    private int degree;
    private String achievement;
    private String address;
    private Date create_at;
    private Date update_at;
    private Date delete_at;
    private boolean active;
    private int[] role;
}
