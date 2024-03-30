package Helper;
import java.sql.*;

public class DBConnection {

	Connection connection = null;
	public DBConnection() {
		// TODO Auto-generated constructor stub
	}
	
	public Connection connectDB() {
		try {
			connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/mybookingapp4?user=root&password=2001hasan");
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}
