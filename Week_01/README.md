# Week01-1 JVM作业

<a name="yrXh7"></a>
# 1.第二题
<a name="1KUIR"></a>
### java代码
```java
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoad extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = new byte[0];
        try {
            bytes = getBytes("/Users/jvm/work/Hello/Hello.xlass");
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte)(255 - bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    private static byte[] getBytes(String fileName) throws IOException {
        File file = new File(fileName);
        int length = (int)file.length();
        byte[] bytes = new byte[length];
        new FileInputStream(file).read(bytes);
        return bytes;
    }

    public static void main(String[] args) {
        try {
            Class<?> aClass = new MyClassLoad().findClass("Hello");
            Object hello = aClass.newInstance();
            Method method = aClass.getMethod("hello");
            method.invoke(hello);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
```
<a name="m9oGm"></a>
### 执行结果
![image.png](https://img2020.cnblogs.com/blog/1135109/202010/1135109-20201018210103219-621449306.png)<br />

<a name="KkyyE"></a>
# 2.第三题
![image.png](https://img2020.cnblogs.com/blog/1135109/202010/1135109-20201018210137073-603086409.png)<br />

> 题目

**Week01 作业题目（周四）：**

1. （选做）、自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码，有问题群里讨论。<br />
1. （必做）、自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。<br />
1. （必做）、画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系。<br />
1. （选做）、检查一下自己维护的业务系统的 JVM 参数配置，用 jstat 和 jstack、jmap 查看一下详情，并且自己独立分析一下大概情况，思考有没有不合理的地方，如何改进。<br />

注意：如果没有线上系统，可以自己 run 一个 web/java 项目。<br />
<br />
