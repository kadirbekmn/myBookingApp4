package View;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Employee;
import Model.Product;
import Model.SaledProduct;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class ProductReport extends JFrame {

	private JPanel contentPane;
	private JTextField textField_employee_name;
	private JTable table_saled_product;
	private Product product = new Product();
	private DefaultTableModel productDefaultTableModel;
	private Object[] saledProductDataObjects = null;
	SaledProduct saledProduct = new SaledProduct();
	Employee employee = new Employee();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductReport frame = new ProductReport();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProductReport() {
	initialize();
	}
	
	
	private void initialize() {
		 setTitle("Satılan Ürün Rapor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1366, 776);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel top_panel = new JPanel();
		top_panel.setBounds(10, 11, 1265, 79);
		contentPane.add(top_panel);
		top_panel.setLayout(null);
		
		JLabel lbl_start_date = new JLabel("Başlangıç Tarihi:");
		lbl_start_date.setBounds(232, 15, 117, 14);
		top_panel.add(lbl_start_date);
		
		JLabel lbl_end_date = new JLabel("Bitiş Tarihi:");
		lbl_end_date.setBounds(376, 15, 84, 14);
		top_panel.add(lbl_end_date);
		
		JLabel lbl_employee_name = new JLabel("Çalışan Adı:");
		lbl_employee_name.setBounds(10, 15, 91, 14);
		top_panel.add(lbl_employee_name);
		
		textField_employee_name = new JTextField();
		textField_employee_name.setBounds(111, 12, 86, 20);
		top_panel.add(textField_employee_name);
		textField_employee_name.setColumns(10);
		
		JLabel lbl_product_name = new JLabel("Ürün Adı:");
		lbl_product_name.setBounds(602, 15, 67, 14);
		top_panel.add(lbl_product_name);
		
		JComboBox cmbBox_products = new JComboBox();
		cmbBox_products.setBounds(679, 11, 84, 22);
		top_panel.add(cmbBox_products);
		
		JButton btn_search = new JButton("Ara");
		btn_search.setBounds(10, 40, 89, 23);
		top_panel.add(btn_search);
		
		JPanel center_panel = new JPanel();
		center_panel.setBounds(10, 101, 1265, 471);
		contentPane.add(center_panel);
		center_panel.setLayout(null);
		

		
		table_saled_product = new JTable(createTable());
		table_saled_product.setBounds(1, 1, 450, 0);
		center_panel.add(table_saled_product);
		
		JScrollPane scrollPane_saled_products = new JScrollPane(table_saled_product);
		scrollPane_saled_products.setBounds(10, 0, 1245, 402);
		center_panel.add(scrollPane_saled_products);
		
	
	}
	
	private DefaultTableModel createTable() {
		productDefaultTableModel = new DefaultTableModel();
		Object[] columnProductTable = {"ID", "Satılan Ürün Adedi", "Ürün Adı", "Alış Fiyatı", "Satış Fiyatı", "Satan Çalışan","Alınan Prim Yüzdesi","Alınan Prim","Elde Edilen Kar","Satış Tarihi"};
		productDefaultTableModel.setColumnIdentifiers(columnProductTable);

		saledProductDataObjects = new Object[10];
		
		
		try {
			int siz = saledProduct.getSaledProductsList().size();
			System.out.println(siz);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			for (int i = 0; i < saledProduct.getSaledProductsList().size(); i++) {
				saledProductDataObjects[0] = saledProduct.getSaledProductsList().get(i).getId(); 
				saledProductDataObjects[1] = saledProduct.getSaledProductsList().get(i).getSelectedProductNumber();
				saledProductDataObjects[2] = product.getNameById(Integer.parseInt(saledProduct.getSaledProductsList().get(i).getName()));
				saledProductDataObjects[3] = saledProduct.getSaledProductsList().get(i).getPurchasePrice();
				saledProductDataObjects[4] = saledProduct.getSaledProductsList().get(i).getSalePrice();
				saledProductDataObjects[5] = employee.getNameByID(saledProduct.getSaledProductsList().get(i).getSellingEmployeeID());
				saledProductDataObjects[6] = saledProduct.getSaledProductsList().get(i).getPremiumPercentage();
				saledProductDataObjects[7] = saledProduct.getSaledProductsList().get(i).getPremiumFee();
				saledProductDataObjects[8] = saledProduct.getSaledProductsList().get(i).getProfit();
				saledProductDataObjects[9] = saledProduct.getSaledProductsList().get(i).getTransactionTime();
		
			
				productDefaultTableModel.addRow(saledProductDataObjects);

				//originalStocksMap.put(product.getProductsList().get(i).getName(), product.getProductsList().get(i).getStock());
			}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(5);
			}
		
		return productDefaultTableModel;
	}
}
