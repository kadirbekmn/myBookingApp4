package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Manager{
	
	DBConnection conn = new DBConnection();
	
	int id;
	String nickname, password;
	
	
	public Manager(int id, String nickname, String password) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.password = password;
	}
	
	public Manager() {}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String name) {
		this.nickname = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
//	public ArrayList<User> getDoctorList() throws SQLException {
//		ArrayList<User> list = new ArrayList<>();
//
//		User obj;
//		try {
//			st = con.createStatement();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			rs = st.executeQuery("SELECT * FROM  user WHERE type = 'doktor'");
//			while(rs.next()) {
//				obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), rs.getString("password"), rs.getString("type"));
//				list.add(obj);
//			} 
//		}catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//	}
//	
//	
//	public boolean addDoctor(String tcno, String password, String name) {
//		
//		String query = "INSERT INTO user " + "(tcno, password, name, type) VALUES" + "(?,?,?,?)";
//		boolean key = false;
//		try {
//			st = con.createStatement();
//			preparedStatement = con.prepareStatement(query);
//			preparedStatement.setString(1, tcno);
//			preparedStatement.setString(2, password);
//			preparedStatement.setString(3, name);
//			preparedStatement.setString(4, "doktor");
//			preparedStatement.executeUpdate();
//			key = true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		if (key) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	
//	public boolean deleteDoctor(int id) {
//		
//		String query = "DELETE FROM user WHERE id = ?";
//		boolean key = false;
//		try {
//			st = con.createStatement();
//			preparedStatement = con.prepareStatement(query);
//			preparedStatement.setInt(1, id);
//			preparedStatement.executeUpdate();
//			key = true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		if (key) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public boolean updateDoctor(int id, String tcno, String password, String name) {
//		String query = "UPDATE user SET name = ?, tcno = ?, password = ? WHERE id = ?";
//		boolean key = false;
//		try {
//			st = con.createStatement();
//			preparedStatement = con.prepareStatement(query);
//			preparedStatement.setString(1, name);
//			preparedStatement.setString(2, tcno);
//			preparedStatement.setString(3, password);
//			preparedStatement.setInt(4, id);
//			preparedStatement.executeUpdate();
//			key = true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		if (key) {
//			return true;
//		} else {
//			return false;
//		}
//	}
}
