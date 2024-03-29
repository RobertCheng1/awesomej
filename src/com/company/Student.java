package com.company;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.MapType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/* 单继承：
   Java只允许单继承，所有类最终的根类是Object；没有明确写extends的类，编译器会自动加上extends Object, 所以任何类除了Object都会继承自某个类

   继承有个特点，就是子类无法访问父类的 private 字段或者 private 方法。 例如，Student类就无法访问 Person 类的name和age字段,
   这使得继承的作用被削弱了。为了让子类可以访问父类的字段，我们需要把 private 改为 protected。用protected修饰的字段可以被子类访问。
   因此，protected 关键字可以把字段和方法的访问权限控制在继承树内部，一个 protected 字段和方法可以被其子类，以及子类的子类所访问。
   注意：子类自动获得了父类的所有字段，严禁定义与父类重名的字段！
 */
public class Student extends Person {
    private int score;
    private final String school = "BJSY";
    /* 关于子类的构造方法：
       1. 子类不会继承任何父类的构造方法。子类默认的构造方法是编译器自动生成的，不是继承的。
          原因是如果一个类没有定义构造方法，编译器会自动为我们生成一个默认构造方法。from:继承

       2. 在Java中，任何 class 的构造方法，第一行语句必须是调用父类的构造方法。
          如果子类没有明确地调用父类的构造方法，编译器会帮我们自动加一句super()，这就要求父类必须有无参数的构造方法即默认的构造方法，否则编译失败！
          如果父类没有默认的构造方法，子类就必须显式调用super()并给出参数以便让编译器定位到父类的一个合适的构造方法。

       3. 在子类的覆写方法中，如果要调用父类的被覆写的方法，可以通过super来调用：super.run() from:多态
     */
    public Student(String name, int age, int score){
        //super();
        super(name,age);
        //充分证明了：继承有个特点，就是子类无法访问父类的 private 字段或者 private 方法。
        //但是可以通过 public or protected 方法来访问 private 字段。 比如：this.setName(name+name) 是可以的
        //this.name = name + name;  //java: name 在 com.company.Person 中是 private 访问控制
        this.score = score;
    }

    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return this.score;
    }

    /* Override: 在继承关系中，子类如果定义了一个与父类方法签名完全相同的方法,被称为覆写（Override）。
       Override(覆写) 和Overload(重载)不同的是：
          如果方法签名如果不同，就是Overload，Overload方法是一个新方法；
          如果方法签名相同，并且返回值也相同，就是Override。

       覆写Object方法: 因为所有的class最终都继承自 Object，而Object定义了几个重要的方法：
          toString()：把instance输出为String；
          equals()：判断两个instance是否逻辑相等；
          hashCode()：计算一个instance的哈希值。
     */
    @Override
    public void run(){
        System.out.println("In the Student.run");
    }

    // 虽然向上转型是可以成功的，但是不可以用父类变量调用“只在子类中出现的方法”
    // Person perstu = new Student("rob", 12, 81);
    // perstu.study(); // 编译会报错的
    public void study() {
        System.out.println("In the Student.study");
    }
}

// 面向对象编程--面向对象基础--包和作用域两章:
// 一个.java文件只能包含一个 public 类，但可以包含多个非 public 类。如果有 public 类，文件名必须和public类的名字相同。
class Teacher extends Person{
    public void teach(){
        System.out.println("In the teach of class Teacher");
    }
}

