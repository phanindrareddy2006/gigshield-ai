package com.gigshield.repository;

import com.gigshield.model.Claim;
import com.gigshield.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Claim entity.
 */
@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    /**
     * Find all claims for a worker.
     */
    List<Claim> findByWorker(Worker worker);

    /**
     * Find all claims by status.
     */
    List<Claim> findByStatus(Claim.ClaimStatus status);

    /**
     * Find all claims for a worker by status.
     */
    List<Claim> findByWorkerAndStatus(Worker worker, Claim.ClaimStatus status);

    /**
     * Find all claims by disruption type.
     */
    List<Claim> findByDisruptionType(String disruptionType);

    /**
     * Find all approved and paid claims for a worker.
     */
    List<Claim> findByWorkerAndStatusIn(Worker worker, List<Claim.ClaimStatus> statuses);
}
