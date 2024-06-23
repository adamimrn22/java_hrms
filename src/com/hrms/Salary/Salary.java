package com.hrms.Salary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.hrms.Users.Staff;
import com.hrms.Utils.Auth;

public class Salary {
    private String empID;
    private double grossSalary;
    private double netSalary;

    private final double kwspEmployeeDeduct = 0.11;
    private final double kwspEmployerDeduct = 0.13;

    public static final String salaryDataPath = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\salary.txt";

    public static ArrayList<Salary> salaryList = new ArrayList<>();

    public Salary() {
        loadSalary();
    }

    public Salary(String empID, double grossSalary, double netSalary) {
        this.empID = empID;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
    }

    public Salary(String empID, double grossSalary) {
        this.empID = empID;
        this.grossSalary = grossSalary;
        calculateNetSalary();
    }

    public static void loadSalary() {
        salaryList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(salaryDataPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String empID = parts[0].trim();
                    double grossSalary = Double.parseDouble(parts[1].trim());
                    double netSalary = Double.parseDouble(parts[2].trim());

                    Salary salary = new Salary(empID, grossSalary, netSalary);
                    salaryList.add(salary);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading salary data from file: " + e.getMessage());
        }
    }

    public String getEmpID() {
        return this.empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    private void calculateNetSalary() {
        this.netSalary = grossSalary - getEmployeeAmountDeduct();
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
        calculateNetSalary();
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public double getKwspEmployeeDeduct() {
        return kwspEmployeeDeduct;
    }

    public double getKwspEmployerDeduct() {
        return kwspEmployerDeduct;
    }

    public double getEmployerAmountDeduct() {
        return grossSalary * kwspEmployerDeduct;
    }

    public double getEmployeeAmountDeduct() {
        return grossSalary * kwspEmployeeDeduct;
    }

    public void updateNetSalary(double newNetSalary) {
        this.netSalary = newNetSalary;
    }

    public static void viewEmployeeSalary() {
        System.out.printf("%-10s | %-15s | %-13s | %-10s\n", "STAFF ID", "Name", "Gross Salary", "Net Salary");
        System.out.println("--------------------------------------------------------------");
        for (Salary record : salaryList) {
            Staff staff = Staff.getEmpByID(record.getEmpID());
            if (staff != null) {
                System.out.printf("%-10s | %-15s | %-13.2f | %-10.2f\n",
                        staff.getEmpID(),
                        staff.getName().getFirstName() + " " + staff.getName().getLastName(),
                        record.getGrossSalary(),
                        record.getNetSalary());
            }
        }
        System.err.println("\n\n");
    }

    @Override
    public String toString() {
        return "Gross Salary : RM" + grossSalary + "\n" +
                "Net Salary : RM" + netSalary + "\n" +
                "KWSP Company Contribution  : RM" + getEmployerAmountDeduct() + "\n" +
                "KWSP Employee Contribution : RM" + getEmployeeAmountDeduct() + "\n" +
                "=============================================";
    }

    public static Salary getSalaryByEmpID(String empID) {
        for (Salary salary : salaryList) {
            if (salary.getEmpID().equals(empID)) {
                return salary;
            }
        }
        return null;
    }
}
