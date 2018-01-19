package com.lv.service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/19 9:57
 * @Description
 **/
public class EmployeeServiceTest {
    private ApplicationContext context =null;
    private EmployeeService service;
    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("beans-new.xml");
        service = context.getBean(EmployeeService.class);
        System.out.println("setup");

    }

    @After
    public void setDown() {
        context = null;
        System.out.println("setdown");
    }

    @Test
    public void update() throws Exception {

            service.update(1,44);

    }


}