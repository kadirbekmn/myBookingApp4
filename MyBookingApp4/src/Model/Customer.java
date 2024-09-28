package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	private int customerBirthDiscount;


	public Customer(int id, String customerName, Date customerBirth, int customerPhone, String customerExplanation, int customerDiscount) {
		this.id = id;
		this.customerName = customerName;
		this.customerBirth = customerBirth;
		this.customerPhone = customerPhone;
		this.customerExplanation = customerExplanation;
		this.customerBirthDiscount = customerDiscount;
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

	public int getCustomerBirthDiscount() {
		return customerBirthDiscount;
	}

	public void setCustomerbirthDiscount(int customerbirthDiscount) {
		this.customerBirthDiscount = customerbirthDiscount;
	}

	public List<Customer> getCustomerList(String searchKeyword) throws SQLException {
		list = new ArrayList<>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM customer WHERE customerName LIKE '" + searchKeyword + "%'");
			while (rs.next()) {
				Customer customer = new Customer(rs.getInt("id"), rs.getString("customerName"), rs.getDate("customerBirth"),
						rs.getInt("customerPhone"), rs.getString("customerExplanation"), rs.getInt("customerbirthDiscount"));
				list.add(customer);
			}
		} finally {
			closeResources();
		}
		return list;
	}

	public boolean addCustomer(String customerName, Date customerBirth, int customerPhone, String customerExplanation, int customerBirthDiscount) {
		String query = "INSERT INTO customer (customerName, customerBirth, customerPhone, customerExplanation, customerbirthDiscount) VALUES (?, ?, ?, ?, ?)";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, customerName);
			preparedStatement.setDate(2, customerBirth);
			preparedStatement.setInt(3, customerPhone);
			preparedStatement.setString(4, customerExplanation);
			preparedStatement.setInt(5, customerBirthDiscount);
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

	public boolean updateCustomer(int id, String customerName, Date customerBirth, int customerPhone, String customerExplanation, int customerBirthDiscount) {
		String query = "UPDATE customer SET customerName = ?, customerBirth = ?, customerPhone = ?, customerExplanation = ?, customerbirthDiscount = ? WHERE id = ?";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, customerName);
			preparedStatement.setDate(2, customerBirth);
			preparedStatement.setInt(3, customerPhone);
			preparedStatement.setString(4, customerExplanation);
			preparedStatement.setInt(5, customerBirthDiscount);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
    public String toString() {
        return this.customerName;
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