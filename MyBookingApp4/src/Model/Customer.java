package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Helper.DBConnection;

public class Customer {

	private DBConnection conn;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement preparedStatement;
	public List<Customer> list = new ArrayList<>();

	private int id;
	private String customerName;
	private Date customerBirth;
	private int customerPhone;
	private String customerExplanation;
	private int customerBirhDiscount;


	public Customer(int id, String customerName, Date customerBirth, int customerPhone, String customerExplanation, int customerBirhDiscount) {
		this.id = id;
		this.customerName = customerName;
		this.customerBirth = customerBirth;
		this.customerPhone = customerPhone;
		this.customerExplanation = customerExplanation;
		this.customerPhone = customerBirhDiscount;
	}

	public Customer() {
		conn = new DBConnection();
		con = conn.connectDB();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getCustomerBirth() {
		return customerBirth;
	}

	public void setCustomerBirth(Date customerBirth) {
		this.customerBirth = customerBirth;
	}

	public int getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(int customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerExplanation() {
		return customerExplanation;
	}

	public void setCustomerExplanation(String customerExplanation) {
		this.customerExplanation = customerExplanation;
	}

	public int getCustomerBirhDiscount() {
		return customerBirhDiscount;
	}

	public void setCustomerBirhDiscount(int customerBirhDiscount) {
		this.customerBirhDiscount = customerBirhDiscount;
	}

	public List<Customer> getCustomerList(String searchKeyword) throws SQLException {
		list = new ArrayList<>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM customer WHERE customerName LIKE '" + searchKeyword + "%'");
			while (rs.next()) {
				Customer customer = new Customer(rs.getInt("id"), rs.getString("customerName"), rs.getDate("customerBirth"),
						rs.getInt("customerPhone"), rs.getString("customerExplanation"), rs.getInt("customerBirhDiscount"));
				list.add(customer);
			}
		} finally {
			closeResources();
		}
		return list;
	}

	public boolean addCustomer(String customerName, Date customerBirth, int customerPhone, String customerExplanation, int customerBirhDiscount) {
		String query = "INSERT INTO customer (customerName, customerBirth, customerPhone, customerExplanation, customerBirhDiscount) VALUES (?, ?, ?, ?, ?)";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, customerName);
			preparedStatement.setDate(2, customerBirth);
			preparedStatement.setInt(3, customerPhone);
			preparedStatement.setString(4, customerExplanation);
			preparedStatement.setInt(5, customerBirhDiscount);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	public boolean deleteCustomer(int id) {
		String query = "DELETE FROM customer WHERE id = ?";
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

	public boolean updateCustomer(int id, String customerName, Date customerBirth, int customerPhone, String customerExplanation, int customerBirhDiscount) {
		String query = "UPDATE customer SET customerName = ?, customerBirth = ?, customerPhone = ?, customerExplanation = ?, customerBirhDiscount = ? WHERE id = ?";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, customerName);
			preparedStatement.setDate(2, customerBirth);
			preparedStatement.setInt(3, customerPhone);
			preparedStatement.setString(4, customerExplanation);
			preparedStatement.setInt(5, customerBirhDiscount);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
//    public List<String> getOperationTypes() throws SQLException {
//        List<String> operationTypes = new ArrayList<>();
//        try {
//            st = con.createStatement();
//            rs = st.executeQuery("SELECT DISTINCT operationName FROM operation");
//            while (rs.next()) {
//                operationTypes.add(rs.getString("operationName"));
//            }
//        } finally {
//            closeResources();
//        }
//        return operationTypes;
//    }

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