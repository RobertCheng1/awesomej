package com.company.practice;

import java.util.Arrays;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: ZeroOnePackage
 * @Date: 2022/5/3 12:38
 */
public class ZeroOnePackage {
    /**
     * https://blog.csdn.net/shanghairuoxiao/article/details/62426727?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1.pc_relevant_default&utm_relevant_index=2
     * 01背包问题的描述：有编号分别为a,b,c,d,e的五件物品，它们的重量分别是2,2,6,5,4，它们的价值分别是6,3,5,4,6，
     * 现在给你个承重为10的背包，如何让背包里装入的物品具有最大的价值总和？
     *
     * 要说明这个问题，要先了解一下背包问题的状态转换方程： f[i,j] = Max{ f[i-1,j-Wi]+Pi( j >= Wi ), f[i-1,j] }
     * 其中：
     * f[i,j]表示在前i件物品中选择若干件放在承重为 j 的背包中，可以取得的最大价值。
     * Pi表示第i件物品的价值。
     *
     * 初学者最不懂的地方可能就是这个状态方程了，i是什么鬼，j又是什么鬼？下面具体来说这个状态方程怎么来的。
     * 之前说过动态规划是考虑递归的思想，要解决这个问题，首先想到解决其子问题。
     * 要从5个中选出若干个装入容量为10的背包，可以分解为，
     * 将a物品装入背包，然后从其他四个中选出若干个装入剩余容量为8的袋子，因为a已经占去了2个位置；
     * 或者不装a，从其他四个中选出若干个装入容量为10的袋子？这两种做法中，价值最大的就是我们需要的方案。
     *
     * 如果选择了第一种方案，那么继续分解，将b物品装入袋子，从其余三个中选出若干个装入剩余容量为6的袋子，或者不装b（也许你更乐意装b），
     * 从剩余三个中选出若干个装入剩余容量为8的袋子，选择这两种方案中价值最大的。
     * 依次类推，直到五个物品都选择完毕。将其一般化，用i代替a，用j代替10，用数学公式表达出来就是上面那个公式了，是不是觉得已经看懂了这个公式。
     */
    char []things = {'0', 'a', 'b', 'c', 'd', 'e'};
    // int []weight = {0, 2, 2, 6, 5, 4};
    // int []value = {0, 6, 3, 5, 4, 6};

    int []weight = {0, 2, 2, 6, 5, 4};
    int []value = {0, 6, 3, 5, 4, 1};

    public void test01Package() {
        int packageCap = 10;
        int thingKinds = things.length;
        int [][]result = new int[thingKinds][packageCap+1];

        for (int i=0; i<thingKinds; i++) {
            for(int j=0; j<packageCap+1; j++) {
                if ((i == 0) || (j == 0)) {
                    result[i][j] = 0;
                } else {
                    if (j >= weight[i]) {
                        result[i][j] = Math.max(result[i-1][j], result[i-1][j-weight[i]] + value[i]);
                    } else {
                        result[i][j] = result[i-1][j];
                    }
                }
            }
        }

        for (int i=0; i<result.length; i++) {
            System.out.println(Arrays.toString(result[i]));
            // for(int j=0; j<packageCap+1; j++) {
            //     System.out.printf("%2d", result[i][j]);
            // }
            // System.out.println();
        }

    }
}
