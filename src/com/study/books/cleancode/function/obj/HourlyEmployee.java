package com.study.books.cleancode.function.obj;

public class HourlyEmployee extends Employee
{
    public HourlyEmployee(EmployeeRecord r) {
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
