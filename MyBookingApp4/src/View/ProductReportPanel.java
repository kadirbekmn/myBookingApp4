package View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import com.toedter.calendar.JDateChooser;
import Model.Employee;
import Model.Product;
import Model.SaledProduct;

public class ProductReportPanel extends JPanel {

    private JTextField textField_employee_name;
    private TableRowSorter<DefaultTableModel> tableRowSorter;
    private JTable table_saled_product;
    private JTextField txtFld_productName;
    private DefaultTableModel productDefaultTableModel;
    private SaledProduct saledProduct = new SaledProduct();
    private Employee employee = new Employee();
    private Product product = new Product();
    private JDateChooser dateStartDate;
    private JDateChooser dateEndDate;
    private JLabel lbl_mostSoldProductNumber;
    private JLabel lbl_totalProfitNumber;
    private JLabel lbl_totalPremiumNumber;
    private JLabel lbl_mostSoldProductName;

    public ProductReportPanel() {
        initialize();
    }

    public void initialize() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        JPanel top_panel = new JPanel();
        top_panel.setBounds(10, 11, 903, 79);
        add(top_panel);
        top_panel.setLayout(null);

        JLabel lbl_start_date = new JLabel("Başlangıç Tarihi:");
        lbl_start_date.setBounds(195, 15, 105, 14);
        top_panel.add(lbl_start_date);

        dateStartDate = new JDateChooser();
        dateStartDate.setBounds(297, 12, 112, 20);
        top_panel.add(dateStartDate);
        
        JLabel lbl_end_date = new JLabel("Bitiş Tarihi:");
        lbl_end_date.setBounds(436, 15, 84, 14);
        top_panel.add(lbl_end_date);
        
        dateEndDate = new JDateChooser();
        dateEndDate.setBounds(517, 12, 112, 20);
        top_panel.add(dateEndDate);

        JLabel lbl_employee_name = new JLabel("Çalışan Adı:");
        lbl_employee_name.setBounds(10, 15, 91, 14);
        top_panel.add(lbl_employee_name);

        textField_employee_name = new JTextField();
        textField_employee_name.setBounds(81, 12, 86, 20);
        top_panel.add(textField_employee_name);
        textField_employee_name.setColumns(10);

        JLabel lbl_product_name = new JLabel("Ürün Adı:");
        lbl_product_name.setBounds(646, 15, 67, 14);
        top_panel.add(lbl_product_name);

        txtFld_productName = new JTextField();
        txtFld_productName.setColumns(10);
        txtFld_productName.setBounds(700, 12, 86, 20);
        top_panel.add(txtFld_productName);

        JPanel center_panel = new JPanel();
        center_panel.setBounds(10, 101, 903, 328);
        add(center_panel);
        center_panel.setLayout(null);

        // Initialize the table and model
        productDefaultTableModel = createTable();
        table_saled_product = new JTable(productDefaultTableModel);
        tableRowSorter = new TableRowSorter<>(productDefaultTableModel);
        table_saled_product.setRowSorter(tableRowSorter);
        
        

