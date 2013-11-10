/**
 *  �����ҵ�һ��stu���ģ��
 *  ���԰Ѷ�stu��ĸ��ֲ�����װ����ģ����
 */

package com.test2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class StuModel extends AbstractTableModel {

	//rowData�������������
	//columnNames�����������
			
	Vector rowData, columnNames;
	
	//��ѧ����Ϣ�Ĳ���������ɾ���ģ�
	public boolean updStu(String sql, String []paras){
		//����SqlHelper��������򲢷��Բ����ǣ����԰�SqlHelper����static��
		SqlHelper sqlHelper = new SqlHelper();
		return sqlHelper.updExecute(sql, paras);
	}
	
	//�ò�ѯ�ı��ʾ��ǳ�ʼ��
	public void queryStu(String sql, String[] paras){
		
		SqlHelper sqlHelper = null;
		
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
					
			sqlHelper = new SqlHelper();
			ResultSet rs = sqlHelper.queryExecute(sql, paras);
			
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
			sqlHelper.close();
		}
	}

	
//	//��һ�����캯�������ڳ�ʼ�����ǵ�����ģ��
//	public StuModel(){
//
//	}
	
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
