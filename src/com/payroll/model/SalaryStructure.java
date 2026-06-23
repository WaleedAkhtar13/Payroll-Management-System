package com.payroll.model;

import com.payroll.interfaces.ITaxCalculator;

public class SalaryStructure {
    private double basicPay;
    private double houseRentAllowance;
    private double medicalAllowance;
    private double transportAllowance;
    private double providentFundDeduction;
    private double loanInstallment;
    private ITaxCalculator taxStrategy;

    public SalaryStructure(double basicPay, double houseRent, double medical, double transport, double pf, double loan, ITaxCalculator taxStrategy) {
        this.basicPay = basicPay;
        this.houseRentAllowance = houseRent;
        this.medicalAllowance = medical;
        this.transportAllowance = transport;
        this.providentFundDeduction = pf;
        this.loanInstallment = loan;
        this.taxStrategy = taxStrategy;
    }

    public double getGrossSalary() {
        return basicPay + houseRentAllowance + medicalAllowance + transportAllowance;
    }

    public double getStaticDeductions() {
        return providentFundDeduction + loanInstallment;
    }

    public ITaxCalculator getTaxStrategy() { return taxStrategy; }
    public double getBasicPay() { return basicPay; }
    public double getHouseRentAllowance() { return houseRentAllowance; }
    public double getMedicalAllowance() { return medicalAllowance; }
    public double getTransportAllowance() { return transportAllowance; }
    public double getProvidentFundDeduction() { return providentFundDeduction; }
    public double getLoanInstallment() { return loanInstallment; }
}