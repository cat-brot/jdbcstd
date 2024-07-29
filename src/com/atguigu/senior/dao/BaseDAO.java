package com.atguigu.senior.dao;

import com.atguigu.senior.util.JDBCUtilV2;

import javax.xml.transform.Result;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO {
    public int executeUpdate(String sql, Object... params ) throws Exception{
        //使用JDBCV2进行数据库连接
        Connection connection = JDBCUtilV2.getConnection();
        //进行sql语句的预编译
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //对占位符进行赋值，执行sql，接受返回结果
        if(params!=null && params.length > 0){
            for(int i = 0;i<params.length;i++){
                preparedStatement.setObject(i+1,params[i]);
            }
        }

        //拿出受影响的行数
        int row = preparedStatement.executeUpdate();
        //处理结果
        JDBCUtilV2.release();
        preparedStatement.close();
        return row;


    }
    //通用的增删改方法
    public <T> List<T> executeQuery(Class<T> clazz,String sql,Object... params) throws Exception {
        //获取连接
        Connection connection = JDBCUtilV2.getConnection();
        //预编译sql
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //设置占位值
        if(params!=null && params.length > 0){
            for(int i = 0;i < params.length;i++){
                preparedStatement.setObject(i+1,params[i]);
            }
        }
        //执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        //直接通过result...来获取每个列的数量以及名称
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();//利用metadata的方法将列的数量取出并赋值给colu.....
        //处理结果
        List<T> list = new ArrayList<>();
        while (resultSet.next()){
            T t = clazz.newInstance();//利用反射来创建一个对象，反射的概念暂时不会
            //循环遍历，查看有多少列
            for(int i = 1;i <= columnCount ;i++){
                Object value = resultSet.getObject(i);
                //通过resultSet的getObject方法来为value进行赋值，由于不知道类型，只能通过下标来赋值。
                //获取到的value，就是t这个对象某一个“属性”
                String fieldName = metaData.getColumnLabel(i);
                //通过类对象和fieldName获取要封装对象的属性
                Field field = clazz.getDeclaredField(fieldName);
                //突破封装的private
                field.setAccessible(true);
                field.set(t,value);
            }
            list.add(t);
        }
        resultSet.close();
        preparedStatement.close();
        JDBCUtilV2.release();
        return list;
    }
    //单个数据的方法
    public <T> T executeQueryBean(Class<T> clazz,String sql,Object... params) throws Exception{
        List<T> list = this.executeQuery(clazz,sql,params);
        if(list == null || list.isEmpty()){
            return null;
        }
        return list.getFirst();
    }
    }


