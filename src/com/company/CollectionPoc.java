package com.company;

import javax.xml.namespace.QName;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

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
         *  另一种 LinkedList 通过“链表”也实现了List接口。在 LinkedList 中，它的内部每个元素都指向下一个元素：
         *  在实际应用中，需要增删元素的有序列表，我们使用最多的是ArrayList。
         */
        System.out.println("In the listEntry");
        List<String> fruitList = new ArrayList<>();
        fruitList.add("apple"); // size=1
        fruitList.add("pear"); // size=2
        fruitList.add("apple"); // 允许重复添加元素，size=3
        System.out.println(fruitList.size());
        // 创建 List：
        // 因为List只是一个接口，如果我们调用List.of()，它返回的是一个只读List：联想 RecordPoc.txt 中的 PointAdv 的 of()方法
        // list.add(78); // UnsupportedOperationException
        // List<String> list = List.of("apple", "pear", "banana"); //为了用 Java8,暂时注释掉
        List<String> list = fruitList; //为了用 Java8,暂时这么赋值
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
        // Iterator对象有两个方法：boolean hasNext()判断是否有下一个元素，E next()返回下一个元素。详情参考：iteratorEntry()
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
        // List和Array转换: 给 toArray(T[]) 传入一个类型相同的Array，List内部自动把元素复制到传入的Array中：
        System.out.println("List和Array转换");
        String[] array = list.toArray(new String[3]);
        // String[] array = list.toArray(String[]::new); // ToDO：这种函数式写法我们会在后续讲到，这是第一次看到::的用法。
        for (String str : array) {
            System.out.println(str);
        }
        // 反过来，把Array变为List就简单多了，通过List.of(T...)方法最简单：
        Integer[] intArray = { 1, 2, 3 };
        // List<Integer> intList = List.of(intArray); //为了用 Java8,暂时注释掉
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
         * 因此，我们总结一下 equals() 方法的正确编写方法：
         *     1. 先确定实例“相等”的逻辑，即哪些字段相等，就认为实例相等；
         *     2. 用instanceof判断传入的待比较的Object是不是当前类型，如果是，继续比较，否则，返回false；
         *     3. 对引用类型用 Objects.equals() 比较，对基本类型直接用==比较。
         *        使用 Objects.equals() 比较两个引用类型是否相等的目的是省去了判断null的麻烦。两个引用类型都是null时它们也是相等的。
         * 如果不调用List的contains()、indexOf()这些方法，那么放入的元素就不需要实现 equals()方法。
         */
        //为了用 Java8,暂时注释掉
        // List<Worker> workers = List.of(
        //         new Worker("Robert", 1000),
        //         new Worker("Bruce", 2000)
        // );
        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker("Robert", 1000));
        workers.add( new Worker("Bruce", 2000));
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
         * 正确使用 Map 必须保证：
         * 1. 作为 key 的对象必须正确覆写 equals()方法，相等的两个key实例调用equals()必须返回true；
         * 2. 作为 key 的对象还必须正确覆写 hashCode()方法，且hashCode()方法要严格遵循以下规范：
         *      如果两个对象a和b相等，那么a.equals(b)一定为true，则a.hashCode()必须等于b.hashCode()；
         *      如果两个对象a和b不相等，那么a.equals(b)一定为false，则a.hashCode()和b.hashCode()尽量不要相等。
         * 即相同的key对象（使用equals()判断时返回true）必须要计算出相同的索引，否则，相同的key每次取出的value就不一定对。===精准的解释===
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
         * 还有一种 Map，它在内部会对Key进行排序，这种Map就是SortedMap。注意到SortedMap是接口，它的实现类是TreeMap。
         * 使用TreeMap时，放入的 Key必须实现 Comparable接口。
         * String、Integer这些类已经实现了 Comparable接口，因此可以直接作为Key使用。作为Value的对象则没有任何要求。
         * 如果作为 Key 的class没有实现 Comparable接口，那么，必须在创建TreeMap时同时指定一个自定义排序算法。
         * 注意到 Comparator接口要求实现 compare 方法，它负责比较传入的两个元素a和b，
         * 如果a<b，则返回负数，通常是-1，如果a==b，则返回0，如果a>b，则返回正数，通常是1。TreeMap内部根据比较结果对Key进行排序。
         *
         * TreeMap 和 HashMap 的区别(自己总结的)：
         * TreeMap 要根据 Key 查找 Value 时，TreeMap不使用 Key 对象的 equals()和 hashCode() 参考教程中：因为TreeMap不使用equals()和hashCode()
         * TreeMap 在比较两个 Key 是否相等时依赖 Key 的 (Comparable接口的)compareTo()方法或者 Comparator.compare()方法。在两个Key相等时，必须返回0。
         * 难道TreeMap不存在(HashMap中存在的)哈希冲突的情况，因为key相等就替换value 参考教程中：new Student("Bob", 66)进行查找时，结果为null
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
        System.out.println("In the mapMisc");
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
                System.out.println("In the compare of TreeMap");
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
        // 验证了 TreeMap 不存在(HashMap中存在的)哈希冲突的情况，因为key相等就替换value,
        // 如果 TreeMap 只是以 worker 的 salary 排序，则下面的 put 执行后，在 for 循环中会输出 Bob-44 而不是 Bobbbb-44
        map2.put(new Worker("Bobbbb", 3000), 44);
        map2.put(new Worker("Alen", 2000), 3);
        for (Map.Entry<Worker, Integer> entry : map2.entrySet()) {
            System.out.println(entry.getKey().getName() + '-' + entry.getValue());
        }
    }

    public void properties() throws  java.io.UnsupportedEncodingException, java.io.IOException{
        System.out.println("In the properties:" + getClass());
        /**
         * 在编写应用程序的时候，经常需要读写配置文件。例如，用户的设置：
         *      # 上次最后打开的文件:
         *      last_open_file=/data/hello.txt
         *      # 自动保存文件的时间间隔:
         *      auto_save_interval=60
         * 配置文件的特点是，它的Key-Value一般都是String-String类型的，因此我们完全可以用Map<String, String>来表示它。
         * 因为配置文件非常常用，所以Java集合库提供了一个Properties来表示一组“配置”。===术语注意名词的精确使用===
         * Properties 设计的目的是存储 String 类型的 key－value，但 Properties 实际上是从 Hashtable 派生的，它的设计实际上是有问题的，
         * 但是为了保持兼容性，现在已经没法修改了。 除了getProperty()和setProperty()方法外，还有从Hashtable继承下来的get()和put()方法，
         * 这些方法的参数签名是 Object，我们在使用Properties的时候，不要去调用这些从 Hashtable 继承下来的方法。
         *
         * 用 Properties读取配置文件，一共有三步：
         *     创建Properties实例；
         *     调用load()读取文件； load(InputStream)方法接收一个InputStream实例，表示一个字节流，它可以是文件流,也可以是从jar包中读取的资源流：
         *     调用getProperty()获取配置。
         *
         * 编码：
         * 早期版本的Java规定.properties文件编码是ASCII编码（ISO8859-1），如果涉及到中文就必须用name=\u4e2d\u6587来表示，非常别扭。
         * 从JDK9开始，Java的.properties文件可以使用UTF-8编码了。
         * 不过，需要注意的是，由于 load(InputStream) 默认总是以 ASCII 编码读取字节流，所以会导致读到乱码。===这个很精准的描述===
         * 我们需要用另一个重载方法load(Reader)读取：
         *      Properties props = new Properties();
         *      props.load(new FileReader("settings.properties", StandardCharsets.UTF_8));  就可以正常读取中文。
         * InputStream和Reader的区别是一个是字节流，一个是字符流。字符流在内存中已经以char类型表示了，不涉及编码问题。
         */
        // 从文件系统读取这个.properties文件
        Properties props = new Properties();
        String f = "";
        String os = System.getProperty("os.name");
        if (os.contains("Windows")){
            f = "src\\com\\company\\misc\\setting.properties";
        } else {
            f = "src/com/company/misc/setting.properties";
        }
        props.load(new java.io.FileInputStream(f)); //关于读写文件即IO流的操作，请参考 classpathResourceEntry() of IOPoc.Java
        // 也可以从 classpath 读取.properties文件:(另外IO--读取classpath资源也提到了 getResourceAsStream)
        // 因为load(InputStream)方法接收一个InputStream实例，表示一个字节流，它不一定是文件流，也可以是从jar包中读取的资源流
        // props.load(getClass().getResourceAsStream("/common/setting.properties"));
        // 从内存读取一个字节流:
        // String cfg = "# test" + "\n" + "course=Java" + "\n" + "last_open_date=2019-08-07T12:35:01";
        // ByteArrayInputStream input = new ByteArrayInputStream(cfg.getBytes("UTF-8"));
        // props.load(input);
        System.out.println("course: " + props.getProperty("course"));
        System.out.println("last_open_date: " + props.getProperty("last_open_date"));
        System.out.println("last_open_file: " + props.getProperty("last_open_file"));
        System.out.println("auto_save: " + props.getProperty("auto_save", "60"));

        props.setProperty("url", "http://www.liaoxuefeng.com");
        props.setProperty("language", "Java");
        props.store(new FileOutputStream(f), "这是写入的properties注释");
    }

    public void setEntry(){
        /**
         * Set实际上相当于只存储key、不存储value的Map。我们经常用 Set 用于去除重复元素。
         * 因为放入Set的元素和Map的key类似，都要正确实现equals()和 hashCode()方法，否则该元素无法正确地放入Set。
         * 最常用的 Set 实现类是 HashSet，实际上，HashSet仅仅是对 HashMap 的一个简单封装.
         *      public class HashSet<E> implements Set<E> {
         *          // 持有一个HashMap:
         *          private HashMap<E, Object> map = new HashMap<>();
         *          // 放入HashMap的value:
         *          private static final Object PRESENT = new Object();
         *      
         *          public boolean add(E e) {
         *              return map.put(e, PRESENT) == null;
         *          }
         *          public boolean contains(Object o) {
         *              return map.containsKey(o);
         *          }
         *          public boolean remove(Object o) {
         *              return map.remove(o) == PRESENT;
         *          }
         *      }
         * Set接口并不保证有序，而SortedSet接口则保证元素是有序的：
         *     HashSet是无序的，因为它实现了Set接口，并没有实现SortedSet接口；
         *     TreeSet是有序的，因为它实现了SortedSet接口。
         * Set用于存储不重复的元素集合：
         *     放入HashSet的元素与作为HashMap的key要求相同；主要是指上文提到的 "正确使用 Map 必须保证" 中对 key 的要求
         *     放入TreeSet的元素与作为TreeMap的Key要求相同；添加的元素必须正确实现 Comparable 接口，如果没有实现Comparable接口，那么创建TreeSet时必须传入一个Comparator对象。
         *             ┌───┐
         *             │Set│
         *             └───┘
         *               ^
         *          ┌────┴─────┐
         *          │          │
         *      ┌───────┐ ┌─────────┐
         *      │HashSet│ │SortedSet│
         *      └───────┘ └─────────┘
         *                     ^
         *                     │
         *                ┌─────────┐
         *                │ TreeSet │
         *                └─────────┘
         * Set用于存储不重复的元素集合，它主要提供以下几个方法：
         *     将元素添加进Set<E>：boolean add(E e)
         *     将元素从Set<E>删除：boolean remove(Object e)
         *     判断是否包含元素：boolean contains(Object e)
         */
        System.out.println("In the setEntry");
        Set<String> set = new HashSet<>();
        System.out.println(set.add("apple")); // true
        System.out.println(set.add("banana")); // true
        System.out.println(set.add("pear")); // true
        System.out.println(set.add("banana")); // false，添加失败，因为元素已存在, 这是和 List<String> 的区别
        System.out.println(set.contains("xyz")); // false，元素不存在
        System.out.println(set.remove("hello")); // false，删除失败，因为元素不存在
        System.out.println(set.size()); // 4，一共两个元素
        // 注意输出的顺序既不是添加的顺序，也不是String排序的顺序，在不同版本的JDK中，这个顺序也可能是不同的。===普通 set 的输出===
        // 把 HashSet 换成 TreeSet，在遍历TreeSet时，输出就是有序的，这个顺序是元素的排序顺序：
        for(String entry : set){
            System.out.println(entry);
        }
        System.out.println("test the TreeSet");
        Set<String> tset = new TreeSet<>();
        tset.add("apple");
        tset.add("banana");
        tset.add("pear");
        for(String entry : tset){
            System.out.println(entry);
        }
    }

    public void queueEntry(){
        System.out.println("In the queueEntry");
        /**
         * 队列（Queue）是一种经常使用的集合。Queue实际上是实现了一个先进先出（FIFO：First In First Out）的有序表。
         * 它和List的区别在于，List可以在任意位置添加和删除元素，而Queue只有两个操作：
         *     把元素添加到队列末尾；
         *     从队列头部取出元素。
         * 在Java的标准库中，队列接口Queue定义了以下几个方法：
         *     int size()：获取队列长度；
         *     boolean add(E)  /  boolean offer(E)：添加元素到队尾；
         *     E remove()      /  E poll()：获取队首元素并从队列中删除；
         *     E element()     /  E peek()：获取队首元素但并不从队列中删除。
         * 对于具体的实现类，有的Queue有最大队列长度限制，有的Queue没有。
         * 注意到添加、删除和获取队列元素总是有两个方法，这是因为在添加或获取元素失败时，这两个方法的行为是不同的。我们用一个表格总结如下：
         *      	            throw Exception	            返回false或null
         *      添加元素到队尾	    add(E e)	                boolean offer(E e)
         *      取队首元素并删除	E remove()	                E poll()
         *      取队首元素但不删除	E element()	                E peek()
         * 注意：不要把null添加到队列中，否则poll()方法返回null时，很难确定是取到了null元素还是队列为空。
         *
         * LinkedList即实现了 List 接口(参考 list 章节中提到的 ArrayList 和 LinkedList)，又实现了 Queue 接口，但是，在使用的时候，
         * 如果我们把它当作List，就获取List的引用，如果我们把它当作Queue，就获取Queue的引用：
         *      // 这是一个List:
         *      List<String> list = new LinkedList<>();
         *      // 这是一个Queue:
         *      Queue<String> queue = new LinkedList<>();
         * 始终按照面向抽象编程的原则编写代码，可以大大提高代码的质量。
         */
        Queue<String> queue = new LinkedList<>();
        queue.offer("apple");
        queue.offer("banana");
        queue.offer("pear");
        System.out.println(queue.poll()); // apple
        System.out.println(queue.poll()); // banana
        System.out.println(queue.peek()); // pear    队首永远都是pear，因为peek()不会删除它:
        System.out.println(queue.peek()); // pear
    }

    public void priorityQueueEntry(){
        System.out.println("In the priQueueEntry");
        /**
         * PriorityQueue和Queue的区别在于:
         * 它的出队顺序与元素的优先级有关，对 PriorityQueue 调用remove()或poll()方法，返回的总是优先级最高的元素。
         * 放入PriorityQueue的元素，必须实现 Comparable 接口，PriorityQueue会根据元素的排序顺序决定出队的优先级。
         *
         * 如果我们要放入的元素并没有实现 Comparable 接口怎么办？
         * PriorityQueue允许我们提供一个Comparator对象来判断两个元素的顺序。我们以银行排队业务为例，实现一个PriorityQueue：
         */
        Queue<String> q = new PriorityQueue<>();
        // 添加3个元素到队列:
        q.offer("apple");
        q.offer("pear");
        q.offer("banana");
        // 我们放入的顺序是"apple"、"pear"、"banana"，但是取出的顺序却是"apple"、"banana"、"pear"，
        // 这是因为从字符串的排序看，"apple"排在最前面，"pear"排在最后面
        System.out.println(q.poll()); // apple
        System.out.println(q.poll()); // banana
        System.out.println(q.poll()); // pear
        System.out.println(q.poll()); // null,因为队列为空

        Queue<Worker> qw = new PriorityQueue<>(new Comparator<Worker>() {
            @Override
            public int compare(Worker o1, Worker o2) {
                System.out.println("In the compare of PriorityQueue:" + o1.getName() + o2.getName());
                return o1.getName().compareTo(o2.getName());
            }
        });
        qw.add(new Worker("A2", 10));
        qw.add(new Worker("A10", 20));
        qw.add(new Worker("V1", 10));
        System.out.println("----");
        System.out.println(qw.poll().getName());
        System.out.println(qw.poll().getName());
        System.out.println(qw.poll().getName());
    }

    public void dequeueEntry(){
        /**
         * Java集合提供了接口 Deque来实现一个双端队列，它的功能是：
         *     既可以添加到队尾，也可以添加到队首；
         *     既可以从队首获取，又可以从队尾获取。
         * Deque是一个接口，它的实现类有 ArrayDeque 和 LinkedList。
         *
         * 比较一下Queue和Deque出队和入队的方法：
         * 	                Queue	                    Deque
         * 添加元素到队尾	    add(E e) / offer(E e)	    addLast(E e) / offerLast(E e)
         * 取队首元素并删除	E remove() / E poll()	    E removeFirst() / E pollFirst()
         * 取队首元素但不删除	E element() / E peek()	    E getFirst() / E peekFirst()
         * 添加元素到队首	    无	                        addFirst(E e) / offerFirst(E e)
         * 取队尾元素并删除	无	                        E removeLast() / E pollLast()
         * 取队尾元素但不删除	无	                        E getLast() / E peekLast()
         *
         * 对于添加元素到队尾的操作，Queue提供了add()/offer()方法，而Deque提供了addLast()/offerLast()方法。
         * 添加元素到对首、取队尾元素的操作在Queue中不存在，在Deque中由addFirst()/removeLast()等方法提供。
         * 注意到 Deque 接口实际上扩展自Queue：
         *      public interface Deque<E> extends Queue<E> {
         *          ...
         *      }
         * 因此，Queue提供的add()/offer()方法在Deque中也可以使用，
         * 但是，使用 Deque，最好不要调用offer()，而是调用offerLast()
         *
         * 我们发现 LinkedList 真是一个全能选手，它即是List，又是Queue，还是Deque。===你咋这么优秀呢===
         * 但是我们在使用的时候，总是用特定的接口来引用它，这是因为持有接口说明代码的抽象层次更高，而且接口本身定义的方法代表了特定的用途。
         *      // 不推荐的写法:
         *      LinkedList<String> d1 = new LinkedList<>();
         *      d1.offerLast("z");
         *      // 推荐的写法：
         *      Deque<String> d2 = new LinkedList<>();
         *      d2.offerLast("z");
         * 可见面向抽象编程的一个原则就是：尽量持有接口，而不是具体的实现类。
         */
        System.out.println("In the dequeueEntry");
        Deque<String> deque = new LinkedList<>(); // 注意到Deque接口实际上扩展自Queue
        deque.offerLast("A"); // A
        deque.offerLast("B"); // A <- B
        deque.offerFirst("C"); // C <- A <- B
        System.out.println(deque.pollFirst()); // C, 剩下A <- B
        System.out.println(deque.pollLast()); // B, 剩下A
        System.out.println(deque.pollFirst()); // A
        System.out.println(deque.pollFirst()); // null
    }

    public void stackEntry(){
        /**
         * Stack只有入栈和出栈的操作：
         *     把元素压栈：push(E)；
         *     把栈顶的元素“弹出”：pop(E)；
         *     取栈顶元素但不弹出：peek(E)。
         *
         * 在Java中，我们用Deque可以实现Stack的功能：
         *     把元素压栈：push(E)/addFirst(E)；
         *     把栈顶的元素“弹出”：pop(E)/removeFirst()；
         *     取栈顶元素但不弹出：peek(E)/peekFirst()。
         *
         * 为什么Java的集合类没有单独的Stack接口呢？
         * 因为有个遗留类名字就叫Stack，出于兼容性考虑，所以没办法创建Stack接口，只能用Deque接口来“模拟”一个Stack了。
         * 当我们把Deque作为Stack使用时，
         * 注意只调用push()/pop()/peek()方法，不要调用addFirst()/removeFirst()/peekFirst()方法，这样代码更加清晰。
         */
        System.out.println("In the stackEntry");
    }

    public void iteratorEntry(){
        /**
         * Java的集合类都可以使用for each循环，List、Set和Queue会迭代每个元素，Map会迭代每个key。以List为例：
         *      List<String> list = List.of("Apple", "Orange", "Pear");
         *      for (String s : list) {
         *          System.out.println(s);
         *      }
         * 实际上，Java编译器并不知道如何遍历List。
         * 上述代码能够编译通过，只是因为编译器把 for each循环通过 Iterator改写为了普通的 for 循环：
         *      for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
         *          String s = it.next();
         *          System.out.println(s);
         *      }
         * 我们把这种通过 Iterator 对象遍历集合的模式称为迭代器。
         * 使用迭代器的好处在于，调用方总是以统一的方式遍历各种集合类型，而不必关系它们内部的存储结构。
         *
         * Iterator对象是集合对象自己在内部创建的，它自己知道如何高效遍历内部的数据集合，调用方则获得了统一的代码，
         * 编译器才能把标准的 for each 循环自动转换为Iterator遍历。
         * 如果我们自己编写了一个集合类，想要使用for each循环，只需满足以下条件：
         *     a. 集合类实现Iterable接口，该接口要求返回一个Iterator对象；
         *     b. 用Iterator对象迭代集合内部数据。
         * 这里的关键在于，集合类通过调用iterator()方法，返回一个Iterator对象，这个对象必须自己知道如何遍历该集合。
         */
        System.out.println("In the iteratorEntry");
        ReverseList<String> rlist = new ReverseList<>();
        rlist.add("Apple");
        rlist.add("Orange");
        rlist.add("Pear");
        for (String s : rlist) {
            System.out.println(s);
        }
    }

    public void streamEntry(){
        /**
         * Stream 作为 Java 8 的一大亮点，它与 java.io 包里的 InputStream 和 OutputStream 是完全不同的概念。
         * 它也不同于 StAX 对 XML 解析的 Stream，也不是 Amazon Kinesis 对大数据实时处理的 Stream。
         * Java 8 中的 Stream 是对集合（Collection）对象功能的增强，
         * 它专注于对集合对象进行各种非常便利、高效的聚合操作（aggregate operation），或者大批量数据操作 (bulk data operation)。
         * Stream API 借助于同样新出现的 Lambda 表达式，极大的提高编程效率和程序可读性。同时它提供串行和并行两种模式进行汇聚操作，
         * 并发模式能够充分利用多核处理器的优势，使用 fork/join 并行方式来拆分任务和加速处理过程。通常编写并行代码很难而且容易出错,
         * 但使用 Stream API 无需编写一行多线程的代码，就可以很方便地写出高性能的并发程序。
         * 所以说，Java 8 中首次出现的 java.util.stream 是一个函数式语言+多核时代综合影响的产物。
         *
         * 流的操作类型分为两种：
         * Intermediate：
         *      map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、
         *      peek、 limit、 skip、 parallel、 sequential、 unordered
         *      一个流可以后面跟随零个或多个 intermediate 操作。其目的主要是打开流，做出某种程度的数据映射/过滤，
         *      然后返回一个新的流，交给下一个操作使用。这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
         * Terminal：
         *      forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、
         *      anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
         *      一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。
         *      Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。
         * 在对于一个 Stream 进行多次转换操作 (Intermediate 操作)，每次都对 Stream 的每个元素进行转换，而且是执行多次，
         * 这样时间复杂度就是 N（转换次数）个 for 循环里把所有操作都做掉的总和吗？其实不是这样的，转换操作都是 lazy 的，
         * 多个转换操作只会在 Terminal 操作的时候融合起来，一次循环完成。我们可以这样简单的理解，Stream 里有个操作函数的集合，
         * 每次转换操作就是把转换函数放入这个集合中，在 Terminal 操作的时候循环 Stream 对应的集合，然后对每个元素执行所有的函数。
         * 请参考 https://blog.csdn.net/a13662080711/article/details/84928181 
         */
        System.out.println("In the streamEntry");
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream().map(n -> n * n).collect(Collectors.toList());
        System.out.println(squareNums);
    }
}



class ReverseList<T> implements Iterable<T> {
    private List<T> list = new ArrayList<>();

    public void add(T t) {
        list.add(t);
    }

    @Override
    public Iterator<T> iterator() {
        return new ReverseIterator(list.size());
    }

    class ReverseIterator implements Iterator<T> {
        // 在编写Iterator的时候，我们通常可以用一个内部类来实现Iterator接口，这个内部类可以直接访问对应的外部类的所有字段和方法。
        // 例如，该代码中，内部类ReverseIterator可以用ReverseList.this获得当前外部类的this引用，
        // 然后，通过这个this引用就可以访问ReverseList的所有字段和方法。
        int index;

        ReverseIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index > 0;
        }

        @Override
        public T next() {
            index--;
            return ReverseList.this.list.get(index);
        }
    }
}

