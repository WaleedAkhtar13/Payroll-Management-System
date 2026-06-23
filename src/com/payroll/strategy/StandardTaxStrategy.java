package com.payroll.strategy;

import com.payroll.interfaces.ITaxCalculator;

public class StandardTaxStrategy implements ITaxCalculator {
    @Override
    public double calculateTax(double grossSalary) {
        // Standard progressive slab-based tax rule
        if (grossSalary > 100000) {
            return grossSalary * 0.15; // 15% tax
        } else if (grossSalary > 50000) {
            return grossSalary * 0.05; // 5% tax
        }
        return 0;
    }
}