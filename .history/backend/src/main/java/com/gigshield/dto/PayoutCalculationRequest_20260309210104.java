package com.gigshield.dto;

import lombok.*;
import java.math.BigDecimal;

/**
 * DTO for Payout calculation request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayoutCalculationRequest {
    private BigDecimal dailyIncome;
    private BigDecimal workingHours;
    private BigDecimal disruptionDuration; // in hours
}
