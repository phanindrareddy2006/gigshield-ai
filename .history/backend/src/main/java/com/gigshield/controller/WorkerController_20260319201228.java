package com.gigshield.controller;

import com.gigshield.dto.LoginRequest;
import com.gigshield.dto.WorkerRegistrationRequest;
import com.gigshield.dto.WorkerResponse;
import com.gigshield.service.WorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping("/register")
    public ResponseEntity<WorkerResponse> registerWorker(@Valid @RequestBody WorkerRegistrationRequest request) {
        WorkerResponse response = workerService.registerWorker(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<WorkerResponse> loginWorker(@RequestBody LoginRequest request) {
        WorkerResponse response = workerService.loginWorker(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutWorker() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerResponse> getWorkerById(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.getWorkerById(id));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<WorkerResponse> getWorkerByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(workerService.getWorkerByPhone(phone));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<WorkerResponse>> getWorkersByCity(@PathVariable String city) {
        return ResponseEntity.ok(workerService.getWorkersByCity(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkerResponse> updateWorker(
            @PathVariable Long id,
            @Valid @RequestBody WorkerRegistrationRequest request) {
        return ResponseEntity.ok(workerService.updateWorker(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateWorker(@PathVariable Long id) {
        workerService.deactivateWorker(id);
        return ResponseEntity.noContent().build();
    }
}