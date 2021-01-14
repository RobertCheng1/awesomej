package com.company;

import java.math.BigDecimal;

/* BigDecimal也是从Number继承的，也是不可变对象。
   如果查看BigDecimal的源码，可以发现，实际上一个 BigDecimal 是通过一个BigInteger和一个scale来表示的，
   即BigInteger表示一个完整的整数，而scale表示小数位数：
        public class BigDecimal extends Number implements Comparable<BigDecimal> {
            private final BigInteger intVal;
            private final int scale;
        }
 */
public class BigDecimalPoc {

    public void compare(){
        // 测试 BigDecimal: 如果一个BigDecimal的scale()返回负数，例如，-2，表示这个数是个整数，并且末尾有2个0
        System.out.println("In the compare of BigDecimalPoc");
        BigDecimal d1 = new BigDecimal("1234500");
        BigDecimal d2 = d1.stripTrailingZeros();
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d1.scale()); // 0
        System.out.println(d2.scale()); // -2
        System.out.println(d1.unscaledValue());
        System.out.println(d2.unscaledValue());

        BigDecimal d3 = new BigDecimal("136.34");
        System.out.println(d3.scale()); // 2
        System.out.println(d3.unscaledValue()); // 13634
    }
}

