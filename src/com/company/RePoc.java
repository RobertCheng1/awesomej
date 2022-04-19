package com.company;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: RePoc
 * @Date: 2022/4/17 19:28
 */
public class RePoc {

    public void baseTest() {
        // 正则表达式测试
        String re = "java";
        if ("java".matches(re)) {
            System.out.println("good");
        } else {
            System.out.println("bad");
        }
        System.out.println("abcde".substring(2));
    }
}


