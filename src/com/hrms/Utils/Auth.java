package com.hrms.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.hrms.Employee.RoleType;
import com.hrms.Users.Admin;
import com.hrms.Users.Staff;

public class Auth {
    private static final String empData = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\empData.txt";
    private static final String authData = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\auth.txt";

    public static String loggedInEmpID = null;

    private static final Admin admin = new Admin();
    private static final Staff staff = new Staff();

    public static void loginMenu() {
        Scanner s = new Scanner(System.in);
        String id = null;
        String password = null;

        try {
            System.out.println("Welcome! Please Login..");
            System.out.print("Username: ");
            id = s.nextLine();
            System.out.print("Enter password: ");
            password = s.nextLine();

            RoleType roleType = authenticateUser(id, password);

            if (roleType != null) {
                System.out.println(roleType);

                System.out.println("Login successful as " + roleType);
                createAuthFile(loggedInEmpID);

                switch (roleType) {
                    case ADMIN:
                        loadAuthFile();
                        adminMenu();
                        break;
                    case STAFF:
                        loadAuthFile();
                        staffMenu();
                        break;
                    default:
                        System.out.println("Invalid role type.");
                }
            } else {
                System.out.println("Invalid Credentials");
            }
        } catch (Exception e) {
            System.err.println("Something went wrong: " + e.getMessage());
        }

        s.close();
    }

    private static RoleType authenticateUser(String id, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(empData))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 10) {
                    String userId = parts[0].trim();
                    String userPassword = parts[0].trim();
                    RoleType roleType = RoleType.valueOf(parts[9].trim());

                    if (userId.equals(id) && userPassword.equals(password)) {
                        Auth.loggedInEmpID = userId;
                        return roleType;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null;
    }

    private static void createAuthFile(String empID) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(authData))) {
            writer.write(empID);
        } catch (IOException e) {
            System.err.println("Error writing auth file: " + e.getMessage());
        }

    }

    private static void loadAuthFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(authData))) {
            loggedInEmpID = reader.readLine();
        } catch (IOException e) {
            System.err.println("Error reading auth file: " + e.getMessage());
        }
    }

    public static void adminMenu() {
        admin.printMenu();
    }

    public static void staffMenu() {
        Util.clearMenu();
        staff.printMenu();
    }

    public static void logout() {
        Util.clearMenu();
        loggedInEmpID = null;
        File authFile = new File(authData);
        if (authFile.exists()) {
            authFile.delete();

        }
        System.out.print("Logging Out");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(100);
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nLogged out successfully.");
    }
}
