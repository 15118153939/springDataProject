package com.lv;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/18 10:09
 * @Description
 * 测试配置是否成功咯
 **/
public class DataSourceTest  {

    private ApplicationContext context =null;

    @Before
    public void setUp(){
        context = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("setup");
    }
    @After
    public void setDown(){
        context = null;
        System.out.println("setdown");
    }

    @Test
    public void testDataSource(){
       DataSource dataSource = (DataSource) context.getBean("dataSource");
        System.out.println("datasource");
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void testjdbcTemplate(){
       JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
        System.out.println("jdbcTemplate");
        Assert.assertNotNull(jdbcTemplate);
    }


}
