package com.company;

/*
   class是一种对象模版，它定义了如何创建实例，因此，class本身就是一种数据类型,
   而instance是对象实例，instance是根据class创建的实例，可以创建多个instance，每个instance类型相同，但各自属性可能不相同。

   进阶：
   1. 阻止继承（from：继承和多态）：
        正常情况下，只要某个 class 没有 final 修饰符，那么任何类都可以从该class继承。
        从Java 15开始，允许使用sealed修饰class，并通过permits明确写出能够从该class继承的子类名称。
            例如，定义一个Shape类：
            public sealed class Shape permits Rect, Circle, Triangle {
                ...
            }
       这种sealed类主要用于一些框架，防止继承被滥用。
       sealed类在Java 15中目前是预览状态，要启用它，必须使用参数--enable-preview和--source 15。

       final修饰符有多种作用：
            final修饰的方法可以阻止被覆写；
            final修饰的class可以阻止被继承；
            final修饰的field必须在创建对象时初始化，随后不可修改。
       final实例案例：
            Java字符串的一个重要特点就是字符串不可变。这种不可变性是通过内部的private final char[]字段，以及没有任何修改char[]的方法实现的。

   2. 静态字段：
        在一个class中定义的字段，我们称之为实例字段。实例字段的特点是，每个实例都有独立的字段，各个实例的同名字段互不影响。
        还有一种字段，是用static修饰的字段，称为静态字段：static field。
        实例字段在每个实例中都有自己的一个独立“空间”，但是静态字段只有一个共享“空间”，所有实例都会共享该字段。
 */


// 面向对象编程--面向对象基础--包和作用域两章:
// 1. 当把 public class Person ---> class Person 仍然可以在 Main.java 中访问 class Person：因为包作用域。
//    包作用域是指一个类允许访问同一个package的没有public、private修饰的class，以及没有public、protected、private修饰的字段和方法。
// 2. 一个.java文件只能包含一个 public 类，但可以包含多个非 public 类。如果有 public 类，文件名必须和public类的名字相同。
public class Person {
    private String name;
    private int age;
    /*
      创建实例的时候，实际上是通过构造方法来初始化实例的。
      构造方法的名称就是类名。构造方法的参数没有限制，在方法内部，也可以编写任意语句。
      但是，和普通方法相比，构造方法没有返回值（也没有void），调用构造方法，必须用 new 操作符。

      如果一个类没有定义构造方法，编译器会自动为我们生成一个默认构造方法，它没有参数，也没有执行语句，类似这样：
            class Person {
                public Person() {

                }
            }
     1. 要特别注意的是，如果我们自定义了一个构造方法，那么，编译器就不再自动创建默认构造方法。
     2. 如果既要能使用带参数的构造方法，又想保留不带参数的构造方法，那么只能把两个构造方法都定义出来。
        对于多个构造方法，在通过new操作符调用的时候，编译器通过构造方法的参数数量、位置和类型自动区分。
        一个构造方法可以调用其他构造方法，这样做的目的是便于代码复用。调用其他构造方法的语法是this(…)

       多个构造函数说白了就方法的重载。ps: 方法重载的返回值类型通常都是相同的,只有参数有所不同
    */
    public Person() {
        this("Unnamed"); // 调用另一个构造方法Person(String)， 调用 this 的话其必须是构造函数里的第一句。
        System.out.println("In the Person() constructor method");
    }
    public Person(String name) {
        this(name, 18); // 调用另一个构造方法Person(String, int)， 调用 this 的话其必须是构造函数里的第一句。
        System.out.println("In the Person(String name) constructor method");
    }
    public Person(String name, int age) {
        System.out.println("In the Person(String name, int age) constructor method");
        this.name = name;
        this.age = age;
    }


    // 在方法内部，可以使用一个隐含的变量this，它始终指向当前实例。因此，通过this.field就可以访问当前实例的字段。
    // 如果没有命名冲突，可以省略 this。
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("invalid name");
        }
        this.name = name.strip(); // 去掉首尾空格
    }

    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        if (age < 0 || age > 100) {
            throw new IllegalArgumentException("invalid age value");
        }
        this.age = age;
    }
    public int getBirthYear(){
        return calcBirthYear(2019);
    }


    // private方法: 定义private方法的理由是内部方法是可以调用private方法的。
    private int calcBirthYear(int currentYear) {
        return currentYear - this.age;
    }

    // 测试会被子类覆写的方法,
    // 主要是为了测试多态的特性：多态是指，针对某个类型的方法调用，其真正执行的方法取决于运行时期实际类型的方法。
    public void run(){
        System.out.println("In the Person.run");
    }
}

