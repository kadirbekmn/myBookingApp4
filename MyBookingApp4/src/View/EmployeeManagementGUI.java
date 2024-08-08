package View;

import java.awt.Dimension;
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
import Model.Employee;
import Model.Operation;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;

public class EmployeeManagementGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fld_EmployeeName;
	private JTextField fld_EmployeeType;
	private JTable table_employee;
	static Employee employee = new Employee();
	private Object[] employeeData = null;
	private DefaultTableModel employeeModel = null;
	private JTextField fld_employeeId;
	private JTextField fld_employeeToSearch;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeManagementGUI frame = new EmployeeManagementGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EmployeeManagementGUI() throws SQLException {
		employeeModel = new DefaultTableModel();
		Object[] colEmployeeName = new Object[4];
		colEmployeeName[0] = "Çalışan ID";
		colEmployeeName[1] = "Çalışan Ad Soyad";
		colEmployeeName[2] = "Çalışan Tip";
		colEmployeeName[3] = "Çalışan İşlemler";
		employeeModel.setColumnIdentifiers(colEmployeeName);
		employeeData = new Object[4];
		fld_employeeToSearch = new JTextField();
		employee.getEmployeeList(fld_employeeToSearch.getText());
		for (int i = 0; i < employee.list.size(); i++) {
			employeeData[0] = employee.list.get(i).getId();
			employeeData[1] = employee.list.get(i).getName();
			employeeData[2] = employee.list.get(i).getType();
			employeeData[3] = !employee.findChoosedOperationNames(employee.list.get(i).getId()).isEmpty() ? employee.findChoosedOperationNames(employee.list.get(i).getId()) : "Seçili İşlem Yok";
			employeeModel.addRow(employeeData);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 700);
		contentPane = new JPanel();
		contentPane.setToolTipText("Çalışan Adı");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 117, 819, 492);
		contentPane.add(w_tab);

		JPanel panel = new JPanel();
		w_tab.addTab("Çalışan Yönetim Sekmesi", null, panel, null);
		panel.setLayout(null);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 10, 635, 445);
		panel.add(w_scrollDoctor);

		table_employee = new JTable(employeeModel);
		w_scrollDoctor.setViewportView(table_employee);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(655, 10, 149, 321);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lbl_employeeName_1_1_1 = new JLabel("Yeni Çalışan Ekle");
		lbl_employeeName_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_employeeName_1_1_1.setBounds(10, 0, 124, 24);
		panel_1.add(lbl_employeeName_1_1_1);
		lbl_employeeName_1_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));

		JLabel lbl_employeeName = new JLabel("Çalışan Adı:");
		lbl_employeeName.setBounds(10, 34, 80, 14);
		panel_1.add(lbl_employeeName);
		lbl_employeeName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));

		fld_EmployeeName = new JTextField();
		fld_EmployeeName.setToolTipText("Çalışan Adı Giriniz");
		fld_EmployeeName.setBounds(10, 54, 129, 20);
		panel_1.add(fld_EmployeeName);
		fld_EmployeeName.setColumns(10);

		JLabel lbl_employeeType = new JLabel("Çalışan Tipi:");
		lbl_employeeType.setBounds(10, 90, 80, 14);
		panel_1.add(lbl_employeeType);
		lbl_employeeType.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));

		fld_EmployeeType = new JTextField();
		fld_EmployeeType.setToolTipText("Çalışan İşlemleri Seçiniz");
		fld_EmployeeType.setBounds(10, 109, 124, 20);
		panel_1.add(fld_EmployeeType);
		fld_EmployeeType.setColumns(10);
		
		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane(checkBoxPanel);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_1.setBounds(10, 168, 124, 112);
		panel_1.add(scrollPane_1);

		List<String> operationTypes = null;
		try {
		    Operation operation = new Operation();
		    operationTypes = operation.getOperationTypes();
		} catch (SQLException e) {
		    e.printStackTrace();
		}

		JCheckBox[] checkBoxes = new JCheckBox[operationTypes.size()];
		for (int i = 0; i < operationTypes.size(); i++) {
		    checkBoxes[i] = new JCheckBox(operationTypes.get(i));
		    checkBoxes[i].setBounds(10, 30 * i, 200, 26);
		    checkBoxPanel.add(checkBoxes[i]);
		}

		int panelWidth = 140;
		int panelHeight = operationTypes.size() * 30;
		checkBoxPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

		JLabel lbl_employeeOperation = new JLabel("Çalışan İşlemleri:");
		lbl_employeeOperation.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_employeeOperation.setBounds(10, 144, 124, 14);
		panel_1.add(lbl_employeeOperation);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(655, 341, 149, 114);
		panel.add(panel_2);
		panel_2.setLayout(null);

		JButton btn_addEmployee = new JButton("Ekle");
		btn_addEmployee.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (fld_EmployeeName.getText().length() == 0 || fld_EmployeeType.getText().length() == 0) {
		            Helper.showMsg("fill");
		        } else {
		            if (Helper.confirm("sure")) {
		            	Employee tempEmployee = new Employee();
		            	tempEmployee.setName(fld_EmployeeName.getText());
		            	tempEmployee.setType(fld_EmployeeType.getText());
		                boolean control = employee.addEmployee(tempEmployee);
		                if (control) {
		                    List<Operation> selectedOperations = new ArrayList<>();
		                    for (JCheckBox checkBox : checkBoxes) {
		                        if (checkBox.isSelected()) {
		                        	
		                        	
		                            selectedOperations.add(employee.findOperationByName(checkBox.getText()));
		                            
		                            
		                        }
		                    }
		                    employee.addOperations(selectedOperations, tempEmployee);
		                    Helper.showMsg("success");
		                    fld_EmployeeName.setText(null);
		                    fld_EmployeeType.setText(null);
		                    try {
		                        fld_employeeToSearch.setText(null);
		                        updateEmployeeModel();
		                    } catch (SQLException e1) {
		                        e1.printStackTrace();
		                    }
		                }
		            }
		        }
		    }
		});
		btn_addEmployee.setBounds(10, 290, 129, 21);
		panel_1.add(btn_addEmployee);
		btn_addEmployee.setActionCommand("OK");

		JLabel lbl_employeeName_1_1 = new JLabel("Çalışan Sil");
		lbl_employeeName_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_employeeName_1_1.setBounds(10, 0, 126, 24);
		panel_2.add(lbl_employeeName_1_1);
		lbl_employeeName_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));

		JLabel lbl_employeeName_1 = new JLabel("Silinecek Çalışan ID:");
		lbl_employeeName_1.setBounds(10, 34, 126, 14);
		panel_2.add(lbl_employeeName_1);
		lbl_employeeName_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));

		JButton btn_delEmployee = new JButton("Sil");
		btn_delEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_employeeId.getText().length() == 0) {
					Helper.showMsg("Lütfen geçerli bir çalışan seçiniz !");
				} else {
					if (Helper.confirm("sure")) {
						int selectedId = Integer.parseInt(fld_employeeId.getText());
						try {
							boolean control = employee.deleteEmployee(selectedId);
							if (control) {
								Helper.showMsg("success");
								fld_employeeId.setText(null);
								fld_employeeToSearch.setText(null);
								updateEmployeeModel();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		btn_delEmployee.setActionCommand("OK");
		btn_delEmployee.setBounds(10, 83, 126, 21);
		panel_2.add(btn_delEmployee);

		fld_employeeId = new JTextField();
		fld_employeeId.setToolTipText("Seçili Çalışan Silinecektir");
		fld_employeeId.setColumns(10);
		fld_employeeId.setBounds(10, 53, 126, 20);
		fld_employeeId.setEditable(false);
		panel_2.add(fld_employeeId);

		table_employee.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_employeeId.setText(table_employee.getValueAt(table_employee.getSelectedRow(), 0).toString());
				} catch (Exception e4) {

				}
			}
		});

		table_employee.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectedId = Integer
							.parseInt(table_employee.getValueAt(table_employee.getSelectedRow(), 0).toString());
					String selectedName = table_employee.getValueAt(table_employee.getSelectedRow(), 1).toString();
					String selectedType = table_employee.getValueAt(table_employee.getSelectedRow(), 2).toString();
					try {
						boolean control = employee.updateEmployee(selectedId, selectedName, selectedType);
					} catch (Exception e6) {

					}
				}
			}
		});

		JLabel lblNewLabel = new JLabel("Çalışan Yönetim Ekranı");
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

		fld_employeeToSearch = new JTextField();
		fld_employeeToSearch.setToolTipText("Çalışan Adı Giriniz");
		fld_employeeToSearch.setColumns(10);
		fld_employeeToSearch.setBounds(102, 87, 129, 20);
		contentPane.add(fld_employeeToSearch);

		JLabel lbl_employeeName_1_1_1_1 = new JLabel("Çalışan Ara:");
		lbl_employeeName_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_employeeName_1_1_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		lbl_employeeName_1_1_1_1.setBounds(-12, 83, 124, 24);
		contentPane.add(lbl_employeeName_1_1_1_1);

		JButton btn_searchEmployee = new JButton("Ara");
		btn_searchEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					employee.getEmployeeList(fld_employeeToSearch.getText());
					updateEmployeeModel();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_searchEmployee.setActionCommand("OK");
		btn_searchEmployee.setBounds(241, 86, 58, 21);
		contentPane.add(btn_searchEmployee);
	}

	public void updateEmployeeModel() throws SQLException {
		employee.getEmployeeList(fld_employeeToSearch.getText());
		DefaultTableModel clearModel = (DefaultTableModel) table_employee.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < employee.list.size(); i++) {
			employeeData[0] = employee.list.get(i).getId();
			employeeData[1] = employee.list.get(i).getName();
			employeeData[2] = employee.list.get(i).getType();
			employeeData[3] = !employee.findChoosedOperationNames(employee.list.get(i).getId()).isEmpty() ? employee.findChoosedOperationNames(employee.list.get(i).getId()) : "Seçili İşlem Yok";
			employeeModel.addRow(employeeData);
		}
	}
}