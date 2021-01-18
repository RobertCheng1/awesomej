package com.company;

public class ExceptionPoc {

    public void touchExcep(){
        try {
            Integer.parseInt("abc");
        } catch (Exception e) {
            System.out.println("catched");
            // throw new RuntimeException(e);
        } finally {
            System.out.println("finally");
        }
        System.out.println("---------");

    }
}
