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
    https://blog.csdn.net/cpxsxn/article/details/122744367
    在 Java中，我们使用package来解决名字冲突:
    Java定义了一种名字空间，称之为包：package。一个类总是属于某个包，类名（比如Person）只是一个简写，真正的完整类名是包名.类名。
    在定义class的时候，我们需要在第一行声明这个class属于哪个包。
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

    在IDE中运行Java程序，IDE自动传入的-cp参数是当前工程的bin目录和引入的jar包。
    假设我们有一个编译后的Hello.class，它的包名是com.example，当前目录是C:\work，那么，目录结构必须如下：
        C:\work
        └─ com
           └─ example
              └─ Hello.class
    运行这个Hello.class必须在当前目录下使用如下命令： C:\work> java -cp . com.example.Hello
    JVM根据classpath设置的.在当前目录下查找com.example.Hello，即实际搜索文件必须位于com/example/Hello.class。
    如果指定的.class文件不存在，或者目录结构和包名对不上，均会报错

    如果我们要执行一个jar包的class，就可以把jar包放到classpath中：
        java -cp ./hello.jar abc.xyz.Hello   这样JVM会自动在hello.jar文件里去搜索某个类。
    jar包还可以包含一个特殊的/META-INF/MANIFEST.MF文件，MANIFEST.MF是纯文本，可以指定Main-Class和其它信息。
    JVM会自动读取这个MANIFEST.MF文件，如果存在Main-Class，我们就不必在命令行指定启动的类名，而是用更方便的命令：
        java -jar hello.jar
    Spring Boot的插件(spring-boot-maven-plugin)会自动定位应用程序的入口Class，我们执行以下Maven命令即可打包：mvn clean package//from:打包SpringBoot应用

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


JDBC编程--JDBC简介、JDBC查询：
    一个MySQL的JDBC的驱动就是一个jar包，它本身也是纯Java编写的。我们自己编写的代码只需要引用Java标准库提供的java.sql包下面的相关接口，
    由此再间接地通过MySQL驱动的jar包通过网络访问MySQL服务器，所有复杂的网络通讯都被封装到JDBC驱动中，
    因此，Java程序本身只需要引入一个MySQL驱动的jar包就可以正常访问MySQL服务器：

    JDBC是一套接口规范，它在哪呢？
    就在Java的标准库java.sql里放着，不过这里面大部分都是接口。接口并不能直接实例化，而是必须实例化对应的实现类，然后通过接口引用这个实例。
    JDBC接口的实现类在哪？
    因为JDBC接口并不知道我们要使用哪个数据库，所以，用哪个数据库，我们就去使用哪个数据库的“实现类”，我们把某个数据库实现了JDBC接口的jar包称为JDBC驱动。
    JDBC DEMO:
        // JDBC连接的URL, 不同数据库有不同的格式: (URL是由数据库厂商指定的格式)
        String JDBC_URL = "jdbc:mysql://localhost:3306/test";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "password";
        // 获取连接:
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        // 访问数据库 xxx
        // 关闭连接:
        conn.close();
    核心代码是DriverManager提供的静态方法getConnection()。
    DriverManager会自动扫描classpath，找到所有的JDBC驱动，然后根据我们传入的URL自动挑选一个合适的驱动。


Servlet多线程模型: from: Web开发--Servlet入门和Servlet进阶
    上一节知道:编写HTTP服务器其实是非常简单的，只需要先编写基于多线程的TCP服务，然后在一个TCP连接中读取HTTP请求，发送HTTP响应即可。
    但是，要编写一个完善的HTTP服务器，以HTTP/1.1为例，需要考虑的包括：
        识别正确和错误的HTTP请求； 识别正确和错误的HTTP头；  复用TCP连接； 复用线程； IO异常处理；...
    这些基础工作需要耗费大量的时间，并且经过长期测试才能稳定运行。
    如果我们只需要输出一个简单的HTML页面，就不得不编写上千行底层代码，那就根本无法做到高效而可靠地开发。
    因此，在JavaEE平台上，处理TCP连接，解析HTTP协议这些底层工作统统扔给现成的Web服务器去做，我们只需要把自己的应用程序跑在Web服务器上。
    为了实现这一目的，JavaEE提供了Servlet API，我们使用Servlet API编写自己的Servlet来处理HTTP请求，Web服务器实现Servlet API接口，实现底层功能：
                         ┌───────────┐
                         │My Servlet │
                         ├───────────┤
                         │Servlet API│
        ┌───────┐  HTTP  ├───────────┤
        │Browser│<──────>│Web Server │
        └───────┘        └───────────┘
    现在问题又来了：我们应该如何运行这个war文件？
    普通的Java程序是通过启动JVM，然后执行main()方法开始运行。但是Web应用程序有所不同，
    我们无法直接运行war文件，必须先启动Web服务器，再由Web服务器加载我们编写的HelloServlet，这样就可以让HelloServlet处理浏览器发送的请求。

    因为我们编写的Servlet并不是直接运行，而是由Web服务器加载后创建实例运行，所以，类似Tomcat这样的Web服务器也称为Servlet容器。===术语===
    在Servlet容器中运行的Servlet具有如下特点：
        1. 无法在代码中直接通过new创建Servlet实例，必须由Servlet容器自动创建Servlet实例；
        2. Servlet容器只会给每个Servlet类创建唯一实例；
        3. Servlet容器会使用多线程执行doGet()或doPost()方法。
    复习一下Java多线程的内容，我们可以得出结论：
        在Servlet中定义的实例变量会被多个线程同时访问，要注意线程安全；
        HttpServletRequest和HttpServletResponse实例是由Servlet容器传入的局部变量，它们只能被当前线程访问，不存在多个线程访问的问题；
        在doGet()或doPost()方法中，如果使用了ThreadLocal，但没有清理，那么它的状态很可能会影响到下次的某个请求，因为Servlet容器很可能用线程池实现线程复用。

    内嵌 Tomcat: 通过main()方法启动Tomcat服务器并加载我们自己的webapp有如下好处：
        启动简单，无需下载Tomcat或安装任何IDE插件；
        调试方便，可在IDE中使用断点调试；
        使用Maven创建war包后，也可以正常部署到独立的Tomcat服务器中。
    对SpringBoot有所了解的童鞋可能知道，SpringBoot也支持在main()方法中一行代码直接启动Tomcat，并且还能方便地更换成Jetty等其他服务器。
    评论中提到: 如果我在main 中自己写了启动Tomcat 的代码，本地调试确实是没问题，但是如果打WAR 以后在放到 容器里是不是重复， 是要在打包的时候删除MAIN 的代码吗？
    廖老师回答：容器直接读web.xml找servlet，容器不会管哪个class里有main方法
              事实上你引用的很多第三方jar包里面也有main方法，那就是一个普通方法，不是说定义了一个main方法它就肯定是入口。


    一个Servlet类在服务器中只有一个实例，但对于每个HTTP请求，Web服务器会使用多线程执行请求。
    因此，一个Servlet的doGet()、doPost()等处理请求的方法是多线程并发执行的。如果Servlet中定义了字段，要注意多线程并发访问的问题：
         public class HelloServlet extends HttpServlet {
             private Map<String, String> map = new ConcurrentHashMap<>();
    
             protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                 // 注意读写map字段是多线程并发的:
                 this.map.put(key, value);
             }
         }
    对于每个请求，Web服务器会创建唯一的HttpServletRequest和HttpServletResponse实例，
    因此，HttpServletRequest和HttpServletResponse实例只有在当前处理线程中有效，它们总是局部变量，不存在多线程共享的问题。

    ServletContext: 一个Web服务器可以运行一个或多个WebApp，对于每个WebApp，Web服务器都会为其创建一个全局唯一的 ServletContext 实例，//from: Web开发--使用Listener
    ServletRequest、HttpSession等很多对象也提供 getServletContext() 方法获取到同一个ServletContext实例。
    ServletContext实例最大的作用就是设置和共享全局信息。

    过Listener我们可以监听Web应用程序的生命周期，获取HttpSession等创建和销毁的事件；
    ServletContext是一个WebApp运行期的全局唯一实例，可用于设置和共享配置信息。


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

    为什么Spring刻意不初始化Proxy继承的字段？来自下面的评论
    1. 因为你初始化的时候很可能会用到注入的其他类：
        @Component
        public class MailService {
            @Value("${smtp.from:xxx}")
            String mailFrom;

            SmtpSender sender;

            @PostConstruct
            public void init() {
                sender = new SmtpSender(mailFrom, ...);
            }

            public void sentMail(String to) {
                ...
            }
        }
    你看，MailService的字段sender初始化需要依赖其他注入，并且已经初始化了一次，proxy类没法正确初始化sender
    主要原因就是spring无法在逻辑上正常初始化proxy的字段，所以干脆不初始化，并通过NPE直接暴露出来
    2. 还有一个原因是如果对字段进行修改，proxy的字段其实根本没改：
        @Component
        public class MailService {
            String status = "init";

            public void sentMail(String to) {
                this.status = "sent";
            }
        }
    因为只有原始Bean的方法会对自己的字段进行修改，他无法改proxy的字段


