package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: chengpengxing
 * @description
 * @date: 2022/11/22 3:59 PM
 */
public class ThreadLocalPoc {

    private final static ThreadLocal<List<String>> STRING_LIST_HOLDER = new ThreadLocal<>();

    public void threadLocalPoc() {

        // ThreadLocal 的测试
        List<String> nameList = new ArrayList<>();
        nameList.add("google");
        STRING_LIST_HOLDER.set(nameList);
        nameList.add("jd");
        List<String> nameListBak= STRING_LIST_HOLDER.get();
        System.out.println(nameListBak);
    }
}
