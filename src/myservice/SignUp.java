package myservice;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import info.UserInfo;
import util.SQLConnection;
public class SignUp extends JSONSupport{
	private Connection conn=null;
	private UserInfo info;
	public String signUp() {
		try {
			conn=new SQLConnection().connectDataBase();
			createUserTable();
			String sql="insert into user (phone,name,password,type,sign_time) values(";
			sql=sql+dealInfo(info.getPhone())+","+dealInfo(info.getName())+","+dealInfo(info.getPassword())+","+dealInfo(info.getType())+","+dealInfo(info.getSignTime())+")";
			Statement stmt=conn.createStatement();
			stmt.executeUpdate(sql);
			closeDataBase(conn);
		} catch (ClassNotFoundException e) {
			dataMap.put("status", "error");
			dataMap.put("code", 103);        //mysql驱动异常
			return SUCCESS;
		} catch (SQLException e) {
			dataMap.put("status", "error");
			dataMap.put("code", 104);       //mysql库操作异常
			e.printStackTrace();
			return SUCCESS;
		}
		dataMap.put("status", "ok");
		dataMap.put("code", 0);
		dataMap.put("UserInfo", info);
		return SUCCESS;
	}
	private void createUserTable() throws SQLException {     //创建用户表用于记录用户操作
		String sql="create table "+info.getName()+"(\n" + 
				"	id    int(6) AUTO_INCREMENT primary key,\n" + 
				"	name  varchar(20)  NOT NULL,\n" + 
				"	phone varchar(15)  NOT NULL,\n" + 
				"	type  varchar(15)  NOT NULL,\n" + 
				"	op_time char(19)   NOT NULL,\n" + 
				"	op_loc  varchar(20) NOT NULL,\n" + 
				"	op_dev  varchar(10)	NOT NULL,\n" + 
				"	note    varchar(10) NOT NULL\n" + 
				");";
		Statement stmt=conn.createStatement();
		stmt.executeUpdate(sql);
	}
	public UserInfo getInfo() {
		return info;
	}
	public void setInfo(UserInfo info) {
		this.info = info;
	}
	private void closeDataBase(Connection conn) throws SQLException {
		if(conn!=null)
			conn.close();
	}
	private String dealInfo(String info) {
		return "'"+info+"'";
	}
}
