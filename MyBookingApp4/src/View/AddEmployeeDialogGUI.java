package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Model.Employee;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;

public class AddEmployeeDialogGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fld_EmployeeType;
	private JTextField fld_employeeName;
	static Employee employee = new Employee();
	private JTable table_employee;
	private Object[] employeeData = null;
	private DefaultTableModel employeeModel = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddEmployeeDialogGUI frame = new AddEmployeeDialogGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AddEmployeeDialogGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 285, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBounds(0, 0, 271, 228);
		contentPane.add(contentPanel);
		
		JLabel lbl_employeeName = new JLabel("Çalışan Adı:");
		lbl_employeeName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_employeeName.setBounds(10, 54, 80, 14);
		contentPanel.add(lbl_employeeName);
		
		JLabel lbl_employeeType = new JLabel("Çalışan Tipi:");
		lbl_employeeType.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_employeeType.setBounds(10, 79, 80, 14);
		contentPanel.add(lbl_employeeType);
		
		JLabel lbl_purchasePrice = new JLabel("İşlemler:");
		lbl_purchasePrice.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_purchasePrice.setBounds(10, 104, 80, 14);
		contentPanel.add(lbl_purchasePrice);
		
		fld_EmployeeType = new JTextField();
		fld_EmployeeType.setColumns(10);
		fld_EmployeeType.setBounds(94, 77, 147, 20);
		contentPanel.add(fld_EmployeeType);
		
		fld_employeeName = new JTextField();
		fld_employeeName.setColumns(10);
		fld_employeeName.setBounds(94, 52, 147, 20);
		contentPanel.add(fld_employeeName);
		
		JCheckBoxMenuItem chckbxmnıtmNewCheckItem = new JCheckBoxMenuItem("New check item");
		chckbxmnıtmNewCheckItem.setBounds(93, 104, 148, 26);
		contentPanel.add(chckbxmnıtmNewCheckItem);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(2, 230, 269, 33);
		contentPane.add(buttonPane);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton btn_addEmployee = new JButton("Çalışanı Ekle");
		btn_addEmployee.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (fld_employeeName.getText().length() == 0 || fld_EmployeeType.getText().length() == 0) {
		            Helper.showMsg("fill");
		        } else {
		            boolean control = employee.addEmployee(fld_employeeName.getText(), fld_EmployeeType.getText());
					if (control) {
					    Helper.showMsg("success");
					    fld_employeeName.setText(null);
					    fld_EmployeeType.setText(null);
//		                    updateEmployeeModel();
					    dispose();
					}
		        }
		    }
		});

		btn_addEmployee.setActionCommand("OK");
		buttonPane.add(btn_addEmployee);
		
		JButton cancelButton = new JButton("İptal");
		cancelButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        dispose();
		    }
		});
		buttonPane.add(cancelButton);
	}
	
//	public void updateEmployeeModel() throws SQLException {
//		DefaultTableModel clearModel = (DefaultTableModel) table_employee.getModel();
//		clearModel.setRowCount(0);
//		for(int i = 0; i < employee.getEmployeeList().size(); i++) {
//			employeeData[0] = employee.getEmployeeList().get(i).getId();
//			employeeData[1] = employee.getEmployeeList().get(i).getName();
//			employeeData[2] = employee.getEmployeeList().get(i).getType();
//			employeeModel.addRow(employeeData);
//		}
//	}
}
