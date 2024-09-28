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

import Helper.DBConnection;
import Helper.Helper;
import Model.Manager;
import Model.Operation;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import javax.swing.JCheckBoxMenuItem;

public class OperationManagementGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fld_operationName;
	private JTextField fld_operationTime;
	private JTable table_operation;
	static Operation operation = new Operation();
	private Object[] operationData = null;
	private DefaultTableModel operationModel = null;
	private JTextField fld_operationId;
	private JTextField fld_operationToSearch;
	private static Manager manager = new Manager();
	private JTextField fld_operationPrice;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperationManagementGUI frame = new OperationManagementGUI(manager);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public OperationManagementGUI(Manager manager) throws SQLException {
		operationModel = new DefaultTableModel();
		Object[] colOperationName = new Object[4];
		colOperationName[0] = "Operasyon ID";
		colOperationName[1] = "Operasyon Adı";
		colOperationName[2] = "Operasyon Süresi (dk)";
		colOperationName[3] = "Operasyon Fiyatı (tl)";
		operationModel.setColumnIdentifiers(colOperationName);
		operationData = new Object[4];
		fld_operationToSearch = new JTextField();
		operation.getOperationList(fld_operationToSearch.getText());
		for (int i = 0; i < operation.list.size(); i++) {
			operationData[0] = operation.list.get(i).getId();
			operationData[1] = operation.list.get(i).getOperationName();
			operationData[2] = operation.list.get(i).getOperationTime();
			operationData[3] = operation.list.get(i).getOperationPrice();
			operationModel.addRow(operationData);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 700);
		contentPane = new JPanel();
		contentPane.setToolTipText("Çalışan Adı");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 213, 819, 396);
		contentPane.add(w_tab);

		JPanel panel = new JPanel();
		w_tab.addTab("Operasyon Yönetim Sekmesi", null, panel, null);
		panel.setLayout(null);

		JScrollPane w_scrollOperation = new JScrollPane();
		w_scrollOperation.setBounds(10, 10, 635, 338);
		panel.add(w_scrollOperation);

		table_operation = new JTable(operationModel);
		w_scrollOperation.setViewportView(table_operation);

		JPanel pnl_operationAdd = new JPanel();
		pnl_operationAdd.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnl_operationAdd.setBounds(655, 10, 149, 214);
		panel.add(pnl_operationAdd);
		pnl_operationAdd.setLayout(null);

		JLabel lbl_operationAdd = new JLabel("Yeni Operasyon Ekle");
		lbl_operationAdd.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_operationAdd.setBounds(0, 0, 149, 24);
		pnl_operationAdd.add(lbl_operationAdd);
		lbl_operationAdd.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));

		JLabel lbl_operationName = new JLabel("Operasyon Adı:");
		lbl_operationName.setBounds(10, 38, 129, 14);
		pnl_operationAdd.add(lbl_operationName);
		lbl_operationName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));

		fld_operationName = new JTextField();
		fld_operationName.setToolTipText("Çalışan Adı Giriniz");
		fld_operationName.setBounds(10, 58, 129, 20);
		pnl_operationAdd.add(fld_operationName);
		fld_operationName.setColumns(10);

		JLabel lbl_operationTime = new JLabel("Operasyon Süresi (dk):");
		lbl_operationTime.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_operationTime.setBounds(0, 90, 149, 14);
		pnl_operationAdd.add(lbl_operationTime);
		lbl_operationTime.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));

		fld_operationTime = new JTextField();
		fld_operationTime.setToolTipText("Çalışan İşlemleri Seçiniz");
		fld_operationTime.setBounds(10, 109, 124, 20);
		pnl_operationAdd.add(fld_operationTime);
		fld_operationTime.setColumns(10);

		JButton btn_addOperation = new JButton("Ekle");
		btn_addOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_operationName.getText().length() == 0 || fld_operationTime.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					if (Helper.confirm("sure")) {
						boolean control = operation.addOperation(fld_operationName.getText(),
								Integer.parseInt(fld_operationTime.getText()),
								Integer.parseInt(fld_operationPrice.getText()));
						if (control) {
							Helper.showMsg("success");
							fld_operationName.setText(null);
							fld_operationTime.setText(null);
							fld_operationPrice.setText(null);

							try {
								fld_operationToSearch.setText(null);
								updateOperationModel();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});

		btn_addOperation.setBounds(10, 183, 129, 21);
		pnl_operationAdd.add(btn_addOperation);
		btn_addOperation.setActionCommand("OK");

		fld_operationPrice = new JTextField();
		fld_operationPrice.setToolTipText("Çalışan İşlemleri Seçiniz");
		fld_operationPrice.setColumns(10);
		fld_operationPrice.setBounds(11, 158, 124, 20);
		pnl_operationAdd.add(fld_operationPrice);

		JLabel lbl_operationPrice = new JLabel("Operasyon Fiyatı (tl):");
		lbl_operationPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_operationPrice.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_operationPrice.setBounds(0, 138, 149, 14);
		pnl_operationAdd.add(lbl_operationPrice);

		JPanel pnl_operationDel = new JPanel();
		pnl_operationDel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnl_operationDel.setBounds(655, 234, 149, 114);
		panel.add(pnl_operationDel);
		pnl_operationDel.setLayout(null);

		JLabel lbl_operationDelete = new JLabel("Operasyon Sil");
		lbl_operationDelete.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_operationDelete.setBounds(10, 0, 126, 24);
		pnl_operationDel.add(lbl_operationDelete);
		lbl_operationDelete.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));

		JLabel lbl_operationIdToDel = new JLabel("Silinecek Operasyon ID:");
		lbl_operationIdToDel.setBounds(0, 34, 149, 14);
		pnl_operationDel.add(lbl_operationIdToDel);
		lbl_operationIdToDel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));

		JButton btn_delOperation = new JButton("Sil");
		btn_delOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_operationId.getText().length() == 0) {
					Helper.showMsg("Lütfen geçerli bir çalışan seçiniz !");
				} else {
					if (Helper.confirm("sure")) {
						int selectedId = Integer.parseInt(fld_operationId.getText());
						try {
							boolean control = operation.deleteOperation(selectedId);
							if (control) {
								Helper.showMsg("success");
								fld_operationId.setText(null);
								fld_operationToSearch.setText(null);
								updateOperationModel();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		btn_delOperation.setActionCommand("OK");
		btn_delOperation.setBounds(10, 83, 126, 21);
		pnl_operationDel.add(btn_delOperation);

		fld_operationId = new JTextField();
		fld_operationId.setToolTipText("Seçili Çalışan Silinecektir");
		fld_operationId.setColumns(10);
		fld_operationId.setBounds(10, 53, 126, 20);
		fld_operationId.setEditable(false);
		pnl_operationDel.add(fld_operationId);

		table_operation.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_operationId.setText(table_operation.getValueAt(table_operation.getSelectedRow(), 0).toString());
				} catch (Exception e4) {

				}
			}
		});

		table_operation.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectedId = Integer
							.parseInt(table_operation.getValueAt(table_operation.getSelectedRow(), 0).toString());
					String selectedOperationName = table_operation.getValueAt(table_operation.getSelectedRow(), 1)
							.toString();
					int selectedOperationTime = Integer
							.parseInt(table_operation.getValueAt(table_operation.getSelectedRow(), 2).toString());
					int selectedOperationPrice = Integer
							.parseInt(table_operation.getValueAt(table_operation.getSelectedRow(), 3).toString());
					try {
						boolean control = operation.updateOperation(selectedId, selectedOperationName,
								selectedOperationTime, selectedOperationPrice);
					} catch (Exception e6) {

					}
				}
			}
		});

		JLabel lblNewLabel = new JLabel("Operasyon Yönetim Ekranı");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 10, 311, 25);
		contentPane.add(lblNewLabel);

		JButton btn_exit = new JButton("Menüye Geri Dön");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuGUI menuGUI = new MenuGUI(manager);
				menuGUI.setVisible(true);
				dispose();
			}
		});

		btn_exit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_exit.setBounds(645, 21, 181, 27);
		contentPane.add(btn_exit);
		fld_operationToSearch = new JTextField();
		fld_operationToSearch.setToolTipText("Çalışan Adı Giriniz");
		fld_operationToSearch.setColumns(10);
		fld_operationToSearch.setBounds(120, 181, 129, 20);
		contentPane.add(fld_operationToSearch);

		JLabel lbl_operationSearch = new JLabel("Operasyon Ara:");
		lbl_operationSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_operationSearch.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		lbl_operationSearch.setBounds(0, 177, 124, 24);
		contentPane.add(lbl_operationSearch);

		JButton btn_searchOperation = new JButton("Ara");
		btn_searchOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					operation.getOperationList(fld_operationToSearch.getText());
					updateOperationModel();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btn_searchOperation.setActionCommand("OK");
		btn_searchOperation.setBounds(263, 180, 58, 21);
		contentPane.add(btn_searchOperation);
	}

	public void updateOperationModel() throws SQLException {
	    operation.getOperationList(fld_operationToSearch.getText());
		DefaultTableModel clearModel = (DefaultTableModel) table_operation.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < operation.list.size(); i++) {
			operationData[0] = operation.list.get(i).getId();
			operationData[1] = operation.list.get(i).getOperationName();
			operationData[2] = operation.list.get(i).getOperationTime();
			operationData[3] = operation.list.get(i).getOperationPrice();
			operationModel.addRow(operationData);
		}
	}
}