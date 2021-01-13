package com.company;

/* 1.不变类:
     所有的包装类型都是不变类(这应该是第一次提到不变类)。我们查看Integer的源码可知，它的核心代码如下：
          public final class Integer {
              private final int value;
          }
     因此，一旦创建了Integer对象，该对象就是不变的。
     对两个Integer实例进行比较要特别注意：绝对不能用 == 比较，因为Integer是引用类型，必须使用equals()比较：
          public class Main {
              public static void main(String[] args) {
                  Integer x = 127;
                  Integer y = 127;
                  Integer m = 99999;
                  Integer n = 99999;
                  System.out.println("x == y: " + (x==y)); // true
                  System.out.println("m == n: " + (m==n)); // false
                  System.out.println("x.equals(y): " + x.equals(y)); // true
                  System.out.println("m.equals(n): " + m.equals(n)); // true
              }
          }
    仔细观察结果的童鞋可以发现，==比较，较小的两个相同的Integer返回true，较大的两个相同的Integer返回false，
    这是因为Integer是不变类，编译器把 Integer x = 127; 自动变为 Integer x = Integer.valueOf(127);，
    为了节省内存，Integer.valueOf()对于较小的数，始终返回相同的实例，
    因此，==比较“恰好”为true，但我们绝不能因为Java标准库的 Integer 内部有缓存优化就用==比较，必须用equals()方法比较两个Integer。
    按照语义编程，而不是针对特定的底层实现去“优化”。
    
    因为Integer.valueOf()可能始终返回同一个Integer实例，因此，在我们自己创建Integer的时候，以下两种方法：
       方法1：Integer n = new Integer(100);
       方法2：Integer n = Integer.valueOf(100);
    方法2更好，因为方法1总是创建新的Integer实例，方法2把内部优化留给Integer的实现者去做，即使在当前版本没有优化，也有可能在下一个版本进行优化。

  2. 我们把能创建“新”对象的静态方法称为 静态工厂方法。
     Integer.valueOf()就是静态工厂方法，它尽可能地返回缓存的实例以节省内存。创建新对象时，优先选用静态工厂方法而不是new操作符。
     如果我们考察 Byte.valueOf()方法的源码，可以看到，标准库返回的Byte实例全部是缓存实例，
     但调用者并不关心静态工厂方法以何种方式创建新实例还是直接返回缓存的实例。
 */
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



