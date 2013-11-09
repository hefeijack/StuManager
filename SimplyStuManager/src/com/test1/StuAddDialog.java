package com.test1;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.sql.*;

public class StuAddDialog extends JDialog implements ActionListener {
	
	//��������Ҫ��swing���
	JLabel jl1, jl2, jl3, jl4, jl5, jl6;
	JButton jb1, jb2;
	JTextField jtf1, jtf2, jtf3, jtf4, jtf5, jtf6;
	JPanel jp1, jp2, jp3;
	
	//owner ���ĸ�����	�� title ��������  model ָ����ģʽ���ڣ����Ƿ�ģʽ����
	public StuAddDialog(Frame owner, String title, boolean model){
		super(owner, title, model);		//���ø��෽�����ﵽģʽ�Ի���Ч��
		jl1 = new JLabel("ѧ��");
		jl2 = new JLabel("����");
		jl3 = new JLabel("�Ա�");
		jl4 = new JLabel("����");
		jl5 = new JLabel("����");
		jl6 = new JLabel("ϵ��");
		
		jtf1 = new JTextField();
		jtf2 = new JTextField();
		jtf3 = new JTextField();
		jtf4 = new JTextField();
		jtf5 = new JTextField();
		jtf6 = new JTextField();
		
		jb1 = new JButton("���");
		//ע�����
		jb1.addActionListener(this);
		
		jb2 = new JButton("ȡ��");
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		
		//���ò���
		jp1.setLayout(new GridLayout(6, 1));
		jp2.setLayout(new GridLayout(6, 1));
		
		//������
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		jp1.add(jl5);
		jp1.add(jl6);
		
		jp2.add(jtf1);
		jp2.add(jtf2);
		jp2.add(jtf3);
		jp2.add(jtf4);
		jp2.add(jtf5);
		jp2.add(jtf6);
		
		jp3.add(jb1);
		jp3.add(jb2);
		
		this.add(jp1, BorderLayout.WEST);
		this.add(jp2, BorderLayout.CENTER);
		this.add(jp3, BorderLayout.SOUTH);
		
//		jb1.addActionListener(this);
		
		//չ��
		this.setSize(300, 250);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jb1){
			//���û������Ӱ�ť�����Ӧ����
			//�������ݿ�
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			//�������ݿ⣬�ж��û��Ƿ�Ϸ�
			try{
				//1,��������
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//2,�õ�����
				conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "scott", "tiger");
				
				String strsql = "insert into stu values(?,?,?,?,?,?)";
				pstmt = conn.prepareStatement(strsql);
				
				//��������ֵ
				pstmt.setString(1, jtf1.getText());
				pstmt.setString(2, jtf2.getText());
				pstmt.setString(3, jtf3.getText());
				pstmt.setString(4, jtf4.getText());
				pstmt.setString(5, jtf5.getText());
				pstmt.setString(6, jtf6.getText());
				
				//ִ�в���
				pstmt.executeUpdate();
				
				this.dispose();		//�ر����ѧ���ĶԻ���
				
			}catch(ClassNotFoundException e1){
				e1.printStackTrace();
			}catch(SQLException ex){
				ex.printStackTrace();
			}finally{
				//�ͷ����������Ӷ���
				try{
					if(rs != null)
						rs.close();
					if(stmt != null)
						stmt.close();
					if(conn != null)
						conn.close();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
}
