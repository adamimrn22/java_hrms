package com.hrms.Employee;

import com.hrms.Department.Position;

public abstract class Employee {
    private String empID;
    private String password;
    protected Position position;
    private RoleType roleType;
    private WorkStatus workStatus;

    public Employee() {
    };

    public Employee(String empID, String password) {
        this.empID = empID;
        this.password = password;
    }

    public Employee(String empID, String password, Position position, RoleType roleType) {
        this.empID = empID;
        this.password = password;
        this.position = position;
        this.roleType = roleType;
    }

    public String getEmpID() {
        return this.empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public RoleType getRoleType() {
        return this.roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public WorkStatus getWorkStatus() {
        return this.workStatus;
    }

    public void setWorkStatus(WorkStatus workStatus) {
        this.workStatus = workStatus;
    }

    public abstract void printMenu();

}
