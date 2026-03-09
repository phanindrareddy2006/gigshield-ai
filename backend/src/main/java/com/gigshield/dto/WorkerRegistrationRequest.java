package com.gigshield.dto;

import lombok.*;
import java.math.BigDecimal;

/**
 * DTO for Worker registration request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerRegistrationRequest {
    private String name;
    private String phone;
    private String platform;
    private String city;
    private BigDecimal averageDailyIncome;
    private BigDecimal workingHours;
    private String address;
}
