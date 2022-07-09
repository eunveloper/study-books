package com.study.books.cleancode.function.obj;

public abstract class Employee {

    public Type type;

    public abstract boolean isPayday();
    public abstract Money calculatePay();
    public abstract void deliverPay();

    public enum Type {
        COMMISSIONED, HOURLY, SALARIED
    }

}
