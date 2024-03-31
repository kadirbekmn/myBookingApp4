package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Employee {

	DBConnection conn = new DBConnection();
	Connection con = conn.connectDB();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	int id;
	String name, type, password;
	
	public Employee(int id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;	
	}
	
	public Employee() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String password) {
		this.type = type;
	}
	
	public ArrayList<Employee> getEmployeeList() throws SQLException {
		ArrayList<Employee> list = new ArrayList<>();
		Employee obj;
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs = st.executeQuery("SELECT * FROM employee");
			while(rs.next()) {
				obj = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("type"));
				list.add(obj);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean addEmployee(String name, String type) {
		String query = "INSERT INTO employee " + "(name, type) VALUES" + "(?,?)";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, type);
			preparedStatement.executeUpdate();
			key = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteEmployee(int id) {
		String query = "DELETE FROM employee WHERE id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			key = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateEmployee(int id, String name, String type) {
		String query = "UPDATE user SET name = ?, type = ? WHERE id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, type);
			preparedStatement.setInt(3, id);
			preparedStatement.executeUpdate();
			key = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key) {
			return true;
		} else {
			return false;
		}
	}
}