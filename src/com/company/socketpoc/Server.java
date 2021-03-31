package com.company.socketpoc;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import com.company.socketpoc.Handler;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

/**
 * Socket是一个抽象概念，一个应用程序通过一个Socket来建立一个远程连接，而Socket内部通过TCP/IP协议把数据传输到网络：
 *      ┌───────────┐                                   ┌───────────┐
 *      │Application│                                   │Application│
 *      ├───────────┤                                   ├───────────┤
 *      │  Socket   │                                   │  Socket   │
 *      ├───────────┤                                   ├───────────┤
 *      │    TCP    │                                   │    TCP    │
 *      ├───────────┤      ┌──────┐       ┌──────┐      ├───────────┤
 *      │    IP     │<────>│Router│<─────>│Router│<────>│    IP     │
 *      └───────────┘      └──────┘       └──────┘      └───────────┘
 * Socket、TCP和部分IP的功能都是由操作系统提供的，不同的编程语言只是提供了对操作系统调用的简单的封装。
 * 例如，Java提供的几个Socket相关的类就封装了操作系统提供的接口。
 * 为什么需要Socket进行网络通信？
 * 因为仅仅通过IP地址进行通信是不够的，同一台计算机同一时间会运行多个网络应用程序，
 * 例如浏览器、QQ、邮件客户端等。当操作系统接收到一个数据包的时候，如果只有IP地址，它没法判断应该发给哪个应用程序，
 * 所以，操作系统抽象出Socket接口，每个应用程序需要各自对应到不同的Socket，数据包才能根据Socket正确地发到对应的应用程序。
 *
 * 使用 Socket 进行网络编程时，本质上就是两个进程之间的网络通信。
 *      其中一个进程必须充当服务器端，它会主动监听某个指定的端口，
 *      另一个进程必须充当客户端，它必须主动连接服务器的IP地址和指定端口，
 *      如果连接成功，服务器端和客户端就成功地建立了一个TCP连接，双方后续就可以随时发送和接收数据。
 * 因此，当 Socket 连接成功地在服务器端和客户端之间建立后：
 *      对服务器端来说，它的Socket是指定的IP地址和指定的端口号；
 *      对客户端来说，它的Socket是它所在计算机的IP地址和一个由操作系统分配的随机端口号。 ===这个很解惑呀===
 */
public class Server {

    public void runServer() throws IOException {
        System.out.println("In the myServer");
        String type = "tcp";
        // String type = "udp";

        if (type.equals("tcp")){
            tcpServer();
        } else if (type.equals("udp")) {
            updServer();
        }
    }

    public void tcpServer() throws IOException{
        System.out.println("In the tcpServer");
        // 这里我们没有指定IP地址，表示在计算机的所有网络接口上进行监听。
        ServerSocket ss = new ServerSocket(8090);
        System.out.println("server is running");
        for(;;){
            Socket sock = ss.accept();
            System.out.println("connected from " +sock.getRemoteSocketAddress());
            Thread t = new Handler(sock);
            t.start();
        }
    }

    public void updServer() throws IOException {
        System.out.println("In the udpServer");
        DatagramSocket ds = new DatagramSocket(8090); // 监听指定端口
        for (;;) { // 无限循环
            // 数据缓冲区:
            byte[] buffer = new byte[20];
            // 创建一个DatagramPacket，将收到的报文写入buffer中。
            // 注意，这里指定了报文的长度，如果收到的UDP报文比2048大，多余的信息被舍弃
            // https://blog.csdn.net/crab530143383/article/details/17136629
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.printf("Before: packet.getLength() = %d\n", packet.getLength());
            ds.receive(packet); // 收取一个UDP数据包
            // 收取到的数据存储在buffer中，由packet.getOffset(), packet.getLength()指定起始位置和长度
            // 将其按UTF-8编码转换为String:
            String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), "UTF-8");
            System.out.println("[server receive]" + s);
            System.out.printf("After: packet.getOffset() = %d\n", packet.getOffset());
            System.out.printf("After: packet.getLength() = %d\n", packet.getLength());
            // 发送数据:
            // 当服务器收到一个DatagramPacket后，通常必须立刻回复一个或多个UDP包，因为客户端地址在DatagramPacket中，
            // 每次收到的DatagramPacket可能是不同的客户端，如果不回复，客户端就收不到任何UDP包。
            byte[] data = "ACK".getBytes("UTF-8");
            packet.setData(data);
            ds.send(packet);
        }
    }
}


