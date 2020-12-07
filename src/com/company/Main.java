package com.company;

public class Main {

    /**
     * java 入口程序规定的方法必须是静态方法，方法名必须为 main，括号内的参数必须是String数组。
     * @param args
     */
    public static void main(String[] args) {
        // write your code here
        System.out.println("Hello, world!");
        // 变量和数据类型:
        // 在 Java 中，变量分为两种：基本类型的变量和引用类型的变量。
        // Java的 char类型除了可表示标准的ASCII外，还可以表示一个Unicode字符，
        // 注意char类型使用单引号'，且仅有一个字符，要和双引号"的字符串类型区分开。
        // Java在内存中总是使用Unicode表示字符，所以一个英文字符和一个中文字符都用一个char类型表示，它们都占用两个字节：from:字符和字符串
        char a = 'A';
        char zh = '中';
        System.out.println(a);
        System.out.println(zh);
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
        // 有些时候，类型的名字太长，写起来比较麻烦。这时如果想省略变量类型，可以使用 var关键字，使用 var 定义变量，仅仅是少写了变量类型而已。
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
        /* 引用类型： 和 char类型不同，字符串类型String是引用类型，我们用双引号"..."表示字符串 */
        String s1 = "Hello";
        String s2 = "world";
        String ss = s1 + " " + s2 + "!";
        System.out.println(ss);
        i = 25;
        System.out.println(ss + i);
        String s3 = "hellooo";
        String s4 = s3;
        s3 = "world";
        System.out.println(s4); // 输出 hellooo 重点1：这个 demo 非常棒，一下子就所清楚了基本类型和引用类型的区别
        int tmp = 20013;
        System.out.println((char)tmp);
        // 数组
        String[] names = {"ABC", "XYZ", "zoo"};
        String stmp = names[1];
        names[1] = "cat";
        System.out.println(stmp); // s是"XYZ"还是"cat"?
    }
}
