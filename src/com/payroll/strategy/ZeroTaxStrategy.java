package com.payroll.strategy;

import com.payroll.interfaces.ITaxCalculator;

public class ZeroTaxStrategy implements ITaxCalculator {
    @Override
    public double calculateTax(double grossSalary) {
        return 0.0; // Tax-exempt employees [cite: 101]
    }
}