package com.gigshield.service;

import com.gigshield.dto.PolicyCreateRequest;
import com.gigshield.dto.PolicyResponse;
import com.gigshield.model.Policy;
import com.gigshield.model.Worker;
import com.gigshield.repository.PolicyRepository;
import com.gigshield.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Policy-related operations.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final WorkerRepository workerRepository;

    private static final BigDecimal BASIC_LIMIT = new BigDecimal("10000");
    private static final BigDecimal STANDARD_LIMIT = new BigDecimal("25000");
    private static final BigDecimal PRO_LIMIT = new BigDecimal("50000");

    /**
     * Create a new policy for a worker.
     */
    public PolicyResponse createPolicy(PolicyCreateRequest request) {
        Worker worker = workerRepository.findById(request.getWorkerId())
                .orElseThrow(() -> new RuntimeException("Worker not found with ID: " + request.getWorkerId()));

        BigDecimal payoutLimit = getPayoutLimitByPlanType(request.getPlanType());

        LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : LocalDate.now();
        LocalDate endDate = request.getEndDate() != null ? request.getEndDate() : startDate.plusMonths(1);

        Policy policy = Policy.builder()
                .worker(worker)
                .planType(request.getPlanType())
                .monthlyPremium(request.getMonthlyPremium())
                .monthlyPayoutLimit(payoutLimit)
                .startDate(startDate)
                .endDate(endDate)
                .status(Policy.PolicyStatus.ACTIVE)
                .totalPayedOut(BigDecimal.ZERO)
                .build();

        Policy savedPolicy = policyRepository.save(policy);
        return mapToResponse(savedPolicy);
    }

    /**
     * Get policy by ID.
     */
    @Transactional(readOnly = true)
    public PolicyResponse getPolicyById(Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + id));
        return mapToResponse(policy);
    }

    /**
     * Get all policies for a worker.
     */
    @Transactional(readOnly = true)
    public List<PolicyResponse> getPoliciesByWorker(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found with ID: " + workerId));
        return policyRepository.findByWorker(worker).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get active policy for a worker.
     */
    @Transactional(readOnly = true)
    public PolicyResponse getActivePolicyByWorker(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found with ID: " + workerId));
        Policy policy = policyRepository.findFirstByWorkerAndStatusOrderByCreatedAtDesc(
                        worker, Policy.PolicyStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("No active policy found for worker: " + workerId));
        return mapToResponse(policy);
    }

    /**
     * Renew a policy.
     */
    public PolicyResponse renewPolicy(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + policyId));

        policy.setEndDate(policy.getEndDate().plusMonths(1));
        policy.setStatus(Policy.PolicyStatus.ACTIVE);

        Policy renewedPolicy = policyRepository.save(policy);
        return mapToResponse(renewedPolicy);
    }

    /**
     * Cancel a policy.
     */
    public void cancelPolicy(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + policyId));
        policy.setStatus(Policy.PolicyStatus.CANCELLED);
        policyRepository.save(policy);
    }

    /**
     * Get payout limit based on plan type.
     */
    private BigDecimal getPayoutLimitByPlanType(String planType) {
        switch (planType.toUpperCase()) {
            case "BASIC":
                return BASIC_LIMIT;
            case "STANDARD":
                return STANDARD_LIMIT;
            case "PRO":
                return PRO_LIMIT;
            default:
                throw new IllegalArgumentException("Invalid plan type: " + planType);
        }
    }

    /**
     * Map Policy entity to Response DTO.
     */
    private PolicyResponse mapToResponse(Policy policy) {
        return PolicyResponse.builder()
                .id(policy.getId())
                .workerId(policy.getWorker().getId())
                .planType(policy.getPlanType())
                .monthlyPremium(policy.getMonthlyPremium())
                .monthlyPayoutLimit(policy.getMonthlyPayoutLimit())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .status(policy.getStatus().toString())
                .totalPayedOut(policy.getTotalPayedOut())
                .createdAt(policy.getCreatedAt())
                .updatedAt(policy.getUpdatedAt())
                .build();
    }
}