class Handler extends Thread {
    Socket sock;

    public Handler(Socket sock){
        this.sock = sock;
    }

    @Override
    public void run() {
        /**
         * Socket流： 当Socket连接创建成功后，无论是服务器端，还是客户端，我们都使用Socket实例进行网络通信。
         * 因为 TCP 是一种基于流的协议，因此Java标准库使用 InputStream和 OutputStream 来封装Socket的数据流，这样我们使用Socket的流，和普通IO流类似：
         *      // 用于读取网络数据:
         *      InputStream in = sock.getInputStream();
         *      // 用于写入网络数据:
         *      OutputStream out = sock.getOutputStream();
         */
        try (InputStream input = this.sock.getInputStream()) {
            try (OutputStream output = this.sock.getOutputStream()) {
                handle(input, output);
            }
        } catch (Exception e) {
            try {
                this.sock.close();
            } catch (IOException ioe) {
            }
            System.out.println("client disconnected.");
        }
    }

    // Case1: 手写 socket 通信的后端, 所以客户端也应该用 socket（详情参考 socketpoc 工程）,数据流的格式或内容都是自定义的
    // private void handle(InputStream input, OutputStream output) throws IOException {
    //     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
    //     BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
    //     writer.write("hello\n");
    //     writer.flush();
    //     for (;;) {
    //         String s = reader.readLine();  //该 server 端是一读读一行，所以需要客户端发送 行分隔符
    //         if (s.equals("bye")) {
    //             writer.write("bye\n");
    //             writer.flush();
    //             break;
    //         }
    //         writer.write("ok: " + s + "\n");
    //         writer.flush();
    //     }
    // }
    // Case2: 手写解析 HTTP 协议的后端，所以客户端是浏览器即在浏览器中访问 http://127.0.0.1:8090/,数据流的格式或内容是符合 HTTP 协议的
    // ===根据代码的解析逻辑，可以直击 WEB HTTP 协议本身和网络通信的本质，真是满满的细节===
    // 在下面代码中，我们看到，编写HTTP服务器其实是非常简单的，只需要先编写基于多线程的TCP服务，然后在一个TCP连接中读取HTTP请求，发送HTTP响应即可。
    // 但是，要编写一个完善的HTTP服务器，以HTTP/1.1为例，需要考虑的包括：
    //      识别正确和错误的HTTP请求；
    //      识别正确和错误的HTTP头；
    //      复用TCP连接；
    //      复用线程；
    //      IO异常处理；
    //      ...
    // 这些基础工作需要耗费大量的时间，并且经过长期测试才能稳定运行。如果我们只需要输出一个简单的HTML页面，就不得不编写上千行底层代码，那就根本无法做到高效而可靠地开发。
    // 在JavaEE平台上，处理TCP连接，解析HTTP协议这些底层工作统统扔给现成的Web服务器去做，我们只需要把自己的应用程序跑在Web服务器上。
    // 为了实现这一目的，JavaEE提供了Servlet API，我们使用 Servlet API编写自己的Servlet来处理HTTP请求，
    // Web服务器实现 Servlet API接口，实现底层功能（详情参考 mavenpoc 工程）
    // from: Web开发--Web基础 和 Servlet 入门
    private void handle(InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
        // 读取 HTTP 请求: 就是按照 HTTP 协议解析字符流
        boolean requestOk = false;
        String first = reader.readLine();
        if (first.startsWith("GET / HTTP/1.")){
            requestOk = true;
        }
        for(;;){
            String header = reader.readLine();
            if(header.isEmpty()){
                break;
            }
            System.out.println(header);
        }
        System.out.println(requestOk ? "Response OK" : "Response Error");
        if (!requestOk){
            // 发送失败响应
            writer.write("HTTP/1.1 404 Not Found\r\n");
            writer.write("Content-Length:0\r\n");
            writer.write("\r\n");
            writer.flush();
        } else {
            // 发送成功响应:
            String data = "<html><body><h1>Hello, world!</h1></body></html>";
            int length = data.getBytes("UTF-8").length;
            writer.write("HTTP/1.0 200 OK\r\n");
            writer.write("Connection: close\r\n");
            writer.write("Content-Type: text/html\r\n");
            writer.write("Content-Length: " + length + "\r\n");
            writer.write("\r\n"); // 空行标识Header和Body的分隔
            writer.write(data);
            writer.flush();
        }
    }
}

