package com.payroll.interfaces;

import com.payroll.model.Employee;
import com.payroll.model.SalaryStructure;

public interface IPayrollProcessor {
    void setSalaryStructure(Employee emp, SalaryStructure struct);
    void runMonthlyPayroll(String month, int year);
    double calculateNetSalary(Employee emp, String month);
}