/**
 * 这是一个对数据库进行操作的类
 */

package com.test2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SqlHelper {
	
	//定义操作数据库所需要的东西
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String user = "scott";
		String passwd = "tiger";
		
		//关闭数据库资源
		public void close(){
			//释放语句对象，连接对象
			try{
				if(rs != null)
					rs.close();

				if(ct != null)
					ct.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		public ResultSet queryExecute(String sql){
			try{
				//1,加载驱动
				Class.forName(driver);
				//2,得到连接
				ct = DriverManager.getConnection(url, user, passwd);
				
				ps = ct.prepareStatement(sql);

				rs = ps.executeQuery();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
			}
			return rs;
		}
		
		//查询数据库的操作
		public ResultSet queryExecute(String sql, String[] paras){
			try{
				//1,加载驱动
				Class.forName(driver);
				//2,得到连接
				ct = DriverManager.getConnection(url, user, passwd);
				
				ps = ct.prepareStatement(sql);
				//给ps的 ? 赋值
				for(int i=0; i<paras.length; i++){
					ps.setString(i+1, paras[i]);
				}
				rs = ps.executeQuery();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
			}
			return rs;
		}

		//把 增、删、改 合在一起
		//对信息的操作（增、删、改）
		public boolean updExecute(String sql, String []paras){
			boolean b = true;
			//对用户点击添加按钮后的响应动作
			//连接数据库
			
			//连接数据库，判断用户是否合法
			try{
				//1,加载驱动
				Class.forName(driver);
				//2,得到连接
				ct = DriverManager.getConnection(url, user, passwd);
				
				ps = ct.prepareStatement(sql);
				
				//给ps的 ? 赋值
				for(int i=0; i<paras.length; i++){
					ps.setString(i+1, paras[i]);
				}
				
				//执行操作
				if(ps.executeUpdate() != 1){
					b = false;
				}
				
			}catch(Exception e1){
				b = false;
				e1.printStackTrace();
			}finally{
				this.close();
			}
			return b;
		}
	
}
