package com.gigshield.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for Policy response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyResponse {
    private Long id;
    private Long workerId;
    private String planType;
    private BigDecimal monthlyPremium;
    private BigDecimal monthlyPayoutLimit;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private BigDecimal totalPayedOut;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
