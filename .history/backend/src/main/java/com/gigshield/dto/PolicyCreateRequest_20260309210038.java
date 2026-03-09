package com.gigshield.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for Policy creation request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyCreateRequest {
    private Long workerId;
    private String planType;
    private BigDecimal monthlyPremium;
    private LocalDate startDate;
    private LocalDate endDate;
}
