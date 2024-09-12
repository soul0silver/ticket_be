package com.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "classroom")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String link;
    private Long course_id;
    private String name;
    private Double start_at;
    private Double end_at;
    private Date create_at;

}
