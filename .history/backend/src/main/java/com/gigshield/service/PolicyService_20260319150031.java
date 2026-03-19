package com.gigshield.service;

import com.gigshield.dto.PolicyCreateRequest;
import com.gigshield.dto.PolicyResponse;
import com.gigshield.model.Policy;
import com.gigshield.model.Worker;
import com.gigshield.repository.PolicyRepository;
import com.gigshield.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PolicyService {

    private final PolicyRepository policyRepo;
    private final WorkerRepository workerRepo;

    public PolicyService(PolicyRepository p, WorkerRepository w) {
        this.policyRepo = p;
        this.workerRepo = w;
    }

    public PolicyResponse createPolicy(PolicyCreateRequest req) {

        Worker w = workerRepo.findById(req.getWorkerId())
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Policy p = new Policy();
        p.setWorker(w);
        p.setPlanType(req.getPlanType());
        p.setMonthlyPremium(req.getMonthlyPremium());
        p.setMonthlyPayoutLimit(new BigDecimal("10000"));
        p.setStartDate(req.getStartDate() != null ? req.getStartDate() : java.time.LocalDate.now());
        p.setEndDate(req.getEndDate() != null ? req.getEndDate() : p.getStartDate().plusMonths(12));
        p.setStatus(Policy.PolicyStatus.ACTIVE);
        p.setTotalPayedOut(BigDecimal.ZERO);

        Policy saved = policyRepo.save(p);
        return map(saved);
    }

    public PolicyResponse getPolicyById(Long id) {
        return policyRepo.findById(id)
                .map(this::map)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
    }

    public java.util.List<PolicyResponse> getPoliciesByWorker(Long workerId) {
        Worker worker = workerRepo.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        return policyRepo.findByWorker(worker)
                .stream()
                .map(this::map)
                .toList();
    }

    public PolicyResponse getActivePolicyByWorker(Long workerId) {
        Worker worker = workerRepo.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Policy policy = policyRepo.findFirstByWorkerAndStatusOrderByCreatedAtDesc(worker, Policy.PolicyStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Active policy not found"));

        return map(policy);
    }

    public PolicyResponse renewPolicy(Long id) {
        Policy existing = policyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        if (existing.getStatus() != Policy.PolicyStatus.ACTIVE) {
            throw new RuntimeException("Only active policies can be renewed");
        }

        existing.setEndDate(existing.getEndDate().plusMonths(12));
        existing.setStatus(Policy.PolicyStatus.ACTIVE);
        Policy updated = policyRepo.save(existing);
        return map(updated);
    }

    public void cancelPolicy(Long id) {
        Policy existing = policyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        existing.setStatus(Policy.PolicyStatus.CANCELLED);
        policyRepo.save(existing);
    }

    private PolicyResponse map(Policy p) {
        PolicyResponse r = new PolicyResponse();
        r.setId(p.getId());
        r.setWorkerId(p.getWorker().getId());
        r.setPlanType(p.getPlanType());
        r.setMonthlyPremium(p.getMonthlyPremium());
        r.setMonthlyPayoutLimit(p.getMonthlyPayoutLimit());
        r.setStartDate(p.getStartDate());
        r.setEndDate(p.getEndDate());
        r.setStatus(p.getStatus().name());
        r.setTotalPayedOut(p.getTotalPayedOut());
        r.setCreatedAt(p.getCreatedAt());
        r.setUpdatedAt(p.getUpdatedAt());
        return r;
    }
}