package com.lv.repository;

import com.lv.domain.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/18 11:55
 * @Description
 **/
// @Transactional
public class EmployeeRepostoryTest {

    private ApplicationContext context = null;
    EmployeeRepostory repostory = null;

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("beans-new.xml");
        repostory = context.getBean(EmployeeRepostory.class);
        System.out.println("setup");

    }

    @After
    public void setDown() {
        context = null;
        System.out.println("setdown");
    }



    @Test
    public void testGetCount() {
        System.out.println(repostory.getCount());
    }


    @Test
    public void queryLikes() {
        List<Employee> list = repostory.queryLikes("test1");
        for (Employee e : list) {
            System.out.println(e);
        }
    }

    @Test
    public void queryLike() {
        List<Employee> list = repostory.queryLike("test");
        for (Employee e : list) {
            System.out.println(e);
        }
    }

    @Test
    public void queryParamBynameAndAge() {
        List<Employee> list = repostory.queryParamBynameAndAge("test1", 18);

        for (Employee e : list) {
            System.out.println(e);
        }
    }

    @Test
    public void queryParams() {
        List<Employee> list = repostory.queryParams("test1", 18);

        for (Employee e : list) {
            System.out.println(e);
        }
    }

    @Test
    public void getEmployeeId() {
        Employee employee = repostory.getEmployeeByMaxId();
        System.out.println(employee);
    }


    @Test
    public void findByName() throws Exception {
        Employee e = repostory.findByName("test1");
        System.out.println(e);
    }

    @Test
    public void findByNameStartingWithAndAgeLessThan() {
        List<Employee> list = repostory.findByNameStartingWithAndAgeLessThan("test", 22);

        for (Employee e : list) {
            System.out.println(e);
        }
    }

    @Test
    public void findByNameEndingWithAndAgeGreaterThan() {
        List<Employee> list = repostory.findByNameEndingWithAndAgeGreaterThan("6", 17);
        for (Employee e : list) {
            System.out.println(e);
        }
    }

    @Test
    public void testfindByNameInAndAgeLessThan() {

        List tnams = new ArrayList();
        tnams.add("test3");
        tnams.add("test6");
        tnams.add("test8");

        List<Employee> list = repostory.findByNameInAndAgeLessThan(tnams, 17);
        for (Employee e : list) {
            System.out.println(e);
        }
    }

    /**
     * ctrl + shift +alt +j 选中一样的，可以重构变变量名
     */
    @Test
    public void testfindByNameInOrAgeLessThan() {
        List tnams = new ArrayList();
        tnams.add("test3");
        tnams.add("test6");
        tnams.add("test8");
        List<Employee> list = repostory.findByNameInOrAgeLessThan(tnams, 17);
        for (Employee e : list) {
            System.out.println(e);
        }
    }


    @Test
    public void save() {

        Employee employee = new Employee();
        employee.setName("");
        employee.setAge(18);

//        repostory.save(employee);


    }

//    @Test
//    public void findall(){
//        List<Employee> list =repostory.findAll();
//        for (Employee e: list){
//            System.out.println(e);
//        }
//    }
//
//    @Test
//    public void saveArr(){
//        for (int i = 0; i < 10; i++) {
//            Employee e = new Employee("test"+i,18+i%2);
//            repostory.save(e);
//        }
//    }

}