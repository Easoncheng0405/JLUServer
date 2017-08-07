package myservice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.OpreationInfo;
import info.UserInfo;
import util.SQLConnection;

public class SignIn extends JSONSupport{
	private Connection conn;
	private String info;
	private String passWord;
	private OpreationInfo opInfo;
	UserInfo user=null;
	public String signIn() {
		try {
			conn=new SQLConnection().connectDataBase();
			if(isPhoneNum(info)) {
				if((user=getUserInfoByPhoneNum())!=null) {
					dataMap.put("status", "OK");
					dataMap.put("code", 0);
					dataMap.put("UserInfo", user);
					record();
				}else {
					dataMap.put("status", "error");
					dataMap.put("code", 201);      //用户名或密码错误
					dataMap.put("UserInfo", user);
				}
			}
			else {
				if((user=getUserInfoByName())!=null) {
					dataMap.put("status", "OK");
					dataMap.put("code", 0);
					dataMap.put("UserInfo", user);
					record();
				}else {
					dataMap.put("status", "error");
					dataMap.put("code", 201);      //用户名或密码错误
					dataMap.put("UserInfo", user);
				}
			}
			closeDataBase(conn);
		} catch (ClassNotFoundException e) {
			dataMap.put("status", "error");
			dataMap.put("code", 202);      //mysql驱动加载异常
			dataMap.put("UserInfo", null);
		} catch (SQLException e) {
			dataMap.put("status", "error");
			dataMap.put("code", 203);      //mysql操作异常
			dataMap.put("UserInfo", null);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	private UserInfo getUserInfoByPhoneNum() throws SQLException {
		String sql = "select * from user where phone=" + dealInfo(info)+" and password=" +dealInfo(passWord);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			UserInfo userinfo=new UserInfo();
			userinfo.setName(rs.getString(2));
			userinfo.setPhone(rs.getString(3));
			userinfo.setPassword(rs.getString(4));
			userinfo.setType(rs.getString(5));
			userinfo.setSignTime(rs.getString(6));
			return userinfo;
		}
		return null;
	}
	private void closeDataBase(Connection conn) throws SQLException {
		if(conn!=null)
			conn.close();
	}
	private String dealInfo(String info) {
		return "'"+info+"'";
	}
	private UserInfo getUserInfoByName() throws SQLException {
		String sql = "select * from user where name=" + dealInfo(info)+" and password=" +dealInfo(passWord);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			UserInfo userinfo=new UserInfo();
			userinfo.setName(rs.getString(2));
			userinfo.setPhone(rs.getString(3));
			userinfo.setPassword(rs.getString(4));
			userinfo.setType(rs.getString(5));
			userinfo.setSignTime(rs.getString(6));
			return userinfo;
		}
		return null;
	}
	private void record() throws SQLException {
		String sql = "insert into "+user.getName()+"(name,phone,type,op_time,op_loc,op_dev,note) values (" ;
		sql=sql+dealInfo(user.getName())+","+dealInfo(user.getPhone())+","+dealInfo(opInfo.getType());
		sql=sql+","+dealInfo(opInfo.getOpTime())+","+dealInfo(opInfo.getOpLoc())+","+dealInfo(opInfo.getOpDev())+","+dealInfo(opInfo.getNote())+")";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
	}
	
	private boolean isPhoneNum(String phoneNum) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNum);
		return m.matches();
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public OpreationInfo getOpInfo() {
		return opInfo;
	}
	public void setOpInfo(OpreationInfo opInfo) {
		this.opInfo = opInfo;
	}
}
