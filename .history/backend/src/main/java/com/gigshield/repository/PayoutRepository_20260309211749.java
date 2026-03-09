package com.gigshield.repository;

import com.gigshield.model.Payout;
import com.gigshield.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Payout entity.
 */
@Repository
public interface PayoutRepository extends JpaRepository<Payout, Long> {

    /**
     * Find all payouts for a worker.
     */
    List<Payout> findByWorker(Worker worker);

    /**
     * Find all payouts by status.
     */
    List<Payout> findByStatus(Payout.PayoutStatus status);

    /**
     * Find all payouts for a worker by status.
     */
    List<Payout> findByWorkerAndStatus(Worker worker, Payout.PayoutStatus status);

    /**
     * Find all completed payouts for a worker.
     */
    List<Payout> findByWorkerAndStatusOrderByProcessedAtDesc(
        Worker worker, Payout.PayoutStatus status
    );
}
