package com.event_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
@Builder
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String title;
    private String introduce;
    private String description;
    @Column(name = "start_day")
    private String startDay;
    @Column(name = "start_time")
    private String startTime;
    private String tag;
    private long uid;
    private BigDecimal price;

}
