package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Helper.DBConnection;

public class Employee {

    private DBConnection conn;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement preparedStatement;
    public List<Employee> list = new ArrayList<>();
    
    private int id;
    private String name, type;

    public Employee(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
    
    public Employee() {
        conn = new DBConnection();
        con = conn.connectDB();
    }

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

    public void setType(String type) {
        this.type = type;
    }
    
	public List<Employee> getEmployeeList(String searchKeyword) throws SQLException {
		list = new ArrayList<>();
			try {
				st = con.createStatement();
				rs = st.executeQuery("SELECT * FROM employee WHERE name LIKE '" + searchKeyword + "%'");
				while (rs.next()) {
					Employee employee = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("type"));
					list.add(employee);
				}
			} finally {
				closeResources();
			}
		return list;
	}

    public boolean addEmployee(String name, String type) {
        String query = "INSERT INTO employee (name, type) VALUES (?, ?)";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public boolean deleteEmployee(int id) {
        String query = "DELETE FROM employee WHERE id = ?";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public boolean updateEmployee(int id, String name, String type) {
        String query = "UPDATE employee SET name = ?, type = ? WHERE id = ?";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}