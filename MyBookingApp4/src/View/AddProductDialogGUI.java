package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Product;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AddProductDialogGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField_salePrice;
	private JTextField textField_purchasePrice;
	private JTextField textField_stock;
	private JTextField textField_productName;
	static Product product = new Product();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddProductDialogGUI dialog = new AddProductDialogGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddProductDialogGUI() {
		setBounds(100, 100, 285, 300);
		setTitle("Ürün Ekle");
		setModal(true);
		
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 269, 228);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel lbl_productName = new JLabel("Ürün Adı :");
		lbl_productName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_productName.setBounds(10, 54, 67, 14);
		contentPanel.add(lbl_productName);
		
		JLabel lbl_stock = new JLabel("Stok :");
		lbl_stock.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_stock.setBounds(10, 79, 67, 14);
		contentPanel.add(lbl_stock);
		
		JLabel lbl_purchasePrice = new JLabel("Alış Fiyatı :");
		lbl_purchasePrice.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_purchasePrice.setBounds(10, 104, 67, 14);
		contentPanel.add(lbl_purchasePrice);
		
		JLabel lbl_salePrice = new JLabel("Satış Fiyatı :");
		lbl_salePrice.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_salePrice.setBounds(10, 129, 80, 14);
		contentPanel.add(lbl_salePrice);
		
		textField_salePrice = new JTextField();
		textField_salePrice.setBounds(94, 127, 86, 20);
		contentPanel.add(textField_salePrice);
		textField_salePrice.setColumns(10);
		
		textField_purchasePrice = new JTextField();
		textField_purchasePrice.setColumns(10);
		textField_purchasePrice.setBounds(94, 102, 86, 20);
		contentPanel.add(textField_purchasePrice);
		
		textField_stock = new JTextField();
		textField_stock.setColumns(10);
		textField_stock.setBounds(94, 77, 86, 20);
		contentPanel.add(textField_stock);
		
		textField_productName = new JTextField();
		textField_productName.setColumns(10);
		textField_productName.setBounds(94, 52, 86, 20);
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
								|| textField_purchasePrice.getText().length() == 0 || textField_salePrice.getText().length() == 0) {
							Helper.showMsg("fill");
						} else {
							boolean control = false;
							try {
								control = product.addProduct(textField_productName.getText(), Integer.parseInt(textField_purchasePrice.getText()), Integer.parseInt(textField_salePrice.getText()), 
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
								Helper.showMsg("qq");
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
			            setVisible(false);
			        }	
			    });
			    buttonPane.add(cancelButton);
			}
		}
	}
}