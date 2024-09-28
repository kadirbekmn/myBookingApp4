package Model;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Helper.DBConnection;

public class Product {

	DBConnection dbConnection;
	Connection connection;
	ResultSet resultSet;
	Statement statement;
	PreparedStatement preparedStatement;

	protected int id;
	protected String name;
	protected int purchasePrice;
	protected int salePrice;
	protected String type;
	private float price;
	private byte[] image;
	protected int stock;
	private boolean selected;
	private int count;
	

	public Product() {
		dbConnection = new DBConnection();
		connection = dbConnection.connectDB();
	}

	public Product(int id, String name, int purchasePrice, int salePrice, int stock) {
		this.id = id;
		this.name = name;
		this.setPurchasePrice(purchasePrice);
		this.setSalePrice(salePrice);
		this.stock = stock;
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	

	public ArrayList<Product> getProductsList() throws SQLException {
		ArrayList<Product> productList = new ArrayList<>();
		Product objectProduct = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM product WHERE deleted = 0");
			while (resultSet.next()) {
				objectProduct = new Product(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getInt("purchase_price"), resultSet.getInt("sale_price"), resultSet.getInt("stock"));
				productList.add(objectProduct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			closeResources();
		}

		return productList;
	}

	
	
	  public Product getProductById(int id) throws SQLException {
	        String query = "SELECT * FROM product WHERE id = ?";
	        
	        try {
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
			
				if(resultSet.next()) {
					String name = resultSet.getString("name");
					int stock = resultSet.getInt("stock");
					int salePrice = resultSet.getInt("sale_price");
					int purchasePrice = resultSet.getInt("purchase_price");
					return new Product (id, name, purchasePrice, salePrice,stock);
				} else {
					 throw new SQLException("Product with ID " + id + " not found.");
				}
				
			} finally {
				closeResources();
			}
	        
	        
	      /*  try (Connection conn = Database.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setInt(1, id);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    String name = rs.getString("name");
	                    int stock = rs.getInt("stock");
	                    int purchasePrice = rs.getInt("purchase_price");
	                    int salePrice = rs.getInt("sale_price");
	                    return new Product(id, name, stock, purchasePrice, salePrice);
	                } else {
	                    throw new SQLException("Product with ID " + id + " not found.");
	                }
	            }
	        }*/
	    }
	
//	public ArrayList<Product> getSaledProductsList() throws SQLException {
//		ArrayList<Product> saledproductList = new ArrayList<>();
//		Product objectProduct = null;
//		try {
//			statement = connection.createStatement();
//			resultSet = statement.executeQuery("SELECT * FROM saledProduct");
//			while (resultSet.next()) {
//				objectProduct = new Product(resultSet.getInt("id"), resultSet.getString("name"),
//						resultSet.getInt("purchase_price"), resultSet.getInt("sale_price"), resultSet.getInt("stock"));
//				saledproductList.add(objectProduct);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}  finally {
//			closeResources();
//		}
//
//		return saledproductList;
//	}

	public boolean addStockProduct(String productName, int purchasePrice, int salePrice, int stock) throws SQLException {
		String queryString = "INSERT INTO product (name,purchase_price,sale_price,stock) VALUES (?,?,?,?)";
		boolean key = false;

		try {
			statement = connection.createStatement();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(1, productName);
			preparedStatement.setInt(2, purchasePrice);
			preparedStatement.setInt(3, salePrice);
			preparedStatement.setInt(4, stock);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public boolean updateProductStock(int stock, String productName) {
		String query = "UPDATE product SET stock = ? where name = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, stock);
			preparedStatement.setString(2, productName);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	public boolean addSaleProduct(int selectedProductNumber,int productID, int purchasePrice, int salePrice, int stock,Double premiumPercentage,Double premiumFee,Double profit,int sellingEmployeeName) throws SQLException {
		String query = "INSERT INTO saledproduct (selected_product_number,product,purchase_price,sale_price,stock,premium_percentage,premium_fee,profit,selling_employee,transaction_date) VALUES (?,?,?,?,?,?,?,?,?,?)";
		boolean key = false;

		try {
			statement = connection.createStatement();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, selectedProductNumber);
			preparedStatement.setInt(2, productID);
			preparedStatement.setInt(3, purchasePrice);
			preparedStatement.setInt(4, salePrice);
			preparedStatement.setInt(5, stock);
			preparedStatement.setDouble(6, premiumPercentage);
			preparedStatement.setDouble(7, premiumFee);
			preparedStatement.setDouble(8, profit);
			preparedStatement.setInt(9, sellingEmployeeName);
			preparedStatement.setTimestamp(10, new Timestamp(new Date().getTime()));
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public int getIdByName (String name) {
		String query = "Select * FROM product WHERE name = ?";
		int id=-1;
		try {
			statement = connection.createStatement();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				
				id = resultSet.getInt("id");
			}
			return id;
		} catch (Exception e) {
			// TODO: handle exception
			return id;
		}
	}
	
	public String getNameById (int id) {
		String query = "Select * FROM product WHERE id = ?";
		String name = " ";
		try {
			statement = connection.createStatement();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				
				name = resultSet.getString("name");
			}
			return name;
		} catch (Exception e) {
			// TODO: handle exception
			return name;
		}
	}

	
	public boolean updateProduct(int id, String name, int purchasePrice, int salePrice, int stock) {
		String query = "UPDATE product SET name = ?, purchase_price = ?, sale_price = ?, stock= ? WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, purchasePrice);
			preparedStatement.setInt(3, salePrice);
			preparedStatement.setInt(4, stock);
			preparedStatement.setInt(5, id);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	public boolean deleteProduct(int id) {
		
		String query = "UPDATE product SET deleted = 1 WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
		
//		String query = "DELETE FROM product WHERE id = ?";
//		try {
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setInt(1, id);
//			preparedStatement.executeUpdate();
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			closeResources();
//		}
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

	

	public void updateStockById(int id, int newStock) throws SQLException {
        String query = "UPDATE product SET stock = ? WHERE id = ?";
       preparedStatement  = connection.prepareStatement(query);
    		   
            preparedStatement.setInt(1, newStock);
            preparedStatement.setInt(2, id);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Product with ID " + id + " not found.");
            }
        
    }
	
	public List<Product> searchProduct(String name) throws SQLException {
        String query = "SELECT * FROM products WHERE name LIKE ?";
        List<Product> products = new ArrayList<>();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement = connection.prepareStatement(query);
            		 {
            preparedStatement.setString(1, "%" + name + "%");
           resultSet = preparedStatement.executeQuery();
        		   
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String productName = resultSet.getString("name");
                    int stock = resultSet.getInt("stock");
                    int purchasePrice = resultSet.getInt("purchase_price");
                    int salePrice = resultSet.getInt("sale_price");
                    products.add(new Product(id, productName, stock, purchasePrice, salePrice));
                }
            
        }
        return products;
    }
	
	public int getTodaySoldProductCount() throws SQLException {
	    String query = "SELECT SUM(selected_product_number) FROM saledproduct WHERE DATE(transaction_date) = CURDATE()";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
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

	public double getTodayTotalProfit() throws SQLException {
	    String query = "SELECT SUM((sale_price - purchase_price) * selected_product_number) FROM saledproduct WHERE DATE(transaction_date) = CURDATE()";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
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
	
	public int getThisMonthSoldProductCount() throws SQLException {
	    String query = "SELECT SUM(selected_product_number) FROM saledproduct WHERE YEAR(transaction_date) = YEAR(CURDATE()) AND MONTH(transaction_date) = MONTH(CURDATE())";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
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

	public double getThisMonthTotalProfit() throws SQLException {
	    String query = "SELECT SUM((sale_price - purchase_price) * selected_product_number) FROM saledproduct WHERE YEAR(transaction_date) = YEAR(CURDATE()) AND MONTH(transaction_date) = MONTH(CURDATE())";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
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
}