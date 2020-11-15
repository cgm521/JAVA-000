package com.example.springbootdemo.MySingleton;

/**
 * 静态代码块 线程安全
 * 类加载器来保证，start代码块只会被执行一次
 */
public class singleton_7 {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> System.out.println(MyObject_7.getInstance().hashCode())).start();
        }
    }
}

class MyObject_7{
    private static MyObject_7 myObject;

    public MyObject_7() {
        System.out.println("gou");
    }
    static {
        System.out.println("static");
        myObject = new MyObject_7();
    }

    public static MyObject_7 getInstance() {
        System.out.println("getInstance");
        return myObject;
    }
}
