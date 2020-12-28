package com.company;

/*
   class是一种对象模版，它定义了如何创建实例，因此，class本身就是一种数据类型,
   而instance是对象实例，instance是根据class创建的实例，可以创建多个instance，每个instance类型相同，但各自属性可能不相同,
 */
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

    // 会被子类覆写的 public 函数,
    // 主要是为了测试多态的特性：多态是指，针对某个类型的方法调用，其真正执行的方法取决于运行时期实际类型的方法。
    public void run(){
        System.out.println("In the Person.run");
    }
}
/*
    1. 阻止继承：
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

    2_1. 抽象类：和多态关联的比较紧密
        如果一个class定义了方法，但没有具体执行代码，这个方法就是抽象方法，抽象方法用abstract修饰。
        因为无法执行抽象方法，因此这个类也必须申明为抽象类（abstract class）。否则编译器会告诉我们，无法编译Person类，因为它包含抽象方法。
        使用 abstract 修饰的类就是抽象类。我们无法实例化一个抽象类。
        无法实例化的抽象类有什么用？
        因为抽象类本身被设计成只能用于被继承，因此，抽象类可以 强迫 子类实现其定义的抽象方法，否则编译会报错。
        如果不实现抽象方法，则该子类仍是一个抽象类。因此，抽象方法实际上相当于定义了“规范”。

    2_2. 接口：
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
        所谓interface，就是比抽象类还要抽象的纯抽象接口，因为它连字段都不能有。
        因为接口定义的所有方法默认都是 public abstract的，所以这两个修饰符不需要写出来（写不写效果都一样）。
        扩展：参考下面的 interface 中的静态字段

    2_3. 单继承和一个类实现多个interface 以及 interface 中的 default 方法的使用：
        在Java中，一个类只能继承自另一个类，不能从多个类继承。但是，一个类可以实现多个interface。关于单继承，请参考 Student.java
        接口实现类可以不必覆写default方法，default方法的目的是：
        当我们需要给接口新增一个方法时，会涉及到修改全部子类。如果新增的是default方法，那么子类就不必全部修改，只需要在需要覆写的地方去覆写新增方法。
        default方法和抽象类的普通方法是有所不同的。因为interface没有字段，default方法无法访问字段，而抽象类的普通方法可以访问实例字段。

    3. 静态字段和静态方法：
        实例字段在每个实例中都有自己的一个独立“空间”，但是静态字段只有一个共享“空间”，所有实例都会共享该字段
        不推荐用 实例变量.静态字段 去访问静态字段，因为在Java程序中，实例对象并没有静态字段。
        在代码中，实例对象能访问静态字段只是因为编译器可以根据实例类型自动转换为  类名.静态字段  来访问静态对象。
        推荐用类名来访问静态字段。可以把静态字段理解为描述class本身的字段（非实例字段）

        静态方法：
        调用实例方法必须通过一个实例变量，而调用静态方法则不需要实例变量，通过类名就可以调用。静态方法类似其它编程语言的函数。
        静态方法属于class而不属于实例，因此，静态方法内部，无法访问this变量，也无法访问实例字段，它只能访问静态字段。
        通过实例变量也可以调用静态方法，但这只是编译器自动帮我们把实例改写成类名而已(通过实例变量访问静态字段和静态方法，会得到一个编译警告)。

        interface 中的静态字段：
        因为interface是一个纯抽象类，所以它不能定义实例字段。但是，interface 是可以有静态字段的，并且静态字段必须为 final 类型：
            public interface Person {
                public static final int MALE = 1;
                public static final int FEMALE = 2;
            }
        实际上，因为 interface 的字段只能是 public static final 类型，所以我们可以把这些修饰符都去掉，上述代码可以简写为：
            public interface Person {
                // 编译器会自动加上public statc final:
                int MALE = 1;
                int FEMALE = 2;
            }
 */

