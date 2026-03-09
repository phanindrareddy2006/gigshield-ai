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
    private Long id;

    // THIS FIELD IS REQUIRED
    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    private String fraudType;

    private Double riskScore;

    @Enumerated(EnumType.STRING)
    private FraudStatus status;

    public enum FraudStatus {
        FLAGGED,
        REVIEWED,
        CLEARED
    }
}