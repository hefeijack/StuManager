/**
 * 完成 一个mini版的学生管理系统
 * 1，查询任务
 * 2，添加一个学生
 */

package com.test2;

import javax.swing.*;

import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class StuManager extends JFrame implements ActionListener{

	//定义一些控件
	JPanel jp1, jp2;
	JLabel jl1;
	JButton jb1, jb2, jb3, jb4;
	JTable jt;
	JScrollPane jsp;
	JTextField jtf;
	
	StuModel sm;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StuManager test3 = new StuManager();
	}

	//构造函数
	public StuManager(){
		jp1 = new JPanel();
		jtf = new JTextField(10);
		jb1 = new JButton("查询");
		jb1.addActionListener(this);	//对  “查询” 按钮进行注册监听
		
		jl1 = new JLabel("请输入名字：");
		
		//把各个控件加入到jp1
		jp1.add(jl1);
		jp1.add(jtf);
		jp1.add(jb1);
		
		jp2 = new JPanel();
		jb2 = new JButton("添加");
		jb2.addActionListener(this);
		
		jb3 = new JButton("修改");
		jb3.addActionListener(this);
		
		jb4 = new JButton("删除");
		jb4.addActionListener(this);
		
		//把各个按钮加入到jp2
		jp2.add(jb2);
		jp2.add(jb3);
		jp2.add(jb4);
		
		//创建一个数据模型对象
		sm = new StuModel();
		String[] paras={"1"};
		sm.queryStu("select * from stu where 1=?", paras);
		
		//初始化JTable
		jt = new JTable(sm);
		
		//初始化jsp JScrollPane
		jsp = new JScrollPane(jt);
		
		//把jsp放入到jframe
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
		//判断是哪个按钮被点击
		if(arg0.getSource() == jb1){
			System.out.println("用户希望查询");
			
			//因为把对表的数据封装到StuModel中，我们就可以比较简单的完成查询任务
			String name = this.jtf.getText().trim();
			//写一个sql语句
			String sql = "select * from stu where stuName='" + name + "'";
			String[] paras = {name};
			//构建新的数据模型类，并更新
			sm = new StuModel();
			sm.queryStu(sql, paras);
			//更新JTable
			jt.setModel(sm);
			
		}else if(arg0.getSource() == jb2){
			//当用户点击 “添加” 按钮时
			StuAddDialog sa = new StuAddDialog(this, "添加学生", true);
			
			//重新再获得新的数据模型
			//构建新的数据模型类，并更新
			sm = new StuModel();
			String[] paras2 = {"1"};
			sm.queryStu("select * from stu where 1=?", paras2);
			//更新JTable
			jt.setModel(sm);
		}else if(arg0.getSource() == jb3){
			//当用户点击 “修改” 按钮时
			int rowNum = this.jt.getSelectedRow();
			if(rowNum == -1){
				//提示
				JOptionPane.showMessageDialog(this, "请选择一行");
				return ;
			}
			
			//显示修改对话框
			new StuUpdateDialog(this, "修改学生", true, sm, rowNum);
			
			//重新再获得新的数据模型
			//构建新的数据模型类，并更新
			sm = new StuModel();
			String[] paras2 = {"1"};
			sm.queryStu("select * from stu where 1=?", paras2);
			//更新JTable
			jt.setModel(sm);
			
		}else if(arg0.getSource() == jb4){
			//当用户点击 “删除” 按钮时
			//1，得到该学生的ID
			//getSelectedRow()会返回用户点中的行，如果该用户一行都没选，则返回-1
			int rownum = this.jt.getSelectedRow();
			if(rownum == -1){
				//提示
				JOptionPane.showMessageDialog(this, "请选择一行");
				return ;
			}
			//得到学生的id
			String stuId = (String)sm.getValueAt(rownum, 0);
//			System.out.println(" id = " + stuId);
//			System.out.println("ok");
			
			//创建一个sql
			String sql = "delete from stu where stuid=?";
			String[] paras = {stuId};
			StuModel tmp = new StuModel();
			tmp.updStu(sql, paras);
			
			//更新数据模型
			//构建新的数据模型类，并更新
			sm = new StuModel();
			String[] paras2 = {"1"};
			sm.queryStu("select * from stu where 1=?", paras2);
			//更新JTable
			jt.setModel(sm);
			
		}
	}
}
