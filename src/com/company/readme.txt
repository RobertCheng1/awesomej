PS D:\workspace_java\awesome\src\com\company>
PS D:\workspace_java\awesome\src\com\company> java Main （注意源码 Main.class 中有 package com.company;）
错误: 找不到或无法加载主类 Main
原因: java.lang.NoClassDefFoundError: com/company/Main (wrong name: Main)
PS D:\workspace_java\awesome\src\com\company>
PS D:\workspace_java\awesome\src\com\company> 根据 ctrl + shift + F10 的输出 和 jave --help 对 classpath 的解释，尝试 classpath 参数
PS D:\workspace_java\awesome\src\com\company> java -classpath D:\workspace_java\awesome\out\production\awesome  Main
错误: 找不到或无法加载主类 Main
原因: java.lang.ClassNotFoundException: Main
PS D:\workspace_java\awesome\src\com\company>
PS D:\workspace_java\awesome\src\com\company> # 仔细观察 ctrl + shift + F10 的输出：把 Main 改为 com.company.Main 就可以了，这是为什么？
PS D:\workspace_java\awesome\src\com\company> java -classpath D:\workspace_java\awesome\out\production\awesome  com.company.Main
Hello, world!
PS D:\workspace_java\awesome\src\com\company> 猜测 Java 中包的名字和文件系统中文件的名字有对应关系
PS D:\workspace_java\awesome\src\com\company>
PS D:\workspace_java\awesome\src\com\company>

Java简介--安装 JDK：https://www.liaoxuefeng.com/wiki/1252599548343744/1280507291631649:
    细心的童鞋还可以在JAVA_HOME的bin目录下找到很多可执行文件：
        java：这个可执行程序其实就是JVM，运行Java程序，就是启动JVM，然后让JVM执行指定的编译后的代码；
        javac：这是Java的编译器，它用于把Java源码文件（以.java后缀结尾）编译为Java字节码文件（以.class后缀结尾）；
        jar：用于把一组.class文件打包成一个.jar文件，便于发布；
        javadoc：用于从Java源码中自动提取注释并生成文档；
        jdb：Java调试器，用于开发阶段的运行调试。


Java简介--第一个Java程序https://www.liaoxuefeng.com/wiki/1252599548343744/1255878730977024
    java Hello: 给虚拟机传递的参数 Hello 是我们定义的类名，虚拟机自动查找对应的 class 文件并执行。

    一个Java源码只能定义一个public类型的class，并且class名称和文件名要完全一致；
    一个.java文件只能包含一个 public 类，但可以包含多个非 public 类。如果有 public 类，文件名必须和public类的名字相同。面向对象基础--包和作用域两章:
    使用javac可以将.java源码编译成.class字节码；
    使用java可以运行一个已编译的Java程序，参数是类名。

面向对象编程--面向对象基础--包和作用域两章:
    https://www.liaoxuefeng.com/wiki/1252599548343744/1260467032946976
    https://www.liaoxuefeng.com/wiki/1252599548343744/1260466215676512
    在 Java中，我们使用package来解决名字冲突:
    Java定义了一种名字空间，称之为包：package。一个类总是属于某个包，类名（比如Person）只是一个简写，真正的完整类名是包名.类名。
    要特别注意：包没有父子关系。java.util 和 java.util.zip 是不同的包，两者没有任何继承关系。

    Java编译器最终编译出的.class文件只使用完整类名，因此，在代码中，当编译器遇到一个class名称时：
        如果是完整类名，就直接根据完整类名查找这个class；
        如果是简单类名，按下面的顺序依次查找：
            查找当前package是否存在这个class；
            查找import的包是否包含这个class；
            查找java.lang包是否包含这个class。
    如果按照上面的规则还无法确定类名，则编译报错。

    因此，编写class的时候，编译器会自动帮我们做两个 import 动作：
        默认自动import当前package的其他class；
        默认自动import java.lang.*。
    注意：自动导入的是java.lang包，但类似 java.lang.reflect 这些包仍需要手动导入。

    一个.java文件只能包含一个 public 类，但可以包含多个非 public 类。如果有 public 类，文件名必须和 public 类的名字相同。

