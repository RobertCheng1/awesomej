package com.company.practice;

import java.util.*;

/**
 * @author: chengpengxing
 * @description:
 * @date: 2022/10/26 11:08 AM
 */
public class StrangePoc {

    public void strangeTest() {
        Map mapEntry1 = new HashMap();
        mapEntry1.put("name", "jdr");
        Map mapEntry2 = new HashMap();
        mapEntry2.put("name", "jdt");
        Map mapEntry3 = new HashMap();
        mapEntry3.put("name", "jdl");


        List<Map> mapResult = new ArrayList<>();
        mapResult.add(mapEntry1);
        mapResult.add(mapEntry2);
        mapResult.add(mapEntry3);


        // case1:
        System.out.println("this is for case1");
        List<String> userStringList = new ArrayList<>();
        mapResult.stream().map(Map::values).forEach(userStringList::addAll);
        System.out.println(userStringList);
        for(String name : userStringList) {  // 如果所有的value类型是 string，才可以正常输出；
            System.out.println(name);
        }
        System.out.println(userStringList.getClass());
        // Case2: Integer 的 list 怎么可以接住 string 的值;
        // 感觉是 forEach 内部的操作，忽略了 userIntegerList 类型检查；难道和擦拭法有关？？？
        System.out.println("---this is for case2---");
        List<Integer> userIntegerList = new ArrayList<>();
        mapResult.stream().map(Map::values).forEach(userIntegerList::addAll);
        System.out.println(userIntegerList);   // 当所有value的类型是 string 时，这句居然是可以正常输出的
        //for(Integer name : userIntegerList) {  // 提示：java.lang.String cannot be cast to java.lang.Integer；当然所有value的类型是 Integer时，这个 for 循环没问题
        //    System.out.println(name);
        //}
        for(Object name : userStringList) {  // 这样才可以正常输出
            System.out.println(name);
        }
        System.out.println(userIntegerList.getClass());
    }

}
