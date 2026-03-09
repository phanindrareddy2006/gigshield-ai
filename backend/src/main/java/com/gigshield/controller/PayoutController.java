package com.gigshield.controller;

import com.gigshield.dto.PayoutCalculationRequest;
import com.gigshield.dto.PayoutCalculationResponse;
import com.gigshield.service.PayoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Payout calculation endpoints.
 */
@RestController
@RequestMapping("/payout")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PayoutController {

    private final PayoutService payoutService;

    /**
     * Calculate payout based on income and disruption duration.
     */
    @PostMapping("/calculate")
    public ResponseEntity<PayoutCalculationResponse> calculatePayout(@RequestBody PayoutCalculationRequest request) {
        PayoutCalculationResponse response = payoutService.calculatePayout(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
