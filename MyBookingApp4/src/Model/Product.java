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

	DBConnection dbConnection = new DBConnection();
	Connection connection = dbConnection.connectDB();
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
		}
		return productList;
	}

	public boolean addProduct(String productName, int purchasePrice, int salePrice, int stock) throws SQLException {
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
}