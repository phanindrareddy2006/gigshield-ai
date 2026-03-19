package com.gigshield.dto;

import java.math.BigDecimal;

public class WorkerRegistrationRequest {

    private String name;
    private String phone;
    private String platform;
    private String city;
    private BigDecimal averageDailyIncome;
    private BigDecimal workingHours;
    private String address;
    private String password;

    public WorkerRegistrationRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public BigDecimal getAverageDailyIncome() { return averageDailyIncome; }
    public void setAverageDailyIncome(BigDecimal averageDailyIncome) { this.averageDailyIncome = averageDailyIncome; }

    public BigDecimal getWorkingHours() { return workingHours; }
    public void setWorkingHours(BigDecimal workingHours) { this.workingHours = workingHours; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}