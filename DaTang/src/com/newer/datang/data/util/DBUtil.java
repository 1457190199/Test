package com.newer.datang.data.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * 获取连接池
 * @author zxl
 *
 */
public class DBUtil {
	private static DataSource dataSource = null;

	//获取连接
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	static{
		Properties properties = new Properties();
		InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("dbcp.properties");
		try {
			properties.load(in);
			dataSource  =  BasicDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//关闭Connection ResultSet Statement
	public static void closeAll
	(Connection conn,PreparedStatement pstmt, 
			Statement stmt, ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
