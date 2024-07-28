package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.naming.event.NamespaceChangeListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Product;
import View.ProductManagementGUI.ButtonEditor;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddProductDialogGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField_salePrice;
	private JTextField textField_purchasePrice;
	private JTextField textField_stock;
	private JTextField textField_productName;
	private int selectedProductId;
	static Product product = new Product();

	public static void main(String[] args) {
		try {
			AddProductDialogGUI dialog = new AddProductDialogGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public AddProductDialogGUI(int productId) {
		this();
        setTitle("Ürünü Düzenle");
        selectedProductId = productId;
        loadProductDetails(productId);
    }

	
	public AddProductDialogGUI(int productId, String productName, int stock, int purchasePrice, int salePrice) {
	    this(); // Call the default constructor for GUI initialization

	    // Pre-fill text fields with received data
	    selectedProductId = productId;
	    textField_productName.setText(productName);
	    textField_stock.setText(String.valueOf(stock));
	    textField_purchasePrice.setText(String.valueOf(purchasePrice));
	    textField_salePrice.setText(String.valueOf(salePrice));
	  }
	
	   private void loadProductDetails(int productId) {
	        try {
	        	Product updateProduct= product.getProductById(productId);
	          //  Product product = Product.getProductById(productId);
	            textField_productName.setText(updateProduct.getName());
	            textField_stock.setText(String.valueOf(updateProduct.getStock()));
	            textField_purchasePrice.setText(String.valueOf(updateProduct.getPurchasePrice()));
	            textField_salePrice.setText(String.valueOf(updateProduct.getSalePrice()));
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	public AddProductDialogGUI() {

		JLabel lbl_showValidationStock = new JLabel("");
		lbl_showValidationStock.setForeground(Color.RED);
		lbl_showValidationStock.setBounds(190, 57, 80, 20);
		contentPanel.add(lbl_showValidationStock);

		JLabel lbl_showValidationSalePrice = new JLabel("");
		lbl_showValidationSalePrice.setForeground(Color.RED);
		lbl_showValidationSalePrice.setBounds(190, 143, 80, 20);
		contentPanel.add(lbl_showValidationSalePrice);

		setBounds(100, 100, 306, 300);
		setTitle("Ürün Ekle");
		setModal(true);

		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 269, 228);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JLabel lbl_productName = new JLabel("Ürün Adı :");
		lbl_productName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_productName.setBounds(10, 20, 67, 14);
		contentPanel.add(lbl_productName);

		JLabel lbl_stock = new JLabel("Stok :");
		lbl_stock.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_stock.setBounds(10, 59, 67, 14);
		contentPanel.add(lbl_stock);

		JLabel lbl_purchasePrice = new JLabel("Alış Fiyatı :");
		lbl_purchasePrice.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_purchasePrice.setBounds(10, 101, 67, 14);
		contentPanel.add(lbl_purchasePrice);

		JLabel lbl_salePrice = new JLabel("Satış Fiyatı :");
		lbl_salePrice.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_salePrice.setBounds(10, 145, 80, 14);
		contentPanel.add(lbl_salePrice);

		textField_salePrice = new JTextField();
		textField_salePrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = textField_salePrice.getText();
				if (text.length() != 0) {
					try {
						int i = Integer.parseInt(text);
						lbl_showValidationSalePrice.setText("");
					} catch (NumberFormatException e1) {
						lbl_showValidationSalePrice.setText("Geçersiz Sayı");
					}
				} else {
					lbl_showValidationSalePrice.setText("");
				}
			}
		});
		textField_salePrice.setBounds(94, 143, 86, 20);
		contentPanel.add(textField_salePrice);
		textField_salePrice.setColumns(10);

		JLabel lbl_showValidationPurchasePrice = new JLabel("");
		lbl_showValidationPurchasePrice.setForeground(Color.RED);
		lbl_showValidationPurchasePrice.setBounds(190, 99, 80, 20);
		contentPanel.add(lbl_showValidationPurchasePrice);

		textField_purchasePrice = new JTextField();
		textField_purchasePrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = textField_purchasePrice.getText();
				if (text.length() != 0) {
					try {
						int i = Integer.parseInt(text);
						lbl_showValidationPurchasePrice.setText("");
					} catch (NumberFormatException e1) {
						lbl_showValidationPurchasePrice.setText("Geçersiz Sayı");
					}
				} else {
					lbl_showValidationPurchasePrice.setText("");
				}
			}
		});

		textField_purchasePrice.setColumns(10);
		textField_purchasePrice.setBounds(94, 99, 86, 20);
		contentPanel.add(textField_purchasePrice);

		textField_stock = new JTextField();
		textField_stock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				String text = textField_stock.getText();
				if (text.length() != 0) {
					try {
						int i = Integer.parseInt(text);
						lbl_showValidationStock.setText("");
					} catch (NumberFormatException e1) {
						lbl_showValidationStock.setText("Geçersiz Sayı");
					}
				} else {
					lbl_showValidationStock.setText("");
				}
			}
		});

		textField_stock.setColumns(10);
		textField_stock.setBounds(94, 57, 86, 20);
		contentPanel.add(textField_stock);

		textField_productName = new JTextField();
		textField_productName.setColumns(10);
		textField_productName.setBounds(94, 18, 86, 20);
		contentPanel.add(textField_productName);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 228, 269, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton saveButton = new JButton("Kaydet");
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (textField_productName.getText().length() == 0 || textField_stock.getText().length() == 0
								|| textField_purchasePrice.getText().length() == 0
								|| textField_salePrice.getText().length() == 0) {
							Helper.showMsg("fill");
						} else if (selectedProductId != 0) 
						{
							boolean control = false;
							try {
								control = product.updateProduct(selectedProductId, textField_productName.getText(),
										Integer.parseInt(textField_purchasePrice.getText()),
										Integer.parseInt(textField_salePrice.getText()),
										Integer.parseInt(textField_stock.getText()));
							} catch (NumberFormatException e1) {
								e1.printStackTrace();
							}
							if (control) {
								Helper.showMsg("success");
								setVisible(false);

							} else {
								Helper.showMsg("invalid number");
							}
						} else {
							boolean control = false;
							try {
								control = product.addStockProduct(textField_productName.getText(),
										Integer.parseInt(textField_purchasePrice.getText()),
										Integer.parseInt(textField_salePrice.getText()),
										Integer.parseInt(textField_stock.getText()));
							} catch (NumberFormatException e1) {
								e1.printStackTrace();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							if (control) {
								Helper.showMsg("success");
								setVisible(false);

							} else {
								Helper.showMsg("invalid number");
							}

						}
							
						

					}
				});
				saveButton.setActionCommand("OK");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton cancelButton = new JButton("İptal");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						dispose();
						 //setVisible(false);
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

	
}