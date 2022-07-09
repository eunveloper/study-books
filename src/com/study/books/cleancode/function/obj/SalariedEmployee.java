package com.study.books.cleancode.function.obj;

public class SalariedEmployee extends Employee
{
    public SalariedEmployee(EmployeeRecord r) {
        super();
    }

    @Override
    public boolean isPayday() {
        return false;
    }

    @Override
    public Money calculatePay() {
        return null;
    }

    @Override
    public void deliverPay() {

    }
}
