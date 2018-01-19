package com.lv;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/18 11:35
 * @Description
 **/
public class SpringDataTest {
    private ApplicationContext context =null;

    @Before
    public void setUp(){
        context = new ClassPathXmlApplicationContext("beans-new.xml");
        System.out.println("setup");
    }
    @After
    public void setDown(){
        context = null;
        System.out.println("setdown");
    }

    @Test
    public void testEntityManagerFactory(){

    }
}
