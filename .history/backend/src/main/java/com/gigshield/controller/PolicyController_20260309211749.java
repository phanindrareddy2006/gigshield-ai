package com.gigshield.controller;

import com.gigshield.dto.PolicyCreateRequest;
import com.gigshield.dto.PolicyResponse;
import com.gigshield.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Policy-related endpoints.
 */
@RestController
@RequestMapping("/policies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PolicyController {

    private final PolicyService policyService;

    /**
     * Create a new policy.
     */
    @PostMapping("/create")
    public ResponseEntity<PolicyResponse> createPolicy(@RequestBody PolicyCreateRequest request) {
        PolicyResponse response = policyService.createPolicy(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get policy by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponse> getPolicyById(@PathVariable Long id) {
        PolicyResponse response = policyService.getPolicyById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all policies for a worker.
     */
    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<PolicyResponse>> getPoliciesByWorker(@PathVariable Long workerId) {
        List<PolicyResponse> response = policyService.getPoliciesByWorker(workerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get active policy for a worker.
     */
    @GetMapping("/worker/{workerId}/active")
    public ResponseEntity<PolicyResponse> getActivePolicyByWorker(@PathVariable Long workerId) {
        PolicyResponse response = policyService.getActivePolicyByWorker(workerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Renew a policy.
     */
    @PutMapping("/{id}/renew")
    public ResponseEntity<PolicyResponse> renewPolicy(@PathVariable Long id) {
        PolicyResponse response = policyService.renewPolicy(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Cancel a policy.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelPolicy(@PathVariable Long id) {
        policyService.cancelPolicy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
