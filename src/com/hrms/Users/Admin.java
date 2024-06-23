package com.hrms.Users;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.hrms.Employee.Employee;
import com.hrms.Leave.LeaveManagement;
import com.hrms.Salary.SalaryManagement;
import com.hrms.Utils.Auth;
import com.hrms.Utils.Util;
import com.hrms.StaffManagement.StaffManagement;

public class Admin extends Employee {

    public final StaffManagement staff = new StaffManagement();
    public final SalaryManagement salary = new SalaryManagement();
    public final LeaveManagement leave = new LeaveManagement();

    public Admin() {
    };

    public Admin(String empID, String password) {
        super(empID, password);
    }

    @Override
    public void printMenu() {
        Util.clearMenu();
        Scanner s = new Scanner(System.in);
        int opt = 0;
        try {
            while (opt != 4) {
                Util.clearMenu();
                try {
                    System.out.println("1. Manage Staff");
                    System.out.println("2. Manage Leave");
                    System.out.println("3. Manage Salary");
                    System.out.println("4. Logout");

                    System.out.print("Enter option number (1-4): ");
                    opt = s.nextInt();

                    switch (opt) {
                        case 1:
                            staff.manageStaffMenu();
                            break;
                        case 2:
                            leave.manageLeaveMenu();
                            break;
                        case 3:
                            salary.manageSalaryMenu();
                            break;
                        default:
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    s.nextLine();
                }

            }
        } catch (Exception e) {
            System.out.println("Something went wrong.." + e.getMessage());
        }
        s.close();
        Auth.logout();
    }

}
