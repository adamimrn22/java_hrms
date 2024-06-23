package com.hrms.Department;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Position {
    private String positionID;
    private String positionName;
    private Department department;

    private static final String positionData = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\position.txt";
    private static ArrayList<Position> positions = new ArrayList<Position>();

    public Position() {
        loadPositions();
    }

    public Position(String positionID, String positionName, Department department) {
        this.positionID = positionID;
        this.positionName = positionName;
        this.department = department;
    }

    public String getPositionID() {
        return this.positionID;
    }

    public void setPositionID(String positionID) {
        this.positionID = positionID;
    }

    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return String.format("%-20s| %-25s| %-15s", getPositionID(), getPositionName(), getDepartment().getDeptName());
    }

    private void loadPositions() {
        positions.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(positionData))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String positionID = parts[0].trim();
                    String positionName = parts[1].trim();
                    String departmentID = parts[2].trim();

                    Department dept = Department.getDepartmentById(departmentID);

                    if (dept != null) {
                        Position position = new Position(positionID, positionName, dept);
                        positions.add(position);
                    } else {
                        System.out.println("Department with ID " + departmentID + " not found.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading position data from file: " + e.getMessage());
        }
    }

    public static Position getPositionByID(String posID) {
        for (Position pos : positions) {
            if (pos.getPositionID().equalsIgnoreCase(posID)) {
                return pos;
            }
        }
        return null;
    }

    public static void displayPositions() {
        System.out.println("Position ID         | Position Name            | Department Name ");
        System.out.println("-----------------------------------------------------------");

        for (Position position : positions) {
            System.out.println(position);
        }

        System.out.println();
    }
}
