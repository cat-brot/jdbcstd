package com.atguigu.senior.dao;

import com.atguigu.senior.pojo.Employee;

import java.util.List;

public interface EmployeeDao {


    //用来对EmployeeDao表进行查询的所有操作的一个类

    List<Employee> selectAll();



    Employee selectByEmpID(Integer empID);

    int insert(Employee employee);
    //对employee进行插入操作


    int update(Employee employee);
    //对employee进行更新操作

    int delete(Integer empId);
    //对employee进行以empId为主键的删除操作

}
