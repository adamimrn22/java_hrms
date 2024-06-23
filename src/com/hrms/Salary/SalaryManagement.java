package com.hrms.Salary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.hrms.Users.Staff;
import com.hrms.Utils.Util;

public class SalaryManagement {

    public final String salaryTempPath = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\salaryTemp.txt";

    public SalaryManagement() {
    }

    public void manageSalaryMenu() {
        Util.clearMenu();
        Scanner s = new Scanner(System.in);
        int opt = 0;
        try {
            while (opt != 5) {
                try {
                    System.out.println("Manage Salary Menu");
                    System.out.println("1. View Employees Salary");
                    System.out.println("2. Add Employees Salary");
                    System.out.println("3. Update Employee Salary");
                    System.out.println("4. Back to Main Menu");

                    System.out.print("Enter option number (1-4): ");
                    opt = s.nextInt();
                    s.nextLine();

                    switch (opt) {
                        case 1:
                            viewEmployeeSalary();
                            break;
                        case 2:
                            addEmployeeSalary();
                            break;
                        case 3:
                            updateEmployeeSalary();
                            break;
                        case 4:
                            return;
                        default:
                            System.out.println("Invalid option. Please enter a number from 1 to 5.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    s.nextLine();
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong..");
        }
        s.close();
    }

    public void viewEmployeeSalary() {
        Util.clearMenu();
        Salary.viewEmployeeSalary();
    }

    public void addEmployeeSalary() {
        Scanner s = new Scanner(System.in);
        try {
            System.out.print("Enter Employee ID: ");
            String empID = s.nextLine().toUpperCase();

            Staff staff = Staff.getEmpByID(empID);
            if (staff == null) {
                System.out.println("Employee with ID " + empID + " does not exist.");
                return;
            }

            for (Salary salary : Salary.salaryList) {
                if (salary.getEmpID().equals(empID)) {
                    System.out.println("Salary record for employee with ID " + empID + " already exists.");
                    return;
                }
            }

            System.out.print("Enter Gross Salary: ");
            double grossSalary = s.nextDouble();

            Salary newSalary = new Salary(empID, grossSalary);
            Salary.salaryList.add(newSalary);
            writeSalariesToFile();

            Util.clearMenu();
            viewEmployeeSalary();
            System.out.println("Employee salary added successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            s.nextLine();
        }
    }

    public void updateEmployeeSalary() {
        Scanner s = new Scanner(System.in);
        try {

            System.out.print("Enter Employee ID: ");
            String empID = s.nextLine().toUpperCase();

            Salary salaryToUpdate = null;
            for (Salary salary : Salary.salaryList) {
                if (salary.getEmpID().equals(empID)) {
                    salaryToUpdate = salary;
                    break;
                }
            }

            if (salaryToUpdate == null) {
                System.out.println("Salary record for employee with ID " + empID + " does not exist.");
                return;
            }

            System.out.println("Update gross or net? (1 For gross, 2 for net)");
            System.out.print("Option: ");
            int opt = s.nextInt();

            if (opt == 1) {
                System.out.print("Enter New Gross Salary: ");
                double newGrossSalary = s.nextDouble();

                salaryToUpdate.setGrossSalary(newGrossSalary);
                writeSalariesToFile();
            } else if (opt == 2) {
                System.out.print("Enter New Net Salary: ");
                double newNetSalary = s.nextDouble();

                salaryToUpdate.setNetSalary(newNetSalary);
                writeSalariesToFile();
            } else {
                System.out.println("Invalid option. Please enter 1 for gross or 2 for net.");
                return;
            }

            Util.clearMenu();
            viewEmployeeSalary();
            System.out.println("Employee salary updated successfully.\n");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            s.nextLine();
        }
    }

    private void writeSalariesToFile() {
        File tempFile = new File(salaryTempPath);
        File originalFile = new File(Salary.salaryDataPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (Salary salary : Salary.salaryList) {
                String line = String.format("%s | %.2f | %.2f",
                        salary.getEmpID(),
                        salary.getGrossSalary(),
                        salary.getNetSalary());
                writer.write(line + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing to tempSalaryData.txt: " + e.getMessage());
        }

        Util.deleteAndReplaceFile(tempFile, originalFile);
    }
}
