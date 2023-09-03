package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author: chengpengxing
 * @description
 * @date: 2022/11/22 4:05 PM
 */
public class DatePoc {


    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static SimpleDateFormat sdfutc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    public void datePoc() {
        System.out.println("In the datePoc");
        System.out.println(new Date());
        System.out.println(new Date().getTime());

        String formatStr = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(format.format(new Date()));
        datePoc2();
        datePoc3();
    }

    public void datePoc2() {
        System.out.println("In the datePoc2");

        //String formatStr = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String formatStr = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        format.getTimeZone();
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            // 时间字符串的格式和上面的 formatStr 对不上
            Date tmp = format.parse("2022-03-03 20:12:18");
            System.out.println(format.format(tmp));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void datePoc3() {
        System.out.println("In the datePoc3");
        String formatStr = "yyyy-MM-dd HH:mm:ss";
        // 在调用 format.Parse 时，需要指定什么格式 什么时区，比如你可以指定 "yyyy-MM-dd HH:mm:ss"格式 和 UTC 时区, 如果没有设置时区默认就是本地时区
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        format.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        //format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            System.out.println("---good1------");
            Date tmp = format.parse("2022-03-03 20:12:18");
            System.out.println(format.format(tmp));
            System.out.println(tmp);

            System.out.println("---good2------");
            // 把 format 的时区从 GMT+08:00 设置为 UTC
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date tmp2 = format.parse("2022-03-03 20:12:18");
            System.out.println(format.format(tmp2));
            System.out.println(tmp2);


            String utcFormatStr = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            SimpleDateFormat utcFormat = new SimpleDateFormat(utcFormatStr);
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            System.out.println("---good3------");
            System.out.println(utcFormat.format(tmp));
            System.out.println(utcFormat.format(tmp2));
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("---------");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        sdfutc.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println("北京时间: " + sdf.format(new Date()));
        System.out.println("UTC时间: " + sdfutc.format(new Date()));
    }
}

