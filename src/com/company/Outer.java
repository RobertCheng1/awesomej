package com.company;

public class Outer {
    private static String country = "CHINA";
    private String myaddress;

    public Outer(String myaddress){
        this.myaddress = myaddress;
    }

    /* Inner Class：
        Inner Class和普通Class相比，除了能引用 Outer 实例外(Outer.this)，还有一个额外的“特权”，就是可以修改Outer Class的private字段，
        因为Inner Class的作用域在Outer Class内部，所以能访问Outer Class的 private 字段和方法。
        观察Java编译器编译后的.class文件可以发现，Outer类被编译为Outer.class，而Inner类被编译为Outer$Inner.class。
     */
    class Inner{
        void moha(){
            System.out.println("I am in " + Outer.this.myaddress);
            Outer.this.myaddress = Outer.this.myaddress + " hahaha!";
            System.out.println("I am in " + Outer.this.myaddress);
        }
    }


    /* Anonymous Class：
        观察asyncHello()方法，我们在方法内部实例化了一个Runnable。Runnable本身是接口，接口是不能实例化的，
        所以这里实际上是定义了一个实现了Runnable接口的匿名类，并且通过new实例化该匿名类，然后转型为Runnable。
        在定义匿名类的时候就必须实例化它，定义匿名类的写法如下：
              Runnable r = new Runnable() {
                  // 实现必要的抽象方法...
              };

        观察Java编译器编译后的.class文件可以发现，Outer类被编译为Outer.class，而匿名类被编译为Outer$1.class。
        如果有多个匿名类，Java编译器会将每个匿名类依次命名为Outer$1、Outer$2、Outer$3……
     */
    void asyncHello() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("I am in " + Outer.this.myaddress);
            }
        };
        new Thread(r).start();
    }

    /* Static Nested Class：
        用 static 修饰的内部类和Inner Class有很大的不同，它不再依附于Outer的实例，而是一个完全独立的类，
        因此无法引用 Outer.this，但它可以访问 Outer 的 private 静态字段和静态方法。
        如果把StaticNested移到Outer之外，就失去了访问private的权限。
     */
    static class StaticNested {
        void homeland() {
            System.out.println("My homeland is " + Outer.country);
        }
    }
}
