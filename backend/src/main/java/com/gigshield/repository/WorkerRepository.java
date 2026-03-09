package com.gigshield.repository;

import com.gigshield.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

/**
 * Repository interface for Worker entity.
 */
@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    /**
     * Find worker by phone number.
     */
    Optional<Worker> findByPhone(String phone);

    /**
     * Find all workers in a city.
     */
    List<Worker> findByCity(String city);

    /**
     * Find all workers by status.
     */
    List<Worker> findByStatus(Worker.WorkerStatus status);

    /**
     * Find workers in a specific platform.
     */
    List<Worker> findByPlatform(String platform);

    /**
     * Find workers in a specific city and platform.
     */
    List<Worker> findByCityAndPlatform(String city, String platform);
}
