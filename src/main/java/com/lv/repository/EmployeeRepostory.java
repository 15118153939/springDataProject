package com.lv.repository;

import com.lv.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author lvmingliang_glut@163.com
 * @Date 2018/1/18 11:53
 * @Description
 **/

/**
 * 可以注解方式
 */
@RepositoryDefinition(domainClass = Employee.class, idClass = Integer.class)
public interface EmployeeRepostory {
    Employee findByName(String name);

    //    where name like ?% and age<?
    List<Employee> findByNameStartingWithAndAgeLessThan(String name, Integer age);

    //    where name like %? and age>?
    List<Employee> findByNameEndingWithAndAgeGreaterThan(String name, Integer age);

    //    whre name in(?,?,..) or age <?
    List<Employee> findByNameInOrAgeLessThan(List<String> names, Integer age);

    //    whre name in(?,?,..) and age <?
    List<Employee> findByNameInAndAgeLessThan(List<String> names, Integer age);


    /**
     * from 类名
     *
     * @return
     */
    @Query("select o from Employee o where id=(select max(id) from Employee t1)")
    Employee getEmployeeByMaxId();


    /**
     * 根据参数查询
     */
    @Query("select o from Employee o where o.name=?1 and o.age=?2")
    List<Employee> queryParamBynameAndAge(String name, Integer age);

    @Query("select o from Employee  o where o.name=:name and o.age=:age")
    List<Employee> queryParams(@Param("name") String name, @Param("age") Integer age);


    @Query("select o from Employee o where o.name like %?1%")
    List<Employee> queryLike(String name);

    /**
     * 写法不一样
     *
     * @param name
     * @return List
     */
    @Query("select o from Employee o where o.name like %:name%")
    List<Employee> queryLikes(@Param("name") String name);


    /***
     *
     */
    @Query(nativeQuery = true, value = "SELECT count(1) FROM employee")
    Long getCount();


    @Modifying
    @Query("update Employee o set o.age=?2 where o.id=?1")
    void update(Integer id, Integer age);
}

/**
 * 可以继承
 */

// interface EmployeeRepostory extends Repository<Employee,Integer> {
//     Employee findByName(String name);
//
//}


