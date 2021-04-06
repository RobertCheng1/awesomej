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
    要特别注意：包没有父子关系。java.util和java.util.zip是不同的包，两者没有任何继承关系。 from:面向对象编程--面向对象基础--包

    一个.java文件只能包含一个 public 类，但可以包含多个非 public 类。如果有 public 类，文件名必须和 public 类的名字相同。

面向对象编程--面向对象基础--模块:
    java -h 的输出：
    用法: java [-options] class [args...]  (执行类)
    或  java [-options] -jar jarfile [args...]  (执行 jar 文件)

    如果我们要执行一个jar包的class，就可以把jar包放到classpath中：
        java -cp ./hello.jar abc.xyz.Hello   这样JVM会自动在hello.jar文件里去搜索某个类。
    jar包还可以包含一个特殊的/META-INF/MANIFEST.MF文件，MANIFEST.MF是纯文本，可以指定Main-Class和其它信息。
    JVM会自动读取这个MANIFEST.MF文件，如果存在Main-Class，我们就不必在命令行指定启动的类名，而是用更方便的命令：
        java -jar hello.jar

    在Java 9之前，一个大型Java程序会生成自己的jar文件，同时引用依赖的第三方jar文件，
    而JVM自带的Java标准库，实际上也是以jar文件形式存放的，这个文件叫rt.jar，一共有60多M。
    如果是自己开发的程序，除了一个自己的app.jar以外，还需要一堆第三方的jar包，运行一个Java程序，一般来说，命令行写这样：
        java -cp app.jar:a.jar:b.jar:c.jar com.liaoxuefeng.sample.Main

    把一堆 class 封装为jar仅仅是一个打包的过程，
    而把一堆class封装为模块则不但需要打包，还需要写入依赖关系，并且还可以包含二进制代码（通常是JNI扩展）。
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
    过去发布一个Java应用程序，要运行它，必须下载一个完整的JRE，再运行jar包。而完整的JRE块头很大，有100多M。怎么给JRE瘦身呢？这就是模块的意义！
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

    字符串比较:
        public class Main {
            public static void main(String[] args) {
                String s1 = "hello";
                String s2 = "hello";
                System.out.println(s1 == s2);
                System.out.println(s1.equals(s2));
            }
        }
    从表面上看，两个字符串用==和equals()比较都为true，
    但实际上那只是Java编译器在编译期，会自动把所有相同的字符串当作一个对象放入常量池，自然s1和s2的引用就是相同的。
    所以，这种==比较返回true纯属巧合。换一种写法，==比较就会失败：

面向对象编程--Java核心类--包装类型：
    所有的包装类型都是不变类。我们查看Integer的源码可知，它的核心代码如下：
        public final class Integer {
            private final int value;
        }
    因此，一旦创建了Integer对象，该对象就是不变的。
    对两个Integer实例进行比较要特别注意：绝对不能用==比较，因为Integer是引用类型，必须使用equals()比较：
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
    这是因为Integer是不变类，编译器把Integer x = 127;自动变为Integer x = Integer.valueOf(127);，
    为了节省内存，Integer.valueOf()对于较小的数，始终返回相同的实例，因此，==比较“恰好”为true，
    但我们绝不能因为Java标准库的Integer内部有缓存优化就用==比较，必须用equals()方法比较两个Integer。
    按照语义编程，而不是针对特定的底层实现去“优化”。


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


Servlet多线程模型: from: Web开发--Servlet进阶
    一个Servlet类在服务器中只有一个实例，但对于每个HTTP请求，Web服务器会使用多线程执行请求。因此，一个Servlet的doGet()、doPost()等处理请求的方法是多线程并发执行的。如果Servlet中定义了字段，要注意多线程并发访问的问题：
         public class HelloServlet extends HttpServlet {
             private Map<String, String> map = new ConcurrentHashMap<>();
    
             protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                 // 注意读写map字段是多线程并发的:
                 this.map.put(key, value);
             }
         }
    对于每个请求，Web服务器会创建唯一的HttpServletRequest和HttpServletResponse实例，
    因此，HttpServletRequest和HttpServletResponse实例只有在当前处理线程中有效，它们总是局部变量，不存在多线程共享的问题。
     
