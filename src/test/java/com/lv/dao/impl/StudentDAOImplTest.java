package com.lv.dao.impl;

import com.lv.dao.StudentDAO;
import com.lv.domain.Student;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/18 9:30
 * @Description
 **/
public class StudentDAOImplTest {
    @Test
    public void query() throws Exception {

        StudentDAO studentDAO = new StudentDAOImpl();
        List<Student> studentList = studentDAO.query();

        for (Student s:studentList){
            System.out.println(s);
        }
    }

    @Test
    public void save() throws Exception{
        StudentDAO studentDAO = new StudentDAOImpl();
        Student student = new Student();
        student.setName("大宝");
        student.setAge(18);
        studentDAO.save(student);
    }

}