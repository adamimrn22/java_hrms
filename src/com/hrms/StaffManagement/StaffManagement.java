package com.hrms.StaffManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.hrms.Users.Staff;
import com.hrms.Utils.Util;
import com.hrms.Department.Position;
import com.hrms.Employee.Address;
import com.hrms.Employee.Name;
import com.hrms.Employee.RoleType;

public class StaffManagement {

    private final String empDataPath = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\empData.txt";
    private final String tempDataPath = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\tempData.txt";

    public StaffManagement() {
    };

    public void manageStaffMenu() {
        Util.clearMenu();
        Scanner s = new Scanner(System.in);
        int opt = 0;
        try {
            while (opt != 5) {
                try {
                    System.out.println("Manage Staff Menu");
                    System.out.println("1. View Employees");
                    System.out.println("2. Add Employee");
                    System.out.println("3. Update Employee");
                    System.out.println("4. Delete Employee");
                    System.out.println("5. Back to Main Menu");

                    System.out.print("Enter option number (1-5): ");
                    opt = s.nextInt();
                    s.nextLine();

                    switch (opt) {
                        case 1:
                            viewStaff();
                            break;
                        case 2:
                            addEmployee();
                            break;
                        case 3:
                            updateEmployee();
                            break;
                        case 4:
                            deleteEmployee();
                            break;
                        case 5:
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

    private void viewStaff() {
        Util.clearMenu();
        Staff.displayAllStaff();
    }

    private void addEmployee() {
        Util.clearMenu();
        Scanner s = new Scanner(System.in);

        String empID = "";
        String firstName = "";
        String lastName = "";
        String dob = "";
        String gender = "";
        String contactNumber = "";
        String email = "";
        String street = "";
        String postal = "";
        String state = "";
        String country = "";
        Position pos = null;

        try {
            boolean validEmpID = false;
            while (!validEmpID) {
                try {
                    System.out.print("Enter Employee ID: ");
                    empID = s.nextLine().toUpperCase();
                    Staff.checkExistEmpID(empID);
                    validEmpID = true;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.print("Enter first name: ");
            firstName = s.nextLine().toUpperCase();

            System.out.print("Enter last name: ");
            lastName = s.nextLine().toUpperCase();

            System.out.print("Enter date of birth (dd/MM/yyyy): ");
            dob = s.nextLine().toUpperCase();

            boolean validGender = false;
            while (!validGender) {
                System.out.print("Enter gender (MALE/FEMALE): ");
                gender = s.nextLine().toUpperCase();
                if (gender.equals("MALE") || gender.equals("FEMALE")) {
                    validGender = true; // Exit loop if valid gender is entered
                } else {
                    System.out.println("Invalid gender. Please enter 'MALE' or 'FEMALE'.");
                }
            }

            System.out.print("Enter contact info: ");
            contactNumber = s.nextLine();

            System.out.print("Enter email: ");
            email = s.nextLine();

            System.out.print("Street: ");
            street = s.nextLine();

            System.out.print("Postal Code: ");
            postal = s.nextLine();

            System.out.print("State: ");
            state = s.nextLine();

            System.out.print("Country: ");
            country = s.nextLine();

            Position.displayPositions();

            boolean validPositionID = false;
            while (!validPositionID) {
                try {
                    System.out.print("Enter Position ID: ");
                    String positionID = s.nextLine();
                    pos = Position.getPositionByID(positionID);
                    if (pos == null) {
                        throw new IllegalArgumentException("Invalid Position ID. Please try again.");
                    }
                    validPositionID = true;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            Address address = new Address(empID, street, postal, state, country);

            if (!gender.equals("MALE") && !gender.equals("FEMALE")) {
                throw new IllegalArgumentException("Invalid gender: " + gender);
            }

            String deptID = pos.getDepartment().getDeptID();

            Name name = new Name(firstName, lastName);

            Staff staff = new Staff(empID, empID, name, contactNumber, email, dob, gender, pos, RoleType.STAFF,
                    address);

            Staff.staffList.add(staff);
            Address.addresses.add(address);

            writeEmployeeToFile(empID, firstName, lastName, dob, gender, contactNumber, email, pos.getPositionID(),
                    deptID, "STAFF");

            System.out.println("Employee added successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please enter valid data.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeEmployeeToFile(String empID, String firstName, String lastName, String dob, String gender,
            String contactNumber, String email, String positionID, String deptID, String roleType) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(empDataPath, true))) {
            String line = String.format("%-10s | %-15s | %-15s | %-15s | %-8s | %-10s | %-15s | %-10s | %-10s | %-10s",
                    empID, firstName, lastName, dob, gender, contactNumber, email, positionID, deptID, roleType);
            writer.write(line + "\n");
            System.out.println("Employee data written to empData.txt.");
        } catch (IOException e) {
            System.err.println("Error writing to empData.txt: " + e.getMessage());
        }
    }

    private void updateEmployee() {
        Util.clearMenu();
        Scanner s = new Scanner(System.in);

        String empID = "";
        String firstName = "";
        String lastName = "";
        String dob = "";
        String gender = "";
        String contactNumber = "";
        String email = "";
        String street = "";
        String postal = "";
        String state = "";
        String country = "";
        Position pos = null;

        try {
            System.out.print("Enter Employee ID to update: ");
            empID = s.nextLine().toUpperCase();

            Staff staff = Staff.getEmpByID(empID);
            if (staff == null) {
                throw new IllegalArgumentException("Employee ID not found.");
            }

            System.out.print("Enter new first name (leave blank to keep current): ");
            firstName = s.nextLine().toUpperCase();
            if (!firstName.isEmpty()) {
                staff.getName().setFirstName(firstName);
            }

            System.out.print("Enter new last name (leave blank to keep current): ");
            lastName = s.nextLine().toUpperCase();
            if (!lastName.isEmpty()) {
                staff.getName().setLastName(lastName);
            }

            System.out.print("Enter new date of birth (dd/MM/yyyy, leave blank to keep current): ");
            dob = s.nextLine().toUpperCase();
            if (!dob.isEmpty()) {
                staff.setDob(dob);
            }

            boolean validGender = false;
            while (!validGender) {
                System.out.print("Enter new gender (MALE/FEMALE, leave blank to keep current): ");
                gender = s.nextLine().toUpperCase();
                if (gender.isEmpty() || gender.equals("MALE") || gender.equals("FEMALE")) {
                    if (!gender.isEmpty()) {
                        staff.setGender(gender);
                    }
                    validGender = true;
                } else {
                    System.out.println("Invalid gender. Please enter 'MALE' or 'FEMALE'.");
                }
            }

            System.out.print("Enter new contact info (leave blank to keep current): ");
            contactNumber = s.nextLine();
            if (!contactNumber.isEmpty()) {
                staff.setContactNumber(contactNumber);
            }

            System.out.print("Enter new email (leave blank to keep current): ");
            email = s.nextLine();
            if (!email.isEmpty()) {
                staff.setEmailAddress(email);
            }

            Address address = staff.getAddress();

            System.out.print("Enter new street (leave blank to keep current): ");
            street = s.nextLine();
            if (!street.isEmpty()) {
                address.setStreet(street);
            }

            System.out.print("Enter new postal code (leave blank to keep current): ");
            postal = s.nextLine();
            if (!postal.isEmpty()) {
                address.setPostalCode(postal);
            }

            System.out.print("Enter new state (leave blank to keep current): ");
            state = s.nextLine();
            if (!state.isEmpty()) {
                address.setState(state);
            }

            System.out.print("Enter new country (leave blank to keep current): ");
            country = s.nextLine();
            if (!country.isEmpty()) {
                address.setCountry(country);
            }

            Position.displayPositions();

            boolean validPositionID = false;
            while (!validPositionID) {
                System.out.print("Enter new Position ID (leave blank to keep current): ");
                String positionID = s.nextLine();
                if (positionID.isEmpty()) {
                    validPositionID = true;
                } else {
                    try {
                        pos = Position.getPositionByID(positionID);
                        if (pos == null) {
                            throw new IllegalArgumentException("Invalid Position ID. Please try again.");
                        }
                        staff.setPosition(pos);
                        validPositionID = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            updateEmployeeInFile();
            System.out.println("Employee updated successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please enter valid data.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateEmployeeInFile() {
        File tempFile = new File(tempDataPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (Staff staff : Staff.staffList) {
                String empID = staff.getEmpID();
                String firstName = staff.getName().getFirstName();
                String lastName = staff.getName().getLastName();
                String dob = staff.getDob();
                String gender = staff.getGender();
                String contactNumber = staff.getContactNumber();
                String email = staff.getEmailAddress();
                String positionID = staff.getPosition().getPositionID();
                String deptID = staff.getPosition().getDepartment().getDeptID();
                String roleType = staff.getRoleType().toString();

                String line = String.format(
                        "%-10s | %-15s | %-15s | %-15s | %-8s | %-10s | %-15s | %-10s | %-10s | %-10s",
                        empID, firstName, lastName, dob, gender, contactNumber, email, positionID, deptID, roleType);

                writer.write(line + "\n");
            }

            System.out.println("Employee data file updated");

        } catch (IOException e) {
            System.err.println("Error updating file" + e.getMessage());
        }

        Util.deleteAndReplaceFile(tempFile, new File(empDataPath));
    }

    private void deleteEmployee() {
        Util.clearMenu();
        Scanner s = new Scanner(System.in);
        String empID = "";
        try {
            System.out.print("Enter Employee ID to delete: ");
            empID = s.nextLine().toUpperCase();

            Staff staffToDelete = Staff.getEmpByID(empID);

            if (staffToDelete != null) {
                Staff.staffList.remove(staffToDelete);
                System.out.println("Employee with ID " + empID + " deleted from staffList.");
            } else {
                System.out.println("Employee with ID " + empID + " not found in staffList.");
                return;
            }

            deleteEmployeeFromFile(empID);
        } catch (Exception e) {
            System.err.println("An error occurred during deletion: " + e.getMessage());
        }
    }

    private void deleteEmployeeFromFile(String empIDToDelete) {
        File tempFile = new File(tempDataPath);

        try (BufferedReader reader = new BufferedReader(new FileReader(empDataPath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean deleted = false;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");

                for (int i = 0; i < fields.length; i++) {
                    fields[i] = fields[i].trim();
                }

                String empID = fields[0].trim();

                if (empID.equals(empIDToDelete)) {
                    deleted = true;
                } else {
                    writer.write(line + System.lineSeparator());
                }
            }

            if (!deleted) {
                System.out.println("Employee with ID " + empIDToDelete + " not found in file");
            } else {
                System.out.println("Employee with ID " + empIDToDelete + " deleted from file");
            }

        } catch (IOException e) {
            System.err.println("Error deleting employee from file: " + e.getMessage());
        }

        // Replace original file with temporary file
        Util.deleteAndReplaceFile(tempFile, new File(empDataPath));
    }
}
