/**
 *  这是我的一个stu表的模型
 *  可以把对stu表的各种操作封装到该模型中
 */

package com.test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class StuModel extends AbstractTableModel {

	//rowData用来存放行数据
	//columnNames用来存放列名
			
	Vector rowData, columnNames;
			
	//定义操作数据库所需要的东西
	PreparedStatement ps = null;
	Connection ct = null;
	ResultSet rs = null;
	
	public void init(String sql){
		
		if(sql.equals("")){
			sql="select * from stu";
		}
		
		//中间的处理
		columnNames = new Vector();
		//设置列名
		columnNames.add("学号");
		columnNames.add("名字");
		columnNames.add("性别");
		columnNames.add("年龄");
		columnNames.add("籍贯");
		columnNames.add("系别");
				
		rowData = new Vector();
				
		try {
					
			//1,加载驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	
			//2,得到连接
			ct=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "scott", "tiger");
//			ct = DriverManager.getConnection("jdbc:odbc:testjackge","scott","tiger");
			//3,创建sql对象（Statement/PreparedStatement/CallableStatement)
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next()){
				//rowData可以存放多行
				Vector hang = new Vector();
				hang.add(rs.getString(1));
				hang.add(rs.getString(2));
				hang.add(rs.getString(3));
				hang.add(rs.getInt(4));
				hang.add(rs.getString(5));
				hang.add(rs.getString(6));
						
				//加入到rowData
				rowData.add(hang);

			}
					
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭资源，先开的后关闭
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs=null;		//使用垃圾回收
			}

			if(ct != null){
				try {
					ct.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						ct = null;
			}
		}
	}
	
	public void addStu(String sql){
		//根据用户输入的sql语句完成添加任务
	}
	
	//通过传递的sql语句来获得数据模型
	public StuModel(String sql){
		this.init(sql);
	}
	
	//做一个构造函数，用于初始化我们的数据模型
	public StuModel(){
		this.init("");
	}
	
	//得到共有多少行
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
//		System.out.println("getRowCount() 被自动调用了！");
		
		return this.rowData.size();
	}

	//得到共有多少列
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
//		System.out.println("getColumnCount() 被自动调用了！");
		
		return this.columnNames.size();
	}

	//得到某行某列的数据
	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
//		System.out.println("getValueAt() 被自动调用了！");
		
		return ((Vector)this.rowData.get(row)).get(col);
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String)this.columnNames.get(column);
	}

}
