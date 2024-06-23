package com.hrms.Leave;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.hrms.Utils.Util;

public class LeaveManagement {

    public static final String leaveTempPath = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\leaveTemp.txt";

    public void manageLeaveMenu() {
        Util.clearMenu();
        Scanner s = new Scanner(System.in);
        int opt = 0;
        try {
            while (opt != 5) {
                System.out.println("Manage Leave Menu");
                System.out.println("1. View Approved Leaves");
                System.out.println("2. View Pending Leaves");
                System.out.println("3. Approve Leave");
                System.out.println("4. Reject Leave");
                System.out.println("5. Back to Main Menu");

                System.out.print("Enter option number (1-5): ");
                opt = s.nextInt();
                s.nextLine();

                switch (opt) {
                    case 1:
                        viewApprovedLeaves();
                        break;
                    case 2:
                        viewPendingLeaves();
                        break;
                    case 3:
                        approveLeave();
                        break;
                    case 4:
                        rejectLeave();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid option. Please enter a number from 1 to 5.");
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong..");
        }
        s.close();
    }

    public void viewApprovedLeaves() {
        Util.clearMenu();
        System.out.printf("%-10s | %-10s |  %-10s |  %-10s| %-10s | %-10s\n", "LEAVE ID", "STAFF ID", "Start Date",
                "End Date", "Leave Days",
                "Status");
        System.out.println("--------------------------------------------------------------");
        for (Leave leave : Leave.leaveList) {
            if (leave.getStatus() == LeaveStatus.APPROVED) {
                System.out.printf("%-10s | %-10s |  %-10s |  %-10s| %-10s | %-10s\n",
                        leave.getLeaveID(), leave.getEmpID(), leave.getStartDate(), leave.getEndDate(),
                        leave.getLeaveDays(), leave.getStatus());
            }
        }
        System.out.println("\n");
    }

    public void viewPendingLeaves() {
        Util.clearMenu();
        System.out.printf("%-10s | %-10s |  %-10s |  %-10s| %-10s | %-10s\n", "LEAVE ID", "STAFF ID", "Start Date",
                "End Date", "Leave Days",
                "Status");
        System.out.println("--------------------------------------------------------------");
        for (Leave leave : Leave.leaveList) {
            if (leave.getStatus() == LeaveStatus.PENDING) {
                System.out.printf("%-10s | %-10s |  %-10s |  %-10s| %-10s | %-10s\n",
                        leave.getLeaveID(), leave.getEmpID(), leave.getStartDate(), leave.getEndDate(),
                        leave.getLeaveDays(), leave.getStatus());
            }
        }
        System.out.println("\n");
    }

    public void approveLeave() {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter Leave ID to approve leave: ");
        String leaveID = s.nextLine().toUpperCase();

        Leave leaveToApprove = getLeaveByLeaveID(leaveID);
        if (leaveToApprove != null) {
            leaveToApprove.setStatus(LeaveStatus.APPROVED);
            writeLeavesToFile();
            System.out.println("Leave approved for leave ID " + leaveID);
        } else {
            System.out.println("No pending leave found for leave ID " + leaveID);
        }
    }

    public void rejectLeave() {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter Leave ID to reject leave: ");
        String leaveID = s.nextLine().toUpperCase();

        Leave leaveToReject = getLeaveByLeaveID(leaveID);
        if (leaveToReject != null) {
            leaveToReject.setStatus(LeaveStatus.REJECTED);
            writeLeavesToFile();
            System.out.println("Leave rejected for leave ID " + leaveID);
        } else {
            System.out.println("No pending leave found for leave ID " + leaveID);
        }
    }

    private Leave getLeaveByLeaveID(String leaveID) {
        for (Leave leave : Leave.leaveList) {
            if (leave.getLeaveID().equalsIgnoreCase(leaveID)) {
                return leave;
            }
        }
        return null;
    }

    public static void writeLeavesToFile() {
        File tempFile = new File(leaveTempPath);
        File originalFile = new File(Leave.leaveDataPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (Leave leave : Leave.leaveList) {
                String line = String.format("%s | %s | %s | %s  | %.2f | %s",
                        leave.getLeaveID(),
                        leave.getEmpID(),
                        leave.getStartDate(),
                        leave.getEndDate(),
                        leave.getLeaveDays(),
                        leave.getStatus());
                writer.write(line + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing to tempLeaveData.txt: " + e.getMessage());
        }

        Util.deleteAndReplaceFile(tempFile, originalFile);
    }

}
