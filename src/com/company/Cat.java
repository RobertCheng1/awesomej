package com.company;

public class Cat extends Animal {


    public Cat(int weight, String color){
        // 在Java中，任何 class 的构造方法，第一行语句必须是调用父类的构造方法。请参考：Student.java
        this.weight = weight;
        this.color = color;
    }

    @Override
    public void cell(){
        // 覆写 inerface Biology 中的 cell()
        // 如果父类 Animal 不实现接口 Biology 即从 abstract class Animal implements Biology 改为 abstract class Animal
        // 就需要注释掉该方法，否则编译报错：java: 方法不会覆盖或实现超类型的方法
        System.out.println("Cat has many many cells");
    }

    @Override
    public void eat(){
        // 覆写父类 Animal 中的 eat()
        System.out.println("In the eat of class Cat");
    }
}
