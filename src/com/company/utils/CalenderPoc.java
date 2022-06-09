package com.company.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: CalenderPoc
 * @Date: 2022/5/2 10:34
 */
public class CalenderPoc {


    public void dateTest() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            // Date from = formatter.parse(formatter.format(cal.getTime()));
            Date from = formatter.parse("2a");
            cal.add(Calendar.DATE, 1);
            Date to = formatter.parse(formatter.format(cal.getTime()));
            System.out.println(from.getTime());
            System.out.println(to.getTime());

            // 按照指定格式输出时间：
            SimpleDateFormat myformatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date now = new Date();
            System.out.println(myformatter.format(now));
        } catch (Exception e) {
            System.out.println("aaaaaaa");
            // e.printStackTrace();
            System.out.println(e.getStackTrace());
            for (StackTraceElement entry: e.getStackTrace()){
                System.out.println(entry);
            }

        }
    }

}
