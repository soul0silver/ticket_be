package com.userservice.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Summary {
    private Long total;
    private Long active;
    private Long disable;
}
