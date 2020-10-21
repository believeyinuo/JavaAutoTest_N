package main.com.java.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JDBCUtil {
    public static Properties properties = new Properties();
    static {
        System.out.println("开始解析properties文件");
        InputStream iStream;
        try {
            iStream = new FileInputStream(new File("src/test/resources/jdbc.properties"));
            properties.load(iStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据 sql 查询表数据，并以 map 返回，key 为字段名，value 为字段值
     * @param sql  要执行查询语句
     * @param values   条件字段的值
     * @return
     */
    public static Map<String, Object> query(String sql, Object ... values) {
        //1、根据连接信息，获得数据库连接（连上数据库）
        Map<String, Object> columnLabelAndValues = null;
        try {
            //从 properties 取url、user、password
            Connection connection = getConnection();
            //2、获取preparedStatement对象（此类型的对象有提供数据库操作方法）
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //3、设置条件字段的值
            for (int i = 0; i < values.length; i ++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            //4、调用查询方法，执行查询，返回ResultSet（结果集）
            ResultSet resultSet = preparedStatement.executeQuery();
            //获取查询相关的信息
            ResultSetMetaData metaData = resultSet.getMetaData();
            //得到查询字段的数目
            int columnCount = metaData.getColumnCount();
            //5、从结果集取查询数据
            columnLabelAndValues = new HashMap<>();
            while (resultSet.next()) {
                //循环取出每个查询字段的数据
                for (int i = 1; i <= columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i);
                    String columnValue = resultSet.getObject(columnLabel).toString();
                    System.out.println(columnValue);
                    columnLabelAndValues.put(columnLabel, columnValue);
                }
            }
        }catch (Exception e) {

        }
        return columnLabelAndValues;
    }

    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }
}
