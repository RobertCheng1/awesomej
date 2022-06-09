package com.company;

/*
    1_1. 抽象类：和多态关联的比较紧密
        如果一个class定义了方法，但没有具体执行代码，这个方法就是抽象方法，抽象方法用 abstract 修饰。
        因为无法执行抽象方法(没有具体的执行代码，执行什么呀，所以类也就没法被实例化)，因此这个类也必须申明为抽象类（abstract class），
        否则编译报错，无法编译该类因为它包含抽象方法：java:com.company.Animal不是抽象的, 并且未覆盖com.company.Animal中的抽象方法eat()。
        使用 abstract 修饰的类就是抽象类。===我们无法实例化一个抽象类===。
        (Animal ani = new Animal() 编译报错：'Animal' is abstract; cannot be instantiated)
        无法实例化的抽象类有什么用 ？？？
        因为抽象类本身被设计成只能用于被继承，因此，抽象类可以 强迫 子类实现其定义的抽象方法，否则编译会报错。

        如果不实现抽象方法，则该子类仍是一个抽象类。因此，抽象方法实际上相当于定义了“规范”。

        这种尽量引用高层类型 Animal a = new Cat(10, "yellow"); 避免引用实际子类型的方式，称之为面向抽象编程。
        面向抽象编程的本质就是：
            上层代码只定义规范（例如：abstract class Person）；
            不需要子类就可以实现业务逻辑（正常编译）；
            具体的业务逻辑由不同的子类实现，调用者并不关心。

    1_2. 接口 interface：
        在抽象类中，抽象方法本质上是定义接口规范：即规定高层类的接口，从而保证所有子类都有相同的接口实现，这样，多态就能发挥出威力。
        如果一个抽象类没有字段，所有方法全部都是抽象方法：
            abstract class Person {
                public abstract void run();
                public abstract String getName();
            }
        就可以把该抽象类改写为接口：interface。

        在Java中，使用 interface 可以声明一个接口：
            interface Person {
                void run();
                String getName();
            }
        所谓 interface，就是比抽象类还要抽象的纯抽象接口，因为它连字段都不能有。
        因为接口定义的所有方法默认都是 public abstract的，所以这两个修饰符不需要写出来（写不写效果都一样）。
        扩展：参考下面的 interface 中的静态字段

        抽象类和接口的对比如下：
                        abstract class	        interface
            继承	        只能extends一个class	    可以implements多个interface
            字段	        可以定义实例字段	        不能定义实例字段
            抽象方法	    可以定义抽象方法	        可以定义抽象方法
            非抽象方法	可以定义非抽象方法	        可以定义default方法

    1_3. 单继承和一个类实现多个interface 以及 interface 中的 default 方法的使用：
        一个类只能继承自另一个类，不能从多个类继承。但是，一个类可以实现多个interface。关于单继承，请参考 Student.java
        一个interface可以继承自另一个interface。interface继承自interface使用extends，它相当于扩展了接口的方法。例如：
            interface Person {
                void eat();
            }

            interface Soldier extends Person {
                void run();
                String getName();
            }
        此时，Soldier 接口继承自 Person 接口，因此，Person 接口现在实际上有3个抽象方法签名，其中一个来自继承的Person接口。

        default方法的目的是：
        接口实现类可以不必覆写 default 方法，当我们需要给接口新增一个方法时，会涉及到修改全部子类。
        如果新增的是 default 方法，那么子类就不必全部修改，只需要在需要覆写的地方去覆写新增方法。
        default 方法和抽象类的普通方法是有所不同的：因为 interface 没有字段，default方法无法访问字段，而抽象类的普通方法可以访问实例字段。

    2. 静态字段和静态方法：
        实例字段在每个实例中都有自己的一个独立“空间”，但是静态字段只有一个共享“空间”，所有实例都会共享该字段
        不推荐用 实例变量.静态字段 去访问静态字段，因为在Java程序中，实例对象并没有静态字段。
        在代码中，实例对象能访问静态字段只是因为编译器可以根据实例类型自动转换为  类名.静态字段  来访问静态对象。
        推荐用类名来访问静态字段。可以把静态字段理解为描述class本身的字段（非实例字段）

        静态方法：
        调用实例方法必须通过一个实例变量，而调用静态方法则不需要实例变量，通过类名就可以调用。静态方法类似其它编程语言的函数。
        静态方法属于class而不属于实例，因此，静态方法内部，无法访问 this 变量，也无法访问实例字段，它只能访问静态字段。
        通过实例变量也可以调用静态方法，但这只是编译器自动帮我们把实例改写成类名而已(通过实例变量访问静态字段和静态方法，会得到一个编译警告)。

        interface 中的静态字段：
        因为interface是一个纯抽象类，所以它不能定义实例字段。但是，interface 是可以有静态字段的，并且静态字段必须为 final 类型：
            public interface Person {
                public static final int MALE = 1;
                public static final int FEMALE = 2;
            }
        实际上，因为 interface 的字段只能是 public static final 类型，所以我们可以把这些修饰符都去掉，上述代码可以简写为：
            public interface Person {
                // 编译器会自动加上public static final:
                int MALE = 1;
                int FEMALE = 2;
            }
 */

//abstract class Animal {
abstract class Animal implements Biology{
    protected int weight;
    protected String color;

    public Animal(){
        System.out.println("In the Animal() constructor method");
    }
    public void setWeight(int weight){
        this.weight = weight;
    }
    public int getWeight(){
        return this.weight;
    }

    // 抽象方法
    public abstract void eat();
}

