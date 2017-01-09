package cn.rain.usermanage.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class C3p0Utils {

	// 连接池就定义 并且加载配置完毕
	// 命名配置
	// private static DataSource dataSource=new ComboPooledDataSource("itcast");

	// 默认配置
	private static DataSource dataSource = new ComboPooledDataSource();

	// c3p0+DBUtils
	// DBUtils使用时，需要获取dataSource对象
	public static DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 获取链接方法
	 */
	public static Connection getConnection() {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return con;
	}

	public static void main(String[] args) throws SQLException {
		for (int i = 0; i < 40; i++) {
			Connection con = C3p0Utils.getConnection();
			System.out.println("获取到的" + con);
			// 不会关闭con对象，C3P0已经帮我去增强了Connection close方法
			con.close();

		}
	}
}
