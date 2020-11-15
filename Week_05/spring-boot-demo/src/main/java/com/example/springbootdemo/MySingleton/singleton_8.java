package com.example.springbootdemo.MySingleton;

/**
 * 枚举，线程安全
 * 枚举保证构造函数只被执行一次
 */
public class singleton_8 {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> System.out.println(MyEnum.conn.getInstance().hashCode())).start();
        }

    }
}

enum MyEnum {
    conn;
    private  MyObject_8 myObject;

    private MyEnum(){

        System.out.println("MyEnum 构造");
        if (null == myObject) {
            myObject=new MyObject_8();
        }
    }
    public MyObject_8 getInstance() {
        System.out.println("getInstance");
        return myObject;
    }
}

class MyObject_8 {
    public MyObject_8() {
        System.out.println("MyObject_8 构造");
    }
}
