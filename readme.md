# JAVA HRMS

A Mini Project Humann Resource Management System For OOP Subject using java (Section 9 Group 12)

## How to Compile

Here a step by step to run

go to the root folder directory:

```pwsh
cd the project root folder
```

compile the java make sure all src is included such as below:

```pwsh
javac -d out src/com/hrms/*.java src/com/hrms/Department/*.java src/com/hrms/Employee/*.java src/com/hrms/Users/*.java src/com/hrms/Utils/*.java src/com/hrms/StaffManagement/*.java src/com/hrms/Leave/*.java src/com/hrms/Salary/*.java
```

Then go to out directory:

```pwsh
cd out
```

Run java as below:

```pwsh
java com.hrms.HRMS
```

## CHANGE FILE PATH BEFORE RUNNING

Please change your file path variable according to your hardware

Example Below:

```java
    public static final String salaryDataPath = "C:\\Users\\adami\\Desktop\\OOP\\Project\\java_hrms\\src\\com\\data\\salary.txt";
```

Java file that use the file path
**this need to be changed:**

1.  Staff.java
2.  Auth.java
3.  StaffManagement.java
4.  Salary.java
5.  SalaryManagement.java
6.  Leave.java
7.  LeaveManagement.java
8.  Address.java
9.  Department.java
10. Positon.java

## NOTE THIS SYSTEM USE TEXT FILE BASED

2 Important file that need to be added before running <br>
Create this file such as below

```pwsh
department.txt

Department ID     | Department Name
----------------------------------------
D0001             | Finance
D0002             | Human Resource
D0003             | Information Technology

```

```pwsh
position.txt

Position ID     | Position Title         |Department ID
-----------------------------------------------------------
P0001           | Executive Assistant    |D0001
P0002           | Head Manager           |D0002
P0003           | Executive Accountant   |D0003

```

### Text File that included

1.  adress.txt (Employee Address)
2.  position.txt (Employee Position)
3.  department.txt (Department)
4.  empData.txt (Employee Information)
5.  leave.txt (Employee leave)
6.  salary.txt (Employee Salaries)

---
