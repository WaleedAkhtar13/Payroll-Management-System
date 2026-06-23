package com.payroll.model;

public class PayrollRecord {
    private String employeeId;
    private String month;
    private int year;
    private double grossSalary;
    private double taxDeduction;
    private double leaveDeductions;
    private double overtimePay;
    private double netSalary;

    public PayrollRecord(String employeeId, String month, int year, double grossSalary, double taxDeduction, double leaveDeductions, double overtimePay, double netSalary) {
        this.employeeId = employeeId;
        this.month = month;
        this.year = year;
        this.grossSalary = grossSalary;
        this.taxDeduction = taxDeduction;
        this.leaveDeductions = leaveDeductions;
        this.overtimePay = overtimePay;
        this.netSalary = netSalary;
    }

    public String getEmployeeId() { return employeeId; }
    public String getMonth() { return month; }
    public int getYear() { return year; }
    public double getGrossSalary() { return grossSalary; }
    public double getTaxDeduction() { return taxDeduction; }
    public double getLeaveDeductions() { return leaveDeductions; }
    public double getOvertimePay() { return overtimePay; }
    public double getNetSalary() { return netSalary; }
}