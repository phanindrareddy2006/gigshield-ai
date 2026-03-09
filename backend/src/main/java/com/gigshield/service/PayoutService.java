package com.gigshield.service;

import com.gigshield.dto.PayoutCalculationRequest;
import com.gigshield.dto.PayoutCalculationResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service class for Payout calculation operations.
 */
@Service
public class PayoutService {

    /**
     * Calculate payout based on daily income and disruption duration.
     * Formula: (dailyIncome / workingHours) * lostHours
     */
    public PayoutCalculationResponse calculatePayout(PayoutCalculationRequest request) {
        // Validate inputs
        if (request.getDailyIncome() == null || request.getDailyIncome().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Daily income must be greater than 0");
        }
        if (request.getWorkingHours() == null || request.getWorkingHours().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Working hours must be greater than 0");
        }
        if (request.getDisruptionDuration() == null || request.getDisruptionDuration().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Disruption duration cannot be negative");
        }

        // Calculate hourly rate
        BigDecimal hourlyRate = request.getDailyIncome()
                .divide(request.getWorkingHours(), 2, RoundingMode.HALF_UP);

        // Calculate estimated payout
        BigDecimal estimatedPayout = hourlyRate.multiply(request.getDisruptionDuration())
                .setScale(2, RoundingMode.HALF_UP);

        return PayoutCalculationResponse.builder()
                .dailyIncome(request.getDailyIncome())
                .workingHours(request.getWorkingHours())
                .hourlyRate(hourlyRate)
                .disruptionDuration(request.getDisruptionDuration())
                .estimatedPayout(estimatedPayout)
                .build();
    }
}
