package com.hrms;

import com.hrms.Department.Department;
import com.hrms.Department.Position;
import com.hrms.Employee.Address;
import com.hrms.Leave.Leave;
import com.hrms.Salary.Salary;
import com.hrms.Users.Staff;
import com.hrms.Utils.Auth;

public class HRMS {
    public static void main(String[] args) {
        load();
        Auth.loginMenu();
    }

    public static void load() {
        new Department();
        new Position();
        new Address();
        new Staff();
        new Salary();
        new Leave();
    }

}
