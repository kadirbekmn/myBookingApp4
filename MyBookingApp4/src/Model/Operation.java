package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Helper.DBConnection;

public class Operation {

	private DBConnection conn;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement preparedStatement;
	public List<Operation> list = new ArrayList<>();

	private int id;
	private String operationName;
	private int operationTime, operationPrice;

	public Operation(int id, String operationName, int operationTime, int operationPrice) {
		this.id = id;
		this.operationName = operationName;
		this.operationTime = operationTime;
		this.operationPrice = operationPrice;
	}

	public Operation() {
		conn = new DBConnection();
		con = conn.connectDB();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public int getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(int operationTime) {
		this.operationTime = operationTime;
	}

	public int getOperationPrice() {
		return operationPrice;
	}

	public void setOperationPrice(int operationPrice) {
		this.operationPrice = operationPrice;
	}

	public List<Operation> getOperationList(String searchKeyword) throws SQLException {
		list = new ArrayList<>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM operation WHERE operationName LIKE '" + searchKeyword + "%'");
			while (rs.next()) {
				Operation operation = new Operation(rs.getInt("id"), rs.getString("operationName"),
						rs.getInt("operationTime"), rs.getInt("operationPrice"));
				list.add(operation);
			}
		} finally {
			closeResources();
		}
		return list;
	}

	public boolean addOperation(String operationName, int operationTime, int operationPrice) {
		String query = "INSERT INTO operation (operationName, operationTime, operationPrice) VALUES (?, ?, ?)";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, operationName);
			preparedStatement.setInt(2, operationTime);
			preparedStatement.setInt(3, operationPrice);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	public boolean deleteOperation(int id) {
		String query = "DELETE FROM operation WHERE id = ?";
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

	public boolean updateOperation(int id, String operationName, int operationTime, int operationPrice) {
		String query = "UPDATE operation SET operationName = ?, operationTime = ?, operationPrice = ? WHERE id = ?";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, operationName);
			preparedStatement.setInt(2, operationTime);
			preparedStatement.setInt(3, operationPrice);
			preparedStatement.setInt(4, id);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	public List<String> getOperationTypes() throws SQLException {
		List<String> operationTypes = new ArrayList<>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT DISTINCT operationName FROM operation");
			while (rs.next()) {
				operationTypes.add(rs.getString("operationName"));
			}
		} finally {
			closeResources();
		}
		return operationTypes;
	}

	public List<Operation> findOperationTypesByEmployee(Employee employee) throws SQLException {
		List<Operation> operationTypes = new ArrayList<>();
		String query = "SELECT o.id, o.operationName, o.operationTime, o.operationPrice " + "FROM operation o "
				+ "INNER JOIN operations_by_employee r ON r.operation_fk = o.id " + "WHERE r.employee_fk = ?";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, employee.getId());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Operation operation = new Operation(rs.getInt("id"), rs.getString("operationName"),
						rs.getInt("operationTime"), rs.getInt("operationPrice"));
				operationTypes.add(operation);
			}
		} finally {
			closeResources();
		}
		return operationTypes;
	}

	public String toString() {
		return this.operationName;
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