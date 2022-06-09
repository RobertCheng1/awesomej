package com.company.practice;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: FuncParamPoc
 * @Date: 2022/5/2 11:22
 */
public class FuncParamPoc {
    public static boolean test(List<String> nameList) {
        // List 作为参数
        // 不能直接 add:  nameList.add("big"); 报异常 Exception in thread "main" java.lang.UnsupportedOperationException
        // nameList.add("ccc");
        nameList.set(0, "in the test");
        // List<String> newNameList = new ArrayList<>(nameList);
        // newNameList.add("big");
        // System.out.println(newNameList);
        return true;
    }

    public static void testArray(int[] big){
        // 数组作为参数
        System.out.println("In the testArray");
        System.out.println(big[1]);
        big[0] = 99;
    }

    public void funcParaPoc() {
        List<String> nameList = Arrays.asList("aaa", "bbb");
        test(nameList);
        System.out.println("---------------");
        System.out.println(nameList);
        System.out.println(nameList.toString());

        int[] scoreList = new int[5];
        scoreList[1] = 100;
        testArray(scoreList);
        System.out.println(scoreList[0]);
        System.out.println(scoreList[1]);
        System.out.println(scoreList[2]);
    }

}
