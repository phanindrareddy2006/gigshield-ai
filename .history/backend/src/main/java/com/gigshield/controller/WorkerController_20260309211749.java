package com.gigshield.controller;

import com.gigshield.dto.WorkerRegistrationRequest;
import com.gigshield.dto.WorkerResponse;
import com.gigshield.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Worker-related endpoints.
 */
@RestController
@RequestMapping("/workers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class WorkerController {

    private final WorkerService workerService;

    /**
     * Register a new worker.
     */
    @PostMapping("/register")
    public ResponseEntity<WorkerResponse> registerWorker(@RequestBody WorkerRegistrationRequest request) {
        WorkerResponse response = workerService.registerWorker(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get worker by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkerResponse> getWorkerById(@PathVariable Long id) {
        WorkerResponse response = workerService.getWorkerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get worker by phone number.
     */
    @GetMapping("/phone/{phone}")
    public ResponseEntity<WorkerResponse> getWorkerByPhone(@PathVariable String phone) {
        WorkerResponse response = workerService.getWorkerByPhone(phone);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all workers in a city.
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<WorkerResponse>> getWorkersByCity(@PathVariable String city) {
        List<WorkerResponse> response = workerService.getWorkersByCity(city);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update worker information.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkerResponse> updateWorker(@PathVariable Long id, @RequestBody WorkerRegistrationRequest request) {
        WorkerResponse response = workerService.updateWorker(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deactivate a worker.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateWorker(@PathVariable Long id) {
        workerService.deactivateWorker(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
