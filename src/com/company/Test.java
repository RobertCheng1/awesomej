package com.company;

/**
 * @author: chengpengxing
 * @description
 * @date: 2022/11/21 5:52 PM
 */
public class Test{
    public static void main(String[] args) {
        erzi c = new erzi();
        c.test();
        Parent p = new Parent();
        p.test();
    }
}
class erzi extends Parent {
    private int a = 3;
    public static int b = 3;
    public void test() {
        System.out.println("erzi");
        System.out.println("a = " + a + " b = " + b);
    }
    public erzi() {
        super();
    }
}
class Parent {
    public int a = 1;
    public static int b = 2;
    public Parent() {
        test();
    }
    public void test() {
        System.out.println("Parent");
        System.out.println("a = " + a + " b = " + b);
    }
}