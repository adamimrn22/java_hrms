package com.hrms.Leave;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Leave {
    private String leaveID;
    private String empID;
    private String startDate;
    private String endDate;
    private double leaveDays;
    private LeaveStatus status;

    public static final String leaveDataPath = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\leave.txt";;
    public static ArrayList<Leave> leaveList = new ArrayList<>();

    public Leave() {
        loadLeaveData();
    }

    // public Leave(String leaveID, String empID, double leaveDays, String status) {
    // this.leaveID = leaveID;
    // this.empID = empID;
    // this.leaveDays = leaveDays;
    // this.status = status;
    // }

    public Leave(String leaveID, String empID, String startDate, String endDate, double leaveDays,
            LeaveStatus status) {
        this.leaveID = leaveID;
        this.empID = empID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveDays = leaveDays;
        this.status = status;
    }

    public String getLeaveID() {
        return leaveID;
    }

    public void setLeaveID(String leaveID) {
        this.leaveID = leaveID;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(double leaveDays) {
        this.leaveDays = leaveDays;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public static void loadLeaveData() {
        leaveList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(leaveDataPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {

                    String leaveID = parts[0].trim();
                    String empID = parts[1].trim();
                    String startDate = parts[2].trim();
                    String endDate = parts[3].trim();

                    double duration = Double.parseDouble(parts[4].trim());
                    LeaveStatus status = LeaveStatus.valueOf(parts[5].trim());
                    Leave leave = new Leave(leaveID, empID, startDate, endDate, duration, status);
                    leaveList.add(leave);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading leave data from file: " + e.getMessage());
        }

        for (Leave leave : leaveList) {
            leave.toString();
        }
    }

}
