package com.company;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * IO流是一种流式的数据输入/输出模型：
 *     二进制数据以byte为最小单位在 InputStream/OutputStream 中单向流动；
 *     字符数据以char为最小单位在 Reader/Writer 中单向流动。
 * Java标准库的java.io包提供了同步IO功能：
 *     字节流接口：InputStream/OutputStream；
 *     字符流接口：Reader/Writer。
 *
 * InputStream、OutputStream、Reader和 Writer都是同步IO的抽象类，
 * 对应的具体实现类，以文件为例，有 FileInputStream、FileOutputStream、FileReader和 FileWriter。
 */
public class IOPoc {

    public String getMiscDir(){
        String os = System.getProperty("os.name");
        if (os.contains("Windows")){
            return "src\\com\\company\\misc";
        } else {
            return "src/com/company/misc";
        }
    }

    public void fileEntry() throws IOException,InterruptedException {
        System.out.println("In the inputEntry");
        /**
         * File对象既可以表示文件，也可以表示目录。
         * 特别要注意的是，构造一个File对象，即使传入的文件或目录不存在，代码也不会出错，
         * 因为构造一个File对象，并不会导致任何磁盘操作。只有当我们调用File对象的某些方法的时候，才真正进行磁盘操作。
         *
         * Java标准库还提供了一个Path对象，它位于java.nio.file包。Path对象和File对象类似，但操作更加简单：
         *      Path p1 = Paths.get(".", "project", "study"); // 构造一个Path对象
         *      System.out.println(p1);
         *      Path p2 = p1.toAbsolutePath(); // 转换为绝对路径
         *      System.out.println(p2);
         *      Path p3 = p2.normalize(); // 转换为规范路径
         *      System.out.println(p3);
         *      File f = p3.toFile(); // 转换为File对象
         *      System.out.println(f);
         *      for (Path p : Paths.get("..").toAbsolutePath()) { // 可以直接遍历Path
         *          System.out.println("  " + p);
         *      }
         * 如果需要对目录进行复杂的拼接、遍历等操作，使用Path对象更方便。
         */

        File f1 = new File(".");
        // String[] file_str = f1.list();
        // for(String entry:file_str){
        //     System.out.println(entry);
        // }
        // File[] files = f1.listFiles();
        File[] files = f1.listFiles(new FilenameFilter(){
            public boolean accept(File dir, String name) {
                return name.endsWith(".git"); // 返回true表示接受该文件
            }
        });
        for(File entry:files) {
            System.out.println(entry.getCanonicalPath());
        }

        File f2 = new File(getMiscDir() + File.separator + "readme.txt");
        System.out.println(f2.getName());
        System.out.println(f2.isFile());
        System.out.println(f2.isDirectory());
        System.out.println(f2.getAbsolutePath());

        File f = File.createTempFile("tmp-", ".txt"); // 提供临时文件的前缀和后缀
        // System.out.println(new Date());
        // Thread.sleep(1000*5);
        // System.out.println(new Date());
        System.out.println(new Date());
        f.deleteOnExit(); // JVM退出时自动删除, ===ToDo：JVM 是怎么做到的
        System.out.println(f.isFile());
        System.out.println(f.getAbsolutePath());
    }

