package com.gigshield.exception;

public class WorkerAlreadyExistsException extends RuntimeException {
    public WorkerAlreadyExistsException(String message) {
        super(message);
    }
}
