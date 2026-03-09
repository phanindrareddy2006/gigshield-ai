package com.gigshield.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Disruption entity representing external disruptions like rain, pollution, curfews, etc.
 */
@Entity
@Table(name = "disruptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disruption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String type; // RAIN, POLLUTION, CURFEW, ACCIDENT, NATURAL_DISASTER

    @Column(nullable = false, length = 50)
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(precision = 5, scale = 2)
    private BigDecimal affectedHours;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DisruptionStatus status = DisruptionStatus.ACTIVE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum DisruptionStatus {
        ACTIVE, RESOLVED
    }
}
