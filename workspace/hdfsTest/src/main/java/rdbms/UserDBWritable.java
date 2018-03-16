package rdbms;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//统计用户的id、名称、ip、login次数、logout次数、newtweet次数、viewuser次数
public class UserDBWritable {
     private Integer userId;
     private String userName;
     private String userIp;
     private String loginTimes;
     private String logoutTimes;
     private String newtweetTimes;
     private String viewuserTimes;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(String loginTimes) {
		this.loginTimes = loginTimes;
	}
	public String getLogoutTimes() {
		return logoutTimes;
	}
	public void setLogoutTimes(String logoutTimes) {
		this.logoutTimes = logoutTimes;
	}
	public String getNewtweetTimes() {
		return newtweetTimes;
	}
	public void setNewtweetTimes(String newtweetTimes) {
		this.newtweetTimes = newtweetTimes;
	}
	public String getViewuserTimes() {
		return viewuserTimes;
	}
	public void setViewuserTimes(String viewuserTimes) {
		this.viewuserTimes = viewuserTimes;
	}
	//INSERT INTO student VALUES(?,?,?,?,?,?)
	//从程序中把数据往数据库中写
	//这里是建立映射
	//统计用户的id、名称、ip、login次数、logout次数、newtweet次数、viewuser次数
		public void write(PreparedStatement statement) throws SQLException {
			// TODO Auto-generated method stub
			statement.setInt(1, userId);
			statement.setString(2, userName);
			statement.setString(3, userIp);
			statement.setString(4, loginTimes);
			statement.setString(5, logoutTimes);
			statement.setString(6, newtweetTimes);
			statement.setString(7, viewuserTimes);
			
		}

		public void readFields(ResultSet resultSet) throws SQLException {
			// TODO Auto-generated method stub
			userId = resultSet.getInt(1);
			userName = resultSet.getString(2);
			userIp = resultSet.getString(3);
			loginTimes = resultSet.getString(4);
			logoutTimes = resultSet.getString(5);
			newtweetTimes = resultSet.getString(6);
			viewuserTimes = resultSet.getString(7);
		}

		//从数据库往外读取
		public void write(DataOutput out) throws IOException {
			// TODO Auto-generated method stub
			out.writeInt(userId);
			out.writeUTF(userName);
			out.writeUTF(userIp);
			out.writeUTF(loginTimes);
			out.writeUTF(logoutTimes);
			out.writeUTF(newtweetTimes);
			out.writeUTF(viewuserTimes);
		}

		public void readFields(DataInput in) throws IOException {
			// TODO Auto-generated method stub
			userId = in.readInt();
			userName = in.readUTF();
			userIp = in.readUTF();
			loginTimes = in.readUTF();
			logoutTimes = in.readUTF();
			newtweetTimes = in.readUTF();
			viewuserTimes = in.readUTF();
		}
		@Override
		public String toString() {
			return  userId + "|" + userName + "|" + userIp
					+ "|" + loginTimes + "|" + logoutTimes + "|" + newtweetTimes+ "|" + viewuserTimes;
		}

	
}
