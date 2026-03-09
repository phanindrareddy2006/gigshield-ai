package com.gigshield.repository;

import com.gigshield.model.Policy;
import com.gigshield.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Policy entity.
 */
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    /**
     * Find all policies for a worker.
     */
    List<Policy> findByWorker(Worker worker);

    /**
     * Find all active policies for a worker.
     */
    List<Policy> findByWorkerAndStatus(Worker worker, Policy.PolicyStatus status);

    /**
     * Find the most recent active policy for a worker.
     */
    Optional<Policy> findFirstByWorkerAndStatusOrderByCreatedAtDesc(
        Worker worker, Policy.PolicyStatus status
    );

    /**
     * Find all policies by plan type.
     */
    List<Policy> findByPlanType(String planType);

    /**
     * Find all active policies by plan type.
     */
    List<Policy> findByPlanTypeAndStatus(String planType, Policy.PolicyStatus status);
}
