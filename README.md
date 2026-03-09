# GigShield AI
### AI-Powered Parametric Insurance Platform for Gig Workers

---

# Overview

GigShield AI is an intelligent parametric insurance platform designed to protect delivery partners in India’s gig economy from **income loss caused by external disruptions** such as extreme weather, pollution, or sudden curfews.

Delivery workers from platforms like **Swiggy, Zomato, Amazon, and Zepto** often lose earnings when environmental or social disruptions prevent them from working. GigShield AI automatically detects such disruptions and provides **instant compensation based on predicted income loss**.

Unlike traditional insurance systems, GigShield AI uses **AI-driven risk analysis, smart payout calculation, and automated claims processing** to deliver fast and fair protection.

---

# Problem Statement

India’s gig economy relies heavily on delivery partners who earn on a daily basis. However, factors like:

- Heavy rainfall  
- Extreme heat  
- Floods  
- Air pollution  
- Curfews or city shutdowns  

can prevent them from working.

Currently, gig workers **do not have any income protection** against these disruptions, which may cause them to lose **20–30% of their weekly earnings**.

GigShield AI addresses this problem by creating a **parametric insurance platform that automatically compensates workers for income loss during such disruptions.**

---

# Solution

GigShield AI introduces an **AI-powered insurance ecosystem** that:

1. Predicts disruption risks using AI  
2. Dynamically calculates weekly insurance premiums  
3. Automatically detects external disruption events  
4. Calculates income loss using a **Smart Payout Calculator**  
5. Instantly triggers claim payouts  
6. Detects fraud using anomaly detection  

The platform ensures **zero-touch claim processing**, meaning workers receive compensation without manual claim submissions.

---

# Target Persona

## Food Delivery Partners

Platforms:

- Swiggy  
- Zomato  

Typical worker profile:

| Attribute | Value |
|----------|------|
| Average Daily Earnings | ₹800 |
| Working Hours | 8–10 hours |
| Weekly Earnings | ₹5000–₹6000 |

These workers are highly vulnerable to environmental disruptions since their work is primarily outdoors.

---

# Key Features

## AI Risk Assessment

The system evaluates disruption risk based on:

- Weather conditions  
- Air pollution levels  
- Historical disruption patterns  
- Location-based risk factors  

This enables **dynamic insurance pricing**.

---

## Dynamic Weekly Premium Model

Gig workers operate on weekly earning cycles. Therefore, GigShield AI calculates premiums on a **weekly basis**.

Example:

| Plan | Weekly Premium | Coverage |
|-----|-----|-----|
| Basic | ₹40 | ₹300 |
| Standard | ₹70 | ₹600 |
| Pro | ₹120 | ₹1000 |

---

## Smart Payout Calculator (Unique Feature)

Instead of fixed payouts, GigShield AI calculates compensation based on **actual income loss**.

Formula:
Payout = (Daily Income / Working Hours) × Lost Hours


Example:


Daily earnings = ₹800
Working hours = 8 hours
Rain disruption = 3 hours

Payout = ₹300


This ensures **fair and transparent compensation**.

---

## SmartShift AI (Predictive Disruption Detection)

GigShield AI predicts disruptions **before they occur**.

Using weather and environmental data, the system forecasts events such as:

- Heavy rain  
- Heat waves  
- Pollution spikes  

Workers receive alerts suggesting the **best working hours to avoid income loss**.

Example notification:
⚠ Heavy rain expected at 6 PM
Recommended working hours: 2 PM – 5 PM


---

## Automated Claim Processing

The system continuously monitors disruption triggers using external APIs.

If a disruption occurs:

1. Event detected  
2. Affected workers identified  
3. Claim automatically approved  
4. Smart payout calculated  
5. Compensation credited  

Workers **do not need to submit manual claims**.

---

## Fraud Detection

The system uses anomaly detection to identify suspicious claims.

Fraud detection checks:

- GPS spoofing  
- Duplicate claims  
- Inactive worker claims  
- Abnormal claim patterns  

Machine learning algorithms are used to flag suspicious behavior.

---

# System Architecture
React Web Application
↓
Spring Boot Backend
↓
PostgreSQL Database
↓
Python AI Engine
↓
Disruption Monitoring System
↓
Claim Automation Engine
↓
Payment Simulator


---

# Detailed Architecture Diagram

```
                +----------------------+
                |     React Web App    |
                |  (Worker Dashboard)  |
                +----------+-----------+
                           |
                           | REST API
                           |
                +----------v-----------+
                |    Spring Boot API   |
                |   (Core Backend)     |
                +----------+-----------+
                           |
         ------------------------------------------
         |                    |                   |
         |                    |                   |
+--------v--------+  +--------v--------+  +-------v--------+
|  PostgreSQL DB  |  | Python AI Engine|  | Event Monitor  |
|  Workers        |  | Risk Prediction |  | Weather API    |
|  Policies       |  | Fraud Detection |  | AQI API        |
|  Claims         |  | Smart Payout    |  | Alerts         |
+-----------------+  +-----------------+  +----------------+
                           |
                           v
                +----------------------+
                | Claim Automation     |
                | Engine               |
                +----------+-----------+
                           |
                           v
                +----------------------+
                | Payment Simulator    |
                | (Razorpay Sandbox)   |
                +----------------------+
```

### Architecture Summary

- **React** → user interface  
- **Spring Boot** → backend logic  
- **PostgreSQL** → persistent storage  
- **Python AI Engine** → predictions & payout calculations  
- **External APIs** → weather and pollution data  

---

# Technology Stack

## Frontend

**React.js**

Used to build the worker dashboard and admin interface.

---

## Backend

**Spring Boot**

Responsible for:

- Authentication  
- Policy management  
- Claims processing  
- API integrations  

---

## Database

**PostgreSQL**

Stores:

- Worker profiles  
- Insurance policies  
- Claims data  
- Disruption events  
- Payout records  

---

## AI Engine

**Python**

Used for:

- Risk prediction models  
- Fraud detection  
- Smart payout calculation  
- Disruption prediction  

---

# External APIs

Weather Data  
**OpenWeather API**

Air Quality  
**WAQI API**

Maps & Location  
**Google Maps API**

Payment Simulation  
**Razorpay Sandbox**

---

# Core Workflow

```
Worker Registration
        |
        v
Create Worker Profile
        |
        v
AI Risk Assessment
        |
        v
Weekly Policy Selection
        |
        v
System Monitors Disruptions
        |
        v
Disruption Detected
        |
        v
Claim Automatically Triggered
        |
        v
Smart Payout Calculator
        |
        v
Fraud Detection Check
        |
        v
Payment Processed
        |
        v
Worker Receives Compensation
```

---

# Future Enhancements

- Hyperlocal risk prediction  
- Real-time disruption heatmaps  
- AI-driven premium personalization  
- Platform integration with gig companies  
- Worker safety advisory alerts  

---

# Repository Structure

```
gigshield-ai
│
├── frontend
│   └── react-app
│
├── backend
│   └── springboot-api
│
├── ai-engine
│   └── python-models
│
├── docs
│   └── architecture-diagrams
│
└── README.md
```
---

# Demo Video

A short demonstration video explaining the solution and architecture will be provided.

---

# Team

**Team Name: FutureStack**  

**Team Members**

- [Phanindra Reddy](https://github.com/phanindrareddy2006)
- [Tejo Bhargavi](https://github.com/Bhargavi6667)
- [Hima Sisira](https://github.com/himasisira)
- [Bhuvana Sai](https://github.com)  

---
