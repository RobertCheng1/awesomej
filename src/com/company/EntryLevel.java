package com.company;

import java.util.Arrays;

public class EntryLevel {

    public void varPoc(){
        // 变量和数据类型:
        // 在 Java 中，变量分为两种：基本类型的变量和引用类型的变量。
        // Java的 char类型除了可表示标准的ASCII外，还可以表示一个Unicode字符，
        // 注意char类型使用单引号'，且仅有一个字符，要和双引号"的字符串类型区分开。
        // Java在内存中总是使用Unicode表示字符，所以一个英文字符和一个中文字符都用一个char类型表示，它们都占用两个字节：from:字符和字符串
        char a = 'A';
        char zh = '中';
        System.out.printf("Test char a = %s\n", a);
        System.out.printf("Test char zh = %s\n", zh);
        // long型的结尾需要加 L, 如果超过 int 的表示最大范围 2147483647 且 没有加 L,则会报整数太大
        long lx = 2147483640;
        long ly = 15;
        long sum = lx + ly;
        System.out.println(sum); // 2147483655

        // 浮点类型的数就是小数，因为小数用科学计数法表示的时候，小数点是可以“浮动”的，如1234.5可以表示成12.345x102，
        // 也可以表示成1.2345x103，所以称为浮点数。  对于 float 类型，需要加上 f 后缀。
        // 科学计数法表示的3.14x10^38
        float f = 3.14e38f;
        System.out.println(f);
        // 定义变量的时候，如果加上 final 修饰符，这个变量就变成了常量：
        final double PI = 3.14;
        // 有些时候，类型的名字太长，写起来比较麻烦。这时如果想省略变量类型，可以使用 var关键字，
        // 编译器会根据赋值语句自动推断出变量sb的类型是StringBuilder， 使用 var 定义变量，仅仅是少写了变量类型而已。
        // StringBuilder sb = new StringBuilder();   等同下面的语句
        var sb = new StringBuilder();
        // 类型自动提升与强制转型:
        // 类型自动提升： 在运算过程中，如果参与运算的两个数类型不一致，那么计算结果为较大类型的整型。
        // 例如，short和int计算，结果总是int，原因是short首先自动被转型为int
        // 强制类型转型： 比如 short -> int, int的两个高位字节直接被扔掉，仅保留了低位的两个字节，
        //              将浮点型强制转为整型，但超出范围后将始终返回整型的最大值。
        short si = 1234;
        int i = 123456;
        int x = si + i;
        // 因为Java在内存中总是使用Unicode表示字符，所以，一个英文字符和一个中文字符都用一个char类型表示，它们都占用两个字节。
        // 要显示一个字符的Unicode编码，只需将char类型直接赋值给int类型即可：from: 字符和字符串
        int ch2int = '中';
        System.out.printf("ch2int = %d\n", ch2int);
        int tmp = 20013;
        System.out.println((char)tmp);
        /* 引用类型： 和 char类型不同，字符串类型String是引用类型，我们用双引号"..."表示字符串
         * 引用类型的变量类似于 C 语言的指针，它内部存储一个“地址”，指向某个对象在内存的位置。
         *
         * 在Java中，String是一个引用类型，它本身也是一个class，但是Java编译器对String有特殊处理，即可以直接用"..."来表示一个字符串,
         * 实际上字符串在String内部是通过一个char[]数组表示的,因此，按下面的写法也是可以的：
         * String s2 = new String(new char[] {'H', 'e', 'l', 'l', 'o', '!'});
         * 因为String太常用了，所以Java提供了"..."这种字符串字面量表示方法。 from: 面向对象编程--Java核心类--字符串和编码
         */
        String s1 = "Hello";
        String s2 = "world";
        String ss = s1 + " " + s2 + "!";
        System.out.printf("test add strings: ss = %s\n", ss);
        i = 25;
        System.out.println(ss + i);
        //  重点1：这个 demo 非常棒，一下子就所清楚了基本类型和引用类型的区别
        String s3 = "Google";
        String s4 = s3;
        s3 = "Baidu";
        System.out.printf("s4 = %s\n", s4); // 是输出 Google 还是 Baidu？ Google
        // 数组是引用类型: 整形数组和字符串数组的区别
        int[] ns = new int[] {11,22,33}; //如果在定义数组变量的同时初始化数组内容，就不能再指定数组大小了
        int itmp = ns[1];
        ns[1] = 99;
        System.out.printf("itmp = %d\n", itmp); // itmp 是 99 还是 22 ？ 22
        String[] names = {"ABC", "XYZ", "zoo"};
        String stmp = names[1];
        names[1] = "cat";
        System.out.printf("stmp = %s\n",stmp); // stmp 是"XYZ"还是"cat"?  XYZ

        // Java源码的缩进不是必须的，但是用缩进后，格式好看，很容易看出代码块的开始和结束，缩进一般是4个空格或者一个tab。
        int score = 60;
        if (score > 90)
            System.out.println("excellent!");
            System.out.println("soso......"); // 重点2：这句话居然输出了，这和 Python 很不一样,充分证明 Java 不缩进

        // 输入
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Please input your age:");
        // int age = scanner.nextInt();
        // System.out.printf("age = %d\n", age);
        cmpString();
        switchTest();
        overflowInt();  // 重点3-1：非常棒的测试
        forCircle();
    }

