package com.company;


/* 使用String、Integer等类型的时候，这些类型都是不变类，一个不变类具有以下特点：
 *      定义class时使用final，无法派生子类；
 *      每个字段使用final，保证创建实例后无法修改任何字段。
 * 
 * 假设我们希望定义一个Point类，有x、y两个变量，同时它是一个不变类，可以这么写：
 *      public final class Point {
 *          private final int x;
 *          private final int y;
 *
 *          public Point(int x, int y) {
 *              this.x = x;
 *              this.y = y;
 *          }
 *
 *          public int x() {
 *              return this.x;
 *          }
 *
 *          public int y() {
 *              return this.y;
 *          }
 *      }
 * 为了保证不变类的比较，还需要正确覆写equals()和hashCode()方法，这样才能在集合类中正常使用。
 * 后续我们会详细讲解正确覆写equals()和hashCode()，这里演示 Point不变类的写法目的是，这些代码写起来都非常简单，但是很繁琐。
 */

///////////////////////////////////////////////////////////////////////////////////////
/* 使用record关键字，可以一行写出一个不变类。
   把 record Point(int x, int y) {

      }
   改写为class，相当于以下代码：
    public final class Point extends Record {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return this.x;
        }

        public int y() {
            return this.y;
        }

        public String toString() {
            return String.format("Point[x=%s, y=%s]", x, y);
        }

        public boolean equals(Object o) {
            ...
        }
        public int hashCode() {
            ...
        }
    }
    除了用final修饰class以及每个字段外，编译器还自动为我们创建了构造方法，和字段名同名的方法，以及覆写toString()、equals()和hashCode()方法。
 */
record Point(int x, int y) {
}


// 编译器默认按照record声明的变量顺序自动创建一个构造方法，并在方法内给字段赋值。那么问题来了，如果我们要检查参数，应该怎么办？
// 假设Point类的x、y不允许负数，我们就得给 Point的构造方法加上检查逻辑：
record PointAdv(int x, int y) {
    // 注意到方法 public PointAdv {...}被称为Compact Constructor，它的目的是让我们编写检查逻辑
    public PointAdv {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException();
        }
    }

    // 作为 record 的 Point 仍然可以添加静态方法。一种常用的静态方法是 of()方法，用来创建Point：
    public static PointAdv of() {
        return new PointAdv(0, 0);
    }
    public static PointAdv of(int x, int y) {
        return new PointAdv(x, y);
    }
}

