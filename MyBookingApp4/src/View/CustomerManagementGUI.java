package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Model.Customer;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JCheckBox;

public class CustomerManagementGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fld_customerName;
	private JTextField fld_customerBirth;
	private JTable table_customer;
	static Customer customer = new Customer();
	private Object[] customerData = null;
	private DefaultTableModel customerModel = null;
	private JTextField fld_customerId;
	private JTextField fld_customerToSearch;
	private JTextField fld_customerExplanation;
	private JTextField fld_customerPhone;
	private JCheckBox chckbx_birthDiscount;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerManagementGUI frame = new CustomerManagementGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CustomerManagementGUI() throws SQLException {
		customerModel = new DefaultTableModel();
		Object[] colCustomerName = new Object[6];
		colCustomerName[0] = "Müşteri ID";
		colCustomerName[1] = "Müşteri Adı";
		colCustomerName[2] = "Müşteri Doğum Günü";
		colCustomerName[3] = "Müşteri Telefon Numarası";
		colCustomerName[4] = "Müşteri Açıklama";
		colCustomerName[5] = "Müşteri Doğum Günü İndirimi";

		customerModel.setColumnIdentifiers(colCustomerName);
		customerData = new Object[6];
		fld_customerToSearch = new JTextField();
		customer.getCustomerList(fld_customerToSearch.getText());
		for (int i = 0; i < customer.list.size(); i++) {
			customerData[0] = customer.list.get(i).getId();
			customerData[1] = customer.list.get(i).getCustomerName();
			customerData[2] = customer.list.get(i).getCustomerBirth();
			customerData[3] = customer.list.get(i).getCustomerPhone();
			customerData[4] = customer.list.get(i).getCustomerExplanation();
			customerData[5] = customer.list.get(i).getCustomerBirhDiscount();

			customerModel.addRow(customerData);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 700);
		contentPane = new JPanel();
		contentPane.setToolTipText("Müşteri Adı");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 81, 819, 528);
		contentPane.add(w_tab);

		JPanel panel = new JPanel();
		w_tab.addTab("Müşteri Yönetim Sekmesi", null, panel, null);
		panel.setLayout(null);

		JScrollPane w_scrollCustomer = new JScrollPane();
		w_scrollCustomer.setBounds(10, 10, 635, 481);
		panel.add(w_scrollCustomer);

		table_customer = new JTable(customerModel);
		w_scrollCustomer.setViewportView(table_customer);

		JPanel pnl_customerAdd = new JPanel();
		pnl_customerAdd.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnl_customerAdd.setBounds(655, 10, 153, 357);
		panel.add(pnl_customerAdd);
		pnl_customerAdd.setLayout(null);

		JLabel lbl_customerAdd = new JLabel("Yeni Müşteri Ekle");
		lbl_customerAdd.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_customerAdd.setBounds(0, 0, 149, 24);
		pnl_customerAdd.add(lbl_customerAdd);
		lbl_customerAdd.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));

		JLabel lbl_customerName = new JLabel("Müşteri Adı:");
		lbl_customerName.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_customerName.setBounds(10, 44, 129, 14);
		pnl_customerAdd.add(lbl_customerName);
		lbl_customerName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		fld_customerName = new JTextField();
		fld_customerName.setToolTipText("Çalışan Adı Giriniz");
		fld_customerName.setBounds(10, 65, 129, 20);
		pnl_customerAdd.add(fld_customerName);
		fld_customerName.setColumns(10);

		JLabel lbl_customerBirth = new JLabel("Müşteri Doğum Günü:");
		lbl_customerBirth.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_customerBirth.setBounds(0, 95, 149, 14);
		pnl_customerAdd.add(lbl_customerBirth);
		lbl_customerBirth.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));

		fld_customerBirth = new JTextField();
		fld_customerBirth.setToolTipText("Çalışan İşlemleri Seçiniz");
		fld_customerBirth.setBounds(10, 114, 124, 20);
		pnl_customerAdd.add(fld_customerBirth);
		fld_customerBirth.setColumns(10);

		JButton btn_addCustomer = new JButton("Ekle");
		btn_addCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_customerName.getText().length() == 0 || fld_customerBirth.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					if (Helper.confirm("sure")) {
						boolean control = customer.addCustomer(fld_customerName.getText(),
								Date.valueOf(fld_customerBirth.getText()),
								Integer.parseInt(fld_customerPhone.getText()), fld_customerExplanation.getText(),
								Integer.parseInt(chckbx_birthDiscount.getText()));
						if (control) {
							Helper.showMsg("success");
							fld_customerName.setText(null);
							fld_customerBirth.setText(null);
							fld_customerExplanation.setText(null);
							try {
								fld_customerToSearch.setText(null);
								updateCustomerModel();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		btn_addCustomer.setBounds(10, 326, 129, 21);
		pnl_customerAdd.add(btn_addCustomer);
		btn_addCustomer.setActionCommand("OK");

		fld_customerExplanation = new JTextField();
		fld_customerExplanation.setToolTipText("Çalışan İşlemleri Seçiniz");
		fld_customerExplanation.setColumns(10);
		fld_customerExplanation.setBounds(11, 212, 125, 87);
		pnl_customerAdd.add(fld_customerExplanation);

		JLabel lbl_customerExplanation = new JLabel("Müşteri Açıklaması:");
		lbl_customerExplanation.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_customerExplanation.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_customerExplanation.setBounds(11, 193, 128, 14);
		pnl_customerAdd.add(lbl_customerExplanation);

		JCheckBox chckbx_birthDiscount = new JCheckBox("Doğum Günü İndirimi");
		chckbx_birthDiscount.setBounds(10, 305, 133, 21);
		pnl_customerAdd.add(chckbx_birthDiscount);

		fld_customerPhone = new JTextField();
		fld_customerPhone.setToolTipText("Çalışan İşlemleri Seçiniz");
		fld_customerPhone.setColumns(10);
		fld_customerPhone.setBounds(10, 163, 124, 20);
		pnl_customerAdd.add(fld_customerPhone);

		JLabel lbl_customerPhone = new JLabel("Müşteri Numara:");
		lbl_customerPhone.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_customerPhone.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_customerPhone.setBounds(10, 143, 129, 14);
		pnl_customerAdd.add(lbl_customerPhone);

		JPanel pnl_customerDel = new JPanel();
		pnl_customerDel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnl_customerDel.setBounds(655, 377, 153, 114);
		panel.add(pnl_customerDel);
		pnl_customerDel.setLayout(null);

		JLabel lbl_customerDelete = new JLabel("Müşteri Sil");
		lbl_customerDelete.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_customerDelete.setBounds(10, 0, 126, 24);
		pnl_customerDel.add(lbl_customerDelete);
		lbl_customerDelete.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));

		JLabel lbl_customerIdToDel = new JLabel("Silinecek Müşteri ID:");
		lbl_customerIdToDel.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_customerIdToDel.setBounds(10, 34, 129, 14);
		pnl_customerDel.add(lbl_customerIdToDel);
		lbl_customerIdToDel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));

		JButton btn_delCustomer = new JButton("Sil");
		btn_delCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_customerId.getText().length() == 0) {
					Helper.showMsg("Lütfen geçerli bir çalışan seçiniz !");
				} else {
					if (Helper.confirm("sure")) {
						int selectedId = Integer.parseInt(fld_customerId.getText());
						try {
							boolean control = customer.deleteCustomer(selectedId);
							if (control) {
								Helper.showMsg("success");
								fld_customerId.setText(null);
								fld_customerToSearch.setText(null);
								updateCustomerModel();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		btn_delCustomer.setActionCommand("OK");
		btn_delCustomer.setBounds(10, 83, 126, 21);
		pnl_customerDel.add(btn_delCustomer);

		fld_customerId = new JTextField();
		fld_customerId.setToolTipText("Seçili Çalışan Silinecektir");
		fld_customerId.setColumns(10);
		fld_customerId.setBounds(10, 53, 126, 20);
		fld_customerId.setEditable(false);
		pnl_customerDel.add(fld_customerId);

		table_customer.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_customerId.setText(table_customer.getValueAt(table_customer.getSelectedRow(), 0).toString());
				} catch (Exception e4) {

				}
			}
		});

		table_customer.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectedId = Integer
							.parseInt(table_customer.getValueAt(table_customer.getSelectedRow(), 0).toString());
					String selectedCustomerName = table_customer.getValueAt(table_customer.getSelectedRow(), 1)
							.toString();
					Date selectedCustomerBirthDay = (Date) table_customer.getValueAt(table_customer.getSelectedRow(),
							2);
					int selectedCustomerPhone = Integer
							.parseInt(table_customer.getValueAt(table_customer.getSelectedRow(), 3).toString());
					String selectedCustomerExplanation = table_customer.getValueAt(table_customer.getSelectedRow(), 4)
							.toString();
					int selectedCustomerDiscount = Integer
							.parseInt(table_customer.getValueAt(table_customer.getSelectedRow(), 5).toString());
					try {
						boolean control = customer.updateCustomer(selectedId, selectedCustomerName,
								selectedCustomerBirthDay, selectedCustomerPhone, selectedCustomerExplanation,
								selectedCustomerDiscount);
					} catch (Exception e6) {

					}
				}
			}
		});

		JLabel lblNewLabel = new JLabel("Müşteri Yönetim Ekranı");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 10, 311, 25);
		contentPane.add(lblNewLabel);

		JButton btn_exit = new JButton("Menüye Geri Dön");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuGUI menuGUI = new MenuGUI();
				menuGUI.setVisible(true);
				dispose();
			}
		});

		btn_exit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_exit.setBounds(645, 21, 181, 27);
		contentPane.add(btn_exit);
		fld_customerToSearch = new JTextField();
		fld_customerToSearch.setToolTipText("Müşteri Adı Giriniz");
		fld_customerToSearch.setColumns(10);
		fld_customerToSearch.setBounds(105, 49, 129, 20);
		contentPane.add(fld_customerToSearch);

		JLabel lbl_customerSearch = new JLabel("Müşteri Ara:");
		lbl_customerSearch.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_customerSearch.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		lbl_customerSearch.setBounds(10, 45, 103, 24);
		contentPane.add(lbl_customerSearch);

		JButton btn_searchCustomer = new JButton("Ara");
		btn_searchCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					customer.getCustomerList(fld_customerToSearch.getText());
					updateCustomerModel();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btn_searchCustomer.setActionCommand("OK");
		btn_searchCustomer.setBounds(244, 48, 58, 21);
		contentPane.add(btn_searchCustomer);
	}

	public void updateCustomerModel() throws SQLException {
		customer.getCustomerList(fld_customerToSearch.getText());
		DefaultTableModel clearModel = (DefaultTableModel) table_customer.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < customer.list.size(); i++) {
			customerData[0] = customer.list.get(i).getId();
			customerData[1] = customer.list.get(i).getCustomerName();
			customerData[2] = customer.list.get(i).getCustomerBirth();
			customerData[3] = customer.list.get(i).getCustomerPhone();
			customerData[4] = customer.list.get(i).getCustomerExplanation();
			customerData[5] = customer.list.get(i).getCustomerBirhDiscount();
			customerModel.addRow(customerData);
		}
	}
}