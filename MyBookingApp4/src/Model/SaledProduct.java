package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Helper.DBConnection;

public class SaledProduct  extends Product{
	DBConnection dbConnection;
	Connection connection;
	ResultSet resultSet;
	Statement statement;
	PreparedStatement preparedStatement;
	//private static SaledProduct saledProduct = new SaledProduct();
	
	private int selectedProductNumber;
	private double premiumPercentage;
	private double premiumFee;
	private double profit;
	private int sellingEmployeeID;
	private Timestamp transactionDate;
	static Product product = new Product();




	
	
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
		
	}
	


	
	public SaledProduct(int id,int selectedProductNumber,int productID, int purchasePrice, int salePrice, int stock,double premiumPercentage,double premiumFee,double profit,int sellingEmployeeID,Timestamp transactionDate) {
		super(id, product.getNameById(productID), purchasePrice, salePrice, stock);
		//this.id = id;
		this.selectedProductNumber=selectedProductNumber;
		//this.name=name;
		//this.setPurchasePrice(purchasePrice);
		//this.setSalePrice(salePrice);
		//this.stock = stock;
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
				objectSaledProduct = new SaledProduct(resultSet.getInt("id"), resultSet.getInt("selected_product_number"),resultSet.getInt("product"),
						resultSet.getInt("purchase_price"), resultSet.getInt("sale_price"), resultSet.getInt("stock"),resultSet.getDouble("premium_percentage"),
						resultSet.getDouble("premium_fee"),resultSet.getDouble("profit"),resultSet.getInt("selling_employee"),resultSet.getTimestamp("transaction_date"));
				saledProductList.add(objectSaledProduct);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			closeResources();
		}

		return saledProductList;
	}
	
	

	
	public DefaultTableModel getFilteredSales(String employeeName, JDateChooser startDate, JDateChooser endDate, String productName) {
		try {
			 int paramIndex = 1;
			 
			  String query = "SELECT sp.id, sp.selected_product_number, p.name as product_name, sp.purchase_price, sp.sale_price, e.name as employee_name, " +
	                   "sp.premium_percentage, sp.premium_fee, sp.profit, sp.transaction_date " +
	                   "FROM saledproduct sp " +
	                   "JOIN employee e ON sp.selling_employee = e.id " +
	                   "JOIN product p ON sp.product = p.id " +
	                   "WHERE 1=1 ";
			  if (employeeName != null && !employeeName.isEmpty()) {
			        query += "AND e.name LIKE ? ";
			    }
			    if (startDate.getDate() != null) {
			        query += "AND sp.transaction_date >= ? ";
			    }
			    if (endDate.getDate() != null) {
			        query += "AND sp.transaction_date <= ? ";
			    }
			    if (productName != null && !productName.isEmpty()) {
			        query += "AND p.name LIKE ? ";
			    }
			
			preparedStatement = connection.prepareStatement(query);

			
			
		        
		        if (employeeName != null && !employeeName.isEmpty()) {
		            preparedStatement.setString(paramIndex++, "%" + employeeName + "%");
		        }
		        if (startDate.getDate() != null) {
		           preparedStatement.setTimestamp(paramIndex++, new java.sql.Timestamp(startDate.getDate().getTime()));
		        }
		        if (endDate.getDate() != null) {
		           preparedStatement.setTimestamp(paramIndex++, new java.sql.Timestamp(endDate.getDate().getTime()));
		        }
		        if (productName != null && !productName.isEmpty()) {
		            preparedStatement.setString(paramIndex++,"%" + productName + "%");
		        }
		        
		        
		        resultSet = preparedStatement.executeQuery();
	            DefaultTableModel model = new DefaultTableModel(
	                new String[]{"ID", "Satılan Ürün Adedi", "Ürün Adı", "Alış Fiyatı", "Satış Fiyatı", "Satan Çalışan", "Alınan Prim Yüzdesi", "Alınan Prim", "Elde Edilen Kar", "Satış Tarihi"}, 0);

	            while (resultSet.next()) {
	                model.addRow(new Object[]{
	                    resultSet.getInt("id"),
	                    resultSet.getInt("selected_product_number"),
	                    resultSet.getString("product_name"),
	                    resultSet.getInt("purchase_price"),
	                    resultSet.getInt("sale_price"),
	                    resultSet.getString("employee_name"),
	                    resultSet.getDouble("premium_percentage"),
	                    resultSet.getDouble("premium_fee"),
	                    resultSet.getDouble("profit"),
	                    resultSet.getTimestamp("transaction_date")
	                });
	            }
			
	            return model;
		
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			return null;
		}
		
		
		
		
	}

	 
}






















