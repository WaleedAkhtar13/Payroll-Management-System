package com.payroll.service;

import com.payroll.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private static EmployeeManager instance = new EmployeeManager();
    private List<Employee> employees = new ArrayList<>();

    private EmployeeManager() {
        // Presentation aur Testing ke liye starting dummy data
        employees.add(new Employee("EMP001", "Waleed Akhtar", "Software Engineering", "Junior Dev", "2026-01-10"));
        employees.add(new Employee("EMP002", "Ayesha Khan", "HR Department", "Manager", "2025-05-12"));
    }

    public static EmployeeManager getInstance() {
        return instance;
    }

    // 1. CREATE (Add Employee)
    public boolean addEmployee(Employee emp) {
        if (getEmployeeById(emp.getId()) != null) {
            return false; // Duplicate ID Error Handle karne ke liye
        }
        employees.add(emp);
        return true;
    }

    // 2. READ / SEARCH
    public List<Employee> getAllEmployees() {
        return employees;
    }

    public Employee getEmployeeById(String id) {
        for (Employee emp : employees) {
            if (emp.getId().equalsIgnoreCase(id)) return emp;
        }
        return null;
    }

    public List<Employee> searchEmployees(String query) {
        List<Employee> results = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getName().toLowerCase().contains(query.toLowerCase()) ||
                    emp.getId().toLowerCase().contains(query.toLowerCase()) ||
                    emp.getDepartment().toLowerCase().contains(query.toLowerCase())) {
                results.add(emp);
            }
        }
        return results;
    }

    // 3. UPDATE
    public boolean updateEmployee(Employee updatedEmp) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equalsIgnoreCase(updatedEmp.getId())) {
                employees.set(i, updatedEmp);
                return true;
            }
        }
        return false;
    }

    // 4. DELETE
    public boolean deleteEmployee(String empId) {
        return employees.removeIf(emp -> emp.getId().equalsIgnoreCase(empId));
    }
}