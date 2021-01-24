package com.company;

/* JVM 参数：
   1. 如果产生了 NullPointerException，例如，调用a.b.c.x()时产生了NullPointerException，原因可能是:
        a是null；
        a.b是null；
        a.b.c是null；
     从 Java 14开始，如果产生了NullPointerException，JVM可以给出详细的信息告诉我们null对象到底是谁。
     这种增强的 NullPointerException详细信息是 Java 14新增的功能，但默认是关闭的，
     我们可以给JVM添加一个 -XX:+ShowCodeDetailsInExceptionMessages 参数启用它。

    2. JVM默认关闭断言指令，即遇到assert语句就自动忽略了，不执行。
    要执行 assert 语句，必须给 Java 虚拟机传递 -enableassertions（可简写为-ea）参数启用断言。
    还可以有选择地对特定地类启用断言，命令行参数是：-ea:com.itranswarp.sample.Main，表示只对 com.itranswarp.sample.Main这个类启用断言。
    或者对特定地包启用断言，命令行参数是：-ea:com.itranswarp.sample...（注意结尾有3个.），表示对com.itranswarp.sample这个包启动断言。
 */
public class ExceptionPoc {

    public void myparseInt(){
        System.out.println("In the myparseInt");
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("For myparseInt: catched NumberFormatException");
            e.printStackTrace();
            // 为了能追踪到完整的异常栈，在构造异常的时候，把原始的Exception实例传进去，新的Exception就可以持有原始Exception信息
            // throw new IllegalArgumentException(e);
        } finally {
            System.out.println("For myparseInt: finally of myparseInt");
        }
        System.out.println("---end of myparseInt---");
    }

    public void myparseIntAdv() throws Exception{
        System.out.println("In the myparseIntAdv");
        Exception origin = null;
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("For myparseIntAdv: catched NumberFormatException");
            origin = e;
            throw e;
        } finally {
            // 如果在 finally 中抛出异常，应该原始异常加入到原有异常中。
            // 调用方可通过Throwable.getSuppressed()获取所有添加的Suppressed Exception。
            System.out.println("For myparseIntAdv: finally of myparseIntAdv");
            Exception fe = new RuntimeException();
            if(origin != null){
                fe.addSuppressed(origin);
            }
            System.out.println(fe.getSuppressed());
            throw fe;
        }
    }

    public void touchExcep(){
        System.out.println("In the touchExcep");
        try {
            // myparseInt();
            myparseIntAdv();
        } catch (Exception e){
            System.out.println("catched Exception");
            // e.getCause();
            // e.printStackTrace();
        } finally {
            System.out.println("finally of touchExcep");
        }
        System.out.println("---end of touchExcep---");
    }
}
