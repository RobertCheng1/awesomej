package com.company;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Main {
    /**
     * Java 入口程序规定的方法必须是静态方法，方法名必须为 main，括号内的参数必须是String数组。 from：Java 程序基本结构
     * Java源码的缩进不是必须的，但是用缩进后，格式好看，很容易看出代码块的开始和结束，缩进一般是4个空格或者一个tab。from：第一个Java程序
     *
     * Java程序的入口是main方法，而main方法可以接受一个命令行参数，它是一个String[]数组。
     * 命令行参数由JVM接收用户输入并传给main方法； from： Java快速入门--数组操作--命令行参数
     *
     * Java编译器最终编译出的.class文件只使用完整类名，因此，在代码中，当编译器遇到一个class名称时：from：面向对象编程--面向对象基础--包
     *     如果是完整类名，就直接根据完整类名查找这个class；
     *     如果是简单类名，按下面的顺序依次查找：
     *         查找当前package是否存在这个class；
     *         查找import的包是否包含这个class；
     *         查找java.lang包是否包含这个class。
     * 如果按照上面的规则还无法确定类名，则编译报错。
     * 因此，编写class的时候，编译器会自动帮我们做两个 import 动作：
     *     默认自动 import 当前 package 的其他class；
     *     默认自动 import java.lang.*。
     * 注意：自动导入的是 java.lang 包，但类似 java.lang.reflect 这些包仍需要手动导入。
     *
     * @param args
     */
    // public static void main(String[] args)  {
    public static void main(String[] args) throws Exception {
        // write your code here
        System.out.println("Hello, world!");
        EntryLevel obj = new EntryLevel();
        obj.varPoc();

        Person per = new Person();
        System.out.println(per.getName());

        Student stu = new Student("robb", 11, 80);
        System.out.println(stu.getScore());
        System.out.println(stu.getName());
        /* 测试覆写 Override 和 向上转型 和 向下转型 from: 面向对象编程--继承
           如果Student是从Person继承下来的，那么，一个引用类型为Person的变量，能否指向Student类型的实例？
                Person p = new Student(); // ???
           测试一下就可以发现，这种指向是允许的！
           这是因为Student继承自Person，因此，它拥有Person的全部功能。Person类型的变量，如果指向Student类型的实例，对它进行操作是没问题的！
           这种把一个子类类型安全地变为父类类型的赋值，被称为向上转型（upcasting）.向上转型实际上是把一个子类型安全地变为更加抽象的父类型.

           和向上转型相反，如果把一个父类类型强制转型为子类类型，就是向下转型（downcasting）。例如：
                Person p1 = new Student(); // upcasting, ok
                Person p2 = new Person();
                Student s1 = p1;           // java: 不兼容的类型: com.company.Person无法转换为com.company.Student
                Student s1 = (Student) p1; // ok 向下转型是需要强转的，否则报上面的错误，向上转型则不用加强转
                Student s2 = (Student) p2; // runtime error! ClassCastException!
                System.out.println(p1.getClass()); // class com.company.Student
           如果测试上面的代码，可以发现：
           Person类型p1实际指向Student实例，Person类型变量p2实际指向Person实例。在向下转型的时候，
           把p1转型为Student会成功，因为p1确实指向Student实例，
           把p2转型为Student会失败，因为p2的实际类型是Person，不能把父类变为子类，因为子类功能比父类多，多的功能无法凭空变出来。
           因此，向下转型很可能会失败。失败的时候，Java虚拟机会报ClassCastException。
         */
        Person perstu = new Student("rob", 12, 81);
        perstu.run();

        // 测试抽象类和接口
        // Animal ani = new Animal()  编译报错：'Animal' is abstract; cannot be instantiated
        Cat miao = new Cat(10, "yellow");
        miao.eat();
        System.out.println(miao.getWeight());

        // 测试内部类或者说嵌套类:
        // 要实例化一个 Inner，我们必须首先创建一个 Outer 的实例，然后，调用Outer实例的 new 来创建Inner实例,
        // 这是因为Inner Class除了有一个this指向它自己，还隐含地持有一个Outer Class实例，可以用Outer.this访问这个实例。
        // 所以，实例化一个Inner Class不能脱离Outer实例。
        Outer outer = new Outer("BeiJingYiZhuang"); // 实例化一个Outer
        Outer.Inner inner = outer.new Inner(); // 实例化一个Inner
        inner.moha();
        outer.asyncHello();
        // 用 static 修饰的内部类和Inner Class有很大的不同，它不再依附于Outer的实例，而是一个完全独立的类，因此无法引用 Outer.this
        Outer.StaticNested sn = new Outer.StaticNested();
        sn.homeland();

        // 测试包装类型：
        WrapperPoc wrap = new WrapperPoc();
        wrap.int2Integer();

        // 要枚举一个JavaBean的所有属性，可以直接使用Java核心库提供的 Introspector from: 面向对象编程--Java核心类--JavaBean
        // 为了执行下面的代码：需要把 public static void main(String[] args)  {
        // 改为  public static void main(String[] args) throws Exception {  否则编译报如下错误：
        // Error:(65, 49) java: 未报告的异常错误java.beans.IntrospectionException; 必须对其进行捕获或声明以便抛出
        BeanInfo info = Introspector.getBeanInfo(Student.class);
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            System.out.println(pd.getName());
            System.out.println("  " + pd.getReadMethod());
            System.out.println("  " + pd.getWriteMethod());
        }
        // 测试枚举类：
        EnumPoc enumObj = new EnumPoc();
        enumObj.touchEnum();
        // 测试记录类: For MAC：Error:java: 源发行版 14 与 --enable-preview 一起使用时无效 （仅发行版 15 支持预览语言功能）
        // RecordPoc recordObj = new RecordPoc();
        // recordObj.touchRecord();

        // 测试 BigDecimal
        BigDecimalPoc big = new BigDecimalPoc();
        big.compare();

        byte[] b1 = "abc".getBytes("UTF-8");
        for(byte i:b1){
            System.out.println(i);
        }
        String s6 = new String(b1);

        // 测试异常处理:
        ExceptionPoc exce = new ExceptionPoc();
        exce.touchExcep();

        // 测试日志系统：
        // System.out.println(getClass());  无法从静态上下文中引用非静态 方法 getClass();放到其他类里的非静态方法即可
        Log log = LogFactory.getLog(Main.class);
        log.info("start...");

        // 测试反射: 通过反射的访问字段和调用方法，修改业务实例的字段
        ReflecitonPoc ref = new ReflecitonPoc();
        ref.touchReflect();
        System.out.printf("The current score = %d\n", stu.getScore());
        ref.accessField(stu);
        System.out.printf("After the reflection, the score = %d\n", stu.getScore());
        ref.accessMethod(stu);
        System.out.printf("After the accessMethod, the score = %d\n", stu.getScore());
        ref.constructor(stu);
        ref.inherit();
        ref.dynamicProxy();

        // 测试注解:正好也用到了反射的知识
        AnnotationPoc annpoc = new AnnotationPoc("BeiJing");
        annpoc.fieldCheck();
        AnnotationRep annrep = new AnnotationRep();
        annrep.touchAnnoRep();

        // 测试泛型：
        GenericPoc gen = new GenericPoc();
        gen.genericEntry();
        gen.genericAdv();
        gen.cutomizeGeneric();
        gen.typeErasureGeneric();
        gen.extendsGeneric();
        gen.superGeneric();
        // ToDo: copy()方法的另一个好处是可以安全地把一个List<Integer>添加到List<Number>，但是无法反过来添加： from:super通配符
        // // copy List<Integer> to List<Number> ok:
        // List<Number> numList = ...;
        // List<Integer> intList = ...;
        // Collections.copy(numList, intList);
        //
        // // ERROR: cannot copy List<Number> to List<Integer>:
        // Collections.copy(intList, numList);
        //
        // 测试集合：
        CollectionPoc cp = new CollectionPoc();
        cp.listEntry();
        cp.listEquals();
    }
}

