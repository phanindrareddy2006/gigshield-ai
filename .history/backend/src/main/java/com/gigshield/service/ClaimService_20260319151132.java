package com.gigshield.service;

import com.gigshield.dto.ClaimResponse;
import com.gigshield.dto.ClaimTriggerRequest;
import com.gigshield.model.Claim;
import com.gigshield.model.Policy;
import com.gigshield.model.Worker;
import com.gigshield.repository.ClaimRepository;
import com.gigshield.repository.PolicyRepository;
import com.gigshield.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final WorkerRepository workerRepository;
    private final PolicyRepository policyRepository;

    // ✅ REQUIRED constructor (NO Lombok)
    public ClaimService(ClaimRepository claimRepository,
                        WorkerRepository workerRepository,
                        PolicyRepository policyRepository) {
        this.claimRepository = claimRepository;
        this.workerRepository = workerRepository;
        this.policyRepository = policyRepository;
    }

    public ClaimResponse triggerClaim(ClaimTriggerRequest request) {

        Worker worker = workerRepository.findById(request.getWorkerId())
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Policy policy = policyRepository.findById(request.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        BigDecimal hourlyRate = worker.getAverageDailyIncome()
                .divide(worker.getWorkingHours(), 2, RoundingMode.HALF_UP);

        BigDecimal claimAmount = hourlyRate.multiply(request.getLostHours());

        if (claimAmount.compareTo(policy.getMonthlyPayoutLimit()) > 0) {
            claimAmount = policy.getMonthlyPayoutLimit();
        }

        // ✅ NO builder()
        Claim claim = new Claim();
        claim.setWorker(worker);
        claim.setPolicy(policy);
        claim.setDisruptionType(request.getDisruptionType());
        claim.setClaimAmount(claimAmount);
        claim.setLostHours(request.getLostHours());
        claim.setHourlyRate(hourlyRate);
        claim.setStatus(Claim.ClaimStatus.PENDING);
        claim.setComments(request.getComments());

        Claim saved = claimRepository.save(claim);

        return mapToResponse(saved);
    }

    public ClaimResponse getClaimById(Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        return mapToResponse(claim);
    }

    public List<ClaimResponse> getClaimsByWorker(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        return claimRepository.findByWorker(worker).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ClaimResponse> getPendingClaimsByWorker(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        return claimRepository.findByWorkerAndStatus(worker, Claim.ClaimStatus.PENDING).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ClaimResponse approveClaim(Long id, String comments) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        if (claim.getStatus() != Claim.ClaimStatus.PENDING) {
            throw new RuntimeException("Only pending claims can be approved");
        }

        claim.setStatus(Claim.ClaimStatus.APPROVED);
        claim.setComments(comments != null ? comments : claim.getComments());
        claim.setApprovedAt(java.time.LocalDateTime.now());

        Claim updated = claimRepository.save(claim);
        return mapToResponse(updated);
    }

    public ClaimResponse rejectClaim(Long id, String rejectionReason) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        if (claim.getStatus() != Claim.ClaimStatus.PENDING) {
            throw new RuntimeException("Only pending claims can be rejected");
        }

        claim.setStatus(Claim.ClaimStatus.REJECTED);
        claim.setRejectionReason(rejectionReason);
        claim.setUpdatedAt(java.time.LocalDateTime.now());

        Claim updated = claimRepository.save(claim);
        return mapToResponse(updated);
    }

    private ClaimResponse mapToResponse(Claim claim) {

        ClaimResponse res = new ClaimResponse();
        res.setId(claim.getId());
        res.setWorkerId(claim.getWorker().getId());
        res.setPolicyId(claim.getPolicy().getId());
        res.setDisruptionType(claim.getDisruptionType());
        res.setClaimAmount(claim.getClaimAmount());
        res.setLostHours(claim.getLostHours());
        res.setHourlyRate(claim.getHourlyRate());
        res.setStatus(claim.getStatus().name());
        res.setRejectionReason(claim.getRejectionReason());
        res.setComments(claim.getComments());
        res.setCreatedAt(claim.getCreatedAt());
        res.setUpdatedAt(claim.getUpdatedAt());
        res.setApprovedAt(claim.getApprovedAt());

        return res;
    }
}