package com.gigshield.controller;

import com.gigshield.dto.ClaimResponse;
import com.gigshield.dto.ClaimTriggerRequest;
import com.gigshield.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Claim-related endpoints.
 */
@RestController
@RequestMapping("/claims")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClaimController {

    private final ClaimService claimService;

    /**
     * Trigger a new claim.
     */
    @PostMapping("/trigger")
    public ResponseEntity<ClaimResponse> triggerClaim(@RequestBody ClaimTriggerRequest request) {
        ClaimResponse response = claimService.triggerClaim(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get claim by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClaimResponse> getClaimById(@PathVariable Long id) {
        ClaimResponse response = claimService.getClaimById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all claims for a worker.
     */
    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<ClaimResponse>> getClaimsByWorker(@PathVariable Long workerId) {
        List<ClaimResponse> response = claimService.getClaimsByWorker(workerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get pending claims for a worker.
     */
    @GetMapping("/worker/{workerId}/pending")
    public ResponseEntity<List<ClaimResponse>> getPendingClaimsByWorker(@PathVariable Long workerId) {
        List<ClaimResponse> response = claimService.getPendingClaimsByWorker(workerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Approve a claim.
     */
    @PutMapping("/{id}/approve")
    public ResponseEntity<ClaimResponse> approveClaim(@PathVariable Long id, @RequestParam(required = false) String comments) {
        ClaimResponse response = claimService.approveClaim(id, comments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Reject a claim.
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<ClaimResponse> rejectClaim(@PathVariable Long id, @RequestParam String rejectionReason) {
        ClaimResponse response = claimService.rejectClaim(id, rejectionReason);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
