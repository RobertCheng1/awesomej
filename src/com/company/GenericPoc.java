package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


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
    // setFirst 函数是为了测试 extends 通配符的限制
    public void setFirst(T first) {
        this.first = first;
    }

    /**
     * 对静态方法使用<T>:
     *      public static Pair<T> create(T first, T last) {
     *          return new Pair<T>(first, last);
     *      }
     * 上述代码会导致编译错误，我们无法在静态方法create()的方法参数和返回类型上使用泛型类型T。
     * 有些同学在网上搜索发现，可以在static修饰符后面加一个<T>，编译就能通过。但实际上，这个<T>和 Pair<T>类型的<T>已经没有任何关系了。
     *      public static <T> Pair<T> create(T first, T last) {
     *         return new Pair<T>(first, last);
     *      }
     * 评论中的清晰解释：
     * 因为普通的方法是通过类的实例来调用的，创建实例的过程调用了构造方法，也就是说对象已经知道这个时候类上面定义的<T>的具体类型了；
     * 而静态方法不需要对象实例来调用，所以也就不知道<T>的具体类型，虚拟机不允许这种情况发生，所以编译的时候就报错了。
     * <K>放在static后面，你可以理解为既然静态方法不知道Pair里面的具体类型，你就手动的告诉它具体的类型。
     */
    // 静态泛型方法应该使用其他类型区分: 这样才能清楚地将 静态方法的泛型类型 和 实例类型的泛型类型 区分开。
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

class IntPair extends Pair<Integer> {
    public IntPair(Integer first, Integer last) {
        super(first, last);
    }
}


public class GenericPoc {
    public void genericEntry(){
        /**
         * 在讲解什么是泛型之前，我们先观察Java标准库提供的ArrayList，它可以看作“可变长度”的数组，因为用起来比数组更方便。
         * 实际上ArrayList内部就是一个Object[]数组，配合存储一个当前分配的长度，就可以充当“可变数组”：
         *      public class ArrayList {
         *          private Object[] array;
         *          private int size;
         *          public void add(Object e) {...}
         *          public void remove(int index) {...}
         *          public Object get(int index) {...}
         *      }
         *  如果用上述ArrayList存储String类型，会有这么几个缺点：
         *      a. 需要强制转型；
         *      b. 不方便，易出错。
         *  例如，代码必须这么写
         */
        System.out.println("In the genericEntry");
        ArrayList list = new ArrayList();
        list.add("Hello");
        // 获取到Object，必须强制转型为String,如果去掉类型强转(String)编译提示不兼容的类型: java.lang.Object无法转换为java.lang.String
        String first = (String) list.get(0);

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

    public void genericAdv(){
        /**
         * 泛型就是定义一种模板，例如ArrayList<T>，然后在代码中为用到的类创建对应的ArrayList<类型>
         * 泛型就是编写模板代码来适应任意类型；
         * 泛型的好处是使用时不必对类型进行强制转换，它通过编译器对类型进行检查；
         *
         * 使用泛型：
         * 使用泛型时，把  泛型参数<T>替换为需要的class类型，例如：ArrayList<String>，ArrayList<Number>等
         * 使用ArrayList时，如果不定义泛型类型时，泛型类型实际上就是Object：
         *      // 编译器警告:
         *      List list = new ArrayList();
         *      list.add("FaceBook");
         *      String first = (String) list.get(0);
         * 此时，只能把<T>当作Object使用，没有发挥泛型的优势。
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
        System.out.println("In the genericAdv");
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
        // 编写泛型类 比 普通类 要复杂。通常来说，泛型类一般用在集合类中，例如ArrayList<T>，我们很少需要编写泛型类。
        // 编写泛型类时，需要定义泛型类型<T>；要特别注意，泛型类型<T>不能用于静态方法。
        // 静态方法不能引用泛型类型<T>，必须定义其他类型（例如<K>）来实现静态泛型方法；具体参考上面的 Pair 的静态方法 create
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
        PairTwo<String, Integer> alen = new PairTwo<>("Alen", 174);
        System.out.println(alen.getFirst());
        System.out.println(alen.getLast());

        // PairTwo wade = new PairTwo(179, "Wade");  //这也可以编译通过
        PairTwo<Integer, String> wade = new PairTwo<>(179, "Wade");
        System.out.println(wade.getFirst());
        System.out.println(wade.getLast());
    }
    
    public void typeErasureGeneric(){
        /**
         * 所谓擦拭法是指，虚拟机对泛型其实一无所知，所有的工作都是编译器做的。
         * Java使用擦拭法实现泛型，导致了：
         *      编译器把类型<T>视为Object；
         *      编译器根据<T>实现安全的强制转型。
         * 而虚拟机执行的代码并没有泛型：
         *      Pair p = new Pair("Hello", "world");
         *      String first = (String) p.getFirst();
         *      String last = (String) p.getLast();
         * 所以，Java的泛型是由编译器在编译时实行的，编译器内部永远把所有类型T视为Object处理，
         * 但是，在需要转型的时候，编译器会根据T的类型自动为我们实行安全地强制转型。
         *
         *
         * 前面讲了，我们无法获取Pair<T>的T类型，即给定一个变量Pair<Integer> p，无法从p中获取到Integer类型。
         * 但是，在父类是泛型类型的情况下，编译器就必须把类型T（对IntPair来说，也就是Integer类型）保存到子类的class文件中，
         * 不然编译器就不知道IntPair只能存取Integer这种类型。
         * 在继承了泛型类型的情况下，子类可以获取父类的泛型类型。例如：IntPair可以获取到父类的泛型类型Integer
         */
        System.out.println("In the typeErasureGeneric");
        Class<IntPair> clazz = IntPair.class;
        Type t = clazz.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            Type[] types = pt.getActualTypeArguments(); // 可能有多个泛型类型
            Type firstType = types[0]; // 取第一个泛型类型
            Class<?> typeClass = (Class<?>) firstType;
            System.out.println(typeClass); // Integer
        }
    }


