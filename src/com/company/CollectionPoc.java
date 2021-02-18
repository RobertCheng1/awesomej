package com.company;

import javax.xml.namespace.QName;
import java.util.*;


class Worker{
    private String name;
    private int salary;

    public Worker(String name, int salary){
        this.name = name;
        this.salary = salary;
    }
    public String getName(){
        return this.name;
    }
    public int getSalary(){
        return this.salary;
    }

    public boolean equals(Object other){
        System.out.println("In the equals of worker");
        if (other instanceof Worker) {
            Worker tmp = (Worker) other;
            // if (this.name != null) {
            //     return this.name.equals(tmp.name) && this.salary == tmp.salary;
            // }
            return Objects.equals(this.name, tmp.name) && this.salary == tmp.salary;

        }
        return false;
    }
}
public class CollectionPoc {
    public void listEntry() {
        /**
         *  List是最基础的一种集合：它是一种有序列表。
         *  实现List接口并非只能通过数组（即ArrayList的实现方式）来实现，
         *  另一种 LinkedList 通过“链表”也实现了List接口。在LinkedList中，它的内部每个元素都指向下一个元素：
         *  在实际应用中，需要增删元素的有序列表，我们使用最多的是ArrayList。
         */
        System.out.println("In the listEntry");
        List<String> fruitList = new ArrayList<>();
        fruitList.add("apple"); // size=1
        fruitList.add("pear"); // size=2
        fruitList.add("apple"); // 允许重复添加元素，size=3
        System.out.println(fruitList.size());
        // 创建 List：
        // 因为List只是一个接口，如果我们调用List.of()，它返回的是一个只读List：
        // list.add(78); // UnsupportedOperationException
        List<String> list = List.of("apple", "pear", "banana");
        // 遍历 List：
        // 这种方式并不推荐，一是代码复杂，二是因为get(int)方法只有ArrayList的实现是高效的，换成LinkedList后，索引越大，访问速度越慢。
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            System.out.println(s);
        }
        System.out.println("迭代器Iterator来访问List");
        // 所以我们要始终坚持使用迭代器 Iterator 来访问List。
        // Iterator本身也是一个对象，但它是由 List 的实例调用 iterator() 方法的时候创建的。
        // Iterator对象知道如何遍历一个List，并且不同的List类型，返回的 Iterator 对象实现也是不同的，但总是具有最高的访问效率。
        // Iterator对象有两个方法：boolean hasNext()判断是否有下一个元素，E next()返回下一个元素。
        for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
            String s = it.next();
            System.out.println(s);
        }
        // 有童鞋可能觉得使用 Iterator 访问List的代码比使用索引更复杂。但是，要记住，通过 Iterator 遍历List永远是最高效的方式。并且，
        // 由于Iterator遍历是如此常用，所以，Java 的 for each 循环本身就可以帮我们使用Iterator遍历。
        // 把上面的代码再改写如下，就是我们编写遍历List的常见代码。
        // 实际上，只要实现了Iterable接口的集合类都可以直接用 for each 循环来遍历，
        // Java编译器本身并不知道如何遍历集合对象，但它会自动把 for each 循环变成Iterator的调用，
        // 原因就在于Iterable接口定义了一个Iterator<E> iterator()方法，强迫集合类必须返回一个Iterator实例
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("List和Array转换");
        String[] array = list.toArray(new String[3]);
        // String[] array = list.toArray(String[]::new); // ToDO：这种函数式写法我们会在后续讲到，这是第一次看到::的用法。
        for (String str : array) {
            System.out.println(str);
        }
        // 反过来，把Array变为List就简单多了，通过List.of(T...)方法最简单：
        Integer[] intArray = { 1, 2, 3 };
        List<Integer> intList = List.of(intArray);
    }

    public void listEquals() {
        System.out.println("In the listEquals");
        /**
         * List是一种有序链表：List内部按照放入元素的先后顺序存放，并且每个元素都可以通过索引确定自己的位置。
         * List还提供了boolean contains(Object o)方法来判断List是否包含某个指定元素。
         * 此外，int indexOf(Object o)方法可以返回某个元素的索引，如果元素不存在，就返回-1。
         *
         * 在List中查找元素时，List的实现类通过元素的equals()方法比较两个元素是否相等，
         * 因此，放入的元素必须正确覆写equals()方法，Java标准库提供的String、Integer等已经覆写了equals()方法；
         * 因此，我们总结一下equals()方法的正确编写方法：
         *     1. 先确定实例“相等”的逻辑，即哪些字段相等，就认为实例相等；
         *     2. 用instanceof判断传入的待比较的Object是不是当前类型，如果是，继续比较，否则，返回false；
         *     3. 对引用类型用 Objects.equals() 比较，对基本类型直接用==比较。
         *     使用Objects.equals()比较两个引用类型是否相等的目的是省去了判断null的麻烦。两个引用类型都是null时它们也是相等的。
         *     如果不调用List的contains()、indexOf()这些方法，那么放入的元素就不需要实现equals()方法。
         */
        List<Worker> workers = List.of(
                new Worker("Robert", 1000),
                new Worker("Bruce", 2000)
        );
        boolean flag = workers.contains(new Worker("Bruce", 2000));
        System.out.println(flag);
    }


    public void mapEntry(){
        // Map也是一个接口，最常用的实现类是HashMap。
        System.out.println("In the mapEntry");
        Worker alen = new Worker("Alen", 3000);
        // 联想泛型代码中提到的：Java标准库的Map<K, V>就是使用两种泛型类型的例子。它对Key使用一种类型，对Value使用另一种类型。
        Map<String, Worker> map = new HashMap<>();
        map.put("Alen", alen); // 将"Alen"和 Worker 实例映射并关联
        Worker target = map.get("Alen"); // 通过key查找并返回映射的Student实例
        System.out.println(target == alen); // true，同一个实例
        System.out.println(target.getSalary()); // 3000
        Worker another = map.get("Bob"); // 通过另一个key查找
        System.out.println(another); // 未找到返回null

        // 遍历 Map：在遍历的时候，遍历的顺序既不一定是put()时放入的key的顺序，也不一定是key的排序顺序
        Map<String, Integer> mpasi = new HashMap<>();
        mpasi.put("apple", 123);
        mpasi.put("pear", 456);
        mpasi.put("banana", 789);
        for (String key : mpasi.keySet()) {
            Integer value = mpasi.get(key);
            System.out.println(key + " = " + value);
        }

        for (Map.Entry<String, Integer> entry : mpasi.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " = " + value);
        }
    }

}

