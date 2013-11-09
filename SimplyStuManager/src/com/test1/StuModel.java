/**
 *  �����ҵ�һ��stu���ģ��
 *  ���԰Ѷ�stu��ĸ��ֲ�����װ����ģ����
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

	//rowData�������������
	//columnNames�����������
			
	Vector rowData, columnNames;
			
	//����������ݿ�����Ҫ�Ķ���
	PreparedStatement ps = null;
	Connection ct = null;
	ResultSet rs = null;
	
	public void init(String sql){
		
		if(sql.equals("")){
			sql="select * from stu";
		}
		
		//�м�Ĵ���
		columnNames = new Vector();
		//��������
		columnNames.add("ѧ��");
		columnNames.add("����");
		columnNames.add("�Ա�");
		columnNames.add("����");
		columnNames.add("����");
		columnNames.add("ϵ��");
				
		rowData = new Vector();
				
		try {
					
			//1,��������
			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	
			//2,�õ�����
			ct=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "scott", "tiger");
//			ct = DriverManager.getConnection("jdbc:odbc:testjackge","scott","tiger");
			//3,����sql����Statement/PreparedStatement/CallableStatement)
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next()){
				//rowData���Դ�Ŷ���
				Vector hang = new Vector();
				hang.add(rs.getString(1));
				hang.add(rs.getString(2));
				hang.add(rs.getString(3));
				hang.add(rs.getInt(4));
				hang.add(rs.getString(5));
				hang.add(rs.getString(6));
						
				//���뵽rowData
				rowData.add(hang);

			}
					
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر���Դ���ȿ��ĺ�ر�
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs=null;		//ʹ����������
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
		//�����û������sql�������������
	}
	
	//ͨ�����ݵ�sql������������ģ��
	public StuModel(String sql){
		this.init(sql);
	}
	
	//��һ�����캯�������ڳ�ʼ�����ǵ�����ģ��
	public StuModel(){
		this.init("");
	}
	
	//�õ����ж�����
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
//		System.out.println("getRowCount() ���Զ������ˣ�");
		
		return this.rowData.size();
	}

	//�õ����ж�����
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
//		System.out.println("getColumnCount() ���Զ������ˣ�");
		
		return this.columnNames.size();
	}

	//�õ�ĳ��ĳ�е�����
	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
//		System.out.println("getValueAt() ���Զ������ˣ�");
		
		return ((Vector)this.rowData.get(row)).get(col);
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String)this.columnNames.get(column);
	}

}
