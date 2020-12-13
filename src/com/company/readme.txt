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


https://www.liaoxuefeng.com/wiki/1252599548343744/1255878730977024
java Hello: 给虚拟机传递的参数 Hello 是我们定义的类名，虚拟机自动查找对应的 class 文件并执行。


/Library/Java/JavaVirtualMachines/jdk-15.0.1.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=50357:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/chengpengxing/workspace_java/awesomej/out/production/awesomej com.company.Main













