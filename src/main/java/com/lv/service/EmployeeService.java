package com.lv.service;

import com.lv.repository.EmployeeRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/19 9:52
 * @Description 一般情况都在service层添加事物
 **/
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepostory employeeeRepostory;

    @Transactional
    public void update(Integer id, Integer age) {

        employeeeRepostory.update(id,age);

    }
}
