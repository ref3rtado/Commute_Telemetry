package com.github.commutetelemetry.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class TimeWindow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long window_id;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_window")
    private Report reportWindow;

}
