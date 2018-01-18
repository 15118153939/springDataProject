package com.lv.dao;

import com.lv.domain.Student;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/18 9:19
 * @Description
 **/
@Component
public interface StudentDAO {
    /**
     *
     *查询所有学生
     */
    public List<Student> query();

    public int save(Student student);
}
