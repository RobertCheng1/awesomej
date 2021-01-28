package com.company;
import java.lang.annotation.*;
import java.lang.reflect.*;

/* Java语言使用@interface语法来定义注解（Annotation），它的格式如下：
        public @interface Report {
            int type() default 0;
            String level() default "info";
            String value() default "";
        }
   注解的参数类似无参数方法，可以用default设定一个默认值（强烈推荐）。最常用的参数应当命名为value。

   元注解：
   有一些注解可以修饰其他注解，这些注解就称为元注解（meta annotation）。
   Java标准库已经定义了一些元注解，我们只需要使用元注解，通常不需要自己去编写元注解
   @Target 使用@Target可以定义Annotation能够被应用于源码的哪些位置：
        类或接口：ElementType.TYPE；
        字段：ElementType.FIELD；
        方法：ElementType.METHOD；
        构造方法：ElementType.CONSTRUCTOR；
        方法参数：ElementType.PARAMETER。
   @Retention  使用@Retention定义了Annotation的生命周期：
        仅编译期：RetentionPolicy.SOURCE；
        仅class文件：RetentionPolicy.CLASS；
        运行期：RetentionPolicy.RUNTIME。
   @Repeatable 使用@Repeatable这个元注解可以定义Annotation是否可重复
   @Inherited  使用@Inherited定义子类是否可继承父类定义的Annotation。
        @Inherited仅针对@Target(ElementType.TYPE)类型的annotation有效，并且仅针对class的继承，对interface的继承无效

 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Range{
    int min() default 5;
    int max() default 10;
}

public class AnnotationPoc {
    @Range
    public String address;

    public void fieldCheck() throws java.lang.IllegalAccessException, IllegalArgumentException{
        // 这个例子稍微有点不恰当：正常来讲应该先检查值，如果符合要求则再复制，而这里是赋值后再利用注解检测，哈哈哈
        System.out.println("In the fieldCheck");
        // 遍历所有Field:
        for (Field field : this.getClass().getFields()) {
            // 获取Field定义的@Range:
            Range range = field.getAnnotation(Range.class);
            // 如果@Range存在:
            if (range != null){
                // 获取Field的值:
                Object value = field.get(this);
                System.out.println(value);
                if (value instanceof String){
                    String s = (String) value;
                    // 判断值是否满足@Range的min/max:
                    if (s.length() < range.min() || s.length() > range.max()){
                        throw new IllegalArgumentException("Invalid field: " + field.getName());
                    }
                }
            }

        }
    }

    AnnotationPoc(String address) throws Exception{
        this.address = address;
    }
}



// 测试 Repeatable 元注解
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Reports.class)
@Target(ElementType.TYPE)
@interface Report {
    int type() default 0;
    String level() default "info";
    String value() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Reports {
    Report[] value();
}

@Report(type=1, level="debug")
@Report(type=2, level="warning")
@Report(type=3, level="warning")
class AnnotationRep {
    public void touchAnnoRep(){
        System.out.println("In the AnnotationRep");
        System.out.println(this.getClass().isAnnotationPresent(Reports.class));
        Reports reports = this.getClass().getAnnotation(Reports.class);
        System.out.println(reports);
    }
}



