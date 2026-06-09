package com.github.commutetelemetry.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="route_used")
    private Route routeUsed;

    private boolean active;

    private LocalDate startDate;

    private LocalDate endDate;

    @UpdateTimestamp
    private LocalDateTime lastModified;
}
