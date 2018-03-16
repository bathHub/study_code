package rdbms;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

public class StudentDBWritable implements Writable,DBWritable{
private int studentId;
private int age;
private String studentName;
private String gender;
private String classNo;
private int proId;
	public int getStudentId() {
	return studentId;
}
public void setStudentId(int studentId) {
	this.studentId = studentId;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public String getStudentName() {
	return studentName;
}
public void setStudentName(String studentName) {
	this.studentName = studentName;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getClassNo() {
	return classNo;
}
public void setClassNo(String classNo) {
	this.classNo = classNo;
}
public int getProId() {
	return proId;
}
public void setProId(int proId) {
	this.proId = proId;
}
//INSERT INTO student VALUES(?,?,?,?,?,?)
//从程序中把数据往数据库中写
//这里是建立映射
	public void write(PreparedStatement statement) throws SQLException {
		// TODO Auto-generated method stub
		statement.setInt(1, studentId);
		statement.setInt(2, age);
		statement.setString(3, studentName);
		statement.setString(4, gender);
		statement.setString(5, classNo);
		statement.setInt(6, proId);
		
	}

	public void readFields(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		studentId = resultSet.getInt(1);
		age = resultSet.getInt(2);
		studentName = resultSet.getString(3);
		gender = resultSet.getString(4);
		classNo = resultSet.getString(5);
		proId = resultSet.getInt(6);
	}

	//从数据库往外读取
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeInt(studentId);
		out.writeInt(age);
		out.writeUTF(studentName);
		out.writeUTF(gender);
		out.writeUTF(classNo);
		out.writeInt(proId);
	}

	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		studentId = in.readInt();
		age = in.readInt();
		studentName = in.readUTF();
		gender = in.readUTF();
		classNo = in.readUTF();
		proId = in.readInt();
	}
	@Override
	public String toString() {
		return  studentId + "|" + age + "|" + studentName
				+ "|" + gender + "|" + classNo + "|" + proId;
	}

}
