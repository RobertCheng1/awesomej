package com.company.practice;

import com.sun.jmx.snmp.internal.SnmpSubSystem;

import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: MaxSubString
 * @Date: 2022/5/2 10:33
 */
public class MaxSubString {

    int[][] result = null;
    int fromI = 0;
    int fromJ = 0;
    public void maxSubString () {
        String a = "aabbcc";
        String b = "aaabbcccc";
        result = new int[a.length()][b.length()];

        for(int i=0; i<a.length(); i++){
           for(int j=0; j<b.length(); j++) {
               if (a.charAt(i) == b.charAt(j)) {
                   result[i][j] = 1;
               } else {
                   result[i][j] = 0;
               }
           }
        }
        System.out.printf("二维数组的行: %d\n",result.length);
        System.out.printf("二维数组的列: %d\n",result[0].length);
        for (int i=0; i<result.length; i++) {
            System.out.println(Arrays.toString(result[i]));
        }
        int max = 0;
        int tmp = 0;
        for (int i=0; i<a.length(); i++) {
            for(int j=0; j<b.length(); j++) {
                if( result[i][j] == 1)  {
                    tmp = findSubString(i, j);
                    if ((max == 0) || (max < tmp) ) {
                        max = tmp;
                        fromI = i;
                        fromJ = j;
                    }
                }
            }
        }
        System.out.printf("max = %d; fromI = %d; fromJ = %d\n", max, fromI, fromJ);
    }

    int findSubString(int i, int j) {
        int max = 1;
        System.out.printf("findSubString: i = %d, j = %d\n", i, j);
        while (true) {
            if (i+1 < result.length && j+1 < result[0].length) {
                if (result[i+1][j+1] == 1) {
                    i = i + 1;
                    j = j + 1;
                    max = max + 1;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return max;
    }
}
