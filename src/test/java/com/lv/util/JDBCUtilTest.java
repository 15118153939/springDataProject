package com.lv.util;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/17 19:29
 * @Description
 **/
public class JDBCUtilTest {
    @Test
    public void getConnection() throws Exception {

        Connection con =JDBCUtil.getConnection();

        Assert.assertNotNull(con);
    }

}