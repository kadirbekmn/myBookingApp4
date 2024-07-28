package View;

import javax.management.loading.PrivateClassLoader;
import javax.management.openmbean.InvalidOpenTypeException;
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
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class ProductManagementGUI extends JFrame {

private JPanel w_pane;
private static Manager manager = new Manager();
private static Product product = new Product();
private static Employee employee = new Employee();
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
private JComboBox cmbBox_worker;

private Map<String, Integer> originalStocksMap = new HashMap<>();


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
//Object[] columnProductTable = {"ID", "Ürün Adı", "Stok", "Alış Fiyatı", "Satış Fiyatı", ""};
//productDefaultTableModel.setColumnIdentifiers(columnProductTable);
productDefaultTableModel.addColumn("ID");
productDefaultTableModel.addColumn("Ürün Adı");
productDefaultTableModel.addColumn("Stok");
productDefaultTableModel.addColumn("Alış Fiyatı");
productDefaultTableModel.addColumn("Satış Fiyatı");
productDefaultTableModel.addColumn("");
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

originalStocksMap.put(product.getProductsList().get(i).getName(), product.getProductsList().get(i).getStock());
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

setSize(1205, 696);
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

JButton btn_search = new JButton("ARA");
btn_search.addActionListener(e -> {
String searchTerm = textField_productName.getText();
if (!searchTerm.isEmpty()) {
try {
product.searchProduct(searchTerm);
updateProductDefaultTableModel(searchTerm);
} catch (SQLException e1) {
e1.printStackTrace();
}
}
});
btn_search.setBounds(185, 38, 89, 23);
center_panel.add(btn_search);

JScrollPane scrollPane = new JScrollPane(table_product);
scrollPane.setBounds(10, 73, 500, 240);
center_panel.add(scrollPane);

JLabel lbl_selected_products = new JLabel("Seçilen Ürünler");
lbl_selected_products.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
lbl_selected_products.setBounds(590, 11, 106, 20);
center_panel.add(lbl_selected_products);

selectedProductDefaultTableModel = new DefaultTableModel();
Object[] columnSelectedProductTable = {"Adet", "Ürün Adı", "Kalan Stok", "Alış Fiyatı", "Satış Fiyatı", "Prim Yüzdesi", "Prim Ücreti", "Kar"};
selectedProductDefaultTableModel.setColumnIdentifiers(columnSelectedProductTable);
selectedProductDataObjects = new Object[8];

table_selected_product = new JTable(selectedProductDefaultTableModel);
table_selected_product.setRowHeight(40);
table_selected_product.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
table_selected_product.getModel().addTableModelListener(e -> {
// int row = e.getFirstRow();
// double kar = Double.parseDouble(selectedProductDefaultTableModel.getValueAt(row,
// 7).toString());
// double kar2 = kar;
//
// if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 5) {

int row = e.getFirstRow();
if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 5) {
// Get the necessary values from the table model
double primYuzdesi = Double.parseDouble(selectedProductDefaultTableModel.getValueAt(row, 5).toString());
int selectedProductQuantity = (int) selectedProductDefaultTableModel.getValueAt(row, 0);
double purchasePrice = Double.parseDouble(selectedProductDefaultTableModel.getValueAt(row, 3).toString());
double salePrice = Double.parseDouble(selectedProductDefaultTableModel.getValueAt(row, 4).toString());
double currentProfit = (salePrice - purchasePrice) * selectedProductQuantity;

// Calculate the commission fee
double primUcreti = (currentProfit * primYuzdesi) / 100;

// Calculate the updated profit
double kar = currentProfit - primUcreti;

// Update the table model with the new values
selectedProductDefaultTableModel.setValueAt(primUcreti, row, 6);
selectedProductDefaultTableModel.setValueAt(kar, row, 7);

//
// row = e.getFirstRow();
// double primYüzdesi = Double.parseDouble(selectedProductDefaultTableModel.getValueAt(row,
// 5).toString());
//
// double primÜcreti = (kar * primYüzdesi) / 100;
//
// selectedProductDefaultTableModel.setValueAt(primÜcreti, row, 6);
//
// int seçilenÜrünAdeti = (int) selectedProductDefaultTableModel.getValueAt(row, 0);
// int alışFiyati = (int) selectedProductDefaultTableModel.getValueAt(row, 3);
// int satisFiyati = (int) selectedProductDefaultTableModel.getValueAt(row, 4);
// kar = ((satisFiyati - alışFiyati)*seçilenÜrünAdeti) - primÜcreti;
// selectedProductDefaultTableModel.setValueAt(kar, row, 7);

//
// int salePrice = Integer.parseInt(selectedProductDefaultTableModel.getValueAt(row,
// 4).toString());
// double discountedPrice = salePrice - (salePrice * discountRate / 100);
// selectedProductDefaultTableModel.setValueAt(discountedPrice, row, 6);
//
// int quantity = (int) selectedProductDefaultTableModel.getValueAt(row, 0); int
// purchasePrice = (int) selectedProductDefaultTableModel.getValueAt(row, 3);
// double profit = (discountedPrice - purchasePrice) * quantity;
// selectedProductDefaultTableModel.setValueAt(profit, row, 7);
//

// int row = e.getFirstRow();
// Double discountRate =
// Double.parseDouble(selectedProductDefaultTableModel.getValueAt(row,
// 5).toString()); int salePrice =
// Integer.parseInt(selectedProductDefaultTableModel.getValueAt(row,
// 4).toString());
// double discountedPrice = salePrice - (salePrice *
// discountRate / 100);
// selectedProductDefaultTableModel.setValueAt(discountedPrice, row, 6);
//
// int quantity = (int) selectedProductDefaultTableModel.getValueAt(row, 0); int
// purchasePrice = (int) selectedProductDefaultTableModel.getValueAt(row, 3);
// double profit = (discountedPrice - purchasePrice) * quantity;
// selectedProductDefaultTableModel.setValueAt(profit, row, 7);

}
});

