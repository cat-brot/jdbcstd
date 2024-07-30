package com.atguigu.senior;

import com.atguigu.senior.dao.EmployeeDao;
import com.atguigu.senior.dao.impl.EmployeeDaoImpl;
import com.atguigu.senior.pojo.Employee;
import com.atguigu.senior.util.JDBCUtil;
import com.atguigu.senior.util.JDBCUtilV2;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class JDBCUtilTest {
    @Test
    public void testGetConnection(){
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);

        JDBCUtil.release(connection);
    }
    @Test
    public void JDBCUtil2(){

        Connection connection1 = JDBCUtilV2.getConnection();
        Connection connection2 = JDBCUtilV2.getConnection();
        Connection connection3 = JDBCUtilV2.getConnection();

        System.out.println(connection1);
        System.out.println(connection2);
        System.out.println(connection3);
    }


    @Test
    public void testEmployeeDao(){
          EmployeeDao employeeDao = new EmployeeDaoImpl();
//        List<Employee> employeeList = employeeDao.selectAll();
//        int sum = 0;
//        for(Employee employee : employeeList){
//            System.out.println("employee = " +employee);
//            sum++;
//        }
//        System.out.println(sum);

//        Employee employee = employeeDao.selectByEmpID(1);
//        System.out.println("employee = " + employee);
          Employee employee = new Employee(1,"andy", 656.0,38);
          int update = employeeDao.update(employee);
          System.out.println("update = " + update);

    }
}
