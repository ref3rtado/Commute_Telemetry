package com.github.commutetelemetry.model;


import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="source")
    private Location source;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="destination")
    private Location destination;

    @UpdateTimestamp
    private LocalDateTime lastModified;
}