Spring开发--IoC容器/使用AOP
    Spring的核心就是提供了一个IoC容器，它可以管理所有轻量级的JavaBean组件，
    提供的底层服务包括组件的生命周期管理、配置和组装服务、AOP支持，以及建立在AOP基础上的声明式事务服务等。

    在Spring的IoC容器中，我们把所有组件统称为JavaBean，即配置一个组件就是配置一个Bean。from:IoC容器--IoC原理
    Spring提供的容器又称为IoC容器，什么是IoC？ (解决了什么问题?) from: Spring开发--IoC容器--装配Bean
        我们需要创建一个Spring的IoC容器实例，然后加载配置文件，让Spring容器为我们创建并装配好配置文件中指定的所有Bean，这只需要一行代码：
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        可以看到 Spring容器就是ApplicationContext，它是一个接口，有很多实现类，这里我们选择ClassPathXmlApplicationContext，表示它会自动从 classpath 中查找指定的XML配置文件。
        我们从创建 Spring容器的代码：可以看到，Spring(自己加的IoC)容器就是 ApplicationContext，它是一个接口，有很多实现类，
        这里我们选择 ClassPathXmlApplicationContext，表示它会自动从classpath中查找指定的XML配置文件。
        获得了 ApplicationContext 的实例，就获得了IoC容器的引用。===这简直太一针见血了, 印证了 springioc_annotation工程中猜测===

    定制Bean:
        默认情况下，当我们标记了一个@Autowired后，Spring如果没有找到对应类型的Bean，它会抛出NoSuchBeanDefinitionException异常。
        可以给@Autowired增加一个required = false的参数：
            @Component
            public class MailService {
                @Autowired(required = false)
                ZoneId zoneId = ZoneId.systemDefault();
                ...
            }
        这个参数告诉Spring容器，如果找到一个类型为ZoneId的Bean，就注入，如果找不到，就忽略。
        这种方式非常适合有定义就使用定义，没有就使用默认值的情况。

        默认情况下，对一种类型的Bean，容器只创建一个实例。但有些时候，我们需要对一种类型的Bean创建多个实例。
        例如，同时连接多个数据库，就必须创建多个DataSource实例，这时可以用@Bean("name")指定别名，也可以用@Bean+@Qualifier("name")指定别名

    可以理解为IoC是AOP的基础, 没有IoC就没有AOP: 会在 用CGLIB自动创建的 代理类中注入原始的类；仅仅根据"注入"两字，就得出这个结论是不是有点偏颇
    在IoC容器--使用Annotation配置:@ComponentScan，它告诉容器，自动搜索当前类所在的包以及子包，把所有标注为@Component的Bean自动创建出来，并根据@Autowired进行装配
    在使用AOP--使用 AOP: 既然SecurityCheckBookService的代码都是标准的Proxy样板代码，不如把权限检查视作一种切面（Aspect），把日志、事务也视为切面，
                       然后，以某种自动化的方式，把切面织入到核心逻辑中，实现(这个词很一针见血)Proxy模式。===最终也是实现Proxy模式，只不过是自动化的===
    在使用AOP--装配 AOP: Spring的IoC容器看到该注解(@EnableAspectJAutoProxy)，就会自动查找带有 @Aspect 的Bean，然后根据每个方法的@Before、@Around等注解把AOP注入到特定的Bean中
                       Spring容器启动时为我们自动创建的注入了Aspect的子类，它取代了原始的UserService（原始的UserService实例作为内部变量隐藏在UserServiceAopProxy中）。
                       如果我们打印从Spring容器获取的UserService实例类型，它类似UserService$$EnhancerBySpringCGLIB$$1f44e01c，实际上是Spring使用CGLIB动态创建的子类，但对于调用方来说，感觉不到任何区别。
                       Spring对接口类型使用JDK动态代理，对普通类使用CGLIB创建子类。如果一个Bean的class是final，Spring将无法为其创建子类。
    在使用AOP--使用注解装配 AOP:我们在使用AOP时，要注意到虽然Spring容器可以把指定的方法通过AOP规则装配到指定的Bean的指定方法前后，但是，
                            如果自动装配时，因为不恰当的范围，容易导致意想不到的结果，即很多不需要AOP代理的Bean也被自动代理了，并且，后续新增的Bean，如果不清楚现有的AOP装配规则，容易被强迫装配。
                            使用AOP时，被装配的Bean最好自己能清清楚楚地知道自己被安排了。
                            例如，Spring提供的@Transactional就是一个非常好的例子。如果我们自己写的Bean希望在一个数据库事务中被调用，就标注上@Transactional, 当然还需要在public class AppConfig 上增加注解@EnableTransactionManagement 和定义Bean PlatformTransactionManager
                            扩展：“性能监控例子”中自定义的注解MetricTime类似使用声明式事务中的 @Transactional注解;
                                 “性能监控例子”中@ComponentScan 和 带有@Aspect和@Component的Bean MetricAspect的配合 类似 声明式事务中的@EnableTransactionManagement注解和Bean PlatformTransactionManager的配合
                                  声明了@EnableTransactionManagement后，不必额外添加@EnableAspectJAutoProxy。from:使用AOP--装配 AOP
    无论是使用AspectJ(即@Before、@Around等拦截器)语法，还是配合Annotation，
    使用AOP，实际上就是让Spring自动为我们创建一个Proxy，使得调用方能无感知地调用指定方法，
    但运行期却动态“织入”了其他逻辑，因此，AOP本质上就是一个代理模式。因为Spring使用了CGLIB来实现运行期动态创建Proxy。 from:使用AOP--AOP避坑指南

    Spring对一个声明式事务的方法，如何开启事务支持？原理仍然是AOP代理，即通过自动创建Bean的Proxy实现：from:访问数据库--使用声明式事务
        public class UserService$$EnhancerBySpringCGLIB extends UserService {   // 该类是Spring通过CGLIB动态创建的代理类,该话术来自: 使用AOP--AOP避坑指南
            UserService target = ...
            PlatformTransactionManager txManager = ...

            public User register(String email, String password, String name) {
                TransactionStatus tx = null;
                try {
                    tx = txManager.getTransaction(new DefaultTransactionDefinition());
                    target.register(email, password, name);
                    txManager.commit(tx);
                } catch (RuntimeException e) {
                    txManager.rollback(tx);
                    throw e;
                }
            }
            ...
        }

