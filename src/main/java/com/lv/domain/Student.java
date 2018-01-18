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
