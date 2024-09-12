package com.userservice.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    private long senderId;
    private long receiverId;
    private String message;
}