    public void cmpString(){
        String s1 = "hello";
        String s2 = "HELLO".toLowerCase();
        System.out.printf("s1 = %s, s2 = %s\n", s1, s2);
        if (s1 == s2 ){
            System.out.println("s1 = s2");
        } else {
            System.out.println("s1 != s2");
        }
        // 判断引用类型的变量内容是否相等，必须使用equals()方法
        if (s1.equals(s2)){
            System.out.println("s1 = s2");
        } else {
            System.out.println("s1 != s2");
        }
    }


    public void switchTest(){
        String fruit = "apple";
        // case 语句有 "穿透性"
        switch (fruit) {
            case "apple":
                System.out.println("Selected apple");
                break;
            case "mango":
                System.out.println("Selected mango");
                break;
            default:
                System.out.println("No fruit selected");
                break;
        }


        // 从Java 12开始，switch语句升级为更简洁的表达式语法，
        // 使用类似模式匹配（Pattern Matching）的方法，保证只有一种路径会被执行，并且不需要break语句：
        // 注意新语法使用->，如果有多条语句，需要用{}括起来。不要写break语句，因为新语法只会执行匹配的语句，没有穿透效应。
        switch (fruit) {
            case "apple" -> System.out.println("Selected apple");
            case "mango" -> {
                System.out.println("Selected mango");
                System.out.println("Good choice!");
            }
            default -> System.out.println("No fruit selected");
        }

        //大多数时候，在switch表达式内部，我们会返回简单的值。
        //但是，如果需要复杂的语句，我们也可以写很多语句，放到{...}里，然后，用 yield 返回一个值作为switch语句的返回值
        fruit = "orange";
        int opt = switch (fruit) {
            case "apple" -> 1;
            case "pear", "mango" -> 2;
            default -> {
                int code = fruit.hashCode();
                yield code; // switch语句返回值
            }
        };
        System.out.println("opt = " + opt);
    }

    public void overflowInt(){
        // 重点3：
        // 表面上看，上面的while循环是一个死循环，但是，Java的int类型有最大值，达到最大值后，再加1会变成负数，结果，意外退出了while循环。
        System.out.println("In the overflowInt");
        int sum = 0;
        int n = 1;
        while (n > 0) {
            sum = sum + n;
            n ++;
        }
        System.out.println(n); // -2147483648
        System.out.println(sum);
    }

    public void forCircle(){
        System.out.println("In the forCircle");
        int[] ns = { 1, 4, 9, 16, 25};
        for (int i=0; i<ns.length; i++) {
            System.out.println(ns[i]);
        }
        // 很多时候，我们实际上真正想要访问的是数组每个元素的值。Java还提供了另一种 for each循环，它可以更简单地遍历数组
        System.out.println("===for each===");
        for (int n : ns) {
            System.out.println(n);
        }
        // 直接打印数组变量，得到的是数组在JVM中的引用地址 from: Java 快速入门--遍历数组
        System.out.println(ns);
        // 使用for each循环打印也很麻烦。幸好Java标准库提供了Arrays.toString()，可以快速打印数组内容
        System.out.println(Arrays.toString(ns));
        int[] nss = { 1, 14, 9, 16, 25 };
        Arrays.sort(nss);

    }

}