    public static String readAsString(InputStream input) throws IOException {
        int n;
        StringBuilder sb = new StringBuilder();
        while ((n = input.read()) != -1) {
            sb.append((char) n);
        }
        return sb.toString();
    }
    public void inputStreamEntry() throws IOException{
        /**
         * InputStream就是Java标准库提供的最基本的输入流。它位于java.io这个包里。java.io包提供了所有同步IO的功能。
         * 要特别注意的一点是，InputStream并不是一个接口，而是一个抽象类，它是所有输入流的超类。
         * 这个抽象类定义的一个最重要的方法就是int read()，签名如下：
         *      public abstract int read() throws IOException;
         * 这个方法会读取输入流的下一个字节，并返回字节表示的int值（0~255）。如果已读到末尾，返回-1表示不能继续读取了。
         * 在计算机中，类似文件、网络端口这些资源，都是由操作系统统一管理的。
         * 应用程序在运行的过程中，如果打开了一个文件进行读写，完成后要及时地关闭，以便让操作系统把资源释放掉，
         * 否则，应用程序占用的资源会越来越多，不但白白占用内存，还会影响其他应用程序的运行。
         * InputStream 和 OutputStream都是通过 close() 方法来关闭流。关闭流就会释放对应的底层资源。
         * 我们还要注意到在读取或写入IO流的过程中，可能会发生错误，例如，文件不存在导致无法读取，没有写权限导致写入失败，等等，
         * 这些底层错误由Java虚拟机自动封装成 IOException 异常并抛出。因此，所有与IO操作相关的代码都必须正确处理 IOException。
         *
         * 缓冲：
         * 在读取流的时候，一次读取一个字节并不是最高效的方法。很多流支持一次性读取多个字节到缓冲区，
         * 对于文件和网络流来说，利用缓冲区一次性读取多个字节效率往往要高很多。InputStream提供了两个重载方法来支持读取多个字节：
         *      int read(byte[] b)：读取若干字节并填充到byte[]数组，返回读取的字节数
         *      int read(byte[] b, int off, int len)：指定byte[]数组的偏移量和最大填充数
         * 利用上述方法一次读取多个字节时，需要先定义一个byte[]数组作为缓冲区，read()方法会尽可能多地读取字节到缓冲区， 但不会超过缓冲区的大小。read()方法的返回值不再是字节的int值，而是返回实际读取了多少个字节。如果返回-1，表示没有更多的数据了。
         * 利用缓冲区一次读取多个字节的代码如下：
         *      public void readFile() throws IOException {
         *          try (InputStream input = new FileInputStream("src/readme.txt")) {
         *              // 定义1000个字节大小的缓冲区:
         *              byte[] buffer = new byte[1000];
         *              int n;
         *              while ((n = input.read(buffer)) != -1) { // 读取到缓冲区
         *                  System.out.println("read " + n + " bytes.");
         *              }
         *          }
         *      }
         *
         * 用 FileInputStream 可以从文件获取输入流，这是InputStream常用的一个实现类。
         * 此外，ByteArrayInputStream 可以在内存中模拟一个 InputStream。ByteArrayInputStream
         * 实际上是把一个 byte[]数组在内存中变成一个 InputStream，虽然实际应用不多，但测试的时候，可以用它来构造一个InputStream。
         *
         * 实际上，InputStream也有缓冲区。例如，从 FileInputStream 读取一个字节时，操作系统往往会一次性读取若干字节到缓冲区，
         * 并维护一个指针指向未读的缓冲区。然后，每次我们调用 int read()读取下一个字节时，可以直接返回缓冲区的下一个字节，
         * 避免每次读一个字节都导致 IO 操作。当缓冲区全部读完后继续调用read()，则会触发操作系统的下一次读取并再次填满缓冲区。
         * 此处提到的 缓冲区 并不是 上文说的  int read(byte[] b, int off, int len) 中的缓冲。from:OutputStream
         */
        System.out.println("In the inputStreamEntry");
        byte[] data = { 72, 101, 108, 108, 111, 33 }; // hello
        // 编译器只看try(resource = ...) 中的对象是否实现了 java.lang.AutoCloseable接口，
        // 如果实现了，就自动加上finally语句并调用close()方法。
        // InputStream和OutputStream都实现了这个接口，因此，都可以用在try(resource)中。
        try (InputStream input = new ByteArrayInputStream(data)) {
            String s = readAsString(input);
            System.out.println(s);
        }
    }