JScrollPane scrollPane_selected_product = new JScrollPane(table_selected_product);
scrollPane_selected_product.setBounds(524, 73, 604, 240);
center_panel.add(scrollPane_selected_product);

panel_buttons = new JPanel();
FlowLayout fl_panel_buttons = (FlowLayout) panel_buttons.getLayout();
fl_panel_buttons.setAlignment(FlowLayout.LEFT);
fl_panel_buttons.setHgap(20);
w_pane.add(panel_buttons, BorderLayout.SOUTH);


cmbBox_worker = new JComboBox();
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
cmbBox_worker.setBounds(10, 354, 161, 30);
center_panel.add(cmbBox_worker);

cmbBox_worker.insertItemAt("Çalışan Seç", 0);
cmbBox_worker.setSelectedIndex(0);

JButton btn_completeSale = new JButton("Satışı Tamamla");
btn_completeSale.setBounds(224, 354, 131, 30);
center_panel.add(btn_completeSale);
btn_completeSale.addActionListener(e -> completeSale());
btn_completeSale.setFont(new Font("Tahoma", Font.PLAIN, 15));

JButton btn_cancel = new JButton("Satışı İptal Et");
btn_cancel.setBounds(382, 354, 117, 30);
center_panel.add(btn_cancel);
btn_cancel.addActionListener(e -> {
selectedProductDefaultTableModel.setRowCount(0); // Seçilen ürünleri temizle
productDefaultTableModel.setRowCount(0); // Ürün tablosunu temizle
try {
// Ürün listesini yeniden yükle
for (int i = 0; i < product.getProductsList().size(); i++) {
productDataObjects[0] = product.getProductsList().get(i).getId();
productDataObjects[1] = product.getProductsList().get(i).getName();
productDataObjects[2] = product.getProductsList().get(i).getStock();
productDataObjects[3] = product.getProductsList().get(i).getPurchasePrice();
productDataObjects[4] = product.getProductsList().get(i).getSalePrice();
productDataObjects[5] = "";
productDefaultTableModel.addRow(productDataObjects);
}
} catch (SQLException e1) {
e1.printStackTrace();
}
});
btn_cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));

JButton btn_add = new JButton("Yeni Ürün Ekle");
btn_add.setBounds(301, 35, 127, 27);
center_panel.add(btn_add);
btn_add.addActionListener(e -> {
addProductDialog = new AddProductDialogGUI();
addProductDialog.setVisible(true);
updateProductDefaultTableModel();
});
btn_add.setFont(new Font("Tahoma", Font.PLAIN, 15));


}

private void updateProductDefaultTableModel() {
productDefaultTableModel.setRowCount(0);
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
}

