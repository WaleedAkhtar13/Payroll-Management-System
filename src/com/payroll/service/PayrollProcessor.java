package com.payroll.service;

import com.payroll.interfaces.IPayrollProcessor;
import com.payroll.interfaces.IObserver;
import com.payroll.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayrollProcessor implements IPayrollProcessor {
    private static PayrollProcessor instance = new PayrollProcessor();
    private Map<String, SalaryStructure> salaryStructures = new HashMap<>();
    private List<PayrollRecord> payrollHistory = new ArrayList<>();
    private List<IObserver> observers = new ArrayList<>();

    private PayrollProcessor() {
        // Initial setup for default seed employees
        salaryStructures.put("EMP001", new com.payroll.strategy.StandardTaxStrategy() != null ?
                new SalaryStructure(75000, 15000, 5000, 5000, 4000, 0, new com.payroll.strategy.StandardTaxStrategy()) : null);
        salaryStructures.put("EMP002", new SalaryStructure(120000, 25000, 8000, 7000, 6000, 2000, new com.payroll.strategy.StandardTaxStrategy()));
    }

    public static PayrollProcessor getInstance() { return instance; }

    public void registerObserver(IObserver observer) { observers.add(observer); }
    public void notifyObservers() {
        for (IObserver obs : observers) obs.update();
    }

    @Override
    public void setSalaryStructure(Employee emp, SalaryStructure struct) {
        salaryStructures.put(emp.getId(), struct);
    }

    public SalaryStructure getSalaryStructure(String empId) {
        return salaryStructures.get(empId);
    }

    public void removeSalaryStructure(String empId) {
        salaryStructures.remove(empId);
        payrollHistory.removeIf(rec -> rec.getEmployeeId().equalsIgnoreCase(empId));
    }

    @Override
    public void runMonthlyPayroll(String month, int year) {
        payrollHistory.clear(); // Fresh calculation run
        EmployeeManager em = EmployeeManager.getInstance();
        for (Employee emp : em.getAllEmployees()) {
            SalaryStructure struct = salaryStructures.get(emp.getId());
            if (struct == null) continue;

            double gross = struct.getGrossSalary();
            double tax = struct.getTaxStrategy().calculateTax(gross);
            double net = gross - tax - struct.getStaticDeductions();

            PayrollRecord record = new PayrollRecord(emp.getId(), month, year, gross, tax, 0.0, 0.0, net);
            payrollHistory.add(record);
        }
        notifyObservers();
    }

    @Override
    public double calculateNetSalary(Employee emp, String month) {
        for (PayrollRecord rec : payrollHistory) {
            if (rec.getEmployeeId().equals(emp.getId()) && rec.getMonth().equalsIgnoreCase(month)) {
                return rec.getNetSalary();
            }
        }
        return 0.0;
    }

    public List<PayrollRecord> getPayrollHistory() { return payrollHistory; }
}