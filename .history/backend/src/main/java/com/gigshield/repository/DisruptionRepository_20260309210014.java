package com.gigshield.repository;

import com.gigshield.model.Disruption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Disruption entity.
 */
@Repository
public interface DisruptionRepository extends JpaRepository<Disruption, Long> {

    /**
     * Find all disruptions by type.
     */
    List<Disruption> findByType(String type);

    /**
     * Find all disruptions by city.
     */
    List<Disruption> findByCity(String city);

    /**
     * Find all active disruptions in a city.
     */
    List<Disruption> findByCityAndStatus(String city, Disruption.DisruptionStatus status);

    /**
     * Find all disruptions by severity.
     */
    List<Disruption> findBySeverity(String severity);

    /**
     * Find all disruptions by type and severity.
     */
    List<Disruption> findByTypeAndSeverity(String type, String severity);
}
