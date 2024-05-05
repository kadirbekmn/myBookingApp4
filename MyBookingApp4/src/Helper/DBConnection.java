package Helper;

import java.sql.*;

public class DBConnection {

	Connection connection = null;

	public DBConnection() {
	}

	public Connection connectDB() {
		try {
		connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/mybookingapp4?user=root&password=2001hasan");
			//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybookingapp4?user=root&password=1234");
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
