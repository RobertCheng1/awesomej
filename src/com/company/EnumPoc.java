package com.company;


/* 通过 enum 定义的枚举类，和其他的 class有什么区别？
 *    答案是没有任何区别。enum 定义的类型就是class，只不过它有以下几个特点：
 *        定义的enum类型总是继承自java.lang.Enum，且无法被继承；
 *        只能定义出enum的实例，而无法通过new操作符创建enum的实例；
 *        定义的每个实例都是引用类型的唯一实例；
 *        可以将enum类型用于switch语句。
 *    
 *    例如，我们定义的Color枚举类：
 *        public enum Color {
 *            RED, GREEN, BLUE;
 *        }
 *    编译器编译出的class大概就像这样： ===这个细节真是太棒了===
 *        public final class Color extends Enum { // 继承自Enum，标记为final class
 *            // 每个实例均为全局唯一:
 *            public static final Color RED = new Color();
 *            public static final Color GREEN = new Color();
 *            public static final Color BLUE = new Color();
 *    
 *            // private构造方法，确保外部无法调用new操作符:
 *            private Color() {}
 *        }
 *    所以，编译后的enum类和普通class并没有任何区别。但是我们自己无法按定义普通class那样来定义 enum，必须使用enum关键字，这是Java语法规定的。
 *    因为 enum是一个class，每个枚举的值都是 class 实例，from: 面向对象编程--Java核心类--枚举类
 *
 * 进阶:
 *  1. 使用 enum 定义的枚举类是一种引用类型。前面我们讲到，引用类型比较，要使用 equals()方法，
 *     如果使用 == 比较，它比较的是两个引用类型的变量是否是同一个对象。因此，引用类型比较，要始终使用equals()方法，但 enum类型可以例外。
 *
 *  2. enum 和 record(不变类) 的主要区别：
 *     枚举类的字段也可以是 非final 类型，即可以在运行期修改，但是不推荐这样做！
 *     enum的构造方法要声明为 private，字段强烈建议声明为 final；(既然是建议说明就不是强制)。 from: 面向对象编程--Java核心类--枚举类
 */

import lombok.Getter;

import java.time.DayOfWeek;
import java.util.Arrays;

// 面向对象编程--面向对象基础--包和作用域两章:
// 1. 不写 public，也能正确编译，但是这个类将无法从命令行执行。 from:Java程序基础--Java程序基本结构
// 2. 没有 public 的 enum Color 仍然可以在 Main.java 中访问 Color：因为包作用域。
//    包作用域是指一个类允许访问同一个package的没有public、private修饰的class，以及没有public、protected、private修饰的字段和方法。
// 3. 一个.java文件只能包含一个 public 类，但可以包含多个非 public 类。如果有 public 类，文件名必须和public类的名字相同。
//    如果我改为 public enum Color 则会提示：Class 'Color' is public，should be declared in a file named 'Color.java'
enum Color {
    RED, GREEN, BLUE;

    @Override
    public String toString(){
        return "AAA";
    }
}


@Getter
enum Weekday {
    // ordinal(): 返回定义的常量的顺序，从0开始计数，例如：
    //      int n = Weekday.MON.ordinal(); // 1
    // 改变枚举常量定义的顺序就会导致ordinal()返回值发生变化。要编写健壮的代码，就不要依靠ordinal()的返回值。
    // 因为 enum 本身是class，所以我们可以定义private的构造方法，并且，给每个枚举常量添加字段,
    // 这样就无需担心顺序的变化，新增枚举常量时，也需要指定一个int值。
    // 注意：枚举类的字段也可以是 非final 类型，即可以在运行期修改，但是不推荐这样做！===可能这就是 enum 和 record(不变类) 的主要区别===
    MON(1, "星期一"), TUE(2, "星期二"), WED(3, "星期三"),
    THU(4, "星期四"), FRI(5, "星期五"),
    SAT(6, "星期六"), SUN(0, "星期日");

    // 可以为 enum 编写构造方法、字段和方法
    // enum的构造方法要声明为 private，字段强烈建议声明为 final；
    // enum适合用在 switch 语句中。
    public final int dayValue;
    private final String chinese;

    private Weekday(int dayValue, String chinese) {
        this.dayValue = dayValue;
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        //覆写toString()的目的是使输出的信息更有可读性。
        return this.chinese;
    }
}

public class EnumPoc{
    public void touchEnum(){
        //默认情况下，对枚举常量调用toString()会返回和name()一样的字符串。但是，toString()可以被覆写，而name()则不行.
        //所以通过name()获取常量定义的字符串，注意不要使用toString()；
        System.out.println("In the touchEnum");
        Color yanse = Color.RED;
        System.out.println(yanse); //猛一看是直接打印对象，其实是在调用对象的toString()，目的是使输出的信息更有可读性。
        System.out.println(yanse.name()); // 返回常量名 或者说是 常量定义的字符串  from:枚举类


        for(Weekday oneDay: Weekday.values()){
            System.out.println(oneDay.getDayValue());
            System.out.println(oneDay.getChinese());
        }

    }
}

