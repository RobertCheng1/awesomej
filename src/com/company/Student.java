package com.company;

/* 单继承：
   Java只允许单继承，所有类最终的根类是Object；没有明确写extends的类，编译器会自动加上extends Object

   继承有个特点，就是子类无法访问父类的 private 字段或者 private 方法。 例如，Student类就无法访问Person类的name和age字段,
   这使得继承的作用被削弱了。为了让子类可以访问父类的字段，我们需要把private改为protected。用protected修饰的字段可以被子类访问。
   因此，protected 关键字可以把字段和方法的访问权限控制在继承树内部，一个 protected 字段和方法可以被其子类，以及子类的子类所访问。
 */
public class Student extends Person {
    private int score;

    /* 关于子类的构造方法：
       1. 子类不会继承任何父类的构造方法。子类默认的构造方法是编译器自动生成的，不是继承的。

       2. 在Java中，任何 class 的构造方法，第一行语句必须是调用父类的构造方法。
          如果子类没有明确地调用父类的构造方法，编译器会帮我们自动加一句super()，这就要求父类必须有无参数的构造方法即默认的构造方法，否则编译失败！
          如果父类没有默认的构造方法，子类就必须显式调用super()并给出参数以便让编译器定位到父类的一个合适的构造方法。

       3. 在子类的覆写方法中，如果要调用父类的被覆写的方法，可以通过super来调用
     */
    public Student(String name, int age, int score){
        //super();
        super(name,age);
        this.score = score;
    }

    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return this.score;
    }

    // Override 覆写父类的方法：Override(覆写) 和Overload(重载)不同的是，
    // 如果方法签名如果不同，就是Overload，Overload方法是一个新方法；
    // 如果方法签名相同，并且返回值也相同，就是Override。
    @Override
    public void run(){
        System.out.println("In the Student.run");
    }
}

// 面向对象编程--面向对象基础--包和作用域:
// 一个.java文件只能包含一个 public 类，但可以包含多个非 public 类。如果有 public 类，文件名必须和public类的名字相同。
class Teacher extends Person{
    public void teach(){
        System.out.println("In the teach of class Teacher");
    }
}