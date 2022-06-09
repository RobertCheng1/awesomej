package com.company.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: JsonPoc
 * @Date: 2022/4/29 22:59
 */


public class JsonPoc {



    @Data
    static class UserInfo {
        private  Integer age;

        private  String myAddress;
    }


    public void test(){
        String result = "{\"age\":22, \"my_address\":\"BJ\"}";
        UserInfo userInfo = JSON.parseObject(result, UserInfo.class);
        System.out.printf("age = %d, myAddress = %s\n", userInfo.getAge(), userInfo.getMyAddress());

        result = "{\"age\":21, \"myAddress\":\"SH\"}";
        userInfo = JSON.parseObject(result, UserInfo.class);
        System.out.printf("age = %d, myAddress = %s\n", userInfo.getAge(), userInfo.getMyAddress());

        // 这也可以
        result = "{\"age\":25, \"myaddress\":\"CHINA\"}";
        userInfo = JSON.parseObject(result, UserInfo.class);
        System.out.printf("age = %d, myAddress = %s\n", userInfo.getAge(), userInfo.getMyAddress());
    }
}
