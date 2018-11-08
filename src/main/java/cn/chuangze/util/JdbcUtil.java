package cn.chuangze.util;
import java.sql.*;
import java.io.*;
import java.util.*;
/**
 * @author LiZG
 */
public class JdbcUtil {
	private static Properties env;
	static {
		try {
			env = new Properties();
			InputStream is = JdbcUtil.class.getResourceAsStream("/properties/jdbc.properties");
			env.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		} 
	}
	public static final ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	public static Connection getConnection() throws Exception {
		Connection conn = tl.get();
		if(conn == null) {
			Class.forName(env.getProperty("driver"));
			conn = DriverManager.getConnection(
				env.getProperty("url"), 
				env.getProperty("username"), 
				env.getProperty("password"));
			tl.set(conn);
		}
		return conn;
	}
	
	public static Connection getConnection(String dbName) throws Exception{
		Connection conn = tl.get();
		
		
		if(conn == null){
			conn.close();
			tl.remove();
		}
		Connection connection = null;
		String url = "jdbc:mysql://192.168.0.251:3306/" + dbName;

		Class.forName(env.getProperty("driver"));
		connection = DriverManager.getConnection(
			url,
			env.getProperty("username"),
			env.getProperty("password"));
		tl.set(connection);
		
		return connection;
	}
	public static Connection getConnection(String dbName,String ip) throws Exception{
		Connection conn = tl.get();
		
		
		if(conn != null){
			conn.close();
			tl.remove();
		}
		Connection connection = null;
		String url = "jdbc:mysql://" + ip + ":3306/" + dbName;

		Class.forName(env.getProperty("driver"));
		connection = DriverManager.getConnection(
			url,
			env.getProperty("username"),
			env.getProperty("password"));
		tl.set(connection);

		return connection;
	}
	
	public static void release(ResultSet rs,Statement stm,Connection conn) {
		if(rs != null){
			try {rs.close();}catch(Exception e) {}
		}
		if(stm != null){
			try {stm.close();}catch(Exception e) {}
		}
		if(conn != null) {
			try {conn.close();} catch (Exception e) {}
		}
	}
}
