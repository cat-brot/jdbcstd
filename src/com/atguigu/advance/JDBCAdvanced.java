package com.atguigu.advance;
import java.sql.*;

import com.atguigu.advance.pojo.Employee;
import org.junit.Test;

import java.sql.SQLException;

public class JDBCAdvanced {
    @Test
    public void querySingleRow() throws SQLException {
        //1.注册驱动
//        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取数据库连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root","123456");

        //3.创建PreparedStatement对象，并预编译SQL语句，使用?占位符
        PreparedStatement preparedStatement = connection.prepareStatement("select emp_id,emp_name,emp_salary,emp_age from t_emp where emp_id = ?");

        //4.为占位符赋值，索引从1开始，执行SQL语句，获取结果
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        //预先创建实体类变量
        Employee employee = null;
        //5.处理结果
        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            Double empSalary = Double.valueOf(resultSet.getString("emp_salary"));
            int empAge = resultSet.getInt("emp_age");
            //当结果集中有数据，再进行对象的创建
            employee = new Employee(empId,empName,empSalary,empAge);
        }

        System.out.println("employee = " + employee);

        //6.释放资源(先开后关原则)
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }
    @Test
    public void testReturnPK() throws SQLException {
        //1.注册驱动
        //        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取数据库连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "123456");

        //3.创建preparedStatement对象，传入需要主键回显参数Statement.RETURN_GENERATED_KEYS
        PreparedStatement preparedStatement = connection.prepareStatement("insert into t_emp (emp_name, emp_salary, emp_age)values  (?, ?,?)",Statement.RETURN_GENERATED_KEYS);

        //4.编写SQL语句并执行，获取结果
        Employee employee = new Employee(null,"rose",666.66,28);
        preparedStatement.setString(1,employee.getEmpName());
        preparedStatement.setDouble(2,employee.getEmpSalary());
        preparedStatement.setDouble(3,employee.getEmpAge());
        int result = preparedStatement.executeUpdate();

        //5.处理结果
        if(result>0){
            System.out.println("添加成功");
        }else{
            System.out.println("添加失败");
        }

        //6.获取生成的主键列值，返回的是resultSet，在结果集中获取主键列值
        ResultSet resultSet = preparedStatement.getGeneratedKeys();//使用get
        if (resultSet.next()){
            int empId = resultSet.getInt(1);
            employee.setEmpId(empId);
        }

        System.out.println(employee.toString());

        //7.释放资源(先开后关原则)
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }
    @Test
    public void testBatch() throws Exception {
        //1.注册驱动
        //        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?rewriteBatchedStatements=true", "root", "123456");

        //3.编写SQL语句
            /*
                注意：1、必须在连接数据库的URL后面追加?rewriteBatchedStatements=true，允许批量操作
                    2、新增SQL必须用values。且语句最后不要追加;结束，为什么不要追加？？？好像其他的代码都没有追加;结束
                    3、调用addBatch()方法，将SQL语句进行批量添加操作，addBatch方法？？用途就是批量增加，但是他是怎么实现的？
                    4、统一执行批量操作，调用executeBatch()
             */
        String sql = "insert into t_emp (emp_name,emp_salary,emp_age) values (?,?,?)";

        //4.创建预编译的PreparedStatement，传入SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //获取当前行代码执行的时间。毫秒值
        long start = System.currentTimeMillis();
        for(int i = 0;i<10000;i++){
            //5.为占位符赋值
            preparedStatement.setString(1, "marry"+i);
            preparedStatement.setDouble(2, 100.0+i);
            preparedStatement.setInt(3, 20+i);

            preparedStatement.addBatch();//每个结果加入到一个批处理中
        }

        //执行批量操作
        preparedStatement.executeBatch();//执行

        long end = System.currentTimeMillis();

        System.out.println("消耗时间："+(end - start));

        preparedStatement.close();
        connection.close();
    }
}
