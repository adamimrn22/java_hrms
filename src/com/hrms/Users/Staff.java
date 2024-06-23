package com.hrms.Users;

import com.hrms.Employee.Employee;
import com.hrms.Employee.EmployeeInfo;
import com.hrms.Employee.Name;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.hrms.Department.Position;
import com.hrms.Employee.RoleType;
import com.hrms.Leave.LeaveApplication;
import com.hrms.Salary.Salary;
import com.hrms.Utils.Auth;
import com.hrms.Utils.Util;
import com.hrms.Employee.Address;

public class Staff extends Employee implements EmployeeInfo {

    private Name name;
    private String contactNumber;
    private String emailAddress;
    private String dob;
    private String gender;
    private Address address;

    private static final String empDataPath = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\empData.txt";

    public static ArrayList<Staff> staffList = new ArrayList<Staff>();

    public Staff() {
        loadStaff();
    }

    public Staff(String empID, String pass, Name name, String contactNumber,
            String emailAddress, String dob, String gender, Position pos, RoleType role, Address address) {
        super(empID, pass, pos, role);
        this.name = name;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
    }

    public Name getName() {
        return this.name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDob() {
        return this.dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    private void loadStaff() {
        staffList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(empDataPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 10) {
                    String empID = parts[0].trim();
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    String dob = parts[3].trim();
                    String gender = parts[4].trim();
                    String contactNumber = parts[5].trim();
                    String emailAddress = parts[6].trim();
                    String positionID = parts[7].trim();
                    String roleTypeStr = parts[9].trim().toUpperCase();

                    Position position = Position.getPositionByID(positionID);

                    RoleType roleType;
                    try {
                        roleType = RoleType.valueOf(roleTypeStr);
                    } catch (Exception e) {
                        continue;
                    }

                    Name name = new Name(firstName, lastName);
                    Address address = Address.getAddressByEmpID(empID);

                    Staff staff = new Staff(empID, empID, name, contactNumber, emailAddress, dob, gender, position,
                            roleType, address);

                    staffList.add(staff);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading employee data from file: " + e.getMessage());
        }
    }

    public static void displayAllStaff() {
        if (staffList.isEmpty()) {
            System.out.println("No staff records found.");
        } else {
            System.out.println("All Staff Members:");
            System.out.println("==================");
            for (Staff staff : staffList) {
                System.out.println("------------------");
                System.out.println(staff.toString());
            }
        }
    }

    public static Staff getEmpByID(String staffID) {
        for (Staff staff : staffList) {
            if (staff.getEmpID().equalsIgnoreCase(staffID)) {
                return staff;
            }
        }
        return null;
    }

    public static void checkExistEmpID(String empID) {
        for (Staff staff : staffList) {
            if (staff.getEmpID().equals(empID)) {
                throw new IllegalArgumentException("Employee ID already exists. Please enter a different ID.");
            }
        }
    }

    @Override
    public String toString() {
        return "Name: " + name.toString() + "\n" +
                "ID: " + super.getEmpID() + "\n" +
                "Gender: " + gender + "\n" +
                "Contact Number: " + contactNumber + "\n" +
                "Email Address: " + emailAddress + "\n" +
                "Date of Birth: " + dob + "\n" +
                "Address: " + address + "\n" +
                "Position: " + super.getPosition().getPositionName() + "\n" +
                "Department: " + super.getPosition().getDepartment().getDeptName() + "\n";
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
                    System.out.println("1. View Personal Info");
                    System.out.println("2. Request Leave");
                    System.out.println("3. View Salary");
                    System.out.println("4. Logout");

                    System.out.print("Enter option number (1-4): ");
                    opt = s.nextInt();
                    s.nextLine();

                    switch (opt) {
                        case 1:
                            Util.clearMenu();
                            viewDetails();
                            break;
                        case 2:
                            Util.clearMenu();
                            LeaveApplication.leaveMenu();
                            break;
                        case 3:
                            Util.clearMenu();
                            viewSalary();
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
        Auth.logout();
    }

    public void viewSalary() {
        Scanner s = new Scanner(System.in);
        String empID = Auth.loggedInEmpID;
        Salary salary = Salary.getSalaryByEmpID(empID);
        Staff staff = Staff.getEmpByID(empID);

        if (salary != null && staff != null) {
            System.out.println("=============================================");
            System.out.println("Employee ID :" + staff.getEmpID());
            System.out.println(staff.getName());
            System.out.println(salary);
        } else {
            System.out.println("Error: Salary information not found.");
        }

        System.out.println("Press Enter to return to the main menu...");
        s.nextLine();
    }

    @Override
    public void viewDetails() {
        Scanner s = new Scanner(System.in);
        String empID = Auth.loggedInEmpID;
        Staff user = getEmpByID(empID);

        if (user != null) {
            System.out.println("Staff Information: " + empID);
            System.out.println("=========================================");
            System.out.println(user);
            System.out.println("=========================================");
        } else {
            System.out.println("Error: Employee not found.");
        }

        System.out.println("Press Enter to return to the main menu...");
        s.nextLine();
    }
}
