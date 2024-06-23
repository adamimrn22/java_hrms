package com.hrms.Leave;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.hrms.Utils.Auth;
import com.hrms.Utils.Util;

public class LeaveApplication {

    public LeaveApplication() {
        // Leave.loadLeaveData();
    }

    public static void leaveMenu() {
        Scanner s = new Scanner(System.in);

        int opt = 0;
        try {
            while (opt != 4) {
                System.out.println("Leave Management Menu");
                System.out.println("1. Add Leave Request");
                System.out.println("2. View Approved Leave Requests");
                System.out.println("3. View Pending Leave Requests");
                System.out.println("4. Exit");
                System.out.print("Enter option number (1-4): ");
                opt = s.nextInt();
                s.nextLine();

                switch (opt) {
                    case 1:
                        Util.clearMenu();
                        addLeaveRequest();
                        break;
                    case 2:
                        viewLeaveRequests(LeaveStatus.APPROVED);
                        break;
                    case 3:
                        viewLeaveRequests(LeaveStatus.PENDING);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please enter a number from 1 to 4.");
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong..");
        }
        s.close();
    }

    private static void addLeaveRequest() {
        try {
            Scanner s = new Scanner(System.in);
            String empID = Auth.loggedInEmpID;

            if (empID == null || empID.isEmpty()) {
                System.out.println("Error: No authenticated user found.");
                return;
            }

            String leaveID = "L000" + (Leave.leaveList.size() + 1);

            Date startDate = null;
            Date endDate = null;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while (startDate == null) {
                try {
                    System.out.print("Enter Start Date (YYYY-MM-DD): ");
                    String startDateInput = s.nextLine();
                    startDate = dateFormat.parse(startDateInput);
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                }
            }

            while (endDate == null || endDate.before(startDate)) {
                try {
                    System.out.print("Enter End Date (YYYY-MM-DD): ");
                    String endDateInput = s.nextLine();
                    endDate = dateFormat.parse(endDateInput);

                    if (endDate.before(startDate)) {
                        System.out
                                .println("Error: End date cannot be before start date. Please enter a valid end date.");
                    }
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                }
            }

            double duration = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24) + 1;

            String startDateString = dateFormat.format(startDate);
            String endDateString = dateFormat.format(endDate);

            Leave newLeave = new Leave(leaveID, empID, startDateString, endDateString, duration, LeaveStatus.PENDING);
            Leave.leaveList.add(newLeave);
            LeaveManagement.writeLeavesToFile();

            System.out.println("Leave request added successfully.");

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void viewLeaveRequests(LeaveStatus status) {
        String empID = Auth.loggedInEmpID;

        if (empID == null || empID.isEmpty()) {
            System.out.println("Error: No authenticated user found.");
            return;
        }

        Util.clearMenu();

        System.out.printf("%-10s | %-10s | %-15s | %-15s | %-10s | %-10s\n",
                "LEAVE ID", "STAFF ID", "START DATE", "END DATE", "LEAVE DAYS", "STATUS");
        System.out.println("------------------------------------------------------------------------");

        for (Leave leave : Leave.leaveList) {
            if (leave.getEmpID().equals(empID) && leave.getStatus() == status) {
                System.out.printf("%-10s | %-10s | %-15s | %-15s | %-10.2f | %-10s\n",
                        leave.getLeaveID(), leave.getEmpID(), leave.getStartDate(), leave.getEndDate(),
                        leave.getLeaveDays(), leave.getStatus());
            }
        }
        System.out.println("\n");
    }

}