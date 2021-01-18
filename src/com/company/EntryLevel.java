package com.company;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class EntryLevel {

    public void varPoc(){
        /* 变量和数据类型:
           在 Java 中，变量分为两种：基本类型的变量和引用类型的变量。
           基本数据类型是CPU可以直接进行运算的类型。Java定义了以下几种基本数据类型：  from:变量和数据类型
                整数类型：byte，short，int，long
                浮点数类型：float，double
                字符类型：char
                布尔类型：boolean
           对于整型类型，Java只定义了带符号的整型，因此，最高位的bit表示符号位（0表示正数，1表示负数）。
           各种整型能表示的最大范围如下：
                byte：-128 ~ 127
                short: -32768 ~ 32767
                int: -2147483648 ~ 2147483647
                long: -9223372036854775808 ~ 9223372036854775807
           Java的 char类型除了可表示标准的ASCII外，还可以表示一个Unicode字符，
           注意char类型使用单引号'，且仅有一个字符，要和双引号"的字符串类型区分开。
           Java在内存中总是使用Unicode表示字符，所以一个英文字符和一个中文字符都用一个char类型表示，它们都占用两个字节：from:字符和字符串
           Java的 String 和 char 在内存中总是以Unicode编码表示。from: 面向对象--Java核心类--字符串和编码
        */
        byte by = 127; // byte 就是占用一个字节的整形
        System.out.println(by);
        char a = 'A';
        char zh = '中';
        System.out.printf("Test char a = %s\n", a);
        System.out.printf("Test char zh = %s\n", zh);
        // long型的结尾需要加 L, 如果超过 int 的表示最大范围 2147483647 且 没有加 L,则会报整数太大
        long lx = 2147483640;
        long ly = 15;
        long sum = lx + ly;
        System.out.println(sum); // 2147483655
        // Java的包装类型还定义了一些有用的静态变量 from：面向对象--Java核心类--包装类型
        int max = Integer.MAX_VALUE; // 2147483647
        int min = Integer.MIN_VALUE; // -2147483648
        System.out.printf("max = %d, min = %d\n", max, min);
        // long类型占用的bit和byte数量:
        int sizeOfLong = Long.SIZE; // 64 (bits)
        int bytesOfLong = Long.BYTES; // 8 (bytes)
        System.out.printf("sizeOfLong = %d, bytesOfLong = %d\n", sizeOfLong, bytesOfLong);

        /* 浮点类型的数就是小数，因为小数用科学计数法表示的时候，小数点是可以“浮动”的，
         * 如1234.5可以表示成12.345x10^2，也可以表示成1.2345x10^3，所以称为浮点数。  对于 float 类型，需要加上 f 后缀。
         * 整数运算在除数为0时会报错，而浮点数运算在除数为0时，不会报错，但会返回几个特殊值：
         *    NaN表示Not a Number,  比如 double d1 = 0.0 / 0
         *    Infinity表示无穷大,    比如 double d2 = 1.0 / 0
         *    -Infinity表示负无穷大, 比如 double d3 = -1.0 / 0
         * 浮点数 0.1在计算机中就无法精确表示，因为十进制的0.1换算成二进制是一个无限循环小数，
         * 很显然，无论使用float还是double，都只能存储一个0.1的近似值。但是，0.5这个浮点数又可以精确地表示。
         */
        float f = 3.14e38f; // 科学计数法表示的3.14x10^38
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
        // 要显示一个字符的 Unicode 编码，只需将char类型直接赋值给int类型即可：from: 字符和字符串
        // int ch2int = "中"; 会报错的哦，原因参考下面提到的： String是一个引用类型，它本身也是一个class
        int ch2int = '中';
        System.out.printf("ch2int = %d\n", ch2int);
        int tmp = 20013;
        System.out.println((char)tmp);

        /* 引用类型： 和char类型不同，字符串类型 String 是引用类型，我们用双引号"..."表示字符串
         * 引用类型的变量类似于 C 语言的指针，它内部存储一个“地址”，指向某个对象在内存的位置。from: Java 程序基础--变量和数据类型
         *
         * 在Java中，String是一个引用类型，它本身也是一个class，但是Java编译器对String有特殊处理，即可以直接用"..."来表示一个字符串,
         * 实际上字符串在String内部是通过一个char[]数组表示的,因此，按下面的写法也是可以的：
         * String s2 = new String(new char[] {'H', 'e', 'l', 'l', 'o', '!'});
         * 因为String太常用了，所以Java提供了"..."这种字符串字面量表示方法。 from: 面向对象编程--Java核心类--字符串和编码
         * Java字符串的一个重要特点就是字符串不可变。
         * 这种不可变性是通过内部的private final char[]字段，以及没有任何修改char[]的方法实现的。from：Java核心类--字符串和编码
         */
        String s1 = "Hello";
        String s2 = "world";
        String ss = s1 + " " + s2 + "!";
        System.out.printf("test add strings: ss = %s\n", ss);
        i = 25;
        System.out.println(ss + i);

        String s3 = "Google";
        String s4 = s3;
        s3 = "Baidu";
        System.out.printf("s4 = %s\n", s4); // 是输出 Google 还是 Baidu？ Google

        stringCorePoc();

        /* 数组是引用类型:
         * 如果在定义数组变量的同时初始化数组内容，就不能再指定数组大小了，而是由编译器自动推算数组大小
         * int[] ns = new int[] {11,22,33};  等于  int[] ns = {11,22,33};
         *
         * Java 中的数组和 C / Go 中的数组的差异：
         * C 语言中：
         * 数组变量代表了数组首元素的地址，数组变量是不能重新赋值的，参考 http://c.biancheng.net/view/185.html。
         * 若定义了一个数组“int a[5]={1，2，3，4，5}；”，又定义了一个数组“int b[5]；”，
         * 那么如何编写程序才能将数组 a 中的数据赋给数组 b？ 经常有人会这样写：
         *      b = a;
         * 这样写是错误的。前面说过，a 和 b 是数组名，而数组名表示的是数组“第一个元素”的“起始地址”。
         * 即 a 和 b 表示的是地址，是一个常数，不能将一个常数赋给另一个常数。这种错误就类似于将 3 赋给 2，所以是错误的。
         *
         * Go 语言中：参考：awesomeProject/libbase/array_poc.go
         * array := [9]int{1,2,3,4,5,6,7,8,9}
         * array_tmp := array  // 是完全拷贝了一份数组。
         * array_tmp[0] = 655
	     * fmt.Printf("The adrress of array = %p,  array = %d\n", &array, array)
	     * fmt.Printf("The adrress of array_tmp = %p,  array_tmp = %d\n", &array_tmp, array_tmp)
         *
         * Java 中：
         * int[] ns;
         * ns = new int[] {68, 79, 91, 85, 62};
         * ns = new int[] {61，62 }; //居然还能再赋值,根据这个写法猜测Java的机制就当这是一个数组对象(和自定义的类 Student 没区别)而已
         * System.out.println(ns.length); // 5
         * int[] nb = new int[] { 1, 2, 3 };
         * ns = nb;
         * System.out.println(ns.length); // 3
         */
        int[] ns = new int[] {11,22,33}; //如果在定义数组变量的同时初始化数组内容，就不能再指定数组大小了，而是由编译器自动推算数组大小
        int itmp = ns[1];
        ns[1] = 99;
        System.out.printf("itmp = %d\n", itmp); // itmp 是 99 还是 22 ？ 22
        String[] names = {"ABC", "XYZ", "zoo"};
        String stmp = names[1];
        names[1] = "cat";
        System.out.printf("stmp = %s\n",stmp); // stmp 是"XYZ"还是"cat"?  XYZ

        // Java源码的缩进不是必须的，但是用缩进后，格式好看，很容易看出代码块的开始和结束，缩进一般是4个空格或者一个tab。
        // from:Java 快速入门--Java简介--第一个Java程序
        int score = 60;
        if (score > 90)
            System.out.println("excellent!");
            System.out.println("soso......"); // 重点2：这句话居然输出了，这和 Python 很不一样,充分证明 Java 不缩进

        // 输入 from: Java 快速入门--流程控制相关章节
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Please input your age:");
        // int age = scanner.nextInt();
        // System.out.printf("age = %d\n", age);
        stringCmp();
        switchTest();
        overflowInt();  // 重点3-1：非常棒的测试
        forCircle();
    }

    public void stringCmp(){
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
        // 对于字符数组的话，直接打印数组变量会得到字符数组对应的字符串而不是地址。
        // 联系上面提到的 String class 有个 char[] 字段，然后输出字符串的时候，大概率就是从 char[]字段取值的吧
        char[] achar = {'C', 'H', 'I', 'N', 'A'};
        System.out.println(achar);

        // 使用for each循环打印也很麻烦。幸好Java标准库提供了Arrays.toString()，可以快速打印数组内容
        System.out.println(Arrays.toString(ns));
        int[] nss = { 1, 14, 9, 16, 25 };
        Arrays.sort(nss);
    }

    void stringCorePoc(){
        /*
         * 面向对象编程--Java核心类--字符串和编码
         */
        //要分割字符串，使用split()方法，并且传入的也是正则表达式：
        String s = "A,B,C,D";
        String[] ss = s.split("\\,"); // {"A", "B", "C", "D"}

        // 拼接字符串使用：静态方法join()，它用指定的字符串连接字符串数组：
        System.out.println("In the stringAdvPoc");
        String[] names = {"Bob", "Alice", "Grace"};
        var sjon = String.join(", ", names);
        System.out.println(sjon);

        // 字符串提供了 formatted() 方法和 format() 静态方法，可以传入其他参数，替换占位符，然后生成新的字符串
        // String note = "Hi %s, your score is %d!";  // For MAC:Error:(236, 29) java: formatted(java.lang.Object...)是某预览功能中的一个 API
        // System.out.println(note.formatted("Alice", 80));
        System.out.println(String.format("Hi %s, your score is %.2f!", "Bob", 59.5));

        // 类型转换：要把任意基本类型或引用类型转换为字符串，可以使用： 静态方法 valueOf()
        String.valueOf(666); // "666"
        String.valueOf(45.67); // "45.67"
        String.valueOf(true); // "true"
        String.valueOf(new Object()); // 类似java.lang.Object@636be97c
        // 要把字符串转换为其他类型，就需要根据情况。例如，把字符串转换为int类型：
        int n1 = Integer.parseInt("666"); // 666
        int n2 = Integer.parseInt("ff", 16); // 按十六进制转换，255
        // 把字符串转换为boolean类型：
        boolean bo1 = Boolean.parseBoolean("true"); // true
        boolean bo2 = Boolean.parseBoolean("FALSE"); // false

        // String 和 char[] 类型可以互相转换，方法是：
        char[] cs = "GREAT".toCharArray(); // String -> char[]
        System.out.println(cs);
        String s5 = new String(cs); // char[] -> String
        System.out.println(s5);

        /* 在Java中，char类型实际上就是两个字节的 Unicode编码。如果我们要手动把字符串转换成其他编码，可以这样做：
           byte[] b1 = "Hello".getBytes(); // 按系统默认编码转换，不推荐
           byte[] b2 = "Hello".getBytes("UTF-8"); // 按UTF-8编码转换
           byte[] b2 = "Hello".getBytes("GBK"); // 按GBK编码转换
           byte[] b3 = "Hello".getBytes(StandardCharsets.UTF_8); // 按UTF-8编码转换
           注意：转换编码后，就不再是 char 类型，而是 byte类型表示的数组。

           延伸阅读: from: 面向对象编程--Java核心类--字符串和编码
           对于不同版本的JDK，String类在内存中有不同的优化方式。具体来说，早期JDK版本的String总是以char[]存储，它的定义如下：
                public final class String {
                    private final char[] value;
                    private final int offset;
                    private final int count;
                }
            而较新的JDK版本的String则以 byte[]存储：如果String仅包含ASCII字符，则每个byte存储一个字符，否则，每两个byte存储一个字符，
            这样做的目的是为了节省内存，因为大量的长度较短的String通常仅包含ASCII字符：
                public final class String {
                    private final byte[] value;
                    private final byte coder; // 0 = LATIN1, 1 = UTF16
            对于使用者来说，String内部的优化不影响任何已有代码，因为它的public方法签名是不变的。
         */
        // byte[] b1 = "abc".getBytes("UnicodeBigUnmarked"); //会输出 0097 0098 0099, 把00也输出来了
        byte[] b1 = "abc".getBytes();
        for(byte i:b1){
            System.out.println(i);
        }
        String s6 = new String(b1);
        System.out.printf("s6 = %s\n", s6);

        // https://blog.csdn.net/qq_22771739/article/details/84261165
        // https://blog.csdn.net/lcfeng1982/article/details/6830584
        try {
            System.out.println("Good test the getBytes:");
            String temp = "中";
            // 该操作返回了四个字节，-1,-2,是字节顺的一种表示，这是由sun的类库实现，
            // 指示如果没有指定字节就使用默认的UnicodeLittle， 但为了标识这种字节顺，就使用了-1,-2在前面表示。
            byte[] unicodes = temp.getBytes("Unicode");
            System.out.println("unicodes=" + unicodes.length);
            for (int i = 0; i < unicodes.length; i++) {
                System.out.println(unicodes[i]);
            }
            unicodes = temp.getBytes("UnicodeLittleUnmarked");
            System.out.println("unicodes=" + unicodes.length);
            for (int i = 0; i < unicodes.length; i++) {
                System.out.println(unicodes[i]);
            }
            unicodes = temp.getBytes("UnicodeBigUnmarked");
            System.out.println("unicodes=" + unicodes.length);
            for (int i = 0; i < unicodes.length; i++) {
                System.out.println(unicodes[i]);
            }

            //汉字 中 对应的 unicode 码是 4E2D
            String ts = "中中国";
            char[] ca = ts.toCharArray(); // String -> char[], 转换后是个 char 数组，每个元素都是一个 char: 中 中 国
            System.out.println(ca);
            for(char one: ca){
                System.out.println(Integer.toBinaryString(one));
            }
            System.out.println(Integer.toBinaryString(ts.charAt(0))); // 01001110 00101101
            System.out.println(Integer.toBinaryString(ts.charAt(1))); // 01001110 00101101
            System.out.println(Integer.toBinaryString(ts.charAt(2))); // 01010110 11111101

            unicodes = ts.getBytes("UnicodeBigUnmarked");
            System.out.println("unicodes=" + unicodes.length);
            for (int i = 0; i < unicodes.length; i++) {
                System.out.println(unicodes[i]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