        tableRowSorter.setComparator(0, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return Integer.compare(i1, i2);
            }
        });
        
        tableRowSorter.setComparator(1, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return Integer.compare(i1, i2);
            }
        });
        
        tableRowSorter.setComparator(3, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return Integer.compare(i1, i2);
            }
        });
        
        
        tableRowSorter.setComparator(4, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return Integer.compare(i1, i2);
            }
        });
        
        tableRowSorter.setComparator(6, new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return Double.compare(o1, o2);
            }
        });
        
        tableRowSorter.setComparator(7, new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return Double.compare(o1, o2);
            }
        });
        
        tableRowSorter.setComparator(8, new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return Double.compare(o1, o2);
            }
        });
        
        
        JScrollPane scrollPane_saled_products = new JScrollPane(table_saled_product);
        scrollPane_saled_products.setBounds(0, 0, 903, 328);
        center_panel.add(scrollPane_saled_products);

        JButton btn_search = new JButton("Ara");
        btn_search.setBounds(10, 40, 89, 23);
        top_panel.add(btn_search);

        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	searchAction();
            }
        });
        
        JLabel lbl_totalSoldProductNumber = new JLabel("Toplam Satılan Ürün Sayısı :");
        lbl_totalSoldProductNumber.setBounds(20, 440, 166, 14);
        add(lbl_totalSoldProductNumber);
        
        JLabel lbl_totalProfit = new JLabel("Toplam Kar :");
        lbl_totalProfit.setBounds(20, 465, 153, 14);
        add(lbl_totalProfit);
        
        JLabel lbl_totalPremium = new JLabel("Toplam Verilen Prim :");
        lbl_totalPremium.setBounds(20, 496, 153, 14);
        add(lbl_totalPremium);
        
        JLabel lbl_mostSoldProduct = new JLabel("En Çok Satılan Ürün :");
        lbl_mostSoldProduct.setBounds(20, 521, 123, 14);
        add(lbl_mostSoldProduct);
        
        lbl_mostSoldProductNumber = new JLabel("0");
        lbl_mostSoldProductNumber.setBounds(196, 440, 85, 14);
        add(lbl_mostSoldProductNumber);
        
        lbl_totalProfitNumber = new JLabel("0.0");
        lbl_totalProfitNumber.setBounds(196, 465, 85, 14);
        add(lbl_totalProfitNumber);
        
        lbl_totalPremiumNumber = new JLabel("0.0");
        lbl_totalPremiumNumber.setBounds(196, 496, 85, 14);
        add(lbl_totalPremiumNumber);
        
        lbl_mostSoldProductName = new JLabel("Yok");
        lbl_mostSoldProductName.setBounds(196, 521, 145, 14);
        add(lbl_mostSoldProductName);
        
        fillDetailInfo();
        
    }

    private DefaultTableModel createTable() {
        DefaultTableModel model = new DefaultTableModel();
        Object[] columnProductTable = {"ID", "Satılan Ürün Adedi", "Ürün Adı", "Alış Fiyatı", "Satış Fiyatı", "Satan Çalışan", "Alınan Prim Yüzdesi", "Alınan Prim", "Elde Edilen Kar", "Satış Tarihi"};
        model.setColumnIdentifiers(columnProductTable);

        try {
            for (SaledProduct sp : saledProduct.getSaledProductsList()) {
                Object[] rowData = new Object[10];
                rowData[0] = sp.getId();
                rowData[1] = sp.getSelectedProductNumber();
                rowData[2] = sp.getName();
                rowData[3] = sp.getPurchasePrice();
                rowData[4] = sp.getSalePrice();
                rowData[5] = employee.getNameByID(sp.getSellingEmployeeID());
                rowData[6] = sp.getPremiumPercentage();
                rowData[7] = sp.getPremiumFee();
                rowData[8] = sp.getProfit();
                rowData[9] = sp.getTransactionTime();

                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    
    private int totalSoldProduct(DefaultTableModel defaultTableModel) {
    	int total = 0;
        for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
            Object value = defaultTableModel.getValueAt(i, 1); // Ürün adedinin olduğu sütun (1)
            if (value instanceof String) {
                total += Integer.parseInt((String) value);
            } else if (value instanceof Integer) {
                total += (Integer) value;
            }
        }
        return total;
    }
    
    private double totalProfit(DefaultTableModel defaultTableModel) {
    	double total = 0;
    	for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
    		total += (Double)defaultTableModel.getValueAt(i, 8);
    	}
		return total;
    	
    }
    
    private double totalPremium(DefaultTableModel defaultTableModel) {
    	double total = 0;
    	for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
    		total += (Double)defaultTableModel.getValueAt(i, 7);
    	}
		return total;
    	
    }
    
    private String mostSoldProduct(DefaultTableModel defaultTableModel) {
    	Map<String, Integer> productCountMap = new HashMap<>();
    	
    	for (int i = 0; i < defaultTableModel.getRowCount(); i++ ) {
    		String productName = (String) defaultTableModel.getValueAt(i, 2);
    		int productCount = (Integer) defaultTableModel.getValueAt(i, 1);
    		productCountMap.put(productName, productCountMap.getOrDefault(productName, 0) + productCount);
    	}
    	
    	String mostSoldProduct = null;
    	int maxCount = 0;
    	
    	for (Map.Entry<String, Integer> entry : productCountMap.entrySet()) {
    		
    		if (entry.getValue() > maxCount) {
				maxCount = entry.getValue();
				mostSoldProduct = entry.getKey();
			}
    		
    		if (entry.getValue() == maxCount) {
				
			}
    		
    	}
    	
    	return mostSoldProduct != null ? mostSoldProduct + ", " + maxCount : "Yok";
   
    }
    
    private String mostSoldProduct2(DefaultTableModel defaultTableModel) {
        Map<String, Integer> productCountMap = new HashMap<>();
        for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
            String productName = (String) defaultTableModel.getValueAt(i, 2);
            int productCount = (Integer) defaultTableModel.getValueAt(i, 1);
            productCountMap.put(productName, productCountMap.getOrDefault(productName, 0) + productCount);
        }

        List<String> mostSoldProducts = new ArrayList<>();
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : productCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostSoldProducts.clear();
                mostSoldProducts.add(entry.getKey());
                maxCount = entry.getValue();
            } else if (entry.getValue() == maxCount) {
                mostSoldProducts.add(entry.getKey());
            }
        }

        if (mostSoldProducts.isEmpty()) {
            return "Yok";
        } else if (mostSoldProducts.size() == 1) {
            return mostSoldProducts.get(0);
        } else {
            return String.join(", ", mostSoldProducts);
        }
    }

    
    private void searchAction() {
    	  String employeeName = textField_employee_name.getText();
          String productName = txtFld_productName.getText();
          
          tableRowSorter.setSortKeys(null); 
          
        
          // Retrieve filtered data
          DefaultTableModel model = saledProduct.getFilteredSales(employeeName, dateStartDate, dateEndDate, productName);
          tableRowSorter.setRowFilter(null);
         
          
          if (model != null) {
              // Update table model with new data
              productDefaultTableModel.setRowCount(0); // Clear existing data
              for (int i = 0; i < model.getRowCount(); i++) {
                  Object[] rowData = new Object[model.getColumnCount()];
                  for (int j = 0; j < model.getColumnCount(); j++) {
                      rowData[j] = model.getValueAt(i, j);
                  }
                  productDefaultTableModel.addRow(rowData);
                  fillDetailInfo();
              }
          }
          
          tableRowSorter.sort();
    }
    
    private void fillDetailInfo() {
    	 int totalSold = totalSoldProduct(productDefaultTableModel);
    	 String totalSoldString = String.valueOf(totalSold);
    	 
    	 lbl_mostSoldProductNumber.setText(totalSoldString);
    	 lbl_totalProfitNumber.setText(String.valueOf(totalProfit(productDefaultTableModel)));
    	 lbl_totalPremiumNumber.setText(String.valueOf(totalPremium(productDefaultTableModel)));
    	 lbl_mostSoldProductName.setText(mostSoldProduct2(productDefaultTableModel));
    }
}
