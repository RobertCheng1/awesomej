package com.company;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Scanner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Main {

    /**
     * Java 入口程序规定的方法必须是静态方法，方法名必须为 main，括号内的参数必须是String数组。 from：Java 程序基本结构
     * Java源码的缩进不是必须的，但是用缩进后，格式好看，很容易看出代码块的开始和结束，缩进一般是4个空格或者一个tab。from：第一个 Java 程序
     *
     * Java程序的入口是main方法，而main方法可以接受一个命令行参数，它是一个String[]数组。
     * 命令行参数由JVM接收用户输入并传给main方法； from： Java快速入门--数组操作--命令行参数
     *
     * https://www.liaoxuefeng.com/wiki/1252599548343744/1260467032946976
     * 编写class的时候，编译器会自动帮我们做两个 import 动作：
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

        Student stu = new Student("robb", 11, 99);
        System.out.println(stu.getScore());
        System.out.println(stu.getName());
        // 测试覆写 Override
        Person perstu = new Student("rob", 12, 98);
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
        // 测试枚举类： 默认情况下，对枚举常量调用toString()会返回和name()一样的字符串。但是，toString()可以被覆写，而name()则不行
        Color yanse = Color.RED;
        System.out.println(yanse); //猛一看是直接打印对象，其实是在调用对象的toString()，目的是在输出时更有可读性。
        System.out.println(yanse.name()); // 返回常量名 或者说是 常量定义的字符串  from:枚举类
        // 测试记录类: For MAC：Error:java: 源发行版 14 与 --enable-preview 一起使用时无效 （仅发行版 15 支持预览语言功能）
        // var z = PointAdv.of();
        // var p = PointAdv.of(321, 456);
        // System.out.println(p.x());
        // 测试 BigDecimal
        BigDecimalPoc big = new BigDecimalPoc();
        big.compare();

        byte[] b1 = "abc".getBytes("UTF-8");
        for(byte i:b1){
            System.out.println(i);
        }
        String s6 = new String(b1);

        // 测试异常处理: Throwable.getSuppressed();   无法从静态上下文中引用非静态 方法 getSuppressed()
        ExceptionPoc exce = new ExceptionPoc();
        exce.touchExcep();

        // 测试日志系统：
        // System.out.println(getClass());  无法从静态上下文中引用非静态 方法 getClass();放到其他类里的非静态方法即可
        Log log = LogFactory.getLog(Main.class);
        log.info("start...");

        // 测试反射
        ReflecitonPoc ref = new ReflecitonPoc();
        ref.touchReflect();
    }
}

