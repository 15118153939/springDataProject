package com.lv.repository;

import com.lv.domain.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/19 10:09
 * @Description
 **/
public interface EmployeeCrudRepostory extends CrudRepository<Employee,Integer> {
}
