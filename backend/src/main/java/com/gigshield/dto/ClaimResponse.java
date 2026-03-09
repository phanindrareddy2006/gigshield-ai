package com.gigshield.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for Claim response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimResponse {
    private Long id;
    private Long workerId;
    private Long policyId;
    private Long disruptionId;
    private String disruptionType;
    private BigDecimal claimAmount;
    private BigDecimal lostHours;
    private BigDecimal hourlyRate;
    private String status;
    private String rejectionReason;
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime approvedAt;
}
