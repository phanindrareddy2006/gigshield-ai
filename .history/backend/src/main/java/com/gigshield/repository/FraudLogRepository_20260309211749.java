package com.gigshield.repository;

import com.gigshield.model.FraudLog;
import com.gigshield.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for FraudLog entity.
 */
@Repository
public interface FraudLogRepository extends JpaRepository<FraudLog, Long> {

    /**
     * Find all fraud logs for a worker.
     */
    List<FraudLog> findByWorker(Worker worker);

    /**
     * Find all fraud logs by status.
     */
    List<FraudLog> findByStatus(FraudLog.FraudStatus status);

    /**
     * Find all fraud logs for a worker by status.
     */
    List<FraudLog> findByWorkerAndStatus(Worker worker, FraudLog.FraudStatus status);

    /**
     * Find all flagged fraud logs.
     */
    List<FraudLog> findByFraudTypeAndStatus(String fraudType, FraudLog.FraudStatus status);

    /**
     * Find high-risk fraud logs (risk score > threshold).
     */
    List<FraudLog> findByRiskScoreGreaterThan(Double riskScore);
}
