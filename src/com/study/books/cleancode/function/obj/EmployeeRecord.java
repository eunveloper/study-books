package com.study.books.cleancode.function.obj;

public class EmployeeRecord {

    public Employee.Type type;
    public boolean flags = true;
    public int age = 65;

    public boolean isEligibleForFullBenefits() {
        return true;
    }

    public enum Type {
        COMMISSIONED, HOURLY, SALARIED
    }

}
