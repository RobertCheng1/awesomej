package com.company;

/* 反射是为了解决在运行期，对某个实例一无所知的情况下，如何调用其方法。

   class（包括interface）的本质是数据类型（Type）.比如自定义的 class Person、Student 和 JVM 自带的 int、String本质没区别都是数据类型。
   每加载一种class，JVM就为其创建一个Class类型的实例，并关联起来。注意：这里的Class类型是一个名叫 Class 的class。它长这样：
        public final class Class {
            private Class() {}
        }
   以String类为例，当JVM加载String类时，它首先读取String.class文件到内存，然后，为String类创建一个Class实例并关联起来：
        Class cls = new Class(String);
   这个Class实例是JVM内部创建的，如果我们查看JDK源码，可以发现 Class类的构造方法是 private，只有JVM能创建Class实例，
   我们自己的Java程序是无法创建Class实例的。所以，JVM持有的每个Class实例都指向一个数据类型（class或interface）


   通过Class实例获取class信息的方法称为反射（Reflection）。
   方法一：直接通过一个class的静态变量class获取：
        Class cls = String.class;
   方法二：如果我们有一个实例变量，可以通过该实例变量提供的getClass()方法获取：
        String s = "Hello";
        Class cls = s.getClass();
    方法三：如果知道一个class的完整类名，可以通过静态方法Class.forName()获取：
        Class cls = Class.forName("java.lang.String");
    因为Class实例在JVM中是唯一的，所以，上述方法获取的Class实例是同一个实例。可以用==比较两个Class实例：
 */
public class ReflecitonPoc {

    static void printClassInfo(Class cls) {
        System.out.println("Class name: " + cls.getName());
        System.out.println("Simple name: " + cls.getSimpleName());
        if (cls.getPackage() != null) {
            System.out.println("Package name: " + cls.getPackage().getName());
        }
        System.out.println("is interface: " + cls.isInterface());
        System.out.println("is enum: " + cls.isEnum());
        System.out.println("is array: " + cls.isArray());
        System.out.println("is primitive: " + cls.isPrimitive());
    }

    public void touchReflect() {
        System.out.println("In the touchReflect");
        printClassInfo("".getClass());
        printClassInfo(Runnable.class);
        printClassInfo(java.time.Month.class);
        printClassInfo(String[].class);
        printClassInfo(int.class);
    }
}
