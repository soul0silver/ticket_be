package com.userservice.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedBackRequest {
    private String content;
    private int star;
    private Long oddt_id;
}
