package com.company;
/**
 * @Author: chengpengxing
 * @Description:
 * @File: MockGlobal
 * @Date: 2022/3/18 14:14
 */
public class MockGlobal {

    // 直接启动服务，下面的这句话也是会被执行的。参考反射中提到的：每加载一种class，JVM就为其创建一个Class类型的实例，并关联起来。
    protected static String country = "china";
    private Integer age;
}