面向对象编程--面向对象基础--模块:
    把一堆 class 封装为jar仅仅是一个打包的过程，而把一堆class封装为模块则不但需要打包，还需要写入依赖关系，并且还可以包含二进制代码（通常是JNI扩展）。
    此外，模块支持多版本，即在同一个模块中可以为不同的JVM提供不同的版本。
    jar --help: 如果模块描述符 'module-info.class' 位于指定目录的根目录中, 或者位于 jar 档案本身的根目录中, 则该档案是一个模块化 jar。

    JDK提供的命令行工具来编译并创建模块:
    1. 把工作目录切换到oop-module，在当前目录下编译所有的.java文件，并存放到bin目录下:
        $ javac -d bin src/module-info.java src/com/itranswarp/sample/*.java
        oop-module
        ├── bin
        │   ├── com
        │   │   └── itranswarp
        │   │       └── sample
        │   │           ├── Greeting.class
        │   │           └── Main.class
        │   └── module-info.class
        └── src
            ├── com
            │   └── itranswarp
            │       └── sample
            │           ├── Greeting.java
            │           └── Main.java
            └── module-info.java
    2. 把bin目录下的所有class文件先打包成jar，在打包的时候，注意传入--main-class参数，让这个jar包能自己定位main方法所在的类：
        $ jar --create --file hello.jar --main-class com.itranswarp.sample.Main -C bin .
        现在我们就在当前目录下得到了 hello.jar这个jar包，它和普通jar包并无区别，可以直接使用命令 java -jar hello.jar 来运行它，因为
        在创建 jar 时已经指定了 --main-class，所以可以直接运行jar 包，否则就需要 java -jar hello.jar com.itranswarp.sample.Main
        灵感来自： MANIFEST.MF文件可以提供jar包的信息，如Main-Class，这样可以直接运行jar包。 from: 上一节 classpath和jar

    3. 使用JDK自带的jmod命令把一个jar包转换成模块：
        $ jmod create --class-path hello.jar hello.jmod
        在当前目录下我们又得到了hello.jmod这个模块文件，这就是最后打包出来的传说中的模块！
        注：.jmod不能被放入--module-path，
        $ java --module-path hello.jmod --module hello.world 这是会报异常的，换成.jar就没问题了
        $ java --module-path hello.jar --module hello.world 就可以了

    辛辛苦苦创建的hello.jmod有什么用？
        使用模块可以按需打包JRE:
        $ jlink --module-path hello.jmod --add-modules java.base,java.xml,hello.world --output jre/
        Mac jmods路径：/Library/Java/JavaVirtualMachines/jdk-15.0.1.jdk/Contents/Home/jmods
        Win jmods路径：C:\Program Files\Java\jdk-15.0.1\jmods

面向对象编程--Java核心类--字符串和编码:
    在Java中，String是一个引用类型，它本身也是一个class。但是，Java编译器对String有特殊处理，即可以直接用"..."来表示一个字符串：
    String s1 = "Hello!";
    实际上字符串在String内部是通过一个char[]数组表示的，因此，按下面的写法也是可以的：
    String s2 = new String(new char[] {'H', 'e', 'l', 'l', 'o', '!'});
    因为String太常用了，所以Java提供了"..."这种字符串字面量表示方法。

    Java字符串的一个重要特点就是字符串不可变。这种不可变性是通过内部的private final char[]字段，以及没有任何修改char[]的方法实现的。
    这点可以联想到:面向对象编程--多态、继承中相关的 final 的用法




面向对象编程--Java核心类--常用工具类:
    实际使用的时候，可以优先获取高强度的安全随机数生成器，如果没有提供，再使用普通等级的安全随机数生成器：
    ===这种手法很 Java,利用捕获异常来选择生成安全数的方法,实在是高！===
    public class Main {
        public static void main(String[] args) {
            SecureRandom sr = null;
            try {
                sr = SecureRandom.getInstanceStrong(); // 获取高强度安全随机数生成器
            } catch (NoSuchAlgorithmException e) {
                sr = new SecureRandom(); // 获取普通的安全随机数生成器
            }
            byte[] buffer = new byte[16];
            sr.nextBytes(buffer); // 用安全随机数填充buffer
            System.out.println(Arrays.toString(buffer));
        }
    }

在 Java 中 int 貌似不是类, 执行下面的代码得到异常：这个灵感来自泛型，
    // 第一个元素是 String, 第二个元素是 Integer， 这居然是可以的，想想也是 String 和 Integer 都继承自 Object
    Object[] tmp = new Object[10];
    tmp[0] = "hello";
    tmp[1] = new Integer(655);
    tmp[2] = 136; // 为什么整形是可以的，这怎么解释？难道整形也是类，但是下面代码的执行结果证明 Java 中 int 貌似不是类

    int aaa = 123;
    if (aaa instanceof Integer){        // java: 意外的类型 需要:引用  找到:int
        System.out.println("noooo");
    }
    System.out.println(aaa.getClass()); // java: 无法取消引用int


/Library/Java/JavaVirtualMachines/jdk-15.0.1.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=50357:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/chengpengxing/workspace_java/awesomej/out/production/awesomej com.company.Main
包作用域和 public 作用域，谁的更宽泛，猜测是public

