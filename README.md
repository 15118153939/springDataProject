# springDataProject
springData jpa 相关
1：开发环境搭建
添加maven 依赖
```
<!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.16.18</version>
      <scope>provided</scope>
    </dependency>

    <!--MySQL Driver-->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.38</version>
    </dependency>

    <!--junit-->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.10</version>
    </dependency>

    <!--spring-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>4.3.5.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>4.3.5.RELEASE</version>
    </dependency>

    <!--spring data jpa-->
    <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-jpa</artifactId>
      <version>1.8.0.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-entitymanager</artifactId>
      <version>4.3.6.Final</version>
    </dependency>
```
2:配置spring
```
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:jpa="http://www.springframework.org/schema/data/jpa"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa-1.3.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">


    <!--1:配置数据源-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="username" value="root"/>
        <property name="password" value="Mingliang520"/>
        <property name="url" value="jdbc:mysql:///spring_data?useSSL=false"/>
    </bean>

    <!--2 配置EntityManagerFactory-->
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="jpaVendorAdapter">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"/>
        </property>

        <property name="packagesToScan" value="com.lv"/>

        <property name="jpaProperties">
            <props>
                <prop key="hibernate.ejb.naming_strategy">org.hibernate.cfg.ImprovedNamingStrategy</prop>
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop>
                <prop key="hibernate.show_sql">true</prop>
                <prop key="hibernate.format_sql">true</prop>
                <prop key="hibernate.hbm2ddl.auto">update</prop>
            </props>
        </property>
    </bean>


    <!--3 配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>

    <!--4 配置支持注解的事务-->
    <tx:annotation-driven transaction-manager="transactionManager"/>

    <!--5 配置spring data-->
    <jpa:repositories base-package="com.lv" entity-manager-factory-ref="entityManagerFactory"/>
    <context:component-scan base-package="com.lv"/>
    
</beans>
```

编写实体类：
```
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

```
属性@Column详解
```
name属性定义了被标注字段在数据库表中所对应字段的名称； 
unique
unique属性表示该字段是否为唯一标识，默认为false。如果表中有一个字段需要唯一标识，则既可以使用该标记，也可以使用@Table标记中的@UniqueConstraint。
nullable
nullable属性表示该字段是否可以为null值，默认为true。 
insertable
insertable属性表示在使用“INSERT”脚本插入数据时，是否需要插入该字段的值。 
updatable
updatable属性表示在使用“UPDATE”脚本插入数据时，是否需要更新该字段的值。insertable和updatable属性一般多用于只读的属性，例如主键和外键等。这些字段的值通常是自动生成的。
columnDefinition
columnDefinition属性表示创建表时，该字段创建的SQL语句，一般用于通过Entity生成表定义时使用。（也就是说，如果DB中表已经建好，该属性没有必要使用。）
table
table属性定义了包含当前字段的表名。 
length
length属性表示字段的长度，当字段的类型为varchar时，该属性才有效，默认为255个字符。 
precision和scale
precision属性和scale属性表示精度，当字段类型为double时，precision表示数值的总长度，scale表示小数点所占的位数。 


```
编写：Repository 接口
继承方式：
```
interface EmployeeRepostory extends Repository<Employee,Integer> {
   Employee findByName(String name);
   }
```
注解方式：
```
@RepositoryDefinition(domainClass = Employee.class, idClass = Integer.class)
public interface EmployeeRepostory {

}
```
整体如下：
```
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

```

查询方法规则：防范定义规则和使用

弊端：
```
1)：方法名讳比较长，约定大于配置
2)：对于一些复炸的查询，是很难实现
```
@Query
在Repository 方法中使用，不需要遵循查询方法命名规范
只需将@Query定义在Repository中的方法之上即可
命名参数及索引参数的使用
本地查询
```
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


```