private void updateProductDefaultTableModel(String searchTerm) {
productDefaultTableModel.setRowCount(0);
try {
for (int i = 0; i < product.getProductsList().size(); i++) {
if (product.getProductsList().get(i).getName().toLowerCase().contains(searchTerm.toLowerCase())) {
productDataObjects[0] = product.getProductsList().get(i).getId();
productDataObjects[1] = product.getProductsList().get(i).getName();
productDataObjects[2] = product.getProductsList().get(i).getStock();
productDataObjects[3] = product.getProductsList().get(i).getPurchasePrice();
productDataObjects[4] = product.getProductsList().get(i).getSalePrice();
productDataObjects[5] = "";
productDefaultTableModel.addRow(productDataObjects);
}
}
} catch (SQLException e) {
e.printStackTrace();
}
}
private void completeSale() {

	boolean result= false;


String selectedEmployeeName = cmbBox_worker.getSelectedItem().toString();
int employeeID = employee.getIDByName(selectedEmployeeName);

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
	int id = product.getIdByName(productName);
	
result = product.addSaleProduct(selectedProductNumber, id, purchasePrice, salePrice, stock, premiumPercentage, premiumFee, profit, employeeID);
if (result) {
//Helper.showMsg("success sale product");
} else {
Helper.showMsg("error sale product");
}
} catch (SQLException ex) {
ex.printStackTrace();

}

try {
result = product.updateProductStock(stock, productName);
} catch (Exception e2) {
// TODO: handle exception
}
}
if (result) {
	Helper.showMsg("success sale product");
}
// Clear the selected product table after transaction
selectedProductDefaultTableModel.setRowCount(0);
}

private void editProduct(int selectedRow) {

	
	if (selectedProductDefaultTableModel.getRowCount() > 0) {
		Helper.showMsg("Öncelikkle seçilen ürünün satışını tamamlayın veya satışı iptal ediniz.");
	} else {
		int id = (int) table_product.getValueAt(selectedRow, 0);
		AddProductDialogGUI addProductDialog = new AddProductDialogGUI(id);
		addProductDialog.setVisible(true);
		updateProductDefaultTableModel();
	}

}

private void deleteProduct(int selectedRow) {
	//int a = table_product.getSelectedRow();
	if (selectedProductDefaultTableModel.getRowCount() > 0) {
		Helper.showMsg("Öncelikle seçilen ürünün satışını tamamlayın veya satışı iptal ediniz.");
	} else if (selectedRow != -1){
	
		if (Helper.confirm("sure")) {
			
			int id = (int) table_product.getValueAt(selectedRow, 0);
			if (product.deleteProduct(id)) {
				//productDefaultTableModel.removeRow(selectedRow);
			Helper.showMsg("done");
			//table_product.clearSelection();
			updateProductDefaultTableModel();
			
			} else {
			Helper.showMsg("error");
			}
			}
		 } else {
			Helper.showMsg("ne olacak bakalım");
		}
	
	
}


//
// for (int i = 0; i < selectedProductDefaultTableModel.getRowCount(); i++) {
// String productName = (String) selectedProductDefaultTableModel.getValueAt(i, 1);
// int soldQuantity = (int) selectedProductDefaultTableModel.getValueAt(i, 0);
//
// try {
// for (int j = 0; j < product.getProductsList().size(); j++) {
// if (product.getProductsList().get(j).getName().equals(productName)) {
// int newStock = product.getProductsList().get(j).getStock() - soldQuantity;
// product.updateStockById(product.getProductsList().get(j).getId(), newStock);
// break;
// }
// }
// } catch (SQLException e) {
// e.printStackTrace();
// }
// }


private void removeSelectedProducts() {
int[] selectedRows = table_selected_product.getSelectedRows();

for (int i = selectedRows.length - 1; i >= 0; i--) {
int row = selectedRows[i];
String productName = (String) selectedProductDefaultTableModel.getValueAt(row, 1);
int removedQuantity = (int) selectedProductDefaultTableModel.getValueAt(row, 0);
int remainingStock = originalStocksMap.get(productName) - removedQuantity;
originalStocksMap.put(productName, remainingStock);

selectedProductDefaultTableModel.removeRow(row);
selectedProductDefaultTableModel.fireTableDataChanged();
selectedProductDefaultTableModel.fireTableRowsDeleted(row, row);

// Ürün tablosundaki stok değerini güncelle
for (int j = 0; j < productDefaultTableModel.getRowCount(); j++) {
if (productDefaultTableModel.getValueAt(j, 1).equals(productName)) {
productDefaultTableModel.setValueAt(remainingStock, j, 2);
break;
}
}
}
}


public class ButtonEditor extends DefaultCellEditor  {

protected JButton btn_plus;
protected JButton btn_edit;
protected JButton btn_delete;

private JPanel panel;
private int selectedProductId;
private String selectedProductName;
private int selectedProductStock;
private int selectedProductPurchasePrice;
private int selectedProductSalePrice;
public int selectedRow;
public int selectedSaleProduct = 1;

public ButtonEditor() {
super(new JCheckBox());


btn_plus = new JButton();
btn_plus.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("plus.png")).getImage()));

btn_edit = new JButton();
btn_edit.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("edit.png")).getImage()));

