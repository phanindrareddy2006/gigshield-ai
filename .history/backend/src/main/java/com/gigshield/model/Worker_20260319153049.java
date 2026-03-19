package com.gigshield.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private BigDecimal averageDailyIncome;

    @Column(nullable = false)
    private BigDecimal workingHours;

    private String address;

    @Enumerated(EnumType.STRING)
    private WorkerStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ✅ Constructors
    public Worker() {}

    // ✅ Lifecycle hooks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = WorkerStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ✅ ENUM
    public enum WorkerStatus {
        ACTIVE, INACTIVE, SUSPENDED, VERIFIED
    }

    // ✅ GETTERS & SETTERS (REAL ONES)

    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getPlatform() { return platform; }

    public void setPlatform(String platform) { this.platform = platform; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public BigDecimal getAverageDailyIncome() { return averageDailyIncome; }

    public void setAverageDailyIncome(BigDecimal averageDailyIncome) {
        this.averageDailyIncome = averageDailyIncome;
    }

    public BigDecimal getWorkingHours() { return workingHours; }

    public void setWorkingHours(BigDecimal workingHours) {
        this.workingHours = workingHours;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public WorkerStatus getStatus() { return status; }

    public void setStatus(WorkerStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
}