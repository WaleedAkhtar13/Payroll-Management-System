# 📊 Automated Payroll Management System
> **Software Construction & Development (CS-3112) — Semester Project Phase 2**

---

## 👤 Student Information
* **Student Name:** Muhammad Waleed Akhtar  
* **Roll Number:** L1F23BSSE0332  
* **Course Title:** Software Construction & Development (Lab)  
* **Submitted To:** Sir Abdul Rehman    
* **Institution:** University of Central Punjab, Lahore  

---

## 📝 Project Description
The **Automated Payroll Management System** is a lightweight, high-performance Java enterprise desktop utility engineered to completely digitize internal corporate employee bookkeeping, salary generation, and monthly wage distributions. 

Adhering strictly to a multi-layered design architecture, the application isolates the user interface logic from back-end computational workflows. In compliance with explicit project guidelines, data persistence is securely managed entirely via thread-safe **In-Memory Java Collections** (`ArrayList` and `HashMap`). This completely bypasses bulky external database configurations, offering an isolated, fast execution runtime state perfectly optimized for desktop environments and evaluation setups.

---

## ⚡ Core System Features
* **Full CRUD Operational Matrix:** Human Resource operators can seamlessly **Create (Add)** new employee records, **Read (Search)** fields, **Update** details, and **Delete (Purge)** obsolete entries via interactive dynamic dashboard forms.
* **Live Search Context Filter:** Implements an on-the-fly text listener component that filters the current graphical personnel list in real-time as the operator types characters matching names, IDs, or departments.
* **Algorithmic Payroll Automation:** Processes monthly salary components including basic pay, house rent, medical, and transport allowances while factoring in strict static deductions (e.g., Provident Fund, active loan installments).
* **Dynamic Strategy Pattern Tax Engine:** Decouples complex mathematical tax rules using policy wrappers. The computation automatically routes calculations through a progressive slab tax rule (`StandardTaxStrategy`) or a completely tax-exempt structure (`ZeroTaxStrategy`) at runtime.
* **Live UI State Synchronization (Observer Pattern):** The core dashboard acts as an active observer to the execution service. Processing a monthly payroll run instantly triggers event notifications that dynamically refresh dashboard statistical counters.
* **Singleton Security Session:** Restricts authentication pipelines to a single global instance tracking system access layers safely (`Admin` vs. `HRStaff` access leveling).

---

## ⚙️ Installation & Run Steps

Follow these basic commands to setup and run the application locally on your machine:

### Prerequisites
* **Java Development Kit (JDK):** Version 11 or higher installed.
* **JavaFX SDK:** Configured in your environment variables/IDE modules.
* **IDE:** IntelliJ IDEA or Eclipse (Recommended).

### Local Setup
1. **Clone the Repository:**
   ```bash
   git clone [https://github.com/WaleedAkhtar13/PayrollManagementSystem.git](https://github.com/WaleedAkhtar13/PayrollManagementSystem.git)
   cd PayrollManagementSystem
