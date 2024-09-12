package com.userservice.model.test;

public class Child extends Parent {

    public String name;

    @Override
    public void demoFunction() {
        System.out.println("lop con");
    }

    public void testFunction() {
        super.demoFunction();
        demoFunction();
    }
}
