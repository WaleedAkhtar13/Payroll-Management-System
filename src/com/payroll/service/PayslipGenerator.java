package com.payroll.service;

import com.payroll.model.*;

public class PayslipGenerator {
    public static String generatePayslip(Employee emp, PayrollRecord record, SalaryStructure struct) {
        return "===========================================\n" +
                "           MONTHLY PAYSLIP RECORD          \n" +
                "===========================================\n" +
                " Employee ID:   " + emp.getId() + "\n" +
                " Name:          " + emp.getName() + "\n" +
                " Department:    " + emp.getDepartment() + "\n" +
                " Designation:   " + emp.getDesignation() + "\n" +
                " Month/Year:    " + record.getMonth() + " " + record.getYear() + "\n" +
                "-------------------------------------------\n" +
                " Basic Salary:  PKR " + struct.getBasicPay() + "\n" +
                " Allowances:    PKR " + (struct.getGrossSalary() - struct.getBasicPay()) + "\n" +
                " Gross Salary:  PKR " + record.getGrossSalary() + "\n" +
                "-------------------------------------------\n" +
                " Tax Deduction: PKR " + record.getTaxDeduction() + "\n" +
                " Allow. Deduct: PKR " + struct.getStaticDeductions() + "\n" +
                " Net Paid Cash: PKR " + record.getNetSalary() + "\n" +
                "===========================================";
    }
}