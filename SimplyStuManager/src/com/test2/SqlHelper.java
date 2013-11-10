/**
 * ����һ�������ݿ���в�������
 */

package com.test2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SqlHelper {
	
	//����������ݿ�����Ҫ�Ķ���
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String user = "scott";
		String passwd = "tiger";
		
		//�ر����ݿ���Դ
		public void close(){
			//�ͷ����������Ӷ���
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
				//1,��������
				Class.forName(driver);
				//2,�õ�����
				ct = DriverManager.getConnection(url, user, passwd);
				
				ps = ct.prepareStatement(sql);

				rs = ps.executeQuery();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
			}
			return rs;
		}
		
		//��ѯ���ݿ�Ĳ���
		public ResultSet queryExecute(String sql, String[] paras){
			try{
				//1,��������
				Class.forName(driver);
				//2,�õ�����
				ct = DriverManager.getConnection(url, user, passwd);
				
				ps = ct.prepareStatement(sql);
				//��ps�� ? ��ֵ
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

		//�� ����ɾ���� ����һ��
		//����Ϣ�Ĳ���������ɾ���ģ�
		public boolean updExecute(String sql, String []paras){
			boolean b = true;
			//���û������Ӱ�ť�����Ӧ����
			//�������ݿ�
			
			//�������ݿ⣬�ж��û��Ƿ�Ϸ�
			try{
				//1,��������
				Class.forName(driver);
				//2,�õ�����
				ct = DriverManager.getConnection(url, user, passwd);
				
				ps = ct.prepareStatement(sql);
				
				//��ps�� ? ��ֵ
				for(int i=0; i<paras.length; i++){
					ps.setString(i+1, paras[i]);
				}
				
				//ִ�в���
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
