package com.gigshield.service;

import com.gigshield.dto.WorkerRegistrationRequest;
import com.gigshield.dto.WorkerResponse;
import com.gigshield.model.Worker;
import com.gigshield.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Worker-related operations.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class WorkerService {

    private final WorkerRepository workerRepository;

    /**
     * Register a new worker.
     */
    public WorkerResponse registerWorker(WorkerRegistrationRequest request) {
        Worker worker = Worker.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .platform(request.getPlatform())
                .city(request.getCity())
                .averageDailyIncome(request.getAverageDailyIncome())
                .workingHours(request.getWorkingHours())
                .address(request.getAddress())
                .status(Worker.WorkerStatus.ACTIVE)
                .build();

        Worker savedWorker = workerRepository.save(worker);
        return mapToResponse(savedWorker);
    }

    /**
     * Get worker by ID.
     */
    @Transactional(readOnly = true)
    public WorkerResponse getWorkerById(Long id) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found with ID: " + id));
        return mapToResponse(worker);
    }

    /**
     * Get worker by phone number.
     */
    @Transactional(readOnly = true)
    public WorkerResponse getWorkerByPhone(String phone) {
        Worker worker = workerRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Worker not found with phone: " + phone));
        return mapToResponse(worker);
    }

    /**
     * Get all workers in a city.
     */
    @Transactional(readOnly = true)
    public List<WorkerResponse> getWorkersByCity(String city) {
        return workerRepository.findByCity(city).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update worker information.
     */
    public WorkerResponse updateWorker(Long id, WorkerRegistrationRequest request) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found with ID: " + id));

        worker.setName(request.getName());
        worker.setPhone(request.getPhone());
        worker.setPlatform(request.getPlatform());
        worker.setCity(request.getCity());
        worker.setAverageDailyIncome(request.getAverageDailyIncome());
        worker.setWorkingHours(request.getWorkingHours());
        worker.setAddress(request.getAddress());

        Worker updatedWorker = workerRepository.save(worker);
        return mapToResponse(updatedWorker);
    }

    /**
     * Deactivate a worker.
     */
    public void deactivateWorker(Long id) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found with ID: " + id));
        worker.setStatus(Worker.WorkerStatus.INACTIVE);
        workerRepository.save(worker);
    }

    /**
     * Map Worker entity to Response DTO.
     */
    private WorkerResponse mapToResponse(Worker worker) {
        return WorkerResponse.builder()
                .id(worker.getId())
                .name(worker.getName())
                .phone(worker.getPhone())
                .platform(worker.getPlatform())
                .city(worker.getCity())
                .averageDailyIncome(worker.getAverageDailyIncome())
                .workingHours(worker.getWorkingHours())
                .address(worker.getAddress())
                .status(worker.getStatus().toString())
                .createdAt(worker.getCreatedAt())
                .updatedAt(worker.getUpdatedAt())
                .build();
    }
}
