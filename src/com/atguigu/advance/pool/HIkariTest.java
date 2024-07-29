package com.atguigu.advance.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class HIkariTest {
    @Test
    public void testResourcesHikari()throws Exception{
        //1.创建Properties集合，用于存储外部配置文件的key和value值。
        Properties properties = new Properties();

        //2.读取外部配置文件，获取输入流，加载到Properties集合里。
        InputStream inputStream = HIkariTest.class.getClassLoader().getResourceAsStream("hikari.properties");
        properties.load(inputStream);

        // 3.创建Hikari连接池配置对象，将Properties集合传进去
        HikariConfig hikariConfig = new HikariConfig(properties);

        // 4. 基于Hikari配置对象，构建连接池
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        // 5. 获取连接
        Connection connection = hikariDataSource.getConnection();
        System.out.println("connection = " + connection);

        //6.回收连接
        connection.close();
    }
}

