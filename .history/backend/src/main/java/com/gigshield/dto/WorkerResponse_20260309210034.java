package com.gigshield.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for Worker response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerResponse {
    private Long id;
    private String name;
    private String phone;
    private String platform;
    private String city;
    private BigDecimal averageDailyIncome;
    private BigDecimal workingHours;
    private String address;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
