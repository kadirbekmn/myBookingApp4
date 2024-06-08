package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import Helper.Helper;
import Model.Employee;
import Model.Manager;
import Model.Product;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Iterator;

public class ProductManagementGUI extends JFrame {

    private JPanel w_pane;
    private static Manager manager = new Manager();
    private static Product product = new Product();
    private static AddProductDialogGUI addProductDialog;
    private static DefaultTableModel productDefaultTableModel;
    private static DefaultTableModel selectedProductDefaultTableModel;
    private Object[] productDataObjects = null;
    private Object[] selectedProductDataObjects = null;
    private JPanel top_panel;
    private JLabel lbl_welcome;
    private JPanel center_panel;
    private JTextField textField_productName;
    private static JTable table_product;
    private JButton btn_exit;
    private JPanel panel_buttons;
    private static JTable table_selected_product;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ProductManagementGUI frame = new ProductManagementGUI(manager);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ProductManagementGUI(Manager manager) {

        productDefaultTableModel = new DefaultTableModel();
        Object[] columnProductTable = {"ID", "Ürün Adı", "Stok", "Alış Fiyatı", "Satış Fiyatı", ""};
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
            e.printStackTrace();
        }

        table_product = new JTable(productDefaultTableModel);
        table_product.setRowHeight(40);
        table_product.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int lastColumnIndex = table_product.getColumnModel().getColumnCount() - 1;
        TableColumn lastColumn = table_product.getColumnModel().getColumn(lastColumnIndex);
        lastColumn.setMinWidth(110);
        lastColumn.setCellRenderer(new ButtonRenderer());
        lastColumn.setCellEditor(new ButtonEditor());

        setSize(1057, 696);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        w_pane = new JPanel();
        w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(w_pane);
        w_pane.setLayout(new BorderLayout(0, 0));
        w_pane.setPreferredSize(getPreferredSize());

        top_panel = new JPanel();
        w_pane.add(top_panel, BorderLayout.NORTH);
        top_panel.setLayout(new BorderLayout(0, 0));

        lbl_welcome = new JLabel("Ürün Yönetim Ekranı");
        lbl_welcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
        top_panel.add(lbl_welcome, BorderLayout.WEST);

