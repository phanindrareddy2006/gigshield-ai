package com.gigshield.dto;

import lombok.*;
import java.math.BigDecimal;

/**
 * DTO for Payout calculation response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayoutCalculationResponse {
    private BigDecimal dailyIncome;
    private BigDecimal workingHours;
    private BigDecimal hourlyRate;
    private BigDecimal disruptionDuration;
    private BigDecimal estimatedPayout;
}
