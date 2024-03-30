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
import Model.Employee;

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

public class EmployeeManagementGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fld_employeeName;
	private JTextField fld_EmployeeType;
	private JTextField fld_employeeId;
	private JTable table_employee;
	static Employee employee = new Employee();
	private Object[] employeeData = null;
	private DefaultTableModel employeeModel = null;

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
		Object[] colEmployeeName = new Object[3];
		colEmployeeName[0] = "Çalışan ID";
		colEmployeeName[1] = "Çalışan Ad Soyad";
		colEmployeeName[2] = "Çalışan Tip";
		employeeModel.setColumnIdentifiers(colEmployeeName);
		employeeData = new Object[3];
		for(int i = 0; i < employee.getEmployeeList().size(); i++) {
			employeeData[0] = employee.getEmployeeList().get(i).getId();
			employeeData[1] = employee.getEmployeeList().get(i).getName();
			employeeData[2] = employee.getEmployeeList().get(i).getType();
			employeeModel.addRow(employeeData);
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 213, 819, 396);
		contentPane.add(w_tab);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		w_tab.addTab("Çalışan Yönetim Sekmesi", null, panel, null);
		
		JLabel lblalanAdSoyad = new JLabel("Çalışan Ad Soyad:");
		lblalanAdSoyad.setFont(new Font("Yu Gothic Medium", Font.BOLD, 15));
		lblalanAdSoyad.setBounds(668, 36, 136, 25);
		panel.add(lblalanAdSoyad);
		
		fld_employeeName = new JTextField();
		fld_employeeName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fld_employeeName.setColumns(10);
		fld_employeeName.setBounds(668, 58, 136, 25);
		panel.add(fld_employeeName);
		
		JLabel label1 = new JLabel("Çalışan Tipi:");
		label1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 15));
		label1.setBounds(668, 103, 114, 25);
		panel.add(label1);
		
		fld_EmployeeType = new JTextField();
		fld_EmployeeType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fld_EmployeeType.setColumns(10);
		fld_EmployeeType.setBounds(668, 125, 136, 25);
		panel.add(fld_EmployeeType);
		
		JButton btn_addEmployee = new JButton("Ekle");
		btn_addEmployee.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (fld_employeeName.getText().length() == 0 || fld_EmployeeType.getText().length() == 0) {
		            Helper.showMsg("fill");
		        } else {
		            try {
		                boolean control = employee.addEmployee(fld_employeeName.getText(), fld_EmployeeType.getText());
		                if (control) {
		                    Helper.showMsg("success");
		                    fld_employeeName.setText(null);
		                    fld_EmployeeType.setText(null);
		                    updateEmployeeModel(); // Tabloyu güncelle
		                }
		            } catch (SQLException e1) {
		                e1.printStackTrace();
		            }
		        }
		    }
		});
		
		btn_addEmployee.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_addEmployee.setBounds(668, 172, 136, 25);
		panel.add(btn_addEmployee);
		
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
		                        updateEmployeeModel();
		                    }
		                } catch (SQLException e1) {
		                    e1.printStackTrace();
		                }
		            }
		        }
		    }
		});

		btn_delEmployee.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_delEmployee.setBounds(668, 323, 136, 25);
		panel.add(btn_delEmployee);
		
		JLabel label3 = new JLabel("Kullanıcı ID:");
		label3.setFont(new Font("Yu Gothic Medium", Font.BOLD, 15));
		label3.setBounds(668, 266, 114, 25);
		panel.add(label3);
		
		fld_employeeId = new JTextField();
		fld_employeeId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fld_employeeId.setColumns(10);
		fld_employeeId.setBounds(668, 288, 136, 25);
		panel.add(fld_employeeId);
		
		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 10, 629, 338);
		panel.add(w_scrollDoctor);
		
		table_employee = new JTable(employeeModel);
		w_scrollDoctor.setViewportView(table_employee);
		
		table_employee.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_employeeId.setText(table_employee.getValueAt(table_employee.getSelectedRow(), 0).toString());
				} catch(Exception e4) {
					
				}
			}			
		});
		
		table_employee.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectedId = Integer.parseInt(table_employee.getValueAt(table_employee.getSelectedRow(), 0).toString());
					String selectedName = table_employee.getValueAt(table_employee.getSelectedRow(), 1).toString();
					String selectedType = table_employee.getValueAt(table_employee.getSelectedRow(), 2).toString();
					try {
						boolean control = employee.updateEmployee(selectedId, selectedName, selectedType);
					} catch (Exception e6) {
						
					}
				}
			}
		});
		
		JLabel lblNewLabel = new JLabel("Hosgeldiniz, Sayın <dynamic>");
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
		btn_exit.setBounds(665, 21, 161, 27);
		contentPane.add(btn_exit);
	}
	
	public void updateEmployeeModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_employee.getModel();
		clearModel.setRowCount(0);
		for(int i = 0; i < employee.getEmployeeList().size(); i++) {
			employeeData[0] = employee.getEmployeeList().get(i).getId();
			employeeData[1] = employee.getEmployeeList().get(i).getName();
			employeeData[2] = employee.getEmployeeList().get(i).getType();
			employeeModel.addRow(employeeData);
		}
	}
}