        btn_exit = new JButton("Menüye Geri Dön");
        btn_exit.addActionListener(e -> {
            MenuGUI menuGUI = new MenuGUI();
            menuGUI.setVisible(true);
            dispose();
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
        btn_addProduct.addActionListener(e -> {
            try {
                addProductDialog = new AddProductDialogGUI();
                addProductDialog.setVisible(true);
                updateProductDefaultTableModel();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        btn_addProduct.setBounds(284, 38, 90, 25);
        center_panel.add(btn_addProduct);

        JScrollPane w_scrollProduct = new JScrollPane();
        w_scrollProduct.setBounds(10, 70, 503, 224);
        center_panel.add(w_scrollProduct);

        w_scrollProduct.setViewportView(table_product);
        
        JScrollPane w_scrollSelectedProduct = new JScrollPane();
        w_scrollSelectedProduct.setBounds(10, 308, 603, 194);
        center_panel.add(w_scrollSelectedProduct);
        
        selectedProductDefaultTableModel = new DefaultTableModel();
        Object[] columnSelectedProductTable = {"Seçilen Ürün Adedi", "Ürün Adı", "Stok", "Alış Fiyatı", "Satış Fiyatı", "Prim Yüzdesi", "Prim Ücredi","Kar"};
        selectedProductDefaultTableModel.setColumnIdentifiers(columnSelectedProductTable);
       
       
          
          table_selected_product = new JTable(selectedProductDefaultTableModel);
          table_selected_product.setRowHeight(40);
          table_selected_product.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
          
          w_scrollSelectedProduct.setViewportView(table_selected_product);
          
          
        
          
          JComboBox cmbBox_worker = new JComboBox();
          Employee employeeModel = new Employee();
          try {
              java.util.List<Employee> employeeList = employeeModel.getEmployeeList("");
              for (Employee employee : employeeList) {
                  cmbBox_worker.addItem(employee.getName());
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
          cmbBox_worker.setToolTipText("");
          cmbBox_worker.setBounds(10, 513, 161, 30);
          center_panel.add(cmbBox_worker);
          
          cmbBox_worker.insertItemAt("Çalışan Seç", 0);
          cmbBox_worker.setSelectedIndex(0);
          
         
          JButton btn_activateTransaction = new JButton("İşlemi Gerçekleştir");
          btn_activateTransaction.addActionListener(new ActionListener() {
          	public void actionPerformed(ActionEvent e) {
          		
          		String selectedEmployeeName = cmbBox_worker.getSelectedItem().toString();
          		if (selectedEmployeeName.equals("Çalışan Seç")) {
					Helper.showMsg("select employee");
					return;
				}
          		if (selectedProductDefaultTableModel.getRowCount() == 0) {
					Helper.showMsg("add product");
					return;
				}
          		
          		for (int i=0; i < selectedProductDefaultTableModel.getRowCount();i++) {
						/*
						 * int selectedProductNumber = (int)
						 * selectedProductDefaultTableModel.getValueAt(i, 0); String productName =
						 * selectedProductDefaultTableModel.getValueAt(i, 1).toString(); int
						 * purchasePrice = ((Number) selectedProductDefaultTableModel.getValueAt(i,
						 * 3)).intValue(); int salePrice = ((Number)
						 * selectedProductDefaultTableModel.getValueAt(i, 4)).intValue(); int stock =
						 * ((Number) selectedProductDefaultTableModel.getValueAt(i, 2)).intValue();
						 * double premiumPercentage = ((Number)
						 * selectedProductDefaultTableModel.getValueAt(i, 5)).doubleValue(); double
						 * premiumFee = ((Number) selectedProductDefaultTableModel.getValueAt(i,
						 * 6)).doubleValue(); double profit = ((Number)
						 * selectedProductDefaultTableModel.getValueAt(i, 7)).doubleValue();
						 */
          			 int selectedProductNumber = Integer.parseInt(selectedProductDefaultTableModel.getValueAt(i, 0).toString());
                     String productName = selectedProductDefaultTableModel.getValueAt(i, 1).toString();
                     int purchasePrice = Integer.parseInt(selectedProductDefaultTableModel.getValueAt(i, 3).toString());
                     int salePrice = Integer.parseInt(selectedProductDefaultTableModel.getValueAt(i, 4).toString());
                     int stock = Integer.parseInt(selectedProductDefaultTableModel.getValueAt(i, 2).toString());
                     double premiumPercentage = Double.parseDouble(selectedProductDefaultTableModel.getValueAt(i, 5).toString());
                     double premiumFee = Double.parseDouble(selectedProductDefaultTableModel.getValueAt(i, 6).toString());
                     double profit = Double.parseDouble(selectedProductDefaultTableModel.getValueAt(i, 7).toString());

                     try {
                         // Call the addSaleProduct method with the gathered data
                         boolean result = product.addSaleProduct(selectedProductNumber, productName, purchasePrice, salePrice, stock, premiumPercentage, premiumFee, profit, selectedEmployeeName);
                         if (result) {
                             Helper.showMsg("success sale product");
                         } else {
                             Helper.showMsg("error sale product");
                         }
                     } catch (SQLException ex) {
                         ex.printStackTrace();
                         Helper.showMsg("error sale product");
                     }
                     try {
						boolean result = product.updateProductStock(stock, productName);
					} catch (Exception e2) {
						// TODO: handle exception
					}
                 }

                 // Clear the selected product table after transaction
                 selectedProductDefaultTableModel.setRowCount(0);
				}
          		
          	
          });
       
          btn_activateTransaction.setBounds(452, 513, 161, 30);
          center_panel.add(btn_activateTransaction);
        
          
			/*
			 * selectedProductDefaultTableModel.addTableModelListener(new
			 * TableModelListener() {
			 * 
			 * @Override public void tableChanged(TableModelEvent e) { // TODO
			 * Auto-generated method stub if (e.getType() == TableModelEvent.UPDATE &&
			 * e.getColumn() == 5) { int row = e.getFirstRow(); DefaultTableModel model =
			 * (DefaultTableModel) e.getSource();
			 * 
			 * // Değerleri doğru türde alalım double kar = ((Number) model.getValueAt(row,
			 * 7)).doubleValue(); double primYuzdesi = ((Number) model.getValueAt(row,
			 * 5)).doubleValue();
			 * 
			 * // Prim ücretini hesapla double primUcreti = (kar * primYuzdesi) / 100;
			 * 
			 * // Hesaplanan değerleri tabloya yazalım model.setValueAt(primUcreti, row, 6);
			 * model.setValueAt(kar - primUcreti, row, 7); } } });
			 */
          selectedProductDefaultTableModel.addTableModelListener(new TableModelListener() {
        	    @Override
        	    public void tableChanged(TableModelEvent e) {
        	        if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 5) {
        	            int row = e.getFirstRow();
        	            DefaultTableModel model = (DefaultTableModel) e.getSource();

        	            try {
        	                // Değerleri Number nesneleri olarak alın
        	                double kar = Double.parseDouble(model.getValueAt(row, 7).toString());
        	                double primYuzdesi = Double.parseDouble(model.getValueAt(row, 5).toString());

        	                // Prim ücretini hesaplayın
        	                double primUcreti = (kar * primYuzdesi) / 100;

        	                // Tablo modelini güncelleyin
        	                model.setValueAt(primUcreti, row, 6);
        	                model.setValueAt(kar - primUcreti, row, 7);
        	            } catch (NumberFormatException ex) {
        	                ex.printStackTrace();
        	                // Hata mesajı göster veya logla
        	            }
        	        }
        	    }
        	});


        


    }

    public static void updateSelectedProductDefaultTableModel() throws SQLException {
        DefaultTableModel clearTableModel = (DefaultTableModel) table_selected_product.getModel();
        clearTableModel.setRowCount(0);
        for (int i = 0; i < product.getProductsList().size(); i++) {
            Object[] productDataObjects = new Object[6];
            productDataObjects[0] = product.getProductsList().get(i).getId();
            productDataObjects[1] = product.getProductsList().get(i).getName();
            productDataObjects[2] = product.getProductsList().get(i).getStock();
            productDataObjects[3] = product.getProductsList().get(i).getPurchasePrice();
            productDataObjects[4] = product.getProductsList().get(i).getSalePrice();
            productDataObjects[5] = "";
            productDefaultTableModel.addRow(productDataObjects);
        }
    }
    
    public static void updateProductDefaultTableModel() throws SQLException {
        DefaultTableModel clearTableModel = (DefaultTableModel) table_product.getModel();
        clearTableModel.setRowCount(0);
        for (int i = 0; i < product.getProductsList().size(); i++) {
            Object[] productDataObjects = new Object[6];
            productDataObjects[0] = product.getProductsList().get(i).getId();
            productDataObjects[1] = product.getProductsList().get(i).getName();
            productDataObjects[2] = product.getProductsList().get(i).getStock();
            productDataObjects[3] = product.getProductsList().get(i).getPurchasePrice();
            productDataObjects[4] = product.getProductsList().get(i).getSalePrice();
            productDataObjects[5] = "";
            productDefaultTableModel.addRow(productDataObjects);
        }
    }


    class ButtonRenderer extends JPanel implements TableCellRenderer {
        Image img_plus = new ImageIcon(this.getClass().getResource("plus.png")).getImage();
        Image img_delete = new ImageIcon(this.getClass().getResource("delete.png")).getImage();
        Image img_edit = new ImageIcon(this.getClass().getResource("edit.png")).getImage();

        private JButton btn_plus;
        private JButton btn_edit;
        private JButton btn_delete;

        public ButtonRenderer() {
            super();
            setLayout(new GridLayout(1, 3)); // GridLayout kullanarak 1 satır ve 3 sütun oluşturuyoruz

            btn_plus = new JButton();
            btn_plus.setIcon(new ImageIcon(img_plus));
            add(btn_plus);

            btn_edit = new JButton();
            btn_edit.setIcon(new ImageIcon(img_edit));
            add(btn_edit);

            btn_delete = new JButton();
            btn_delete.setIcon(new ImageIcon(img_delete));
            add(btn_delete);
        }

        @Override
        public ButtonRenderer getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

   static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JPanel panel;
        private JButton btn_plus;
        private JButton btn_edit;
        private JButton btn_delete;
        private Object clickedObject;
        public int selectedProductId;
        public  String selectedProductName;
        public int selectedSaleProduct = 1;
        public int selectedProductStock;
        public int selectedProductPurchasePrice;
        public int selectedProductSalePrice;
        private Object[] selectedProductDataObjects = null;
        
        

        public ButtonEditor() {
            panel = new JPanel();
            panel.setLayout(new GridLayout(1, 3)); // GridLayout kullanarak 1 satır ve 3 sütun oluşturuyoruz

            btn_plus = new JButton();
            btn_plus.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("plus.png")).getImage()));
            btn_plus.addActionListener(this);

            btn_edit = new JButton();
            btn_edit.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("edit.png")).getImage()));
            btn_edit.addActionListener(this);

            btn_delete = new JButton();
            btn_delete.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("delete.png")).getImage()));
            btn_delete.addActionListener(this);

            panel.add(btn_plus);
            panel.add(btn_edit);
            panel.add(btn_delete);
           
        }

        @Override
        public Object getCellEditorValue() {
            return clickedObject;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        	
            int selectedRow = table_product.getSelectedRow();
            if (selectedRow != -1) {
            	 
            // Satır seçildiyse devam et
			/*
			 * selectedProductId = (int) table_product.getValueAt(selectedRow, 0); // Ürün
			 * ID'sini al selectedProductName = table_product.getValueAt(selectedRow,
			 * 1).toString(); selectedProductStock = (int)
			 * table_product.getValueAt(selectedRow, 2); // Ürün ID'sini al
			 * selectedProductPurchasePrice = (int) table_product.getValueAt(selectedRow,
			 * 3); // Ürün ID'sini al selectedProductSalePrice = (int)
			 * table_product.getValueAt(selectedRow, 4); // Ürün ID'sini al
			 */                
            	 selectedProductId = Integer.parseInt(table_product.getValueAt(selectedRow, 0).toString());
                 selectedProductName = table_product.getValueAt(selectedRow, 1).toString();
                 selectedProductStock = Integer.parseInt(table_product.getValueAt(selectedRow, 2).toString());
                 selectedProductPurchasePrice = Integer.parseInt(table_product.getValueAt(selectedRow, 3).toString());
                 selectedProductSalePrice = Integer.parseInt(table_product.getValueAt(selectedRow, 4).toString());
                if (e.getSource() == btn_plus) {
                	  if (selectedProductStock > 0) {
                          selectedProductStock--; // Stoktan bir adet eksilt
                          double currentProfit = (selectedProductSalePrice - selectedProductPurchasePrice) ;
                          

                	
                      boolean productExists = false;
                      for (int i = 0; i < selectedProductDefaultTableModel.getRowCount(); i++) {
                          if (selectedProductName.equals(selectedProductDefaultTableModel.getValueAt(i, 1))) {
                              selectedSaleProduct = (int) selectedProductDefaultTableModel.getValueAt(i, 0) + 1;                            
                              selectedProductDefaultTableModel.setValueAt(selectedSaleProduct, i, 0);
                              selectedProductDefaultTableModel.setValueAt(selectedProductStock, i, 2);
                              selectedProductDefaultTableModel.setValueAt(currentProfit  + ((Number) selectedProductDefaultTableModel.getValueAt(i, 7)).doubleValue(), i, 7);
                              productExists = true;
                              break;
                          }
                      }
                      if (!productExists) {
                          selectedProductDataObjects = new Object[8];
                          selectedProductDataObjects[0] = selectedSaleProduct; // Initial count is 1
                          selectedProductDataObjects[1] = selectedProductName;
                          selectedProductDataObjects[2] = selectedProductStock;
                          selectedProductDataObjects[3] = selectedProductPurchasePrice;
                          selectedProductDataObjects[4] = selectedProductSalePrice;
                          selectedProductDataObjects[5] = 0.0;
                          selectedProductDataObjects[6] = 0.0;
                          selectedProductDataObjects[7] = currentProfit;
                          selectedProductDefaultTableModel.addRow(selectedProductDataObjects);
                      }
                	
                      table_product.setValueAt(selectedProductStock, selectedRow, 2);
                	
                	
                	  } else {
						Helper.showMsg("no stock");
					}
                    
                } 
                else if (e.getSource() == btn_edit) {
                	
                	 if (Helper.confirm("updateSure")) {
                    	
                         try {
                        	 addProductDialog = new AddProductDialogGUI(selectedProductId,selectedProductName,selectedProductStock,selectedProductPurchasePrice,selectedProductSalePrice);
                        	 addProductDialog.setVisible(true);
                             updateProductDefaultTableModel();
                             
                         } catch (SQLException ex) {
                             ex.printStackTrace();
                             // Silme işlemi sırasında bir hata oluşursa kullanıcıya bildir
                             JOptionPane.showMessageDialog(null, "Ürün silinirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                         }
                     }
                	
                    System.out.println("Edit button clicked for row: " + selectedRow);
                    
                } else if (e.getSource() == btn_delete) {
                	System.out.println(selectedProductId);
                    if (Helper.confirm("sure")) {
                    	 
                        try {
                            
                            product.deleteProduct(selectedProductId);
                        
                            updateProductDefaultTableModel();
                            System.out.println(product.getProductsList().size());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            // Silme işlemi sırasında bir hata oluşursa kullanıcıya bildir
                            JOptionPane.showMessageDialog(null, "Ürün silinirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else {
            	 JOptionPane.showMessageDialog(null, "Lütfen bir ürün seçin!");
			}
        }

    }
}