btn_delete = new JButton();
btn_delete.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("delete.png")).getImage()));

btn_plus.addActionListener(e -> {
selectedSaleProduct = 1; // İşleme başlamadan önce sıfırlanır
if (selectedProductStock > 0) {
selectedProductStock--; // Stoktan bir adet eksilt
double currentProfit = (selectedProductSalePrice - selectedProductPurchasePrice);
boolean productExists = false;

for (int i = 0; i < selectedProductDefaultTableModel.getRowCount(); i++) {
if (selectedProductName.equals(selectedProductDefaultTableModel.getValueAt(i, 1))) {
selectedSaleProduct = (int) selectedProductDefaultTableModel.getValueAt(i, 0) + 1;
selectedProductDefaultTableModel.setValueAt(selectedSaleProduct, i, 0);
selectedProductDefaultTableModel.setValueAt(selectedProductName, i, 1);
selectedProductDefaultTableModel.setValueAt(selectedProductStock, i, 2);
selectedProductDefaultTableModel.setValueAt(currentProfit + ((Number) selectedProductDefaultTableModel.getValueAt(i, 7)).doubleValue(), i, 7);
productExists = true;
//updateProductDefaultTableModel();
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

});

btn_edit.addActionListener(e -> {
	editProduct(selectedRow);
	update();
//	
//int id = (int) table_product.getValueAt(selectedRow, 0);
//AddProductDialogGUI addProductDialog = new AddProductDialogGUI(id);
//addProductDialog.setVisible(true);
//updateProductDefaultTableModel();
});


btn_delete.addActionListener(e -> {
	deleteProduct(selectedRow);
//	updateProductDefaultTableModel();
//	update();
//if (Helper.confirm("sure")) {
//int id = (int) table_product.getValueAt(selectedRow, 0);
//if (product.deleteProduct(id)) {
//Helper.showMsg("done");
//table_product.clearSelection();
//updateProductDefaultTableModel();
//} else {
//Helper.showMsg("error");
//}
//}
});

panel = new JPanel(new GridLayout(1, 3));
panel.add(btn_plus);
panel.add(btn_edit);
panel.add(btn_delete);
}

@Override
public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
selectedRow = row;
selectedProductId = (int) table.getValueAt(selectedRow, 0);
selectedProductName = (String) table.getValueAt(selectedRow, 1);
selectedProductStock = (int) table.getValueAt(selectedRow, 2);
selectedProductPurchasePrice = (int) table.getValueAt(selectedRow, 3);
selectedProductSalePrice = (int) table.getValueAt(selectedRow, 4);

if (column == 5) {
JPanel panel = new JPanel(new GridLayout(1, 3));

btn_plus.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("plus.png")).getImage()));


btn_edit.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("edit.png")).getImage()));

btn_delete.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("delete.png")).getImage()));

panel.add(btn_plus);
panel.add(btn_edit);
panel.add(btn_delete);
return panel;
} else {
return super.getTableCellEditorComponent(table, value, isSelected, row, column);
}
}

public void update() {
    
 	 selectedProductId = Integer.parseInt(table_product.getValueAt(selectedRow, 0).toString());
      selectedProductName = table_product.getValueAt(selectedRow, 1).toString();
      selectedProductStock = Integer.parseInt(table_product.getValueAt(selectedRow, 2).toString());
      selectedProductPurchasePrice = Integer.parseInt(table_product.getValueAt(selectedRow, 3).toString());
      selectedProductSalePrice = Integer.parseInt(table_product.getValueAt(selectedRow, 4).toString());
}
}

public class ButtonRenderer extends JPanel implements TableCellRenderer {

	


Image img_plus = new ImageIcon(this.getClass().getResource("plus.png")).getImage();
Image img_delete = new ImageIcon(this.getClass().getResource("delete.png")).getImage();
Image img_edit = new ImageIcon(this.getClass().getResource("edit.png")).getImage();

private JButton btn_plus;
private JButton btn_edit;
private JButton btn_delete;

public ButtonRenderer() {
setLayout(new GridLayout(1, 3));
btn_plus = new JButton();
btn_plus.setIcon(new ImageIcon(img_plus));
btn_edit = new JButton(new ImageIcon(img_edit));
btn_delete = new JButton(new ImageIcon(img_delete));
add(btn_plus);
add(btn_edit);
add(btn_delete);
}

@Override
public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	
if (column == 5) {
btn_plus.setIcon(new ImageIcon(img_plus));
btn_edit.setIcon(new ImageIcon(img_edit));
btn_delete.setIcon(new ImageIcon(img_delete));
return this;
} else {
return null;
}
}
}
}