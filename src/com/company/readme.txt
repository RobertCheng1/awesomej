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
    使用javac可以将.java源码编译成.class字节码；
    使用java可以运行一个已编译的Java程序，参数是类名。

面向对象编程--面向对象基础--包和作用域:
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

/Library/Java/JavaVirtualMachines/jdk-15.0.1.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=50357:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/chengpengxing/workspace_java/awesomej/out/production/awesomej com.company.Main












