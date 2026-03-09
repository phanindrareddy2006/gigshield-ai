package com.gigshield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * GigShield AI - Income Protection for Gig Workers
 * <p>
 * This is the main entry point for the GigShield AI backend application.
 * The application provides RESTful APIs for managing worker registrations,
 * insurance policies, claims, and payout calculations.
 */
@SpringBootApplication
public class GigShieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(GigShieldApplication.class, args);
    }
}
