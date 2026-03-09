package com.gigshield.dto;

import lombok.*;
import java.math.BigDecimal;

/**
 * DTO for Claim trigger request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimTriggerRequest {
    private Long workerId;
    private Long policyId;
    private Long disruptionId;
    private String disruptionType;
    private BigDecimal lostHours;
    private String comments;
}
