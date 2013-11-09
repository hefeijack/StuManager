/**
 * ��� һ��mini���ѧ������ϵͳ
 * 1����ѯ����
 * 2�����һ��ѧ��
 */

package com.test1;

import javax.swing.*;

import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class StuManager extends JFrame implements ActionListener{

	//����һЩ�ؼ�
	JPanel jp1, jp2;
	JLabel jl1;
	JButton jb1, jb2, jb3, jb4;
	JTable jt;
	JScrollPane jsp;
	JTextField jtf;
	
	StuModel sm;
	
	//����������ݿ�����Ҫ�Ķ���
	PreparedStatement ps = null;
	Connection ct = null;
	ResultSet rs = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StuManager test3 = new StuManager();
	}

	//���캯��
	public StuManager(){
		jp1 = new JPanel();
		jtf = new JTextField(10);
		jb1 = new JButton("��ѯ");
		jb1.addActionListener(this);	//��  ����ѯ�� ��ť����ע�����
		
		jl1 = new JLabel("���������֣�");
		
		//�Ѹ����ؼ����뵽jp1
		jp1.add(jl1);
		jp1.add(jtf);
		jp1.add(jb1);
		
		jp2 = new JPanel();
		jb2 = new JButton("���");
		jb2.addActionListener(this);
		
		jb3 = new JButton("�޸�");
		jb3.addActionListener(this);
		
		jb4 = new JButton("ɾ��");
		jb4.addActionListener(this);
		
		//�Ѹ�����ť���뵽jp2
		jp2.add(jb2);
		jp2.add(jb3);
		jp2.add(jb4);
		
		//����һ������ģ�Ͷ���
		sm = new StuModel();
		
		
		//��ʼ��JTable
		jt = new JTable(sm);
		
		//��ʼ��jsp JScrollPane
		jsp = new JScrollPane(jt);
		
		//��jsp���뵽jframe
		this.add(jsp);
		
		this.add(jp1,"North");
		this.add(jp2,"South");
		
		this.setSize(400, 300);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//�ж����ĸ���ť�����
		if(arg0.getSource() == jb1){
			System.out.println("�û�ϣ����ѯ");
			
			//��Ϊ�ѶԱ�����ݷ�װ��StuModel�У����ǾͿ��ԱȽϼ򵥵���ɲ�ѯ����
			String name = this.jtf.getText().trim();
			//дһ��sql���
			String sql = "select * from stu where stuName='" + name + "'";
			
			//�����µ�����ģ���࣬������
			sm = new StuModel(sql);
			//����JTable
			jt.setModel(sm);
			
		}else if(arg0.getSource() == jb2){
			//���û���� ����ӡ� ��ťʱ
			StuAddDialog sa = new StuAddDialog(this, "���ѧ��", true);
			
			//�����ٻ���µ�����ģ��
			//�����µ�����ģ���࣬������
			sm = new StuModel();
			//����JTable
			jt.setModel(sm);
		}else if(arg0.getSource() == jb3){
			//���û���� ���޸ġ� ��ťʱ
			int rowNum = this.jt.getSelectedRow();
			if(rowNum == -1){
				//��ʾ
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return ;
			}
			
			//��ʾ�޸ĶԻ���
			new StuUpdateDialog(this, "�޸�ѧ��", true, sm, rowNum);
			
			//�����ٻ���µ�����ģ��
			//�����µ�����ģ���࣬������
			sm = new StuModel();
			//����JTable
			jt.setModel(sm);
			
		}else if(arg0.getSource() == jb4){
			//���û���� ��ɾ���� ��ťʱ
			//1���õ���ѧ����ID
			//getSelectedRow()�᷵���û����е��У�������û�һ�ж�ûѡ���򷵻�-1
			int rownum = this.jt.getSelectedRow();
			if(rownum == -1){
				//��ʾ
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return ;
			}
			//�õ�ѧ����id
			String stuId = (String)sm.getValueAt(rownum, 0);
//			System.out.println(" id = " + stuId);
//			System.out.println("ok");
			
			//�������ݿ⣬���ɾ������
			try{
				//1,��������
				Class.forName("oracle.jdbc.driver.OracleDriver");
//				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	
				//2,�õ�����
				ct=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "scott", "tiger");
//				ct = DriverManager.getConnection("jdbc:odbc:testjackge","scott","tiger");
				//3,����sql����Statement/PreparedStatement/CallableStatement)
				ps = ct.prepareStatement("delete from stu where stuid=?");
				ps.setString(1, stuId);
				ps.executeUpdate();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(rs != null){
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					rs=null;		//ʹ����������
				}
				
				if(ps != null){
					try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ps=null;		//ʹ����������
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
			
			//��������ģ��
			//�����µ�����ģ���࣬������
			sm = new StuModel();
			//����JTable
			jt.setModel(sm);
			
		}
	}
}
