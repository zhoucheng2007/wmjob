package com.wm.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	public static Connection getConnection(){
		Connection conn=null;
			//加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//2.获取数据库连接
			conn=(Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/nanda", "root", "123456");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
}
