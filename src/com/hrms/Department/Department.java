package com.hrms.Department;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Department {
    private String deptID;
    private String deptName;

    private static final String departmentData = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\department.txt";

    public static ArrayList<Department> departments = new ArrayList<Department>();

    public Department() {
        loadDepartments();
    }

    public Department(String deptID, String deptName) {
        this.deptID = deptID;
        this.deptName = deptName;
    }

    public String getDeptID() {
        return this.deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    private void loadDepartments() {
        departments.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(departmentData))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String deptId = parts[0].trim();
                    String deptName = parts[1].trim();

                    Department department = new Department(deptId, deptName);
                    departments.add(department);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading department data from file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format("%-18s| %-20s", getDeptID(), getDeptName());
    }

    public static Department getDepartmentById(String deptID) {
        for (Department dept : departments) {
            if (dept.getDeptID().equalsIgnoreCase(deptID)) {
                return dept;
            }
        }
        return null;
    }

    public static void displayAllDepartments() {
        System.out.println("Department ID     | Department Name");
        System.out.println("----------------------------------------");

        for (Department department : departments) {
            System.out.println(department);
        }
        System.out.println();
    }
}