Spring开发--开发 Web 应用:
    使用Spring MVC:
        和普通Spring配置一样，我们编写正常的AppConfig后，只需加上@EnableWebMvc注解，就“激活”了Spring MVC
        除了创建DataSource、JdbcTemplate、PlatformTransactionManager外，AppConfig需要额外创建几个用于Spring MVC的Bean：
        1. WebMvcConfigurer 并不是必须的，但我们在这里创建一个默认的 WebMvcConfigurer，只覆写addResourceHandlers()，目的是让Spring MVC自动处理静态文件，并且映射路径为/static/**。
        2. 另一个必须要创建的Bean是ViewResolver，因为Spring MVC允许集成任何模板引擎，使用哪个模板引擎，就实例化一个对应的 ViewResolver
        3. 剩下的Bean都是普通的@Component，但Controller必须标记为@Controller (且可以)正常使用@Autowired注入(其他Bean)
        对(使用Spring的)Web应用程序：Spring容器应该由谁创建？ from:Spring开发--开发Web应用--使用SpringMVC
        如果是普通的(使用Spring的)Java应用程序，我们通过main()方法可以很简单地创建一个Spring容器的实例===和上面的一针见血注释遥相呼应===：
            public static void main(String[] args) {
                ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            }
            ---充分说明了 Spring 并不是非得是 web 程序,这很一针见血---
        但是问题来了，现在是Web应用程序，而Web应用程序总是由Servlet容器创建(和 Web开发--Servlet入门遥相呼应)，那么，
        Spring容器应该由谁创建？在什么时候创建？Spring容器中的Controller又是如何通过Servlet调用的？
        Spring提供的是一个IoC容器，所有的Bean，包括Controller，都在Spring IoC容器中被初始化，
        Servlet容器由JavaEE服务器提供（如Tomcat），Servlet容器(Tomcat)对Spring一无所知，他们之间到底依靠什么进行联系，又是以何种顺序初始化的？
        答: 在Web应用中启动Spring容器有很多种方法，可以通过Listener启动，也可以通过Servlet启动，可以使用XML配置，也可以使用注解配置。
            这里，我们只介绍一种最简单的启动Spring容器的方式。
            在web.xml中配置Spring MVC提供的 DispatcherServlet(注意是谁提供 DispatcherServlet),
            <web-app>
                <servlet>
                    <servlet-name>dispatcher</servlet-name>
                    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
                    <init-param>
                        <param-name>contextClass</param-name>
                        <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
                    </init-param>
                    <init-param>
                        <param-name>contextConfigLocation</param-name>
                        <param-value>com.itranswarp.learnjava.AppConfig</param-value>
                    </init-param>
                    <load-on-startup>0</load-on-startup>
                </servlet>

                <servlet-mapping>
                    <servlet-name>dispatcher</servlet-name>
                    <url-pattern>/*</url-pattern>
                </servlet-mapping>
            </web-app>
            初始化参数contextClass指定使用注解配置的 AnnotationConfigWebApplicationContext (指定Spring容器 ApplicationContext 的实现类)，
            配置文件的位置参数 contextConfigLocation 指向 AppConfig的完整类名(指定配置类)，
        使用Spring MVC时，整个Web应用程序按如下顺序启动：
            1. 启动Tomcat服务器；
            2. Tomcat读取web.xml并初始化DispatcherServlet (在 web.xml中把这个Servlet映射到/*，即(整个工程中只有这一个servlet)处理所有URL)；
            3. DispatcherServlet (根据web.xml的配置)创建IoC容器并自动注册到ServletContext中。
            Servlet容器(Tomcat)为每个Web应用程序自动创建一个唯一的ServletContext实例，这个实例就代表了Web应用程序本身。
            因为DispatcherServlet持有IoC容器，能从IoC容器中获取所有@Controller(注意用词不是web开发章节中的servlet词系)的Bean，
            因此，DispatcherServlet接收到所有HTTP请求后，根据Controller(注意用词不是web开发章节中的servlet词系)方法配置的路径，
            就可以正确地把请求转发到指定方法，并根据返回的ModelAndView决定如何渲染页面。
            当(web.xml中的)DelegatingFilterProxy生效后，它会自动查找注册在ServletContext上的Spring容器(明白为啥注册了吧)，
            再试图从容器中查找名为 authFilter 的Bean，也就是我们用@Component声明的AuthFilter。//from:Spring开发--开发Web应用--集成Filter
        编写Controller:
        接收的HTTP参数以@RequestParam()标注，可以设置默认值。
        如果方法参数需要传入HttpServletRequest、HttpServletResponse或者HttpSession，直接添加这个类型的参数即可，Spring MVC会自动按类型传入。

    使用REST:
        在Web应用中，除了需要使用MVC给用户显示页面外，还有一类API接口，我们称之为REST，通常输入输出都是JSON，便于第三方调用或者使用页面JavaScript与之交互。
        如果我们想接收JSON，输出JSON，那么可以这样写：
            @PostMapping(value = "/rest",
                         consumes = "application/json;charset=UTF-8",
                         produces = "application/json;charset=UTF-8")
            @ResponseBody
            public String rest(@RequestBody User user) {
                return "{\"restSupport\":true}";
            }
        注意到@PostMapping使用consumes声明能接收的类型，使用produces声明输出的类型，
        并且额外加了@ResponseBody表示返回的String无需额外处理，直接作为输出内容写入HttpServletResponse。
        输入的 JSON 则根据注解 @RequestBody 直接被Spring反序列化为User这个JavaBean（这是怎么做到的,猜测应该是根据 JavaBean 的 setXxx 方法吧）。
        直接用 Spring 的 Controller 配合一大堆注解写REST太麻烦了，因此Spring还额外提供了一个 @RestController 注解，
        使用@RestController替代@Controller后，每个方法自动变成API接口方法。我们还是以实际代码举例，编写ApiController

    集成 Filter:
        如果要在Spring MVC中使用Filter，应该怎么做？
        在Spring中创建的这个AuthFilter是一个普通Bean，Servlet容器并不知道，所以它不会起作用。
        如果我们直接在 web.xml 中声明这个AuthFilter，注意到AuthFilter的实例将由Servlet容器而不是Spring容器初始化，因此，@Autowire根本不生效，用于登录的UserService成员变量永远是null。
        所以，得通过一种方式，让Servlet容器实例化的Filter，间接引用Spring容器实例化的AuthFilter。===有点承上启下的意思了===
        Spring MVC提供了一个DelegatingFilterProxy，专门来干这个事情：
            <web-app>
                <filter>
                    <filter-name>authFilter</filter-name>
                    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
                </filter>

                <filter-mapping>
                    <filter-name>authFilter</filter-name>
                    <url-pattern>/*</url-pattern>
                </filter-mapping>
                ...
            </web-app>
        我们来看实现原理：
            1. Servlet容器从web.xml中读取配置，实例化DelegatingFilterProxy，注意命名是authFilter；
            2. Spring容器通过扫描@Component实例化AuthFilter。

        当 DelegatingFilterProxy 生效后，它会自动查找注册在 ServletContext 上的Spring容器，
        再试图从容器中查找名为authFilter的Bean，也就是我们用@Component声明的AuthFilter。===高度串联了 ServletContext、Spring容器、和Filter===
            ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐ ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
              ┌─────────────────────┐        ┌───────────┐   │
            │ │DelegatingFilterProxy│─│─│─ ─>│AuthFilter │
              └─────────────────────┘        └───────────┘   │
            │ ┌─────────────────────┐ │ │    ┌───────────┐
              │  DispatcherServlet  │─ ─ ─ ─>│Controllers│   │
            │ └─────────────────────┘ │ │    └───────────┘
                                                             │
            │    Servlet Container    │ │  Spring Container
             ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─   ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘

    使用Interceptor:
        Interceptor的拦截范围其实就是 Controller 方法，它实际上就相当于基于AOP的方法拦截。
        因为Interceptor只拦截 Controller 方法，所以要注意，返回ModelAndView后，后续对View的渲染就脱离了Interceptor的拦截范围。
        使用Interceptor的好处是 Interceptor 本身是Spring管理的Bean，因此注入任意Bean都非常简单。
        此外，可以应用多个Interceptor，并通过简单的@Order指定顺序。

        一个Interceptor必须实现HandlerInterceptor接口，可以选择实现preHandle()、postHandle()和afterCompletion()方法。
            preHandle()是Controller方法调用前执行，
            postHandle()是Controller方法正常返回后执行，
            afterCompletion()无论Controller方法是否抛异常都会执行，参数ex就是Controller方法抛出的异常（未抛出异常是null）。
        在preHandle()中，也可以直接处理响应，然后返回false表示无需调用Controller方法继续处理了，通常在认证或者安全检查失败时直接返回错误响应。
        在postHandle()中，因为捕获了Controller方法返回的ModelAndView，所以可以继续往ModelAndView里添加一些通用数据，
        很多页面需要的全局数据如Copyright信息等都可以放到这里，无需在每个Controller方法中重复添加。
        注意: 要让拦截器生效，我们在WebMvcConfigurer中注册所有的Interceptor；如果拦截器没有生效，请检查是否忘了在 WebMvcConfigurer 中注册。

        处理异常:在Controller中，Spring MVC还允许定义基于 @ExceptionHandler 注解的异常处理方法。我们来看具体的示例代码：
            @Controller
            public class UserController {
                @ExceptionHandler(RuntimeException.class)
                public ModelAndView handleUnknowException(Exception ex) {
                    return new ModelAndView("500.html", Map.of("error", ex.getClass().getSimpleName(), "message", ex.getMessage()));
                }
                ...
            }
        异常处理方法没有固定的方法签名，可以传入Exception、HttpServletRequest等，返回值可以是void，也可以是ModelAndView，
        上述代码通过@ExceptionHandler(RuntimeException.class)表示当发生RuntimeException的时候，就自动调用此方法处理。
        注意到我们返回了一个新的ModelAndView，这样在应用程序内部如果发生了预料之外的异常，可以给用户显示一个出错页面，而不是简单的500 Internal Server Error或404 Not Found。

    集成JMS:
        JMS是一组接口定义，如果我们要使用JMS，还需要选择一个具体的JMS产品。常用的JMS服务器有开源的ActiveMQ，商业服务器如WebLogic、WebSphere等也内置了JMS支持


Spring Boot开发--第一个SpringBoot应用:
    application.yml: 是Spring Boot默认的配置文件，它采用YAML格式而不是.properties格式，文件名必须是application.yml而不是其他名称。
        使用环境变量: ${DB_HOST:localhost}意思是，
        首先从环境变量查找DB_HOST，如果环境变量定义了，那么使用环境变量的值，否则，使用默认值localhost。
        这使得我们在开发和部署时更加方便，因为开发时无需设定任何环境变量，直接使用默认值即本地数据库，而实际线上运行的时候，只需要传入环境变量即可：
        $ DB_HOST=10.0.1.123 DB_USER=prod DB_PASSWORD=xxxx java -jar xxx.jar  ===原来这就是定义环境变量呀===
    logback-spring.xml: 这是Spring Boot的logback配置文件名称（也可以使用logback.xml），一个标准的写法如下：
        它主要通过 <include resource="..." /> 引入了Spring Boot的一个缺省配置，这样我们就可以引用类似${CONSOLE_LOG_PATTERN}这样的变量。
    pom.xml: 使用Spring Boot时，强烈推荐从spring-boot-starter-parent继承，因为这样就可以引入Spring Boot的预置配置。紧接着，
        我们引入了依赖spring-boot-starter-web和spring-boot-starter-jdbc，它们分别引入了Spring MVC相关依赖和Spring JDBC相关依赖，
        无需指定版本号，因为引入的<parent>内已经指定了，只有我们自己引入的某些第三方jar包需要指定版本号。

    存放源码的src/main/java目录中，Spring Boot对Java包的层级结构有一个要求。
    注意到我们的根package是com.itranswarp.learnjava，下面还有entity、service、web等子package。
    Spring Boot要求main()方法所在的启动类必须放到根package下，命名不做要求，这里我们以Application.java命名，它的内容如下：
        @SpringBootApplication
        public class Application {
            public static void main(String[] args) throws Exception {
                SpringApplication.run(Application.class, args);
            }

            @Bean // 映射路径`/static/`到classpath路径:
            WebMvcConfigurer createWebMvcConfigurer(@Autowired HandlerInterceptor[] interceptors) {
                return new WebMvcConfigurer() {
                    @Override
                    public void addResourceHandlers(ResourceHandlerRegistry registry) {
                        // 映射路径`/static/`到classpath路径:
                        registry.addResourceHandler("/static/**")
                                .addResourceLocations("classpath:/static/");
                    }
                };
            }
        }
    启动Spring Boot应用程序只需要一行代码加上一个注解@SpringBootApplication，该注解实际上又包含了：
        @SpringBootConfiguration
            @Configuration
        @EnableAutoConfiguration
            @AutoConfigurationPackage
        @ComponentScan
    这样一个注解就相当于启动了自动配置和自动扫描。 ===一针见血指出了Spring Boot的工作原理===

    Spring Boot自动启动了嵌入式Tomcat，当看到Started Application in xxx seconds时，Spring Boot应用启动成功。
    现在，我们在浏览器输入localhost:8080就可以直接访问页面。那么问题来了：
    前面我们定义的数据源、声明式事务、JdbcTemplate在哪创建的？怎么就可以直接注入到自己编写的UserService中呢？
    这些自动创建的Bean就是Spring Boot的特色：AutoConfiguration。
    当我们引入 spring-boot-starter-jdbc 时，启动时会自动扫描所有的XxxAutoConfiguration：
        DataSourceAutoConfiguration：自动创建一个DataSource，其中配置项从application.yml的spring.datasource读取；
        DataSourceTransactionManagerAutoConfiguration：自动创建了一个基于JDBC的事务管理器；
        JdbcTemplateAutoConfiguration：自动创建了一个JdbcTemplate。
        因此，我们自动得到了一个 DataSource、一个 DataSourceTransactionManager 和一个JdbcTemplate。
    类似的，当我们引入 spring-boot-starter-web 时，自动创建了：
        ServletWebServerFactoryAutoConfiguration：自动创建一个嵌入式Web服务器，默认是Tomcat；
        DispatcherServletAutoConfiguration：自动创建一个DispatcherServlet；
        HttpEncodingAutoConfiguration：自动创建一个CharacterEncodingFilter；
        WebMvcAutoConfiguration：自动创建若干与MVC相关的Bean。
        ...
    引入第三方pebble-spring-boot-starter时，自动创建了：
        PebbleAutoConfiguration：自动创建了一个PebbleViewResolver。

    Spring Boot大量使用XxxAutoConfiguration来使得许多组件被自动化配置并创建，而这些创建过程又大量使用了Spring的 Conditional 功能。
    例如，我们观察 JdbcTemplateAutoConfiguration，它的代码如下：
        @Configuration(proxyBeanMethods = false)
        @ConditionalOnClass({ DataSource.class, JdbcTemplate.class })
        @ConditionalOnSingleCandidate(DataSource.class)
        @AutoConfigureAfter(DataSourceAutoConfiguration.class)
        @EnableConfigurationProperties(JdbcProperties.class)
        @Import({ JdbcTemplateConfiguration.class, NamedParameterJdbcTemplateConfiguration.class })
        public class JdbcTemplateAutoConfiguration {
            ...
        }
    当满足条件：
        @ConditionalOnClass：在classpath中能找到DataSource和JdbcTemplate；
        @ConditionalOnSingleCandidate(DataSource.class)：在当前Bean的定义中能找到唯一的DataSource；
    该 JdbcTemplateAutoConfiguration 就会起作用。实际创建由导入的 JdbcTemplateConfiguration 完成：
        @Configuration(proxyBeanMethods = false)
        @ConditionalOnMissingBean(JdbcOperations.class)
        class JdbcTemplateConfiguration {
            @Bean
            @Primary
            JdbcTemplate jdbcTemplate(DataSource dataSource, JdbcProperties properties) {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                JdbcProperties.Template template = properties.getTemplate();
                jdbcTemplate.setFetchSize(template.getFetchSize());
                jdbcTemplate.setMaxRows(template.getMaxRows());
                if (template.getQueryTimeout() != null) {
                    jdbcTemplate.setQueryTimeout((int) template.getQueryTimeout().getSeconds());
                }
                return jdbcTemplate;
            }
        }
    创建JdbcTemplate之前，要满足@ConditionalOnMissingBean(JdbcOperations.class)，即不存在JdbcOperations的Bean。
    如果我们自己创建了一个JdbcTemplate，例如，在Application中自己写个方法：
        @SpringBootApplication
        public class Application {
            ...
            @Bean
            JdbcTemplate createJdbcTemplate(@Autowired DataSource dataSource) {
                return new JdbcTemplate(dataSource);
            }
        }
    那么根据条件@ConditionalOnMissingBean(JdbcOperations.class)，Spring Boot就不会再创建一个重复的JdbcTemplate（因为JdbcOperations是JdbcTemplate的父类）。
    可见，Spring Boot自动装配功能是通过自动扫描+条件装配实现的，这一套机制在默认情况下工作得很好，但是，如果我们要手动控制某个Bean的创建，
    就需要详细地了解Spring Boot自动创建的原理，很多时候还要跟踪XxxAutoConfiguration，以便设定条件使得某个Bean不会被自动创建。

    Spring Boot是一个基于Spring提供了开箱即用的一组套件，它可以让我们基于很少的配置和代码快速搭建出一个完整的应用程序。
    Spring Boot有非常强大的AutoConfiguration功能，它是通过自动扫描+条件装配实现的。

Spring Boot开发--使用开发者工具:
    Spring Boot提供了一个开发阶段非常有用的spring-boot-devtools，能自动检测classpath路径上文件修改并自动重启。
    默认配置下，针对/static、/public和/templates目录中的文件修改，不会自动重启，因为禁用缓存后，这些文件的修改可以实时更新。

Spring Boot开发--打包Spring Boot应用:
    Spring Boot的这款插件(spring-boot-maven-plugin)会自动定位应用程序的入口Class，我们执行以下Maven命令即可打包：mvn clean package

Spring Boot开发--使用Actuator:
    在生产环境中，需要对应用程序的状态进行监控。前面我们已经介绍了使用JMX对Java应用程序包括JVM进行监控，使用JMX需要把一些监控信息以MBean的形式暴露给JMX Server，而Spring Boot已经内置了一个监控功能，它叫Actuator。

Spring Boot开发--使用Profiles:
    Spring Boot允许在一个配置文件中针对不同Profile进行配置；
    Spring Boot在未指定Profile时默认为default。

Spring Boot开发--使用Conditional:
    @ConditionalOnProperty：如果有指定的配置，条件生效；
    @ConditionalOnBean：如果有指定的Bean，条件生效；
    @ConditionalOnMissingBean：如果没有指定的Bean，条件生效；
    @ConditionalOnMissingClass：如果没有指定的Class，条件生效；
    @ConditionalOnWebApplication：在Web环境中条件生效；
    @ConditionalOnExpression：根据表达式判断条件是否生效。
    Spring Boot提供了几个非常有用的条件装配注解，可实现灵活的条件装配。

Spring Boot开发--加载配置文件: ===和 Spring开发--IoC容器--注入配置 遥相呼应===
    为了更好地管理配置，Spring Boot允许创建一个Bean，持有一组配置，并由Spring Boot自动注入。
    假设我们在application.yml中添加了如下配置：
        storage:
          local:
            # 文件存储根目录:
            root-dir: ${STORAGE_LOCAL_ROOT:/var/storage}
            # 最大文件大小，默认100K:
            max-size: ${STORAGE_LOCAL_MAX_SIZE:102400}
            # 是否允许空文件:
            allow-empty: false
            # 允许的文件类型:
            allow-types: jpg, png, gif
    可以首先定义一个Java Bean，持有该组配置：
        @Configuration //这个注解居然还可以用在这里
        @ConfigurationProperties("storage.local")
        public class StorageConfiguration {
            private String rootDir;
            private int maxSize;
            private boolean allowEmpty;
            private List<String> allowTypes;
            // getters and setters
            ...
        }
    @ConfigurationProperties("storage.local")表示将从配置项storage.local读取该项的所有子项配置，并且，
    @Configuration 表示 StorageConfiguration 也是一个Spring管理的Bean，可直接注入到其他Bean中
    ===注解 @Configuration  居然还可以这么用===
    ===Spring Boot开发--集成第三方组件--访问Reids提到的:如果在RedisConfiguration中标注 @Configuration，则可通过Spring Boot的自动扫描机制自动加载，否则，使用@Import手动加载。===
    ToDo: 保证Java Bean的属性名称与配置一致即可，这句话具体怎么理解？
          配置文件 application.yml 中的 里的 root-dir 是怎么和 Bean StorageConfiguration 里的属性名称 rootDir 对应起来的，两个字符串不完全一样？廖老师回答这就是 spring boot注入的规则

Spring Boot开发--禁用自动配置:
    可以通过@EnableAutoConfiguration(exclude = {...})指定禁用的自动配置；
    可以通过@Import({...})导入自定义配置。

================================================================================
一个使用Maven管理的普通的Java项目，它的目录结构默认如下：from:Maven基础--Maven介绍
        a-maven-project
        ├── pom.xml
        ├── src
        │   ├── main
        │   │   ├── java
        │   │   └── resources
        │   └── test
        │       ├── java
        │       └── resources
        └── target
    项目的根目录a-maven-project是项目名，它有一个项目描述文件pom.xml，
    存放Java源码的目录是src/main/java，
    存放资源文件的目录是src/main/resources，
        //Spring开发--IoC容器--使用Resource 和 注入配置 也都提到了这一点
        //Spring开发--开发Web应用--使用Spring MVC中提到:在src/main/resources目录中存放的是Java程序读取的 classpath 资源文件，除了JDBC的配置文件jdbc.properties外，我们又新增了一个logback.xml，这是Logback的默认查找的配置文件
    存放测试源码的目录是src/test/java，存放测试资源的目录是src/test/resources，
    最后，所有编译、打包生成的文件都放在target目录里。这些就是一个Maven项目的标准目录结构。
    所有的目录结构都是约定好的标准结构，我们千万不要随意修改目录结构。使用标准结构不需要做任何配置，Maven就可以正常使用。

    我们还需要在工程目录下创建一个web.xml描述文件，放到src/main/webapp/WEB-INF目录下（固定目录结构，不要修改路径，注意大小写），
    后来教程有更新说： web.xml 配置文件是低版本Servlet必须的，但是高版本Servlet已不再需要，所以无需该配置文件。//from:Web开发--Servlet入门
    我们还硬性规定: 模板必须放在 webapp/WEB-INF/templates目录下，静态文件必须放在webapp/static目录下     //from: Web开发--MVC高级开发
    把所有的静态资源文件放入/static/目录，在开发阶段，有些Web服务器会自动为我们加一个专门负责处理静态文件的Servlet，但如果IndexServlet映射路径为/，会屏蔽掉处理静态文件的Servlet映射  //from: Web开发--部署
    src/main/webapp是标准web目录，WEB-INF存放web.xml，编译的class，第三方jar，以及不允许浏览器直接访问的View模版，static目录存放所有静态文件
    (Spring 的 web 项目还是用 webapp 目录的, 但是 springboot 就不用了)。 from:Spring开发--开发Web应用--使用SpringMVC
    在Java程序中，我们经常会读取配置文件、资源文件等。使用Spring容器时，我们也可以把“文件”注入进来，方便程序读取。//from: Spring开发--IoC容器--使用Resource

    两种用法都可以:
        @PropertySource("classpath:/jdbc.properties")  // from:spring-web-mvc工程
        @PropertySource("app.properties") // 表示读取classpath的app.properties  //from:springioc_annotation工程

Spring Boot的标准目录结构，它完全是一个基于Java应用的普通Maven项目:
        springboot-hello
        ├── pom.xml
        ├── src
        │   └── main
        │       ├── java
        |       |   └── com
        |       |       └── itranswarp
        |       |           └── learnjava
        |       |               ├── Application.java
        |       |               ├── entity
        |       |               │   └── User.java
        |       |               ├── service
        |       |               │   └── UserService.java
        |       |               └── web
        |       |                   └── UserController.java
        │       └── resources
        │           ├── application.yml
        │           ├── logback-spring.xml
        │           ├── static
        │           └── templates
        └── target
    static是静态文件目录，templates是模板文件目录，
    注意它们不再存放在src/main/webapp下，而是直接放到src/main/resources这个classpath目录，因为在Spring Boot中已经不需要专门的webapp目录了。
    在存放源码的src/main/java目录中，Spring Boot对Java包的层级结构有一个要求。
    注意到我们的根package是com.itranswarp.learnjava，下面还有entity、service、web等子package。
    Spring Boot要求main()方法所在的启动类必须放到根package下，命名不做要求，这里我们以Application.java命名，

    pom.xml: 使用Spring Boot时，强烈推荐从spring-boot-starter-parent继承，因为这样就可以引入Spring Boot的预置配置。紧接着，
    我们引入了依赖spring-boot-starter-web和spring-boot-starter-jdbc，它们分别引入了Spring MVC相关依赖和Spring JDBC相关依赖，
    无需指定版本号，因为引入的<parent>内已经指定了，只有我们自己引入的某些第三方jar包需要指定版本号。

    Spring Boot的这款插件(spring-boot-maven-plugin)会自动定位应用程序的入口Class，我们执行以下Maven命令即可打包：mvn clean package//from:打包SpringBoot应用

Web开发--使用Filter:修改请求和修改响应
    Spring开发--开发Web应用--使用SpringMVC:
    如果在方法内部直接操作HttpServletResponse发送响应，返回null表示无需进一步处理：
        public ModelAndView download(HttpServletResponse response) {
            byte[] data = ...
            response.setContentType("application/octet-stream");
            OutputStream output = response.getOutputStream();
            output.write(data);
            output.flush();
            return null;
        }

什么是容器？
    容器是一种为某种特定组件的运行提供必要支持的一个软件环境。例如，Tomcat就是一个Servlet容器，它可以为Servlet的运行提供运行环境。
    类似Docker这样的软件也是一个容器，它提供了必要的Linux环境以便运行一个特定的Linux进程。 //from:Spring开发--IoC容器
Java 提到过的创建：
    反射中提到:Class实例是JVM内部创建的，如果我们查看JDK源码，可以发现 Class类的构造方法是 private，只有JVM能创建Class实例
    Web开发--Servlet入门:无法在代码中直接通过new创建Servlet实例，必须由Servlet容器自动创建Servlet实例
    Spring开发--IoC容器--IoC原理、定制 Bean:因为IoC容器要负责实例化所有的组件，因此，有必要告诉容器如何创建组件，以及各组件的依赖关系。容器初始化时创建Bean，容器关闭前销毁Bean。
Java 中提到过的 scope:
    注解中提到过 scope;
    Maven的 pom.xml 也提到过 scope; provided依赖表示编译时需要，但运行时不需要。最典型的provided依赖是Servlet API，编译的时候需要，但是运行时，Servlet服务器内置了相关的jar，所以运行期不需要：
    Spring的IoC容器也提到过scope from:Spring开发--定制Bean
Java 中提到过的 Filter:
    IO--Filter模式;
    Web开发--使用Filter;
    Spring开发--开发Web应用--集成Filter
应用代码创建不了的:
    无法在代码中直接通过new创建Servlet实例，必须由Servlet容器自动创建Servlet实例；
    ===联想反射中提到的Class的实例只能由JVM创建=== from:mavenpoc工程

    任何标注为@WebListener，且实现了特定接口的类会被Web服务器自动初始化。 //from: Web开发--使用Listener

    如果数据库表的结构恰好和JavaBean的属性名称一致，那么(Spring提供的)BeanPropertyRowMapper就可以直接把一行记录按列名转换为JavaBean。//from: Spring开发--访问数据库--使用JDBC
    类似User、Book这样的用于ORM的Java Bean，我们通常称之为Entity Bean;
    使用JdbcTemplate配合RowMapper可以看作是最原始的ORM。如果要实现更自动化的ORM，可以选择成熟的ORM框架，例如Hibernate //from: Spring开发--访问数据库--集成Hibernate

    PersistenceContext: 还是以UserService为例，除了标注@Component和@Transactional外，我们需要注入一个EntityManager，
    但是不要使用Autowired，而是@PersistenceContext(===居然可以不用 @Autowired 注入===) ;
    Spring遇到标注了@PersistenceContext的EntityManager会自动注入代理，该代理会在必要的时候自动打开EntityManager。
    换句话说，多线程引用的EntityManager虽然是同一个代理类，但该代理类内部针对不同线程会创建不同的EntityManager实例。//from:Spring开发--访问数据库--集成JPA
    剩下的Bean都是普通的@Component，但Controller必须标记为@Controller (且可以)正常使用@Autowired注入(其他Bean) //from:Spring开发--开发Web应用--使用SpringMVC
    使用Interceptor的好处是Interceptor本身是Spring管理的Bean，因此注入任意Bean都非常简单。//from:Spring开发--开发Web应用--使用Interceptor
    @ConfigurationProperties("storage.local")表示将从配置项storage.local读取该项的所有子项配置，
    并且，@Configuration 表示StorageConfiguration也是一个Spring管理的Bean，可直接注入到其他Bean中。 //from:Spring Boot开发--加载配置文件
    这和 Spring容器还提供了一个更简单的@PropertySource来自动读取配置文件 有类似作用。// from:Spring开发--IoC容器--注入配置

	对于Spring容器来说，当我们把一个Bean标记为 @Component 后，它就会自动为我们创建一个单例（Singleton），即容器初始化时创建Bean，容器关闭前销毁Bean。
	在容器运行期间，我们调用getBean(Class)获取到的Bean总是同一个实例。还有一种Bean，我们每次调用getBean(Class)，容器都返回一个新的实例，这种Bean称为Prototype（原型）.

    Spring容器会对上述Bean做如下初始化流程： from:Spring开发--IoC容器--定制Bean 强调的是 PostConstruct
        调用构造方法创建MailService实例；
        根据@Autowired进行注入；
        调用标记有 @PostConstruct 的init()方法进行初始化。而销毁时，容器会首先调用标记有@PreDestroy的shutdown()方法。
    当Servlet容器创建当前Servlet实例后，会自动调用init(ServletConfig)方法(居然不需要注解 @PostConstruct) from:廖雪峰源码工程 web-mvc 的 DispatcherServlet
Spring还提供另一种IoC容器叫 BeanFactory，使用方式和ApplicationContext类似：
    BeanFactory factory = new XmlBeanFactory(new ClassPathResource("application.xml"));
    MailService mailService = factory.getBean(MailService.class);
BeanFactory和ApplicationContext的区别在于，BeanFactory的实现是按需创建，即第一次获取Bean时才创建这个Bean，而ApplicationContext会一次性创建所有的Bean。
实际上，ApplicationContext接口是从BeanFactory接口继承而来的，并且，ApplicationContext提供了一些额外的功能，
包括国际化支持、事件和通知机制等。通常情况下，我们总是使用ApplicationContext，很少会考虑使用BeanFactory。from:Spring开发--IoC容器--装配Bean
使用FactoryBean: 注意改词 from:Spring开发--IoC容器--定制Bean
DispatcherServlet: 我们在web.xml中配置Spring MVC提供的 DispatcherServlet, 有了这个配置，Servlet容器(Tomcat)会首先初始化Spring MVC的DispatcherServlet，
                   在DispatcherServlet启动时，它根据配置AppConfig创建了一个类型是WebApplicationContext的IoC容器，完成所有Bean的初始化，
                   并将容器绑到 ServletContext上。 from: Spring开发--开发Web应用--使用Spring MVC
ServletContext: 一个Web服务器可以运行一个或多个WebApp，对于每个WebApp，Web服务器都会为其创建一个全局唯一的ServletContext实例。 from:Web开发--使用Listener 参考web-servlet-embeded工程中的listener目录下的 AppListener文件
                Servlet容器(Tomcat)为每个Web应用程序自动创建一个唯一的ServletContext实例，这个实例就代表了Web应用程序本身。from:Spring开发--开发Web应用--使用SpringMVC
                注意区分比理解:一个Servlet类在服务器中只有一个实例，但对于每个HTTP请求，Web服务器会使用多线程执行请求。因此，
                一个Servlet的doGet()、doPost()等处理请求的方法是多线程并发执行的。如果Servlet中定义了字段，要注意多线程并发访问的问题。//from: Web开发--Servlet进阶
ApplicationContext: 本质就是一个Spring容器的实例， 参考springioc工程中的:IoC容器--装配Bean
PersistenceContext: 参考上方提到的
JdbcOperations是JdbcTemplate的父类: 根据条件@ConditionalOnMissingBean(JdbcOperations.class)，Spring Boot就不会再创建一个重复的JdbcTemplate（因为JdbcOperations是JdbcTemplate的父类）。from:SpringBoot开发--第一个SpringBoot应用
用到切面 AOP 的场景:
    AOP是一种新的编程方式，它和OOP不同，OOP把系统看作多个对象的交互，AOP把系统分解为不同的关注点，或者称之为切面（Aspect）。
    Spring对一个声明式事务的方法，如何开启事务支持？原理仍然是AOP代理，即通过自动创建Bean的Proxy实现。 from:Spring开发--访问数据库--使用声明式事务
    Interceptor 的拦截范围其实就是Controller方法，它实际上就相当于基于AOP的方法拦截。 from:Spring开发--开发Web应用--使用Interceptor
注解 @Configuration 的使用场景：
    1.  @Configuration //表示该类是一个配置类，因为我们创建ApplicationContext时，使用的实现类是AnnotationConfigApplicationContext，必须传入一个标注了@Configuration的类名。
        public class AppConfig {
            // AppConfig标注了@Configuration，表示它是一个配置类，因为我们创建ApplicationContext时：
            // ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            // 使用的实现类是AnnotationConfigApplicationContext，必须传入一个标注了@Configuration的类名。
            // 此外，AppConfig还标注了@ComponentScan，
            // 它告诉容器，自动搜索当前类所在的包以及子包，把所有标注为@Component的Bean自动创建出来，并根据@Autowired进行装配。
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            ...
        }
    2. 在1springboot-configuration 工程里的 StorageConfiguration 类上



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

/Library/Java/JavaVirtualMachines/jdk-15.0.1.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=50357:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/chengpengxing/workspace_java/awesomej/out/production/awesomej com.company.Main
包作用域和 public 作用域，谁的更宽泛，猜测是public
