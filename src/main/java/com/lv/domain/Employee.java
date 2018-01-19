package com.lv.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/18 11:36
 * @Description
 * 雇员: 先开发实体类==》自动生成数据表
 *
 **/
@Data
@Entity
public class Employee {

    @GeneratedValue
    @Id
    private Integer id;

    @Column(length = 32)
    private String name;

    @Column(nullable = false)
    private Integer age;



    public Employee(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Employee() {
    }
}