使用Session和Cookie: from: Web开发--Servlet进阶--使用Session和Cookie
    在使用多台服务器构成集群时，使用Session会遇到一些额外的问题。通常，多台服务器集群使用反向代理作为网站入口：
                                             ┌────────────┐
                                        ┌───>│Web Server 1│
                                        │    └────────────┘
        ┌───────┐     ┌─────────────┐   │    ┌────────────┐
        │Browser│────>│Reverse Proxy│───┼───>│Web Server 2│
        └───────┘     └─────────────┘   │    └────────────┘
                                        │    ┌────────────┐
                                        └───>│Web Server 3│
                                             └────────────┘
    如果多台Web Server采用无状态集群，那么反向代理总是以轮询方式将请求依次转发给每台Web Server，这会造成一个用户在Web Server 1存储的Session信息，
    在Web Server 2和3上并不存在，即从Web Server 1登录后，如果后续请求被转发到Web Server 2或3，那么用户看到的仍然是未登录状态。
    要解决这个问题:
    方案一是在所有Web Server之间进行Session复制，但这样会严重消耗网络带宽，并且，每个Web Server的内存均存储所有用户的Session，内存使用率很低。
    方案二是采用粘滞会话（Sticky Session）机制，即反向代理在转发请求的时候，总是根据JSESSIONID的值判断，相同的JSESSIONID总是转发到固定的Web Server，但这需要反向代理的支持。
    无论采用何种方案，使用Session机制，会使得Web Server的集群很难扩展，
    因此，Session适用于中小型Web应用程序。对于大型Web应用程序来说，通常需要避免使用Session机制。

Java 类的成员变量的初始化本质：from:Spring开发--使用AOP--AOP避坑指南
    那么最终的问题来了：proxy实例的成员变量，也就是从UserService继承的zoneId，它的值是null。
    原因在于，UserService成员变量的初始化：
            public class UserService {
                public final ZoneId zoneId = ZoneId.systemDefault();
                ...
            }
    在UserService$$EnhancerBySpringCGLIB中，并未执行。原因是，没必要初始化proxy的成员变量，因为proxy的目的是代理方法。===proxy 的本质===
    实际上，成员变量的初始化是在构造方法中完成的。这是我们看到的代码：
            public class UserService {
                public final ZoneId zoneId = ZoneId.systemDefault();
                public UserService() {
                }
            }
    这是编译器实际编译的代码：===Java 类的成员变量的初始化本质===
            public class UserService {
                public final ZoneId zoneId;
                public UserService() {
                    super(); // 构造方法的第一行代码总是调用super()
                    zoneId = ZoneId.systemDefault(); // 继续初始化成员变量
                }
            }
    然而，对于Spring通过CGLIB动态创建的UserService$$EnhancerBySpringCGLIB代理类，它的构造方法中，并未调用super()，
    因此，从父类继承的成员变量，包括final类型的成员变量，统统都没有初始化。
    有的童鞋会问：Java语言规定，任何类的构造方法，第一行必须调用super()，如果没有，编译器会自动加上，怎么Spring的CGLIB就可以搞特殊？
    这是因为自动加super()的功能是Java编译器实现的，它发现你没加，就自动给加上，发现你加错了，就报编译错误。
    但实际上，如果直接构造字节码，一个类的构造方法中，不一定非要调用super()。
    Spring使用CGLIB构造的Proxy类，是直接生成字节码，并没有源码-编译-字节码这个步骤，
    因此：Spring通过CGLIB创建的代理类，不会初始化代理类自身继承的任何成员变量，包括final类型的成员变量！

IDEA 运行 Main 的完全命令行: 这个输出是在安装完 Apache 时设置了 JAVA_HOME 之后进行的
    "C:\Program Files\Java\jdk1.8.0_281\bin\java.exe"
    "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2020.2.3\lib\idea_rt.jar=59882:C:\Program Files\JetBrains\IntelliJ IDEA 2020.2.3\bin"
    -Dfile.encoding=UTF-8
    -classpath "
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\charsets.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\deploy.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\access-bridge-64.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\cldrdata.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\dnsns.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\jaccess.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\jfxrt.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\localedata.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\nashorn.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\sunec.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\sunjce_provider.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\sunmscapi.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\sunpkcs11.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\zipfs.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\javaws.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\jce.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\jfr.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\jfxswt.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\jsse.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\management-agent.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\plugin.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\resources.jar;
    C:\Program Files\Java\jdk1.8.0_281\jre\lib\rt.jar;
    E:\workspace_java\awesomej\out\production\awesomej;
    E:\workspace_java\common_jars\commons-logging-1.2.jar"  com.company.Main
    Hello, world!
    127
    Test char a = A
    Test char zh = 中

Java 中提到过的 scope:
    注解中提到过 scope;
    Maven的 pom.xml 也提到过 scope; provided依赖表示编译时需要，但运行时不需要。
                                  最典型的provided依赖是Servlet API，编译的时候需要，但是运行时，Servlet服务器内置了相关的jar，所以运行期不需要：
    Spring的IoC容器也提到过scope from:Spring开发--定制Bean
Java 中提到过的 Filter:
    IO--Filter模式;
    Web开发--使用Filter;
    Spring开发--开发Web应用--集成Filter
ServletContext: 参考web-servlet-embeded工程 中的 listener目录下的 AppListener
ApplicationContext:参考springpoc工程中的:IoC容器--装配Bean

/Library/Java/JavaVirtualMachines/jdk-15.0.1.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=50357:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/chengpengxing/workspace_java/awesomej/out/production/awesomej com.company.Main
包作用域和 public 作用域，谁的更宽泛，猜测是public

