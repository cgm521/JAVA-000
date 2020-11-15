package com.example.springbootdemo.MySingleton;

/**
 * @auth cgm
 * @date 2017/12/9 18:46
 * 饿汉模式 线程安全
 */
public class singleton_1 {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> System.out.println(MyObject.getInstance().hashCode())).start();
        }
    }

}
 class MyObject{
    private static MyObject myObject = new MyObject();

    public MyObject() {
        System.out.println("我是构造函数");
    }

    public static MyObject getInstance() {
        return myObject;
    }
}
