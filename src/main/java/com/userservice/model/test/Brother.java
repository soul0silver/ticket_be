package com.userservice.model.test;

public class Brother {
    private static int age;
    private String name;
    public static void test() {
        Brother brother = new Brother();
        System.out.println(brother.name);
        System.out.println(Brother.age);
    }

    public void test2() {
        System.out.println(age);
    }

    public void test3() {
        System.out.println();
    }

}
