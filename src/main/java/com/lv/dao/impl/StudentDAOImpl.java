package com.lv.dao.impl;

import com.lv.dao.StudentDAO;
import com.lv.domain.Student;
import com.lv.util.JDBCUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/18 9:20
 * @Description StudentDAO访问杰克实现类，通过最原始的JDBC方式操作
 **/
@Repository
public class StudentDAOImpl implements StudentDAO {
    public List<Student> query() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select id,name,age from student";

        List<Student> studentList = new ArrayList<Student>();
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.release(resultSet, preparedStatement, connection);
        }

        return studentList;
    }

    public int save(Student student) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        String sql = "insert into student(name,age) values(?,?)";
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            result = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
