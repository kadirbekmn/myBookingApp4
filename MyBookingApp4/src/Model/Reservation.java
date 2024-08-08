package Model;

import Helper.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Reservation {
	DBConnection dbConnection = null;
	Connection connection = null;
	ResultSet resultSet = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	
	private Connection con;
	private int id;
	private int customerId;
	private int employeeId;
	private Timestamp reservationTime;
	private int totalOperationTime;
	private Timestamp endTime;
	private int operationId;
	private int groupId;
	private String employeeName;
	private String customerName;
	private String operationName;
	private int totalPrice;

	public Reservation(int id, int customerId, int employeeId, Timestamp reservationTime, int totalOperationTime,
			Timestamp endTime) {
		this.id = id;
		this.customerId = customerId;
		this.employeeId = employeeId;
		this.reservationTime = reservationTime;
		this.totalOperationTime = totalOperationTime;
		this.endTime = endTime;
	}

	public Reservation() {
		this.con = new DBConnection().connectDB();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Timestamp getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(Timestamp reservationTime) {
		this.reservationTime = reservationTime;
	}

	public int getTotalOperationTime() {
		return totalOperationTime;
	}

	public void setTotalOperationTime(int totalOperationTime) {
		this.totalOperationTime = totalOperationTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean addReservation(Reservation reservation, List<Operation> selectedOperations) {
		String query = "INSERT INTO reservation (employee_id, customer_id, operation_id, date, group_id, total_operation_time, end_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
		boolean isSaved = false;
		int groupId = getNewGroupId();

		try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
			for (Operation operation : selectedOperations) {
				preparedStatement.setInt(1, reservation.getEmployeeId());
				preparedStatement.setInt(2, reservation.getCustomerId());
				preparedStatement.setInt(3, operation.getId());
				preparedStatement.setTimestamp(4, reservation.getReservationTime());
				preparedStatement.setInt(5, groupId);
				preparedStatement.setInt(6, reservation.getTotalOperationTime());
				preparedStatement.setTimestamp(7, reservation.getEndTime());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			isSaved = true;
		} catch (SQLException e) {
			e.printStackTrace();
			isSaved = false;
		} finally {
			closeResources();
		}
		return isSaved;
	}

	private int getNewGroupId() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	public List<Reservation> getReservationsByGroupId(int groupId) {
		List<Reservation> reservations = new ArrayList<>();
		String query = "SELECT * FROM reservation WHERE group_id = ?";

		try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
			preparedStatement.setInt(1, groupId);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					Reservation reservation = new Reservation();
					reservation.setId(rs.getInt("id"));
					reservation.setEmployeeId(rs.getInt("employee_id"));
					reservation.setCustomerId(rs.getInt("customer_id"));
					reservation.setOperationId(rs.getInt("operation_id"));
					reservation.setReservationTime(rs.getTimestamp("date"));
					reservation.setGroupId(rs.getInt("group_id"));
					reservations.add(reservation);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return reservations;
	}

	public List<Reservation> getReservationList() {
		List<Reservation> list = new ArrayList<>();
		String query = "SELECT r.group_id, e.name AS employee_name, c.customerName AS customer_name, "
				+ "GROUP_CONCAT(o.operationName ORDER BY o.operationName SEPARATOR ', ') AS operation_names, "
				+ "SUM(o.operationTime) AS total_operation_time, " + "SUM(o.operationPrice) AS total_price, "
				+ "r.date, r.end_time " + "FROM reservation r " + "JOIN employee e ON r.employee_id = e.id "
				+ "JOIN customer c ON r.customer_id = c.id " + "JOIN operation o ON r.operation_id = o.id "
				+ "GROUP BY r.group_id, e.name, c.customerName, r.date, r.end_time";

		try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					Reservation reservation = new Reservation();
					reservation.setGroupId(rs.getInt("group_id"));
					reservation.setEmployeeName(rs.getString("employee_name"));
					reservation.setCustomerName(rs.getString("customer_name"));
					reservation.setOperationName(rs.getString("operation_names"));
					reservation.setTotalOperationTime(rs.getInt("total_operation_time"));
					reservation.setTotalPrice(rs.getInt("total_price"));
					reservation.setReservationTime(rs.getTimestamp("date"));
					reservation.setEndTime(rs.getTimestamp("end_time"));
					list.add(reservation);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return list;
	}

	public boolean deleteReservation(int groupId) {
		String query = "DELETE FROM reservation WHERE group_id = ?";
		try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
			preparedStatement.setInt(1, groupId);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	public boolean isEmployeeAvailable(int employeeId, Timestamp startTime, Timestamp endTime) {
		String query = "SELECT COUNT(*) FROM reservation " + "WHERE employee_id = ? AND "
				+ "(date < ? AND end_time > ?)";

		try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
			preparedStatement.setInt(1, employeeId);
			preparedStatement.setTimestamp(2, endTime);
			preparedStatement.setTimestamp(3, startTime);

			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) == 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return false;
	}
	
	public int getTodayReservationCount() throws SQLException {
	    String query = "SELECT COUNT(*) FROM reservation WHERE date >= ? AND date < ?";
	    
	    Calendar now = Calendar.getInstance();
	    Timestamp startOfDay = new Timestamp(now.getTimeInMillis());

	    now.set(Calendar.HOUR_OF_DAY, 23);
	    now.set(Calendar.MINUTE, 59);
	    now.set(Calendar.SECOND, 59);
	    now.set(Calendar.MILLISECOND, 999);
	    Timestamp endOfDay = new Timestamp(now.getTimeInMillis());

	    try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
	        preparedStatement.setTimestamp(1, startOfDay);
	        preparedStatement.setTimestamp(2, endOfDay);

	        try (ResultSet rs = preparedStatement.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
		} finally {
			closeResources();
		}
	    return 0;
	}
	
	public int getCurrentMonthReservationCount() throws SQLException {
	    String query = "SELECT COUNT(DISTINCT customer_id) FROM reservation WHERE date >= ? AND date < ?";
	    Calendar now = Calendar.getInstance();
	    Timestamp currentTime = new Timestamp(now.getTimeInMillis());

	    now.set(Calendar.DAY_OF_MONTH, 1);
	    now.set(Calendar.HOUR_OF_DAY, 0);
	    now.set(Calendar.MINUTE, 0);
	    now.set(Calendar.SECOND, 0);
	    now.set(Calendar.MILLISECOND, 0);
	    Timestamp startOfMonth = new Timestamp(now.getTimeInMillis());

	    try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
	        preparedStatement.setTimestamp(1, startOfMonth);
	        preparedStatement.setTimestamp(2, currentTime);

	        try (ResultSet rs = preparedStatement.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
		} finally {
			closeResources();
		}
	    return 0;
	}
	
	public int getTotalCustomerCount() throws SQLException {
	    String query = "SELECT COUNT(DISTINCT group_id) FROM reservation";

	    try (PreparedStatement preparedStatement = con.prepareStatement(query);
	         ResultSet resultSet = preparedStatement.executeQuery()) {
	        if (resultSet.next()) {
	            return resultSet.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeResources();
	    }
	    return 0;
	}


	public double getTotalRevenueFromReservations() throws SQLException {
	    String query = "SELECT SUM(o.operationPrice) " +
	                   "FROM reservation r " +
	                   "JOIN operation o ON r.operation_id = o.id " +
	                   "WHERE r.date <= NOW()";

	    try (PreparedStatement preparedStatement = con.prepareStatement(query);
	         ResultSet resultSet = preparedStatement.executeQuery()) {
	        if (resultSet.next()) {
	            return resultSet.getDouble(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
		} finally {
			closeResources();
		}
	    return 0.0;
	}
	
	protected void closeResources() {
	    try {
	        if (resultSet != null) {
	            resultSet.close();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    try {
	        if (statement != null) {
	            statement.close();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    try {
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}