    public void outputStreamEntry() throws IOException{
        /**
         * 和InputStream相反，OutputStream是Java标准库提供的最基本的输出流。
         * 和InputStream类似，OutputStream也是抽象类，它是所有输出流的超类。
         * 这个抽象类定义的一个最重要的方法就是void write(int b)，签名如下：
         *      public abstract void write(int b) throws IOException;
         * 这个方法会写入一个字节到输出流。
         * 要注意的是，虽然传入的是int参数，但只会写入一个字节，即只写入int最低8位表示字节的部分（相当于b & 0xff）， ===这个总结很拔高===
         *
         * 和 InputStream 类似，OutputStream也提供了close()方法关闭输出流，以便释放系统资源。
         * 要特别注意：OutputStream还提供了一个 flush ()方法，它的目的是将缓冲区的内容真正输出到目的地。
         * 为什么要有 flush()？
         * 因为向磁盘、网络写入数据的时候，出于效率的考虑，操作系统并不是输出一个字节就立刻写入到文件或者发送到网络，而是把输出的字节
         * 先放到内存的一个缓冲区里（本质上就是一个byte[]数组），等到缓冲区写满了，再一次性写入文件或者网络。
         * 对于很多IO设备来说，一次写一个字节和一次写1000个字节，花费的时间几乎是完全一样的，
         * 所以 OutputStream 有个 flush()方法，能强制把缓冲区内容输出。 通常情况下，我们不需要调用这个flush()方法，
         * 因为缓冲区写满了OutputStream会自动调用它，并且，在调用 close() 方法关闭 OutputStream 之前，也会自动调用 flush()方法。
         *
         * 用 FileOutputStream 可以从文件获取输出流，这是 OutputStream 常用的一个实现类。
         * 此外，ByteArrayOutputStream 可以在内存中模拟一个OutputStream。ByteArrayOutputStream实际上是把一个 byte[]数组在内存中变成一个 OutputStream，
         * 虽然实际应用不多，但测试的时候，可以用它来构造一个OutputStream。
         *
         * 同时操作多个AutoCloseable资源时，在try(resource) { ... }语句中可以同时写出多个资源，用;隔开。例如，同时读写两个文件：
         *      // 读取input.txt，写入output.txt:
         *      try (InputStream input = new FileInputStream("input.txt");
         *          OutputStream output = new FileOutputStream("output.txt")){
         *          input.transferTo(output);  // transferTo的作用是?
         *      }
         */
        System.out.println("In the outputStreamEntry");
        try (OutputStream output = new FileOutputStream(getMiscDir() + File.separator + "readme.txt")) {
            output.write("Hello".getBytes("UTF-8")); // Hello
        }
    }

    public void filterEntry() throws UnsupportedEncodingException, IOException{
        /**
         * 为了解决依赖继承会导致子类数量失控的问题，JDK首先将InputStream分为两大类：
         * 一类是直接提供数据的基础InputStream，例如：
         *     FileInputStream
         *     ByteArrayInputStream
         *     ServletInputStream
         *     ...
         * 一类是提供额外附加功能的InputStream，例如：
         *     BufferedInputStream
         *     DigestInputStream
         *     CipherInputStream
         *     ...
         * 这种通过一个 “基础” 组件再叠加各种 “附加”功能组件 的模式，称之为 Filter模式（或者装饰器模式：Decorator）。
         * 它可以让我们通过少量的类来实现各种功能的组合，注意到在叠加多个 FilterInputStream，我们只需要持有最外层的InputStream，并且，
         * 当最外层的InputStream关闭时（在try(resource)块的结束处自动关闭），内层的InputStream的close()方法也会被自动调用，
         * 并最终调用到最核心的“基础”InputStream，因此不存在资源泄露。
         */
        System.out.println("In the filterEntry");
        // 我们也可以自己编写FilterInputStream，以便可以把自己的FilterInputStream“叠加”到任何一个InputStream中。
        // 如何编写一个 CountInputStream，它的作用是对输入的字节进行计数：
        byte[] data = "hello, world!".getBytes("UTF-8");
        try (CountInputStream input = new CountInputStream(new ByteArrayInputStream(data))) {
            int n;
            while ((n = input.read()) != -1) {
                System.out.println((char)n);
            }
            System.out.println("Total read " + input.getBytesRead() + " bytes");
        }
    }

