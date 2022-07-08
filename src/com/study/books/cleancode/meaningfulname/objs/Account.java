package com.study.books.cleancode.meaningfulname.objs;

public class Account {

    private String name;

    public Account(String name) {
        this.name = name;
    }

    public static Account GeneratorAccountName(String name) {
        return new Account(name);
    }
}
