package com.lv.dao.impl;

import com.lv.dao.StudentDAO;
import com.lv.domain.Student;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/18 10:21
 * @Description StudentDAO访问杰克实现类，通过springJDBC始的JDBC方式操作
 **/
@Data
public class StudentDAOSpringJdbcImpl implements StudentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Student> query() {
        String sql = "select id,name,age from student";
      final   List<Student> studentList = new ArrayList<Student>();

        jdbcTemplate.query(sql, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");

                    Student student = new Student();
                    student.setId(id);
                    student.setName(name);
                    student.setAge(age);
                    studentList.add(student);
                }
            }
        });

        return studentList;
    }

    public int save(Student student) {

        String sql = "insert into student(name,age) values(?,?)";
       return jdbcTemplate.update(sql,new Object[]{student.getName(),student.getAge()});

    }
}
