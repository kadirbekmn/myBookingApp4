package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;
import View.ProductManagementGUI;

public class Product {

	DBConnection dbConnection = null;
	Connection connection = null;
	ResultSet resultSet = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;

	private int id;
	private String name;
	private int purchasePrice;
	private int salePrice;
	private String type;
	private float price;
	private byte[] image;
	private int stock;
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
			resultSet = statement.executeQuery("SELECT * FROM product");
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
	
	public ArrayList<Product> getSaledProductsList() throws SQLException {
		ArrayList<Product> saledproductList = new ArrayList<>();
		Product objectProduct = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM saledProduct");
			while (resultSet.next()) {
				objectProduct = new Product(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getInt("purchase_price"), resultSet.getInt("sale_price"), resultSet.getInt("stock"));
				saledproductList.add(objectProduct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			closeResources();
		}

		return saledproductList;
	}

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
	
	public boolean addSaleProduct(int selectedProductNumber,String productName, int purchasePrice, int salePrice, int stock,Double premiumPercentage,Double premiumFee,Double profit,String sellingEmployeeName) throws SQLException {
		String query = "INSERT INTO saledproduct (selected_product_number,product_name,purchase_price,sale_price,stock,premium_percentage,premium_fee,profit,selling_employee) VALUES (?,?,?,?,?,?,?,?,?)";
		boolean key = false;

		try {
			statement = connection.createStatement();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, selectedProductNumber);
			preparedStatement.setString(2, productName);
			preparedStatement.setInt(3, purchasePrice);
			preparedStatement.setInt(4, salePrice);
			preparedStatement.setInt(5, stock);
			preparedStatement.setDouble(6, premiumPercentage);
			preparedStatement.setDouble(7, premiumFee);
			preparedStatement.setDouble(8, profit);
			preparedStatement.setString(9, sellingEmployeeName);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
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
		String query = "DELETE FROM product WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
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
	
	private void closeResources() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}