package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class Farmer implements Comparable<Farmer> {
    private String name;
    private int age;

    Farmer(String name, int age){
        this.name = name;
        this.age = age;
    }

    public int getAge(){
        return this.age;
    }
    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return this.name + "-" + this.age;
    }

    @Override
    public int compareTo(Farmer other) {
        // return this.getName().compareTo(other.getName());
        return this.getAge() - other.getAge();
    }
}


class Pair<T> {
    private T first;
    private T last;
    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }
    public T getFirst() {
        return this.first;
    }
    public T getLast() {
        return this.last;
    }


    // 可以编译通过: 但实际上，这个<T>和Pair<T>类型的<T>已经没有任何关系了。
    // public static <T> Pair<T> create(T first, T last) {
    //     return new Pair<T>(first, last);
    // }
    // 静态泛型方法应该使用其他类型区分: 这样才能清楚地将静态方法的泛型类型和实例类型的泛型类型区分开。
    public static <K> Pair<K> create(K first, K last) {
        return new Pair<K>(first, last);
    }
}

class PairTwo<T, K> {
    private T first;
    private K last;
    public PairTwo(T first, K last) {
        this.first = first;
        this.last = last;
    }
    public T getFirst() {
        return this.first;
    }
    public K getLast() {
        return this.last;
    }
}


public class GenericPoc {
    public void genEntry(){
        System.out.println("In the genEntry");
        // 第一个元素是 String 第二个元素是 Integer， 这居然是可以的，想想也是 String 和 Integer 都继承自 Object
        Object[] tmp = new Object[3];
        tmp[0] = "hello";
        tmp[1] = new Integer(655);
        tmp[2] = 5555; // 为什么整形是可以的，这怎么解释？难道整形也是类，但是 123.getClass 和 instanceof 证明 Java 中 int 貌似不是类
        for (Object entry:tmp){
            System.out.println(entry);
            System.out.println(entry.getClass());
        }

        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Google");
        for(String entry:strList){
            System.out.println(entry);
        }
    }


    public void genAdv(){
        /**
         * 使用泛型：
         * 使用ArrayList时，如果不定义泛型类型时，泛型类型实际上就是Object：
         *      // 编译器警告:
         *      List list = new ArrayList();
         *      list.add("FaceBook");
         *      String first = (String) list.get(0);
         * 此时，只能把<T>当作Object使用，没有发挥泛型的优势。
         * 使用泛型时，把泛型参数<T>替换为需要的class类型，例如：ArrayList<String>，ArrayList<Number>等
         *
         * 编译器如果能自动推断出泛型类型，就可以省略后面的泛型类型。例如，对于下面的代码：
         *      List<Number> list = new ArrayList<Number>();
         * 编译器看到泛型类型List<Number>就可以自动推断出后面的ArrayList<T>的泛型类型必须是ArrayList<Number>，因此，可以把代码简写为：
         *      // 可以省略后面的Number，编译器可以自动推断泛型类型：
         *      List<Number> list = new ArrayList<>();
         *
         * 泛型接口:
         * 除了 ArrayList<T>使用了泛型，还可以在接口中使用泛型。
         * 例如，Arrays.sort(Object[])可以对任意数组进行排序，但待排序的元素必须实现 Comparable<T> 这个泛型接口：
         *  public interface Comparable<T> {
         *      // 返回负数: 当前实例比参数o小
         *      // 返回0: 当前实例与参数o相等
         *      // 返回正数: 当前实例比参数o大
         *      int compareTo(T o);
         *  }
         */

        //可以直接对String数组进行排序：这是因为String本身已经实现了Comparable<String>接口
        String[] ss = new String[] { "Orange", "Apple", "Pear" };
        Arrays.sort(ss);
        System.out.println(Arrays.toString(ss));

        Farmer[] farmers = new Farmer[] {
                new Farmer("Rob", 22),
                new Farmer("Bruce", 21),
                new Farmer("Jordan", 23),
        };
        Arrays.sort(farmers);
        System.out.println(Arrays.toString(farmers));
    }

    public void cutomizeGeneric(){
        // 编写泛型类：
        // 编写泛型类比普通类要复杂。通常来说，泛型类一般用在集合类中，例如ArrayList<T>，我们很少需要编写泛型类。
        // 编写泛型类时，要特别注意，泛型类型<T>不能用于静态方法。例如
        System.out.println("In the cutomizeGeneric");
        Pair<String> p = new Pair<String>("Michael", "Jordan");
        System.out.println(p.getFirst());
        System.out.println(p.getLast());


        /**
         * 多个泛型类型:
         * 泛型还可以定义多种类型。例如，我们希望Pair不总是存储两个类型一样的对象，就可以使用类型<T, K>：
         *      public class Pair<T, K> {
         *          private T first;
         *          private K last;
         *          public Pair(T first, K last) {
         *              this.first = first;
         *              this.last = last;
         *          }
         *          public T getFirst() { ... }
         *          public K getLast() { ... }
         *      }
         * 使用的时候，需要指出两种类型：
         * Pair<String, Integer> p = new Pair<>("test", 123);
         * Java标准库的Map<K, V>就是使用两种泛型类型的例子。它对Key使用一种类型，对Value使用另一种类型。
         */
        PairTwo<String, Integer> alen = new PairTwo("Alen", 174);
        System.out.println(alen.getFirst());
        System.out.println(alen.getLast());

        // PairTwo wade = new PairTwo(179, "Wade");  这也可以编译通过
        PairTwo<Integer, String> wade = new PairTwo(179, "Wade");
        System.out.println(wade.getFirst());
        System.out.println(wade.getLast());




    }
}

