package com.hrms.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Address {
    private String empID;
    private String street;
    private String postalCode;
    private String state;
    private String country;

    private static final String addressDataPath = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\address.txt";
    public static ArrayList<Address> addresses = new ArrayList<Address>();

    public Address() {
        loadAddress();
    }

    public Address(String empID, String street, String postalCode, String state, String country) {
        this.empID = empID;
        this.street = street;
        this.postalCode = postalCode;
        this.state = state;
        this.country = country;
    }

    public String getEmpID() {
        return this.empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return " " + getStreet() + ", " + getPostalCode() + ", " + getState() + ", " + getCountry() + " ";
    }

    private void loadAddress() {
        addresses.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(addressDataPath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+\\|\\s+");

                if (parts.length == 5) {
                    String empID = parts[0].trim();
                    String street = parts[1].trim();
                    String postalCode = parts[2].trim();
                    String state = parts[3].trim();
                    String country = parts[4].trim();

                    Address address = new Address(empID, street, postalCode, state, country);
                    addresses.add(address);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Address getAddressByEmpID(String empID) {
        for (Address address : addresses) {
            if (address.getEmpID().equalsIgnoreCase(empID)) {
                return address;
            }
        }
        return null;
    }

}
