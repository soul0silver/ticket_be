package com.userservice.DTO;

import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
public class DiscussionDto {
    private Long id;
    private String content;
    private Date create_at;
    private Long uid;
    private String username;
    private Long course_id;
    private Date update_at;
}
