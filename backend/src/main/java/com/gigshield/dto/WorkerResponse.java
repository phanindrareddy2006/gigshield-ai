package com.gigshield.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WorkerResponse {

    private Long id;
    private String name;
    private String phone;
    private String platform;
    private String city;
    private BigDecimal averageDailyIncome;
    private BigDecimal workingHours;
    private String address;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WorkerResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public BigDecimal getAverageDailyIncome() { return averageDailyIncome; }
    public void setAverageDailyIncome(BigDecimal avg) { this.averageDailyIncome = avg; }

    public BigDecimal getWorkingHours() { return workingHours; }
    public void setWorkingHours(BigDecimal wh) { this.workingHours = wh; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}