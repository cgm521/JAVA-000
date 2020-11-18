package com.demo;

import com.demo.autowired.AutowiredService;
import com.demo.javadisplay.BeanConfig;
import com.demo.javadisplay.JavaDisplayService;
import com.demo.xmldisplay.XmlDisplayService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/18 9:58 下午
 */

public class BeanDemo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        System.out.println("-----------XML显示引用-----------");
        // 在xml中通过p标签或者property注入到目标类中
        XmlDisplayService xmlDisplayService = (XmlDisplayService)context.getBean("xmlDisplayService");
        xmlDisplayService.sayHi();

        System.out.println("----------------------");
        System.out.println("-----------JAVA显示引用-----------");
        // 在配置类中通过@Bean注解获取bean，通过显示的set去注入
        BeanConfig beanConfig = (BeanConfig) context.getBean("beanConfig");
        JavaDisplayService javaDisplayService = beanConfig.getJavaDisplayService();
        javaDisplayService.sayHi();

        System.out.println("----------------------");
        System.out.println("-----------隐式引用，自动装配-----------");
        // component-scan开启自动扫描包路径，通过@Autowired、@Resource注入
        AutowiredService autowiredService = (AutowiredService) context.getBean("autowiredService");
        autowiredService.sayHi();
    }
}
