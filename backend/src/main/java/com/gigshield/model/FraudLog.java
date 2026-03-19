package com.gigshield.model;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(name = "fraud_type")
    private String fraudType;

    @Column(name = "risk_score")
    private Double riskScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FraudStatus status;

    public enum FraudStatus {
        FLAGGED,
        REVIEWED,
        CLEARED
    }
}