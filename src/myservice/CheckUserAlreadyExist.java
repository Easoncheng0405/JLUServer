/*
 * 检查用户注册的用户名或电话号码是否已经存在
 * 返回类型为JSON
 * 成功返回{"status":"OK","code":"0"}
 * 失败返回{"status":"userName already exist","code":"101"},用户名已存在
 * 或{"status":"phoneNum already exist","code":"102"}，电话号已注册
 * */

package myservice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.SQLConnection;

public class CheckUserAlreadyExist extends JSONSupport{
	//客户端需要传递两个参数1.用户名2.电话号
	private String name,phone;
	private Connection conn=null;
	public String check(){
		try {
			conn=new SQLConnection().connectDataBase();
			Statement stmt=conn.createStatement();
			String sql="select * from user where phone="+dealInfo(phone);
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				dataMap.put("status", "error");
				dataMap.put("code", 102);
				closeDataBase(conn);
				return SUCCESS;
			}
			sql="select * from user where name="+dealInfo(name);
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				dataMap.put("status", "error");
				dataMap.put("code", 101);
				closeDataBase(conn);
				return SUCCESS;
			}
			dataMap.put("status", "OK");
			dataMap.put("code", 0);
			closeDataBase(conn);
			return SUCCESS;
		} catch (ClassNotFoundException e) {
			dataMap.put("status", "error");
			dataMap.put("code", "103");
		} catch (SQLException e) {
			dataMap.put("status", "error");
			dataMap.put("code", "104");
		}
		return SUCCESS;
	}
	private String dealInfo(String info) {
		return "'"+info+"'";
	}
	private void closeDataBase(Connection conn) throws SQLException {
		if(conn!=null)
			conn.close();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
