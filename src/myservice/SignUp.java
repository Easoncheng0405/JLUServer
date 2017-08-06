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
			String sql="insert into user (phone,name,password,type,sign_time) values(";
			sql=sql+dealInfo(info.getPhone())+","+dealInfo(info.getName())+","+dealInfo(info.getPassword())+","+dealInfo(info.getType())+","+dealInfo(info.getSignTime())+")";
			Statement stmt=conn.createStatement();
			stmt.executeUpdate(sql);
			closeDataBase(conn);
		} catch (ClassNotFoundException e) {
			dataMap.put("status", "error");
			dataMap.put("code", 103);
			return SUCCESS;
		} catch (SQLException e) {
			dataMap.put("status", "error");
			dataMap.put("code", 104);
			e.printStackTrace();
			return SUCCESS;
		}
		dataMap.put("status", "ok");
		dataMap.put("code", 0);
		dataMap.put("userInfo", info);
		return SUCCESS;
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
