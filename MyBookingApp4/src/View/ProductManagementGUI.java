package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import Helper.Helper;
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
    private JPanel center_panel ;
    private JTextField textField_productName;
    private JTable table_product;
    private JButton btn_exit;
    private JPanel panel_butons;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	
                    ProductManagementGUI frame = new ProductManagementGUI(manager);
                   frame.setUndecorated(true); // Remove frame decorations
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
                    frame.setVisible(true);
                 
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     * @throws SQLException 
     */
    public ProductManagementGUI(Manager manager) {
    	
    	

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Image img_plus = new ImageIcon(this.getClass().getResource("plus.png")).getImage();
      		Image img_delete = new ImageIcon(this.getClass().getResource("delete.png")).getImage();
      		Image img_edit = new ImageIcon(this.getClass().getResource("edit.png")).getImage();
      		
      
      		class ButtonRenderer extends JPanel implements TableCellRenderer,ActionListener {

      		    private JButton btn_plus;
      		    private JButton btn_edit;
      		    private JButton btn_delete;

      		    public ButtonRenderer() {      		       		   
      		        super();
   	
  		    	    
      		        setLayout(new FlowLayout(FlowLayout.LEFT)); // Düğmeleri sola hizala

      		        btn_plus = new JButton();
      		        btn_plus.setIcon(new ImageIcon(img_plus));
      		        add(btn_plus);

      		        btn_edit = new JButton();
      		        btn_edit.setIcon(new ImageIcon(img_edit));
      		        add(btn_edit);

      		        btn_delete = new JButton();
      		        btn_delete.setIcon(new ImageIcon(img_delete));
      		        add(btn_delete);
      		        
      		       
      		        
      		        btn_delete.setPreferredSize(new Dimension(30, 30));
            		btn_edit.setPreferredSize(new Dimension(30, 30));
            		btn_plus.setPreferredSize(new Dimension(30, 30));
            		
            		 btn_plus.addActionListener(this);
            	     btn_edit.addActionListener(this);
            	     btn_delete.addActionListener(this);

      		        // Butonlara click listener ekleyebilirsiniz (isteğe bağlı)
      		    }
      		    @Override
      		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      		        // Her satırda bu paneli döndür
      		        return this;
      		    }
      		

      		    
      		  @Override
      	    public void actionPerformed(ActionEvent e) {
      	        // Identify which button was clicked based on the event source
      	        if (e.getSource() == btn_plus) {
      	            // Plus button clicked! Print message to console
      	            System.out.println("Plus button clicked for row: " + table_product.getSelectedRow());
      	            // ... (Your code for Plus button action)
      	        } else if (e.getSource() == btn_edit) {
      	            // Edit button clicked! Print message to console
      	            System.out.println("Edit button clicked for row: " + table_product.getSelectedRow());
      	            // ... (Your code for Edit button action)
      	        } else if (e.getSource() == btn_delete) {
      	            // Delete button clicked! Print message to console
      	            System.out.println("Delete button clicked for row: " + table_product.getSelectedRow()); // Typo corrected (table_product)
      	            // ... (Your code for Delete button action)
      	        }
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
				productDataObjects[5] = "";
				productDefaultTableModel.addRow(productDataObjects);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table_product = new JTable(productDefaultTableModel);
		table_product.setRowHeight(40);
		table_product.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		int lastColumnIndex = table_product.getColumnModel().getColumnCount() - 1;

		table_product.getColumnModel().getColumn(lastColumnIndex).setCellRenderer(new ButtonRenderer());

		TableColumn lastColumn = table_product.getColumnModel().getColumn(lastColumnIndex);

		// Son sütun için 150 piksel genişlik ayarla
		lastColumn.setMinWidth(110);

	
     
        
      


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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        btn_addProduct.setBounds(284, 38, 90, 25);
        center_panel.add(btn_addProduct);
        
        JScrollPane w_scrollProduct = new JScrollPane();
        w_scrollProduct.setBounds(10, 70, 504, 148);
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