    static int add(Pair<Number> p) {
        Number first = p.getFirst();
        Number last = p.getLast();
        return first.intValue() + last.intValue();
    }
    static int addAdv(Pair<? extends Number> p) {
        Number first = p.getFirst();
        Number last = p.getLast();
        // p.setFirst(xxx) 编译报错：不兼容的类型: java.lang.Integer无法转换为capture#1, 共 ? extends java.lang.Number
        // 有些童鞋会问了，既然 p 的定义是 Pair<? extends Number>，那么setFirst(? extends Number)为什么不能传入Integer？
        // 原因还在于擦拭法。如果我们传入的 p 是Pair<Double>，显然它满足参数定义Pair<? extends Number>，
        // 然而，Pair<Double>的setFirst()显然无法接受 Integer类型。
        //
        // 这就是 <? extends Number>通配符的一个重要限制：
        // 方法参数签名 setFirst(? extends Number) 无法传递任何Number的子类型给setFirst(? extends Number)。
        // 也许你会猜想我直接传 Number 类型会成功吗？答案也是否定的，因为 Number 是个抽象类不能实例化，哈哈哈。
        // p.setFirst(new Integer(first.intValue() + 100));
        return first.intValue() + last.intValue();
    }
    public void extendsGeneric(){
        System.out.println("In the extendsGeneric");
        int sum = add(new Pair<Number>(1, 2)); // 传入的类型是Pair<Number>，实际参数类型是(Integer, Integer)。
        // 既然实际参数是Integer类型，试试传入Pair<Integer>: int sum = add(new Pair<Integer>(1, 2)); 编译报错：
        // 不兼容的类型: com.company.Pair<java.lang.Integer>无法转换为com.company.Pair<java.lang.Number>
        // 原因很明显，因为Pair<Integer>不是Pair<Number>的子类，因此，add(Pair<Number>)不接受参数类型Pair<Integer>。
        // 但是从add()方法的代码可知，传入Pair<Integer>是完全符合内部代码的类型规范,因为语句：
        //      Number first = p.getFirst();
        //      Number last = p.getLast();
        // 实际类型是Integer，引用类型是Number，没有问题。问题在于方法参数类型定死了只能传入Pair<Number>。
        // 
        // 有没有办法使得方法参数接受 Pair<Integer> ?
        // 办法是有的，这就是使用 Pair<? extends Number> 使得方法接收所有泛型类型为Number或Number子类的Pair类型
        // 这样一来，给方法传入Pair<Integer>类型时，它符合参数 Pair<? extends Number>类型。
        // 这种使用 <? extends Number> 的泛型定义称之为上界通配符（Upper Bounds Wildcards），即把泛型类型T的上界限定在 Number了。
        // 除了可以传入Pair<Integer>类型，我们还可以传入Pair<Double>类型，Pair<BigDecimal>类型等等，因为Double和BigDecimal都是Number的子类。
        //
        // 如果我们考察对 Pair<? extends Number>类型调用getFirst()方法，实际的方法签名变成了：
        //      <? extends Number> getFirst();
        // 即返回值是Number或Number的子类，因此，可以安全赋值给Number类型的变量： Number x = p.getFirst();
        System.out.printf("the sum = %d\n", sum);
        int total = addAdv(new Pair<Integer>(11, 22));
        System.out.printf("the total = %d\n", total);
        // 小结：
        // 使用类似<? extends Number>通配符作为方法参数时表示：
        //     方法内部可以调用获取Number引用的方法，例如：Number n = obj.getFirst();；
        //     方法内部无法调用传入Number引用的方法（null除外），例如：obj.setFirst(Number n);。
        // 即一句话总结：使用extends通配符表示可以读，不能写。
        //
        // 使用类似<T extends Number>定义泛型类时表示：
        //      泛型类型限定为Number以及Number的子类。
    }
}

