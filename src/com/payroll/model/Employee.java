package com.payroll.model;

public class Employee {
    private String id;
    private String name;
    private String department;
    private String designation;
    private String joinDate;

    public Employee(String id, String name, String department, String designation, String joinDate) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.joinDate = joinDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }
}