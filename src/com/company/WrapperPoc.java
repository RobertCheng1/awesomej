package com.company;

public class WrapperPoc {
    public void int2Integer(){
        // from: 面向对象编程--Java核心类--包装类型:
        System.out.println("In the int2Integer");
        // 因为int和Integer可以互相转换：
        int i = 100;
        Integer n = Integer.valueOf(i);
        int x = n.intValue();
        // 所以，Java编译器可以帮助我们自动在int和Integer之间转型：
        // 这种直接把int变为Integer的赋值写法，称为自动装箱（Auto Boxing），
        // 反过来，把Integer变为int的赋值写法，称为自动拆箱（Auto Unboxing）。
        // 注意：自动装箱和自动拆箱只发生在编译阶段，目的是为了少写代码。
        //
        // 装箱和拆箱会影响代码的执行效率，因为编译后的class代码是严格区分基本类型和引用类型的。
        // 并且，自动拆箱执行时可能会报NullPointerException：
        Integer big = 100; // 编译器自动使用Integer.valueOf(int)
        int small = big; // 编译器自动使用Integer.intValue()

        // 进制转换：Integer类本身还提供了大量方法，例如，最常用的静态方法parseInt()可以把字符串解析成一个整数：
        int x1 = Integer.parseInt("100"); // 100
        int x2 = Integer.parseInt("100", 16); // 256,因为按16进制解析
        // Integer还可以把整数格式化为指定进制的字符串：
        System.out.println(Integer.toString(100)); // "100",表示为10进制
        System.out.println(Integer.toString(100, 36)); // "2s",表示为36进制
        System.out.println(Integer.toHexString(100)); // "64",表示为16进制
        System.out.println(Integer.toOctalString(100)); // "144",表示为8进制
        System.out.println(Integer.toBinaryString(100)); // "1100100",表示为2进制
        
        // 最后，所有的整数和浮点数的包装类型都继承自Number，因此，可以非常方便地直接通过包装类型获取各种基本类型：
        // 向上转型为Number:
        Number num = new Integer(999);
        // 获取byte, int, long, float, double, ===很好的例子，有点利用 面向对象 的感觉了===
        byte b = num.byteValue();
        int nn = num.intValue();
        long ln = num.longValue();
        float f = num.floatValue();
        double d = num.doubleValue();

        // BigInteger和Integer、Long一样，也是不可变类，并且也继承自Number类。因为Number定义了转换为基本类型的几个方法：
        //     转换为byte：byteValue()
        //     转换为short：shortValue()
        //     转换为int：intValue()
        //     转换为long：longValue()
        //     转换为float：floatValue()
        //     转换为double：doubleValue()
        // 因此，通过上述方法，可以把BigInteger转换成基本类型。
        // 如果BigInteger表示的范围超过了基本类型的范围，转换时将丢失高位信息，即结果不一定是准确的.from:面向对象编程--Java核心类--BigInteger
    }
}



