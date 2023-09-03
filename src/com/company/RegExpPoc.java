package com.company;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.MULTILINE;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: RegExpPoc
 * @Date: 2022/9/19 8:37 PM
 */
public class RegExpPoc {



    public void basicExpressPoc() {
        // 利用正则：整个字符串匹配
        System.out.println("In the basicExpressPoc");
        String re1 = "java\\d"; // 对应的正则是java\d
        System.out.println("java9".matches(re1));


        Pattern p = Pattern.compile("\\d*");
        System.out.println(p.matcher("123").matches());
    }


    public void regularExpressPoc() {
        // 利用正则：字符串查找：
        System.out.println("In the regularExpressPoc");
        //Pattern p= Pattern.compile(".*:\\d{4}/.*");
        Pattern p= Pattern.compile("(.*):(\\d{4})/(.*)");
        String bt = "mydb-bt.jd.com:3306/bt";
        Matcher matcher = p.matcher(bt);

        boolean result = matcher.find();
        System.out.println(result);
        if (result){
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
        }


        Pattern ipPattern = Pattern.compile("^([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$");
        String ip = "1.1.1.1";
        matcher = ipPattern.matcher(ip);
        result = matcher.find();
        System.out.println(result);

        Pattern poc = Pattern.compile("\\d{2}");
        String hello = "hello 22 java 33  655";
        matcher = poc.matcher(hello);
        // 循坏找到所有的符合正则表达式的字符串：比如 22  33
        while (matcher.find()) {
            System.out.printf("start = %d, end = %d\n", matcher.start(), matcher.end());
            System.out.println(matcher.group());
        }
    }


    public void sqlExpressPoc() {
        // 利用正则替换：
        String originalSql = "select * from student WHERE country = 'china'";
        String whereSql = " WHERE 1=1 AND status = 1 ";
        //originalSql = originalSql.replaceAll(("(?s)(.*)\\s(?i)WHERE\\s"), ("$1" + whereSql + " AND "));
        originalSql = originalSql.replaceAll(("(.*)\\sWHERE\\s"), ("$1" + whereSql + " AND "));
        System.out.println(originalSql);
        
        String s = "the quick brown fox jumps over the lazy dog.";
        //String r = s.replaceAll("\\s([a-z]{4})\\s", " <b>$1</b> ");
        String r = s.replaceAll("\\s([a-z]{4})\\s", "aaa");
        System.out.println(r);
    }



    public void expressAdvPoc() {
        // 模拟多行查找
        System.out.println("In the expressAdvPoc");
        //String re1 = "[a-zA-Z]{4}\\d";
        //String re1 = "^[a-zA-Z]{4}\\d$";
        // 用 (?m) 表示多行模式 from: https://blog.csdn.net/zhang__ao/article/details/78092294
        String re1 = "(?m)^[a-zA-Z]{4}\\d$";
        Pattern pattern = Pattern.compile(re1);
        String demo = "java9\nabcd3";

        Matcher matcher = pattern.matcher(demo);
        System.out.println("look at the result---");
        while (matcher.find()) {
            System.out.printf("start = %d, end = %d\n", matcher.start(), matcher.end());
            System.out.println(matcher.group());
        }
    }

}
