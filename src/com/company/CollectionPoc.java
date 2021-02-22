package com.company;

import javax.xml.namespace.QName;
import java.util.*;
import java.time.DayOfWeek;

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

    @Override
    public boolean equals(Object other){
        //该方法是为了验证以该类的实例为元素的 list 的 contains 操作和以该类的实例为 Key 的 HashMap的 put 操作
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
    @Override
    public int hashCode(){
        //该方法是为了验证以该类的实例为 Key 的 HashMap 的 put 操作
        return Objects.hash(this.name, this.salary);
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
         *        使用 Objects.equals() 比较两个引用类型是否相等的目的是省去了判断null的麻烦。两个引用类型都是null时它们也是相等的。
         * 如果不调用List的contains()、indexOf()这些方法，那么放入的元素就不需要实现 equals()方法。
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
        // 查看 Map.Entry 源码，可知其是一个接口（而接口是不能实例化的，参考Outer.java），但 for循环却不报错，难道是采用了匿名类实现抽象方法！
        for (Map.Entry<String, Integer> entry : mpasi.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " = " + value);
        }
    }

    public void mapEquals(){
        /**
         * HashMap之所以能根据key直接拿到value，
         * 原因是它内部通过空间换时间的方法，用一个大数组存储所有value，并根据key直接计算出value应该存储在哪个索引。
         * 既然HashMap内部使用了数组，通过计算 key的 hashCode()直接定位value所在的索引，那么第一个问题来了：
         * hashCode()返回的int范围高达±21亿，先不考虑负数，HashMap内部使用的数组得有多大？
         * 实际上HashMap初始化时默认的数组大小只有 16，任何key，无论它的hashCode()有多大，都可以简单地通过：
         *      int index = key.hashCode() & 0xf; // 0xf = 15  ===这是典型的位操作运算===
         * 把索引确定在0～15，即永远不会超出数组范围，上述算法只是一种最简单的实现。
         *
         * 由于扩容会导致重新分布已有的key-value，所以，频繁扩容对HashMap的性能影响很大。
         * 如果我们确定要使用一个容量为10000个key-value的HashMap，更好的方式是创建HashMap时就指定容量：
         *      Map<String, Integer> map = new HashMap<>(10000);
         * 虽然指定容量是10000(这个值指的是初始容量，后续还会根据扩容因子 DEFAULT_LOAD_FACTOR 扩容)，
         * 但HashMap内部的数组长度总是2^n，因此，实际数组长度被初始化为比10000大的16384（2^14）。
         * https://blog.csdn.net/Maxiao1204/article/details/80729657
         *
         * 正确使用Map必须保证：
         * 1. 作为 key 的对象必须正确覆写 equals()方法，相等的两个key实例调用equals()必须返回true；
         * 2. 作为 key 的对象还必须正确覆写 hashCode()方法，且hashCode()方法要严格遵循以下规范：
         *      如果两个对象a和b相等，那么a.equals(b)一定为true，则a.hashCode()必须等于b.hashCode()；
         *      如果两个对象a和b不相等，那么a.equals(b)一定为false，则a.hashCode()和b.hashCode()尽量不要相等。
         * 即相同的key对象（使用equals()判断时返回true）必须要计算出相同的索引，否则，相同的key每次取出的value就不一定对。
         * 上述第一条规范是正确性，必须保证实现，否则HashMap不能正常工作。
         * 而第二条如果尽量满足，则可以保证查询效率，因为不同的对象，如果返回相同的hashCode()，会造成Map内部存储冲突，使存取的效率下降。
         * 我们经常使用String作为key，因为String已经正确覆写了equals()方法， String类已经正确实现了hashCode()方法。
         *
         * 编写equals()和hashCode()遵循的原则是：
         * equals()用到的用于比较的每一个字段，都必须在hashCode()中用于计算；equals()中没有使用到的字段，绝不可放在hashCode()中计算。
         * 经测试（把Worker的hashCode函数改为 return Objects.hash(this.name)）这个原则不是必须的，只是为了最大程度满足上面提到的第二条规范
         * 在计算hashCode()的时候，经常借助 Objects.hash()来计算：
         *      int hashCode() {
         *          return Objects.hash(firstName, lastName, age);
         *      }
         * 避免 firstName或lastName为null，firstName.hashCode() 抛 NullPointerException 异常。
         *
         * 扩展思考：
         * 如果不同的两个key，例如"a"和"b"，它们的hashCode()恰好是相同的（这种情况是完全可能的，因为不相等的两个实例，只要求hashCode()尽量不相等），那么，当我们放入：
         *      map.put("a", new Person("Xiao Ming"));
         *      map.put("b", new Person("Xiao Hong"));
         * 时，由于计算出的数组索引相同，后面放入的"Xiao Hong"会不会把"Xiao Ming"覆盖了？
         * 当然不会！使用Map的时候，只要 key 不相同，它们映射的value就互不干扰。
         * 但是，在HashMap内部，确实可能存在不同的 key，映射到相同的hashCode()，即相同的数组索引上，肿么办？
         *
         * 我们就假设"a"和"b"这两个key最终计算出的索引都是 5，那么，在HashMap的数组中，实际存储的不是一个Person实例，
         * 而是一个List，它包含两个Entry，一个是"a"的映射，一个是"b"的映射：在查找的时候，例如：
         *      Person p = map.get("a");
         * HashMap内部通过"a"找到的实际上是List<Entry<String, Person>>，它还需要遍历这个List，并找到一个Entry，
         * 它的key字段是"a"，才能返回对应的Person实例。我们把不同的 key 具有相同的hashCode()的情况称之为哈希冲突。
         */
        System.out.println("In the mapEquals");
        Map<Worker, String> map = new HashMap<>();
        map.put(new Worker("rob", 1000), "MVP");
        map.put(new Worker("rob", 1000), "SuperStar");
        for(Map.Entry<Worker, String> entry:map.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    public void mapMisc(){
        /**
         * 如果Map的key是enum类型，推荐使用EnumMap，既保证速度，也不浪费空间。
         * 它在内部以一个非常紧凑的数组存储value，并且根据enum类型的key直接定位到内部数组的索引，并不需要计算hashCode()，不但效率最高，而且没有额外的空间浪费。
         * 使用EnumMap的时候，根据面向抽象编程的原则，应持有Map接口。
         *
         * 还有一种Map，它在内部会对Key进行排序，这种Map就是SortedMap。注意到SortedMap是接口，它的实现类是TreeMap。
         * 使用TreeMap时，放入的Key必须实现 Comparable接口。
         * String、Integer这些类已经实现了 Comparable接口，因此可以直接作为Key使用。作为Value的对象则没有任何要求。
         * 如果作为 Key 的class没有实现 Comparable接口，那么，必须在创建TreeMap时同时指定一个自定义排序算法。
         * TreeMap 和 HashMap 的区别：
         * TreeMap 要根据 Key 查找 Value 时，TreeMap不使用 Key 对象的 equals()和 hashCode()
         * TreeMap 在比较两个 Key 是否相等时依赖 Key 的 compareTo()方法或者 Comparator.compare()方法。在两个Key相等时，必须返回0。
         * 难道TreeMap不存在(HashMap中存在的)哈希冲突的情况，因为key相等就替换value from: new Student("Bob", 66)进行查找时，结果为null
         *             ┌───┐
         *             │Map│
         *             └───┘
         *               ^
         *          ┌────┴─────┐
         *          │          │
         *      ┌───────┐ ┌─────────┐
         *      │HashMap│ │SortedMap│
         *      └───────┘ └─────────┘
         *                     ^
         *                     │
         *                ┌─────────┐
         *                │ TreeMap │
         *                └─────────┘
         */

        Map<DayOfWeek, String> map = new EnumMap<>(DayOfWeek.class);
        map.put(DayOfWeek.MONDAY, "星期一");
        map.put(DayOfWeek.TUESDAY, "星期二");
        map.put(DayOfWeek.WEDNESDAY, "星期三");
        map.put(DayOfWeek.THURSDAY, "星期四");
        map.put(DayOfWeek.FRIDAY, "星期五");
        map.put(DayOfWeek.SATURDAY, "星期六");
        map.put(DayOfWeek.SUNDAY, "星期日");
        System.out.println(Arrays.toString(DayOfWeek.values()));
        System.out.println(map);
        System.out.println(map.get(DayOfWeek.MONDAY));

        Map<String, Integer> map1 = new TreeMap<>();
        map1.put("orange", 1);
        map1.put("apple", 2);
        map1.put("pear", 3);
        for (String key : map1.keySet()) {
            System.out.println(key);
        }

        Map<Worker, Integer> map2 = new TreeMap<>(new Comparator<Worker>() {
            public int compare(Worker p1, Worker p2) {
                // Case1: 按照 Worker 的名字排序
                // return p1.getName().compareTo(p2.getName());
                // Case2: 按照 Worker 的薪水排序
                if (p1.getSalary() == p2.getSalary()){
                    return 0;
                }
                // 如果要倒序的话：return p1.getSalary() < p2.getSalary()?1:-1;
                // 也可以直接借助 Integer.compare(int, int) 也可以返回正确的比较结果。
                return p1.getSalary() < p2.getSalary()?-1:1;

            }
        });
        map2.put(new Worker("Tom", 1000), 1);
        map2.put(new Worker("Bob", 3000), 2);
        // 验证了 TreeMap 不存在(HashMap中存在的)哈希冲突的情况，因为key相等就替换value
        map2.put(new Worker("Bobbbb", 3000), 44);
        map2.put(new Worker("Alen", 2000), 3);
        for (Map.Entry<Worker, Integer> entry : map2.entrySet()) {
            System.out.println(entry.getKey().getName() + '-' + entry.getValue());
        }
    }
}

