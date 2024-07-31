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
	
	 public String getNameByID(int id) {
		 
	        String query = "SELECT name FROM employee WHERE id = ?";
	        String name="";
	        try {
	            preparedStatement = con.prepareStatement(query);
	            preparedStatement.setInt(1, id);
	            rs = preparedStatement.executeQuery();
	            
	            while (rs.next()) {
					
					name = rs.getString("name");
				}
				return name;
	           
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return name;
	        } finally {
	            closeResources();
	        }
	    }
	 
	 public int getIDByName(String name) {
		 
	        String query = "SELECT id FROM employee WHERE name = ?";
	        int id =0;
	        try {
	            preparedStatement = con.prepareStatement(query);
	            preparedStatement.setString(1, name);
	            rs = preparedStatement.executeQuery();
	            
	            while (rs.next()) {
					
					id = rs.getInt("id");
				}
				return id;
	           
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return id;
	        } finally {
	            closeResources();
	        }
	    }


	 public boolean addEmployee(Employee tempEmployee) {
		    String query = "INSERT INTO employee (name, type) VALUES (?, ?)";
		    try {
		        preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		        preparedStatement.setString(1, tempEmployee.getName());
		        preparedStatement.setString(2, tempEmployee.getType());
		        int affectedRows = preparedStatement.executeUpdate();

		        if (affectedRows > 0) {
		            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
		            if (generatedKeys.next()) {
		                tempEmployee.setId(generatedKeys.getInt(1)); // Yeni oluşturulan ID'yi tempEmployee nesnesine ayarla
		            }
		            return true;
		        } else {
		            return false;
		        }
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
    
    public List<Operation> findChoosedOperations(int employeeId) {
        String query = "SELECT operation_fk FROM operations_by_employee WHERE employee_fk = ?";
        List<Operation> operations = new ArrayList<>();
        
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, employeeId);
            rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                int operationId = rs.getInt("operation_fk");
                Operation operation = getOperationById(operationId);
                if (operation != null) {
                    operations.add(operation);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        
        return operations;
    }
    
    public String findChoosedOperationNames(int employeeId) {
        String query = "SELECT o.operationName FROM operations_by_employee obe " +
                       "JOIN operation o ON obe.operation_fk = o.id " +
                       "WHERE obe.employee_fk = ?";
        StringBuilder operationNames = new StringBuilder();

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, employeeId);
            rs = preparedStatement.executeQuery();

            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    operationNames.append(", ");
                }
                operationNames.append(rs.getString("operationName"));
                first = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return operationNames.toString();
    }



    private Operation getOperationById(int operationId) {
        String query = "SELECT * FROM operation WHERE id = ?";
        Operation operation = null;
        
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, operationId);
            rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                operation = new Operation();
                operation.setId(rs.getInt("id"));
                operation.setOperationName(rs.getString("operationName"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return operation;
    }
    
    public boolean addOperations(List<Operation> operations, Employee employee) {
        boolean hasAdded = false;
        String query = "INSERT INTO operations_by_employee (employee_fk, operation_fk) VALUES (?, ?)";
        
        try {
            preparedStatement = con.prepareStatement(query);
            
            for (Operation operation : operations) {
                preparedStatement.setInt(1, employee.getId());
                preparedStatement.setInt(2, operation.getId());
                preparedStatement.executeUpdate();
            }
            
            hasAdded = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return hasAdded;
    }
    
    public Operation findOperationByName(String operationName) {
        String query = "SELECT * FROM operation WHERE operationName = ?";
        Operation foundOperation = null;
        
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, operationName);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("operationName");
                int time = resultSet.getInt("operationTime");
                int price = resultSet.getInt("operationPrice");
                foundOperation = new Operation(id, name, time, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return foundOperation;
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