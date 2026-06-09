package com.github.commutetelemetry.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class ResultSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_inst")
    private Report reportInst;

    private Integer averageTime;

    private Integer lowTime;

    private Integer highTime;

    @CreationTimestamp
    private LocalDateTime dateConcluded;
}
