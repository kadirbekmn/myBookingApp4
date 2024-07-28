package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import Helper.DBConnection;

public class SaledProduct extends Product {
	DBConnection dbConnection = new DBConnection();
	Connection connection = dbConnection.connectDB();
	ResultSet resultSet = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	private static SaledProduct saledProduct = new SaledProduct();
	
	private int selectedProductNumber;
	private double premiumPercentage;
	private double premiumFee;
	private double profit;
	private int sellingEmployeeID;
	private Timestamp transactionDate;



	
	
	public int getSelectedProductNumber() {
		return selectedProductNumber;
	}

	public void setSelectedProductNumber(int selectedProductNumber) {
		this.selectedProductNumber = selectedProductNumber;
	}

	public double getPremiumPercentage() {
		return premiumPercentage;
	}

	public void setPremiumPercentage(double premiumPercentage) {
		this.premiumPercentage = premiumPercentage;
	}

	public double getPremiumFee() {
		return premiumFee;
	}

	public void setPremiumFee(double premiumFee) {
		this.premiumFee = premiumFee;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public int getSellingEmployeeID() {
		return sellingEmployeeID;
	}

	public void setSellingEmployeeID(int sellingEmployeeID) {
		this.sellingEmployeeID = sellingEmployeeID;
	}

	public Timestamp getTransactionTime() {
		return transactionDate;
	}

	public void setTransactionTime(Timestamp transactionTime) {
		this.transactionDate = transactionTime;
	}

	public SaledProduct() {
		dbConnection = new DBConnection();
		connection = dbConnection.connectDB();
		closeResources();
	}
	
	public SaledProduct(int id,int selectedProductNumber,String name, int purchasePrice, int salePrice, int stock,double premiumPercentage,double premiumFee,double profit,int sellingEmployeeID,Timestamp transactionDate) {
		this.id = id;
		this.selectedProductNumber=selectedProductNumber;
		this.name=name;
		this.purchasePrice = purchasePrice;
		this.salePrice= salePrice;
		this.stock = stock;
		this.premiumPercentage = premiumPercentage;
		this.premiumFee = premiumFee;
		this.profit = profit;
		this.sellingEmployeeID = sellingEmployeeID;
		this.transactionDate = transactionDate;
		
	}


	
	public ArrayList<SaledProduct> getSaledProductsList() throws SQLException {
		
		ArrayList<SaledProduct> saledProductList = new ArrayList<>();
		SaledProduct objectSaledProduct = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM saledproduct");
			while (resultSet.next()) {
				objectSaledProduct = new SaledProduct(resultSet.getInt("id"), resultSet.getInt("selected_product_number"),resultSet.getString("product"),
						resultSet.getInt("purchase_price"), resultSet.getInt("sale_price"), resultSet.getInt("stock"),resultSet.getDouble("premium_percentage"),
						resultSet.getDouble("premium_fee"),resultSet.getDouble("profit"),resultSet.getInt("selling_employee"),resultSet.getTimestamp("transaction_date"));
				saledProductList.add(objectSaledProduct);
				closeResources();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			closeResources();
		}

		return saledProductList;
	}



	
	
	 
}






















