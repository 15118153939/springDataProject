# springDataProject
本项目是学习springData
从：从传统方式访问数据库-到jpa
#### 第一种：jdbc原生态方式
##### 步骤1：

创建maven 项目
maven依赖
```
 <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.10</version>
      <scope>test</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
   <!--MySQL driver-->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.38</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.16.18</version>
      <scope>provided</scope>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
    <!--spring - jdbc -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>4.3.12.RELEASE</version>
    </dependency>

    <!--spring 上下文-->
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>4.3.13.RELEASE</version>
    </dependency>

```
#### 步骤2：sql 测试表，以及几条数据，并创建实体类
```
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

INSERT INTO `student` VALUES ('1', '张三', '18');
INSERT INTO `student` VALUES ('2', 'lisi', '28');
INSERT INTO `student` VALUES ('3', 'wangwu', '19');

```
db.properties 文件
```
jdbc.url=jdbc:mysql:///spring_data
jdbc.user=root
jdbc.password=Mingliang520
jdbc.driverClass=com.mysql.jdbc.Driver
```

Student类
```
package com.lv.domain;
import lombok.Data;
/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/17 19:45
 * @Description
 * 实体类
 **/
@Data
public class Student {
    /**
     * id
     */
    private int id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
}
```

#### 步骤3：创建JDBCUtil 
```
public class JDBCUtil {
    /**
     * 获取Connection
     *
     * @return 所获得到的JDBC的Connection
     */
    public static Connection getConnection() throws Exception {
//不推荐硬编码方式
//       jdbc:mysql://ip:3306/数据库名
//       本地可以直接///
//       String url ="jdbc:mysql:///spring_data";
//       String user="root";
//       String password="Mingliang520";
//       String driverClass = "com.mysql.jdbc.Driver";

//        最佳实践，配置
        InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");

        Properties properties = new Properties();
        properties.load(inputStream);
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");
        String driverClass = properties.getProperty("jdbc.driverClass");

        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);

        return connection;
    }

    /**
     * 释放数据库相关资源
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void release(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```
#### 步骤5：dao开发

接口
```
public interface StudentDAO {
    /**
     *
     *查询所有学生
     */
    public List<Student> query();

    public int save(Student student);
}
```
实现类
```
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


```
测试
```
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
```


第二种：spring自带的模板方式 
beans.xml
```
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">


    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="username" value="root"/>
        <property name="password" value="Mingliang520"/>
        <property name="url" value="jdbc:mysql:///spring_data"/>
    </bean>

    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <bean id="studentDAO" class="com.lv.dao.impl.StudentDAOSpringJdbcImpl">
        <property name="jdbcTemplate" ref="jdbcTemplate"/>
    </bean>


</beans>
```
StudentDAOSpringJdbcImpl 
```

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
 * @Description StudentDAO访问杰克实现类，通过springJDBC的方式操作
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


```

测试：
```
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
```
传统方式开发的弊端：
大量编码，dao 有重复代码，分页还需要重新封装