    public void zipEntry() throws IOException{
        System.out.println("In the zipEntry");
        File[] files = {new File(getMiscDir() + File.separator + "readme.txt")};
        String testZip = getMiscDir() + File.separator + "test.zip";
        try(ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(testZip))) {
            for (File entry : files) {
                zip.putNextEntry(new ZipEntry(entry.getName()));
                zip.write("hello google".getBytes("UTF-8"));
                zip.closeEntry();
            }
        }
    }

    public void classpathResourceEntry() throws IOException{
        /**
         * 背景：
         * 从磁盘的固定目录读取配置文件，不是一个好的办法,因为 Windows 和 Linux 系统不一样。
         * 从 classpath 读取文件就可以避免不同环境下文件路径不一致的问题：如果我们把default.properties文件放到 classpath中，
         * 就不用关心它的实际存放路径。在classpath中的资源文件，路径总是以／开头，我们先获取当前的Class对象，
         * 然后调用 getResourceAsStream() 就可以直接从classpath读取任意的资源文件：
         *
         * 把资源存储在classpath中可以避免文件路径依赖；
         * Class对象的 getResourceAsStream() 可以从classpath中读取指定资源；
         * 根据 classpath 读取资源时，需要检查返回的InputStream是否为null。
         * 一个很典型的应用参考: 集合--使用Properties 即 properties() of CollectionPoc.java
         */
        System.out.println("In the classpathResourceEntry");
        System.out.println(getClass());
        try (InputStream input = getClass().getResourceAsStream("/com/company/misc/setting.properties")) {
            if (input != null) {
                System.out.println("Find the setting.properties");
            } else {
                System.out.println("Can Not find the setting.properties");
            }
        }
    }
    public void serializableEntry() throws IOException{
        /**
         * 一个Java对象要能序列化，必须实现一个特殊的java.io.Serializable接口，它的定义如下：
         *      public interface Serializable {
         *
         *      }
         * Serializable 接口没有定义任何方法，它是一个空接口。
         * 我们把这样的空接口称为“标记接口”（Marker Interface），实现了标记接口的类仅仅是给自身贴了个“标记”，并没有增加任何方法。
         *
         * 序列化：
         *  把一个Java对象变为byte[]数组，需要使用ObjectOutputStream。它负责把一个Java对象写入一个字节流：
         *      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
         *      try (ObjectOutputStream output = new ObjectOutputStream(buffer)) {
         *          // 写入int:
         *          output.writeInt(12345);
         *          // 写入String:
         *          output.writeUTF("Hello");
         *          // 写入Object:
         *          output.writeObject(Double.valueOf(123.456));
         *      }
         *      System.out.println(Arrays.toString(buffer.toByteArray()));
         *  ObjectOutputStream既可以写入基本类型，如int，boolean，也可以写入String（以UTF-8编码），
         *  还可以写入实现了Serializable接口的Object。因为写入Object时需要大量的类型信息，所以写入的内容很大。
         * 反序列化：
         *  和 ObjectOutputStream相反，ObjectInputStream负责从一个字节流读取Java对象：
         *      try (ObjectInputStream input = new ObjectInputStream(...)) {
         *          int n = input.readInt();
         *          String s = input.readUTF();
         *          Double d = (Double) input.readObject();
         *      }
         *  除了能读取基本类型和String类型外，调用readObject()可以直接返回一个Object对象。要把它变成一个特定类型，必须强制转型。
         *  readObject()可能抛出的异常有：
         *      ClassNotFoundException：没有找到对应的Class；这种情况常见于一台电脑上的Java程序把一个Java对象，例如，Person对象序列化以后，
         *                              通过网络传给另一台电脑上的另一个Java程序，但是这台电脑的Java程序并没有定义Person类，所以无法反序列化。
         *      InvalidClassException：Class不匹配。这种情况常见于序列化的Person对象定义了一个int类型的age字段，但是反序列化时，
         *                              Person类定义的age字段被改成了long类型，所以导致class不兼容
         * 反序列化时，由JVM直接构造出Java对象，不调用构造方法，构造方法内部的代码，在反序列化时根本不可能执行。===这一点很重要===
         *
         * 安全性：
         * 因为Java的序列化机制可以导致一个实例能直接从byte[]数组创建，而不经过构造方法，因此，它存在一定的安全隐患。
         * 一个精心构造的byte[]数组被反序列化后可以执行特定的Java代码，从而导致严重的安全漏洞。
         * 实际上，Java本身提供的基于对象的序列化和反序列化机制既存在安全性问题，也存在兼容性问题。
         * 更好的序列化方法是通过JSON这样的通用数据结构来实现，只输出基本类型（包括String）的内容，而不存储任何与代码相关的信息。
         */
        System.out.println("In the serializableEntry");
    }

    public void readerEntry() throws IOException{
        System.out.println("In the readerEntry");
        /**
         * java.io.Reader是所有字符输入流的超类，它最主要的方法是：
         *      public int read() throws IOException;
         * 这个方法读取字符流的下一个字符，并返回字符表示的int，范围是0~65535。如果已读到末尾，返回-1。
         * Reader还提供了一次性读取若干字符并填充到char[]数组的方法：
         *      public int read(char[] c) throws IOException
         * 它返回实际读入的字符个数，最大不超过char[]数组的长度。返回 -1表示流结束。
         * 和 InputStream 类似，Reader也是一种资源，需要保证出错的时候也能正确关闭，
         * Reader是基于InputStream构造的：可以通过 InputStreamReader 在指定编码的同时将任何 InputStream 转换为 Reader。
         *
         * FileReader:是 Reader 的一个子类，它可以打开文件并获取 Reader
         * CharArrayReader:可在内存中模拟一个Reader，它的作用实际上是把一个char[]数组变成一个Reader，这和ByteArrayInputStream非常类似：
         *      try (Reader reader = new CharArrayReader("Hello".toCharArray())) {
         *          ...
         *      }
         * StringReader: 可以直接把String作为数据源，它和CharArrayReader几乎一样：
         *      try (Reader reader = new StringReader("Hello")) {
         *          ...
         *      }
         *
         * InputStreamReader：
         * Reader和 InputStream有什么关系？ // 使用InputStreamReader，可以把一个InputStream转换成一个Reader。
         * 除了特殊的CharArrayReader和StringReader，普通的Reader实际上是基于InputStream构造的，
         * 因为 Reader 需要从InputStream中读入字节流（byte），然后，根据编码设置，再转换为 char 就可以实现字符流。 ===很概括的一句话===
         * 如果我们查看FileReader的源码，它在内部实际上持有一个FileInputStream。
         * 既然Reader本质上是一个基于InputStream的byte到char的转换器，那么，如果我们已经有一个InputStream，想把它转换为Reader，是完全可行的。
         * InputStreamReader就是这样一个转换器，它可以把任何 InputStream转换为 Reader。示例代码如下：
         *      // 持有InputStream:
         *      InputStream input = new FileInputStream("src/readme.txt");
         *      // 变换为Reader:
         *      Reader reader = new InputStreamReader(input, "UTF-8");
         * 构造InputStreamReader时，我们需要传入InputStream，还需要指定编码，就可以得到一个Reader对象。
         * 上述代码可以通过try (resource)更简洁地改写如下：
         *      try (Reader reader = new InputStreamReader(new FileInputStream("src/readme.txt"), "UTF-8")) {
         *          ...
         *      }
         * 上述代码实际上就是FileReader的一种实现方式。
         * 使用try (resource)结构时，当我们关闭Reader时，它会在内部自动调用InputStream的close()方法，所以，只需要关闭最外层的Reader对象即可。
         */
        String fullPath = getMiscDir() + File.separator + "readme.txt";
        // 如果我们读取一个纯ASCII编码的文本文件，下述代码工作是没有问题的。但如果文件中包含中文，就会出现乱码，
        // 因为FileReader默认的编码与系统相关，例如，Windows系统的默认编码可能是GBK，打开一个UTF-8编码的文本文件就会出现乱码。
        // 要避免乱码问题，我们需要在创建FileReader时指定编码：
        // Reader reader = new FileReader(getMiscDir() + File.separator + "readme.txt"); //文件如果包含中文就有问题
        // Reader reader = new FileReader(getMiscDir() + File.separator + "readme.txt", StandardCharsets.UTF_8);
        //
        //
        // 所以我们需要用 try (resource)来保证Reader在无论有没有IO错误的时候都能够正确地关闭：
        // try(Reader reader = new FileReader(fullPath, StandardCharsets.UTF_8)){ //为了用 Java8,暂时注释掉
        try(Reader reader = new FileReader(fullPath)){
            for (;;) {
                int n = reader.read(); // 反复调用read()方法，直到返回-1
                if (n == -1) {
                    break;
                }
                System.out.println((char)n); // 打印char
            }
        }

        // try (Reader reader = new FileReader(fullPath, StandardCharsets.UTF_8)) {  //为了用 Java8,暂时注释掉
        try (Reader reader = new FileReader(fullPath)) {
            char[] buffer = new char[1000];
            int n;
            while ((n = reader.read(buffer)) != -1) {
                System.out.println("read " + n + " chars.");
            }
        }
    }

    public void writerEntry() throws IOException{
        /**
         * Writer就是带编码转换器的OutputStream，它把char转换为byte并输出。类比：Reader是带编码转换器的InputStream，它把byte转换为char
         * Writer是所有字符输出流的超类，它提供的方法主要有：
         *     写入一个字符（0~65535）：void write(int c)；
         *     写入字符数组的所有字符：void write(char[] c)；
         *     写入String表示的所有字符：void write(String s)。
         * Writer是基于OutputStream构造的，可以通过OutputStreamWriter将OutputStream转换为Writer，转换时需要指定编码。
         *
         * CharArrayWriter: 可以在内存中创建一个Writer，它的作用实际上是构造一个缓冲区，可以写入char，
         * 最后得到写入的char[]数组，这和ByteArrayOutputStream非常类似：
         *      try (CharArrayWriter writer = new CharArrayWriter()) {
         *          writer.write(65);
         *          writer.write(66);
         *          writer.write(67);
         *          char[] data = writer.toCharArray(); // { 'A', 'B', 'C' }
         *      }
         * StringWriter: 是一个基于内存的 Writer，它和CharArrayWriter类似。
         * 实际上，StringWriter在内部维护了一个StringBuffer，并对外提供了Writer接口。
         *
         * OutputStreamWriter:
         * 除了CharArrayWriter和StringWriter外，普通的Writer实际上是基于OutputStream构造的，它接收char，
         * 然后在内部自动转换成一个或多个byte，并写入OutputStream。因此，OutputStreamWriter就是一个将任意的OutputStream转换为Writer的转换器：
         *      try (Writer writer = new OutputStreamWriter(new FileOutputStream("readme.txt"), "UTF-8")) {
         *          ...
         *      }
         * 上述代码实际上就是FileWriter的一种实现方式。这和上一节的InputStreamReader是一样的。
         */
        System.out.println("In the writerEntry");
        String fullPath = getMiscDir() + File.separator + "writer_test.txt";
        // try (Writer writer = new FileWriter(fullPath, StandardCharsets.UTF_8)) { //为了用 Java8,暂时注释掉
        try (Writer writer = new FileWriter(fullPath)) { //为了用 Java8,暂时注释掉
            writer.write('H');                // 写入单个字符
            writer.write("Hello".toCharArray()); // 写入char[]
            writer.write("Hello");           // 写入String
        }
        // try(Reader reader = new FileReader(fullPath, StandardCharsets.UTF_8)){  //为了用 Java8,暂时注释掉
        try(Reader reader = new FileReader(fullPath)){
            char[] cha = new char[5];
            int n;
            while((n = reader.read(cha)) != -1) {
                System.out.println("read " + n + " chars.");
            }
        }
    }

    public void printStreamAndprintWriterEntry(){
        /**
         * PrintStream是一种 FilterOutputStream，它在OutputStream的接口上，额外提供了一些写入各种数据类型的方法：
         *     写入int：print(int)
         *     写入boolean：print(boolean)
         *     写入String：print(String)
         *     写入Object：print(Object)，实际上相当于print(object.toString())
         *     ...
         * 以及对应的一组println()方法，它会自动加上换行符。
         * 我们经常使用的System.out.println()实际上就是使用 PrintStream 打印各种数据。其中，
         * System.out是系统默认提供的 PrintStream，表示标准输出： ===这个总结很拔高===
         * System.err是系统默认提供的标准错误输出。
         *      System.out.print(12345); // 输出12345
         *      System.out.print(new Object()); // 输出类似java.lang.Object@3c7a835a
         *      System.out.println("Hello"); // 输出Hello并换行

         * PrintStream 和 OutputStream相比:
         * 除了添加了一组print()/println()方法，可以打印各种数据类型，比较方便外，
         * 它还有一个额外的优点，就是不会抛出IOException，这样我们在编写代码的时候，就不必捕获IOException。
         *
         * PrintStream最终输出的总是 byte数据，而
         * PrintWriter则是扩展了 Writer 接口（准确说是抽象类），它的print()/println()方法最终输出的是char数据。两者的使用方法几乎是一模一样的：
         */
        System.out.println("In the printStreamAndprintWriterEntry");
        StringWriter buffer = new StringWriter();
        try (PrintWriter pw = new PrintWriter(buffer)) {
            //这其实是把数据写入 StringWriter 中而不是输出到终端，后面的 System.out.println(buffer.toString()) 才是输出到终端
            pw.println("Hello");
            pw.println(12345);
            pw.println(true);
        }
        System.out.println(buffer.toString());
    }

    public void filesEntry(){
        /**
         * 从Java 7开始，提供了Files和Paths这两个工具类，能极大地方便我们读写文件。
         * 虽然Files和Paths是java.nio包里面的类，但他俩封装了很多读写文件的简单方法，例如，我们要把一个文件的全部内容读取为一个byte[]，可以这么写：
         *      byte[] data = Files.readAllBytes(Paths.get("/path/to/file.txt"));
         * 如果是文本文件，可以把一个文件的全部内容读取为String：
         *      // 默认使用UTF-8编码读取:
         *      String content1 = Files.readString(Paths.get("/path/to/file.txt"));
         *      // 可指定编码:
         *      String content2 = Files.readString(Paths.get("/path/to/file.txt"), StandardCharsets.ISO_8859_1);
         *      // 按行读取并返回每行内容:
         *      List<String> lines = Files.readAllLines(Paths.get("/path/to/file.txt"));
         * 写入文件也非常方便：
         *      // 写入二进制文件:
         *      byte[] data = ...
         *      Files.write(Paths.get("/path/to/file.txt"), data);
         *      // 写入文本并指定编码:
         *      Files.writeString(Paths.get("/path/to/file.txt"), "文本内容...", StandardCharsets.ISO_8859_1);
         *      // 按行写入文本:
         *      List<String> lines = ...
         *      Files.write(Paths.get("/path/to/file.txt"), lines);
         * 此外，Files工具类还有copy()、delete()、exists()、move()等快捷方法操作文件和目录。
         * 特别注意的是，Files提供的读写方法，受内存限制，只能读写小文件，例如配置文件等，不可一次读入几个G的大文件。
         * 读写大型文件仍然要使用文件流，每次只读写一部分文件内容。 对于简单的小文件读写操作，可以使用Files工具类简化代码。
         */
        System.out.println("In the filesEntry");
    }
}


class CountInputStream extends FilterInputStream {
    private int count = 0;

    CountInputStream(InputStream in) {
        // ===这里直接调用父类的构造函数，这种手法很面向对象===
        super(in);
    }

    public int getBytesRead() {
        return this.count;
    }
    public int read() throws IOException {
        int n = in.read();
        if (n != -1) {
            this.count ++;
        }
        return n;
    }
    public int read(byte[] b, int off, int len) throws IOException {
        // 这个方法不实现也可以
        int n = in.read(b, off, len);
        if (n != -1) {
            this.count += n;
        }
        return n;
    }
}


