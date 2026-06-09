package com.github.commutetelemetry.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Commute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commuteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commute_report")
    private Report commuteReport;

    private LocalDateTime callTime;

    private Integer commuteLen;

}
