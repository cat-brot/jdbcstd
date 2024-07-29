package com.atguigu.advance.pool;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import javax.sql.DataSource;

public class DruidTest {
    @Test
    public void testResourcesDruid() throws Exception {

        //1.创建Properties集合，用于存储外部配置文件的key和value值。
        Properties properties = new Properties();

        //2.读取外部配置文件，获取输入流，加载到Properties集合里。
        InputStream inputStream = DruidTest.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(inputStream);

        //3.基于Properties集合构建DruidDataSource连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        //4.通过连接池获取连接对象
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        //5.开发CRUD

        //6.回收连接
        connection.close();
    }
}
