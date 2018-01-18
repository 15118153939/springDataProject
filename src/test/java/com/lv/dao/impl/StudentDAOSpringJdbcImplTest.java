package com.lv.dao.impl;

import com.lv.dao.StudentDAO;
import com.lv.domain.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/18 10:29
 * @Description
 * 测试
 **/

public class StudentDAOSpringJdbcImplTest {

    private ApplicationContext context = null;
    private StudentDAO dao = null;

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("setup");
    }

    @After
    public void setDown() {
        context = null;
        System.out.println("setdown");
    }

    @Test
    public void query() throws Exception {

        dao = (StudentDAO) context.getBean("studentDAO");
        List<Student> studentList = dao.query();
        for (Student s :studentList){
            System.out.println(s);
        }

    }

    @Test
    public void save() throws Exception {
        dao = (StudentDAO) context.getBean("studentDAO");

        Student student = new Student();
        student.setName("小桥流水人家");
        student.setAge(25);

        System.out.println(dao.save(student));
    }

}