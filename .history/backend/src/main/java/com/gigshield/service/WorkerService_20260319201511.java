package com.gigshield.service;

import com.gigshield.dto.LoginRequest;
import com.gigshield.dto.WorkerRegistrationRequest;
import com.gigshield.dto.WorkerResponse;
import com.gigshield.model.Worker;
import com.gigshield.repository.WorkerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkerService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public WorkerResponse registerWorker(WorkerRegistrationRequest request) {

        if (workerRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new com.gigshield.exception.WorkerAlreadyExistsException("Worker already exists");
        }

        Worker worker = new Worker();
        worker.setName(request.getName());
        worker.setPhone(request.getPhone());
        worker.setPlatform(request.getPlatform());
        worker.setCity(request.getCity());
        worker.setAverageDailyIncome(request.getAverageDailyIncome());
        worker.setWorkingHours(request.getWorkingHours());
        worker.setAddress(request.getAddress());

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("Password is required");
        }
        worker.setPassword(passwordEncoder.encode(request.getPassword()));

        worker.setStatus(Worker.WorkerStatus.ACTIVE);

        Worker saved = workerRepository.save(worker);

        return mapToResponse(saved);
    }

    public WorkerResponse getWorkerById(Long id) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return mapToResponse(worker);
    }

    public WorkerResponse getWorkerByPhone(String phone) {
        Worker worker = workerRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return mapToResponse(worker);
    }

    public List<WorkerResponse> getWorkersByCity(String city) {
        return workerRepository.findByCity(city)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public WorkerResponse updateWorker(Long id, WorkerRegistrationRequest request) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        worker.setName(request.getName());
        worker.setPhone(request.getPhone());
        worker.setPlatform(request.getPlatform());
        worker.setCity(request.getCity());
        worker.setAverageDailyIncome(request.getAverageDailyIncome());
        worker.setWorkingHours(request.getWorkingHours());
        worker.setAddress(request.getAddress());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            worker.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return mapToResponse(workerRepository.save(worker));
    }

    public WorkerResponse loginWorker(LoginRequest request) {
        Worker worker = workerRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), worker.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return mapToResponse(worker);
    }

    public void deactivateWorker(Long id) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        worker.setStatus(Worker.WorkerStatus.INACTIVE);
        workerRepository.save(worker);
    }

    private WorkerResponse mapToResponse(Worker worker) {

        WorkerResponse res = new WorkerResponse();
        res.setId(worker.getId());
        res.setName(worker.getName());
        res.setPhone(worker.getPhone());
        res.setPlatform(worker.getPlatform());
        res.setCity(worker.getCity());
        res.setAverageDailyIncome(worker.getAverageDailyIncome());
        res.setWorkingHours(worker.getWorkingHours());
        res.setAddress(worker.getAddress());
        res.setStatus(worker.getStatus().name());
        res.setCreatedAt(worker.getCreatedAt());
        res.setUpdatedAt(worker.getUpdatedAt());

        return res;
    }
}