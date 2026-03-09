package com.gigshield.service;

import com.gigshield.dto.ClaimResponse;
import com.gigshield.dto.ClaimTriggerRequest;
import com.gigshield.model.Claim;
import com.gigshield.model.Policy;
import com.gigshield.model.Worker;
import com.gigshield.repository.ClaimRepository;
import com.gigshield.repository.PolicyRepository;
import com.gigshield.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Claim-related operations.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final WorkerRepository workerRepository;
    private final PolicyRepository policyRepository;

    /**
     * Trigger a new claim.
     */
    public ClaimResponse triggerClaim(ClaimTriggerRequest request) {
        Worker worker = workerRepository.findById(request.getWorkerId())
                .orElseThrow(() -> new RuntimeException("Worker not found with ID: " + request.getWorkerId()));

        Policy policy = policyRepository.findById(request.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + request.getPolicyId()));

        // Calculate claim amount
        BigDecimal hourlyRate = worker.getAverageDailyIncome()
                .divide(worker.getWorkingHours(), BigDecimal.ROUND_HALF_UP);
        BigDecimal claimAmount = hourlyRate.multiply(request.getLostHours());

        // Check if claim exceeds monthly limit
        if (claimAmount.compareTo(policy.getMonthlyPayoutLimit()) > 0) {
            claimAmount = policy.getMonthlyPayoutLimit();
        }

        Claim claim = Claim.builder()
                .worker(worker)
                .policy(policy)
                .disruptionType(request.getDisruptionType())
                .claimAmount(claimAmount)
                .lostHours(request.getLostHours())
                .hourlyRate(hourlyRate)
                .status(Claim.ClaimStatus.PENDING)
                .comments(request.getComments())
                .build();

        Claim savedClaim = claimRepository.save(claim);
        return mapToResponse(savedClaim);
    }

    /**
     * Get claim by ID.
     */
    @Transactional(readOnly = true)
    public ClaimResponse getClaimById(Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found with ID: " + id));
        return mapToResponse(claim);
    }

    /**
     * Get all claims for a worker.
     */
    @Transactional(readOnly = true)
    public List<ClaimResponse> getClaimsByWorker(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found with ID: " + workerId));
        return claimRepository.findByWorker(worker).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get pending claims for a worker.
     */
    @Transactional(readOnly = true)
    public List<ClaimResponse> getPendingClaimsByWorker(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found with ID: " + workerId));
        return claimRepository.findByWorkerAndStatus(worker, Claim.ClaimStatus.PENDING).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Approve a claim.
     */
    public ClaimResponse approveClaim(Long claimId, String comments) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found with ID: " + claimId));

        claim.setStatus(Claim.ClaimStatus.APPROVED);
        claim.setComments(comments);
        claim.setApprovedAt(java.time.LocalDateTime.now());

        // Update policy total payout
        Policy policy = claim.getPolicy();
        policy.setTotalPayedOut(policy.getTotalPayedOut().add(claim.getClaimAmount()));

        Claim approvedClaim = claimRepository.save(claim);
        return mapToResponse(approvedClaim);
    }

    /**
     * Reject a claim.
     */
    public ClaimResponse rejectClaim(Long claimId, String rejectionReason) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found with ID: " + claimId));

        claim.setStatus(Claim.ClaimStatus.REJECTED);
        claim.setRejectionReason(rejectionReason);

        Claim rejectedClaim = claimRepository.save(claim);
        return mapToResponse(rejectedClaim);
    }

    /**
     * Map Claim entity to Response DTO.
     */
    private ClaimResponse mapToResponse(Claim claim) {
        return ClaimResponse.builder()
                .id(claim.getId())
                .workerId(claim.getWorker().getId())
                .policyId(claim.getPolicy().getId())
                .disruptionId(claim.getDisruption() != null ? claim.getDisruption().getId() : null)
                .disruptionType(claim.getDisruptionType())
                .claimAmount(claim.getClaimAmount())
                .lostHours(claim.getLostHours())
                .hourlyRate(claim.getHourlyRate())
                .status(claim.getStatus().toString())
                .rejectionReason(claim.getRejectionReason())
                .comments(claim.getComments())
                .createdAt(claim.getCreatedAt())
                .updatedAt(claim.getUpdatedAt())
                .approvedAt(claim.getApprovedAt())
                .build();
    }
}
