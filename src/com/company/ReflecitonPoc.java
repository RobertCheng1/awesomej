package com.company;

import java.lang.reflect.*;
import java.util.ConcurrentModificationException;

/* 反射是为了解决在运行期，对某个实例一无所知的情况下，如何调用其方法。
   1. class（包括interface）的本质是数据类型（Type）.比如自定义的 class Person、Student 和JVM自带的 int、String本质没区别都是数据类型。
      每加载一种class，JVM就为其创建一个Class类型的实例，并关联起来。注意：这里的Class类型是一个名叫 Class 的class。它长这样：
            public final class Class {
                private Class() {}
            }
      以String类为例，当JVM加载String类时，它首先读取String.class文件到内存，然后，为String类创建一个Class实例并关联起来：
            Class cls = new Class(String);
      这个Class实例是JVM内部创建的，如果我们查看JDK源码，可以发现 Class类的构造方法是 private，只有JVM能创建Class实例，
      我们自己的Java程序是无法创建Class实例的。所以，JVM持有的每个Class实例都指向一个数据类型（class或interface）


   2. 通过Class实例获取 class 信息的方法称为反射（Reflection）。
      方法一：直接通过一个class的静态变量class获取：
            Class cls = String.class;
      方法二：如果我们有一个实例变量，可以通过该实例变量提供的getClass()方法获取：
            String s = "Great";
            Class cls = s.getClass();
      方法三：如果知道一个class的完整类名，可以通过静态方法Class.forName()获取：
            Class cls = Class.forName("java.lang.String");
      因为Class实例在JVM中是唯一的，所以，上述方法获取的Class实例是同一个实例。可以用==比较两个Class实例。
      JVM对每个加载的Class只创建一个Class实例来表示它的类型。from：反射--获取继承关系
       
   3. 如果获取到了一个Class实例，我们就可以通过该Class实例来创建对应类型的实例：
            // 获取String的Class实例:
            Class cls = String.class;
            // 创建一个String实例:
            String s = (String) cls.newInstance();
      上述代码相当于 new String()。通过Class.newInstance()可以创建类实例，
      它的局限是：只能调用public的无参数构造方法。带参数的构造方法，或者非public的构造方法都无法通过Class.newInstance()被调用。

   4. 动态加载:
      JVM在执行Java程序的时候，并不是一次性把所有用到的class全部加载到内存，而是第一次需要用到class时才加载。例如：
            // Main.java
            public class Main {
                public static void main(String[] args) {
                    if (args.length > 0) {
                        create(args[0]);
                    }
                }
                static void create(String name) {
                    Person p = new Person(name);
                }
            }
      当执行Main.java时，由于用到了Main，因此，JVM首先会把Main.class加载到内存。
      然而，并不会加载Person.class，除非程序执行到create()方法，JVM发现需要加载Person类时，才会首次加载Person.class。
      如果没有执行create()方法，那么Person.class根本就不会被加载。这就是JVM动态加载class的特性。

      动态加载class的特性对于Java程序非常重要。利用JVM动态加载class的特性，我们才能在运行期根据条件加载不同的实现类。
      例如，Commons Logging总是优先使用Log4j，只有当Log4j不存在时，才使用JDK的logging。利用JVM动态加载特性，大致的实现代码如下：
            // Commons Logging优先使用Log4j:
            LogFactory factory = null;
            if (isClassPresent("org.apache.logging.log4j.Logger")) {
                factory = createLog4j();
            } else {
                factory = createJdkLog();
            }

            boolean isClassPresent(String name) {
                try {
                    Class.forName(name);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
      这就是为什么我们只需要把Log4j的jar包放到classpath中，Commons Logging就会自动使用Log4j的原因。
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

    public void accessField(Object obj) throws NoSuchFieldException, IllegalAccessException{
        /* 访问字段：
           如何通过Class实例获取字段信息。Class类提供了以下几个方法来获取字段：
                Field getField(name)：根据字段名获取某个public的field（包括父类）
                Field getDeclaredField(name)：根据字段名获取当前类的某个field（不包括父类）
                Field[] getFields()：获取所有public的field（包括父类）
                Field[] getDeclaredFields()：获取当前类的所有field（不包括父类）

           一个Field对象包含了一个字段的所有信息：
                getName()：返回字段名称，例如，"name"；
                getType()：返回字段类型，也是一个Class实例，例如，String.class；
                getModifiers()：返回字段的修饰符，它是一个int，不同的bit表示不同的含义。
                    Modifier.isFinal(m); // true
                    Modifier.isPublic(m); // false
                    Modifier.isProtected(m); // false
                    Modifier.isPrivate(m); // true
                    Modifier.isStatic(m); // false
         */
        System.out.println("In the accessField");
        Class cls = obj.getClass();
        Field field = cls.getDeclaredField("score");
        field.setAccessible(true);
        Object oldValue = field.get(obj);  //String s = (String) value; from: the fieldCheck of Annotation.java
        System.out.printf("for changeScore: Old value = %d\n", oldValue);
        field.set(obj, 90);
    }

    public void accessMethod(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        /* 调用方法：
           Class实例获取所有Method信息。Class类提供了以下几个方法来获取Method：
                Method getMethod(name, Class...)：获取某个public的Method（包括父类）
                Method getDeclaredMethod(name, Class...)：获取当前类的某个Method（不包括父类）
                Method[] getMethods()：获取所有public的Method（包括父类）
                Method[] getDeclaredMethods()：获取当前类的所有Method（不包括父类）

           一个Method对象包含一个方法的所有信息：
                getName()：返回方法名称，例如："getScore"；
                getReturnType()：返回方法返回值类型，也是一个Class实例，例如：String.class；
                getParameterTypes()：返回方法的参数类型，是一个Class数组，例如：{String.class, int.class}；
                getModifiers()：返回方法的修饰符，它是一个int，不同的bit表示不同的含义。
         */
        System.out.println("In the accessMethod");
        Class cls = obj.getClass();
        // 获取 public方法 setScore，参数类型为 int
        Method method = cls.getMethod("setScore",int.class);
        System.out.println(method);
        method.setAccessible(true);
        method.invoke(obj, 100);
    }

    public void constructor(Object obj) throws NoSuchMethodException, InvocationTargetException,
                                               InstantiationException, IllegalAccessException {
        /* 构造方法：
           通过Class实例获取Constructor的方法如下：
                getConstructor(Class...)：获取某个public的Constructor；
                getDeclaredConstructor(Class...)：获取某个Constructor；
                getConstructors()：获取所有public的Constructor；
                getDeclaredConstructors()：获取所有Constructor。
            注意Constructor总是当前类定义的构造方法，和父类无关，因此不存在多态的问题。
            调用 非public 的 Constructor 时，必须首先通过setAccessible(true)设置允许访问。setAccessible(true)可能会失败。

            class.newInstance() 的局限：
            我们通常使用new操作符创建新的实例：
                Person p = new Person();
            如果通过反射来创建新的实例，可以调用Class提供的newInstance()方法：
                Person p = Person.class.newInstance();
            调用Class.newInstance()的局限是，它只能调用该类的public无参数构造方法。
            如果构造方法带有参数，或者不是public，就无法直接通过Class.newInstance()来调用。
            为了调用任意的构造方法，Java的反射API提供了Constructor对象，它包含一个构造方法的所有信息，可以创建一个实例。
            Constructor对象和Method非常类似，不同之处仅在于它是一个构造方法，并且，调用结果总是返回实例。
         */
        System.out.println("In the constructor");
        Class cls = obj.getClass();
        Constructor con = cls.getConstructor(String.class, int.class, int.class);
        Student stu = (Student) con.newInstance("IamFromReflection", 13, 99);
        System.out.println(stu.getName());
    }

    public void inherit(){
        /* 通过Class对象可以获取继承关系：
                Class getSuperclass()：获取父类类型；
                Class[] getInterfaces()：获取当前类实现的所有接口。
                注：getInterfaces()只返回当前类直接实现的接口类型，并不包括其父类实现的接口类型：
           通过Class对象的isAssignableFrom()方法可以判断一个向上转型是否可以实现。
         */
        System.out.println("In the inherit");
        Class i = Integer.class;
        Class n = i.getSuperclass();
        System.out.println(n);
        Class o = n.getSuperclass();
        System.out.println(o);
        System.out.println(o.getSuperclass());
        //获取接口
        Class[] is = i.getInterfaces();
        for (Class entry : is) {
            System.out.println(entry);
        }

        //如果是两个Class实例，要判断一个向上转型是否成立，可以调用isAssignableFrom()：
        //Number包括的子类有整型，浮点型，大整型
        Number.class.isAssignableFrom(Integer.class); // true，因为Integer可以赋值给Number
        Integer.class.isAssignableFrom(Number.class); // false，因为Number不能赋值给Integer
    }

    public void dynamicProxy(){
        /**
         * 有没有可能不编写实现类，直接在运行期创建某个interface的实例呢？
         * 这是可能的，因为Java标准库提供了一种动态代理（Dynamic Proxy）的机制：可以在运行期动态创建某个interface的实例。
         *
         * 动态代理：ToDo:和 Outer.java 中匿名类有些相似
         * 先定义了接口Hello，但是我们并不去编写实现类，而是直接通过JDK提供的一个Proxy.newProxyInstance()创建了一个Hello接口对象。
         * 这种没有实现类但是在运行期动态创建了一个接口对象的方式，我们称为动态代码。JDK提供的动态创建接口对象的方式，就叫动态代理。
         *
         * 在运行期动态创建一个interface实例的方法如下：
         *      1. 定义一个InvocationHandler实例，它负责实现接口的方法调用；
         *      2. 通过Proxy.newProxyInstance()创建interface实例，它需要3个参数：
         *            使用的ClassLoader，通常就是接口类的ClassLoader；
         *            需要实现的接口数组，至少需要传入一个接口进去；
         *            用来处理接口方法调用的 InvocationHandler 实例。
         *      3. 将返回的Object强制转型为接口。
         * 动态代理实际上是JVM在运行期动态创建class字节码并加载的过程，它并没有什么黑魔法，把上面的动态代理改写为静态实现类大概长这样：
         *      public class HelloDynamicProxy implements Hello {
         *          InvocationHandler handler;
         *          public HelloDynamicProxy(InvocationHandler handler) {
         *              this.handler = handler;
         *          }
         *          public void morning(String name) {
         *              // 根据 InvocationHandler 的 invoke 覆写实现，invoke 需要三个参数
         *              handler.invoke(this,
         *                             Hello.class.getMethod("morning", String.class),
         *                             new Object[] { name });
         *          }
         *      }
         * 其实就是JVM帮我们自动编写了一个上述类（不需要源码，可以直接生成字节码），并不存在可以直接实例化接口的黑魔法。
         */
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method);
                if (method.getName().equals("morning")) {
                    System.out.println("Good morning, " + args[0]);
                }
                return null;
            }
        };
        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(), // 传入ClassLoader
                new Class[] { Hello.class }, // 传入要实现的接口
                handler); // 传入处理调用方法的InvocationHandler
        hello.morning("Bob");
        hello.night("Jordan");
    }
}


interface Hello {
    void morning(String name);
    void night(String name);
}

