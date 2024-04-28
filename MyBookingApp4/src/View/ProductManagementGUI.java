package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import Model.Manager;
import Model.Product;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;

public class ProductManagementGUI extends JFrame {

	private JPanel w_pane;
	static Manager manager = new Manager();
	static Product product = new Product();
	static AddProductDialogGUI addProductDialog;
	static DefaultTableModel productDefaultTableModel;
	private Object[] productDataObjects = null;
	private JPanel top_panel;
	private JLabel lbl_welcome;
	private JPanel center_panel;
	private JTextField textField_productName;
	private JTable table_product;
	private JButton btn_exit;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductManagementGUI frame = new ProductManagementGUI(manager);
					;
					// frame.setUndecorated(true); // Remove frame decorations
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProductManagementGUI(Manager manager) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btn_add = new JButton("➕");

		class ButtonRenderer extends DefaultTableCellRenderer {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JButton button = (JButton) value;
				button.setVisible(true);
				return button;
			}
		}

		productDefaultTableModel = new DefaultTableModel();
		Object[] columnProductTable = new Object[6];
		columnProductTable[0] = "ID";
		columnProductTable[1] = "Ürün Adı";
		columnProductTable[2] = "Stok";
		columnProductTable[3] = "Alış Fiyatı";
		columnProductTable[4] = "Satış Fiyatı";
		columnProductTable[5] = "";
		productDefaultTableModel.setColumnIdentifiers(columnProductTable);
		productDataObjects = new Object[6];

		try {
			for (int i = 0; i < product.getProductsList().size(); i++) {
				productDataObjects[0] = product.getProductsList().get(i).getId();
				productDataObjects[1] = product.getProductsList().get(i).getName();
				productDataObjects[2] = product.getProductsList().get(i).getStock();
				productDataObjects[3] = product.getProductsList().get(i).getPurchasePrice();
				productDataObjects[4] = product.getProductsList().get(i).getSalePrice();
				productDataObjects[5] = btn_add;
				productDefaultTableModel.addRow(productDataObjects);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		table_product = new JTable(productDefaultTableModel);

		TableColumn columnUpdate = table_product.getColumnModel().getColumn(5);
		columnUpdate.setCellRenderer(new ButtonRenderer());

		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(new BorderLayout(0, 0));

		top_panel = new JPanel();
		w_pane.add(top_panel, BorderLayout.NORTH);
		top_panel.setLayout(new BorderLayout(0, 0));

		lbl_welcome = new JLabel("Ürün Yönetim Ekranı");
		lbl_welcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		top_panel.add(lbl_welcome, BorderLayout.WEST);

		btn_exit = new JButton("Menüye Geri Dön");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuGUI menuGUI = new MenuGUI();
				menuGUI.setVisible(true);
				dispose();
			}
		});
		btn_exit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		top_panel.add(btn_exit, BorderLayout.EAST);

		center_panel = new JPanel();
		w_pane.add(center_panel, BorderLayout.CENTER);
		center_panel.setLayout(null);

		JLabel lbl_products = new JLabel("Ürünler");
		lbl_products.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbl_products.setBounds(10, 11, 52, 20);
		center_panel.add(lbl_products);

		JLabel lbl_productName = new JLabel("Ürün Adı :");
		lbl_productName.setBounds(20, 42, 68, 17);
		center_panel.add(lbl_productName);

		textField_productName = new JTextField();
		textField_productName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel obj = (DefaultTableModel) table_product.getModel();
				TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
				table_product.setRowSorter(obj1);
				obj1.setRowFilter(RowFilter.regexFilter(textField_productName.getText()));
			}
		});
		textField_productName.setBounds(89, 39, 86, 20);
		center_panel.add(textField_productName);
		textField_productName.setColumns(10);

		JButton btn_search = new JButton("Ara");
		btn_search.setBounds(185, 38, 89, 25);
		center_panel.add(btn_search);

		JButton btn_addProduct = new JButton("Ürün Ekle");
		btn_addProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addProductDialog = new AddProductDialogGUI();
					addProductDialog.setVisible(true);
					updateProductDefaultTableModel();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_addProduct.setBounds(284, 38, 90, 25);
		center_panel.add(btn_addProduct);

		JScrollPane w_scrollProduct = new JScrollPane();
		w_scrollProduct.setBounds(10, 70, 404, 148);
		center_panel.add(w_scrollProduct);

		w_scrollProduct.setViewportView(table_product);
	}

	public void updateProductDefaultTableModel() throws SQLException {
		DefaultTableModel clearTableModel = (DefaultTableModel) table_product.getModel();
		clearTableModel.setRowCount(0);
		for (int i = 0; i < product.getProductsList().size(); i++) {
			productDataObjects[0] = product.getProductsList().get(i).getId();
			productDataObjects[1] = product.getProductsList().get(i).getName();
			productDataObjects[2] = product.getProductsList().get(i).getStock();
			productDataObjects[3] = product.getProductsList().get(i).getPurchasePrice();
			productDataObjects[4] = product.getProductsList().get(i).getSalePrice();
			productDefaultTableModel.addRow(productDataObjects);
		}
	}
}