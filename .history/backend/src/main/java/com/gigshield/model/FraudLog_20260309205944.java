package com.gigshield.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * FraudLog entity for tracking and logging potential fraud activities.
 */
@Entity
@Table(name = "fraud_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FraudLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id")
    private Claim claim;

    @Column(nullable = false, length = 100)
    private String fraudType; // DUPLICATE_CLAIM, SUSPICIOUS_PATTERN, etc.

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, precision = 5, scale = 2)
    private Double riskScore; // 0-100 scale

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FraudStatus status = FraudStatus.FLAGGED;

    @Column(length = 500)
    private String investigationNotes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column
    private LocalDateTime resolvedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum FraudStatus {
        FLAGGED, INVESTIGATING, CONFIRMED, CLEARED, REPORTED
    }
}
