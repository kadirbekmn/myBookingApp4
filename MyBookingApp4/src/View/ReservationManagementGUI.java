package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Component;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import com.toedter.calendar.JCalendar;
import Helper.Helper;
import Model.Employee;
import Model.Customer;
import Model.Operation;
import Model.Reservation;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Calendar;
import java.util.Date;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReservationManagementGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel employeeModel;
	private DefaultTableModel customerModel;
	private DefaultTableModel operationModel;
	private DefaultTableModel reservationModel;
	private JTable employeeTable;
	private JTable customerTable;
	private JTable operationTable;
	private JCalendar calendar;
	private JSpinner timeSpinner;
	private JComboBox<Employee> cmbEmployee;
	private JComboBox<Customer> cmbCustomer;
	private List<Employee> employees;
	private List<Operation> operations;
	private List<Customer> customers;
	private List<Reservation> reservations;
	private Employee employee = new Employee();
	private Customer customer = new Customer();
	private Operation operation = new Operation();
	private Reservation reservation = new Reservation();
	private List<String> operationTypes = new ArrayList<>();
	private JCheckBox[] checkBoxes;
	private JPanel checkBoxPanel = new JPanel();
	private JTable reservationTable;
	private JTextField reservation_id;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ReservationManagementGUI frame = new ReservationManagementGUI();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public ReservationManagementGUI() throws SQLException {
		setTitle("Rezervasyon Yönetim Ekranı");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 853, 758);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("Rezervasyon Yönetim Ekranı");
		lblTitle.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblTitle.setBounds(10, 10, 311, 25);
		contentPane.add(lblTitle);

		JButton btnExit = new JButton("Menüye Geri Dön");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnExit.setBounds(645, 11, 181, 27);
		btnExit.addActionListener(e -> {
			MenuGUI menuGUI = new MenuGUI();
			menuGUI.setVisible(true);
			dispose();
		});
		contentPane.add(btnExit);

		calendar = new JCalendar();
		calendar.setBounds(20, 50, 288, 288);
		contentPane.add(calendar);

		JLabel lblEmployee = new JLabel("Çalışan Seç:");
		lblEmployee.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmployee.setBounds(320, 50, 100, 25);
		contentPane.add(lblEmployee);

		cmbEmployee = new JComboBox<>();
		cmbEmployee.setBounds(420, 50, 150, 25);
		cmbEmployee.addActionListener(e -> {
			try {
				updateOperationDropdown();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
		contentPane.add(cmbEmployee);

		JLabel lblCustomer = new JLabel("Müşteri Seç:");
		lblCustomer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCustomer.setBounds(320, 90, 100, 25);
		contentPane.add(lblCustomer);

		cmbCustomer = new JComboBox<>();
		cmbCustomer.setBounds(420, 90, 150, 25);
		contentPane.add(cmbCustomer);

		JLabel lblOperation = new JLabel("İşlem Seç:");
		lblOperation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOperation.setBounds(320, 160, 100, 25);
		contentPane.add(lblOperation);

		JLabel lblTime = new JLabel("Saat Seç:");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTime.setBounds(320, 125, 100, 25);
		contentPane.add(lblTime);

		timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(new Date());
		timeSpinner.setBounds(420, 125, 150, 25);
		contentPane.add(timeSpinner);

		JButton btnAddReservation = new JButton("Rezervasyon Ekle");
		btnAddReservation.setBounds(320, 320, 250, 30);
		btnAddReservation.addActionListener(e -> {
			try {
				addReservation();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		contentPane.add(btnAddReservation);

		JScrollPane employeeScrollPane = new JScrollPane();
		employeeScrollPane.setBounds(10, 396, 201, 115);
		contentPane.add(employeeScrollPane);

		employeeTable = new JTable();
		employeeScrollPane.setViewportView(employeeTable);

		JScrollPane customerScrollPane = new JScrollPane();
		customerScrollPane.setBounds(263, 396, 201, 115);
		contentPane.add(customerScrollPane);

		customerTable = new JTable();
		customerScrollPane.setViewportView(customerTable);

		JScrollPane operationScrollPane = new JScrollPane();
		operationScrollPane.setBounds(520, 396, 300, 115);
		contentPane.add(operationScrollPane);

		operationTable = new JTable();
		operationScrollPane.setViewportView(operationTable);

		JScrollPane scrollPane_1 = new JScrollPane((Component) null);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_1.setBounds(420, 160, 150, 150);
		contentPane.add(scrollPane_1);
		scrollPane_1.setColumnHeaderView(checkBoxPanel);

		checkBoxPanel.setLayout(null);
		checkBoxPanel.setPreferredSize(new Dimension(140, 150));

		JScrollPane reservationScrollPane = new JScrollPane();
		reservationScrollPane.setBounds(10, 548, 810, 150);
		contentPane.add(reservationScrollPane);

		reservationTable = new JTable();
		reservationScrollPane.setViewportView(reservationTable);
		reservationTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					reservation_id
							.setText(reservationTable.getValueAt(reservationTable.getSelectedRow(), 0).toString());
				} catch (Exception e4) {

				}
			}
		});
		JLabel lblRezervasyonlar = new JLabel("Rezervasyonlar");
		lblRezervasyonlar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblRezervasyonlar.setBounds(10, 513, 311, 37);
		contentPane.add(lblRezervasyonlar);

		JLabel lblMteriler = new JLabel("Çalışanlar");
		lblMteriler.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblMteriler.setBounds(10, 360, 311, 37);
		contentPane.add(lblMteriler);

		JLabel lblMteriler_1 = new JLabel("Müşteriler");
		lblMteriler_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblMteriler_1.setBounds(263, 360, 201, 37);
		contentPane.add(lblMteriler_1);

		JLabel lblIlemler = new JLabel("İşlemler");
		lblIlemler.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblIlemler.setBounds(520, 365, 201, 27);
		contentPane.add(lblIlemler);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(604, 45, 222, 123);
		contentPane.add(panel_2);

		JLabel lbl_employeeName_1_1 = new JLabel("Rezervasyon Sil");
		lbl_employeeName_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_employeeName_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		lbl_employeeName_1_1.setBounds(10, 0, 126, 24);
		panel_2.add(lbl_employeeName_1_1);

		JLabel lbl_employeeName_1 = new JLabel("Silinecek Rezervasyon ID:");
		lbl_employeeName_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_employeeName_1.setBounds(10, 34, 202, 14);
		panel_2.add(lbl_employeeName_1);

		JButton btn_delEmployee = new JButton("Sil");
		btn_delEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (reservation_id.getText().length() == 0) {
					Helper.showMsg("Lütfen geçerli bir çalışan seçiniz !");
				} else {
					if (Helper.confirm("sure")) {
						int selectedId = Integer.parseInt(reservation_id.getText());
						try {
							boolean control = reservation.deleteReservation(selectedId);
							if (control) {
								Helper.showMsg("success");
								reservation_id.setText(null);
								updateTables();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btn_delEmployee.setActionCommand("OK");
		btn_delEmployee.setBounds(10, 83, 202, 21);
		panel_2.add(btn_delEmployee);

		reservation_id = new JTextField();
		reservation_id.setToolTipText("Seçili Çalışan Silinecektir");
		reservation_id.setEditable(false);
		reservation_id.setColumns(10);
		reservation_id.setBounds(10, 53, 202, 20);
		panel_2.add(reservation_id);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainPanel.setBounds(10, 45, 584, 317);
		contentPane.add(mainPanel);

		updateOperationDropdown();

		checkBoxes = new JCheckBox[operationTypes.size()];
		for (int i = 0; i < operationTypes.size(); i++) {
			checkBoxes[i] = new JCheckBox(operationTypes.get(i));
			checkBoxes[i].setBounds(10, 30 * i, 200, 26);
			checkBoxPanel.add(checkBoxes[i]);
		}

		loadEmployeeData();
		loadCustomerData();
		loadOperationData();
		loadReservationData();
		updateTables();
	}

	private void loadEmployeeData() throws SQLException {
		employees = employee.getEmployeeList("");
		for (Employee emp : employees) {
			cmbEmployee.addItem(emp);
		}
	}

	private void loadCustomerData() throws SQLException {
		customers = customer.getCustomerList("");
		for (Customer cust : customers) {
			cmbCustomer.addItem(cust);
		}
	}

	private void loadOperationData() throws SQLException {
		operations = operation.getOperationList("");
	}

	private void loadReservationData() throws SQLException {
		reservations = reservation.getReservationList();
	}

	private void updateOperationDropdown() throws SQLException {
		Employee selectedEmployee = (Employee) cmbEmployee.getSelectedItem();
		operationTypes.clear();
		checkBoxPanel.removeAll();

		if (selectedEmployee != null) {
			List<Operation> availableOperations = operation.findOperationTypesByEmployee(selectedEmployee);
			if (availableOperations != null) {
				for (Operation op : availableOperations) {
					operationTypes.add(op.getOperationName());
				}
			}
		}

		checkBoxes = new JCheckBox[operationTypes.size()];
		for (int i = 0; i < operationTypes.size(); i++) {
			checkBoxes[i] = new JCheckBox(operationTypes.get(i));
			checkBoxes[i].setBounds(10, 30 * i, 200, 26);
			checkBoxPanel.add(checkBoxes[i]);
		}

		checkBoxPanel.revalidate();
		checkBoxPanel.repaint();
	}

	private void addReservation() throws SQLException {
		Employee selectedEmployee = (Employee) cmbEmployee.getSelectedItem();
		Customer selectedCustomer = (Customer) cmbCustomer.getSelectedItem();
		java.util.Date date = calendar.getDate();
		java.util.Date time = (java.util.Date) timeSpinner.getValue();

		if (selectedEmployee != null && selectedCustomer != null && date != null && time != null) {
			Calendar dateTime = Calendar.getInstance();
			dateTime.setTime(date);
			Calendar timeCal = Calendar.getInstance();
			timeCal.setTime(time);
			dateTime.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
			dateTime.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));

			Timestamp reservationStartTime = new Timestamp(dateTime.getTimeInMillis());

			List<Operation> selectedOperations = new ArrayList<>();
			int totalOperationTime = 0;

			for (JCheckBox checkBox : checkBoxes) {
				if (checkBox.isSelected()) {
					Operation operation = employee.findOperationByName(checkBox.getText());
					if (operation != null) {
						selectedOperations.add(operation);
						totalOperationTime += operation.getOperationTime();
					}
				}
			}

			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(date);
			endCalendar.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
			endCalendar.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
			endCalendar.add(Calendar.MINUTE, totalOperationTime);

			Timestamp endTime = new Timestamp(endCalendar.getTimeInMillis());

			Reservation reservation = new Reservation();
			reservation.setEmployeeId(selectedEmployee.getId());
			reservation.setCustomerId(selectedCustomer.getId());
			reservation.setReservationTime(reservationStartTime);
			reservation.setTotalOperationTime(totalOperationTime);
			reservation.setEndTime(endTime);

			boolean success = false;

			if (selectedOperations.isEmpty()) {
				Helper.showMsg("Lütfen işlem seçiniz");
			} else if (!reservation.isEmployeeAvailable(reservation.getEmployeeId(), reservation.getReservationTime(),
					reservation.getEndTime())) {
				Helper.showMsg("Mevcut çalışanın bu saatte rezervasyonu bulunmaktadır!");
			} else {
				success = reservation.addReservation(reservation, selectedOperations);
				if (success) {
					Helper.showMsg("Rezervasyon başarıyla eklendi.");
					updateTables();
				} else {
					Helper.showMsg("Rezervasyon eklenirken bir hata oluştu.");
				}
			}
		}
	}

	private void updateTables() throws SQLException {
		employeeModel = new DefaultTableModel();
		employeeModel.setColumnIdentifiers(new Object[] { "Ad Soyad", "Tip" });
		for (Employee emp : employees) {
			employeeModel.addRow(new Object[] { emp.getName(), emp.getType() });
		}
		employeeTable.setModel(employeeModel);

		customerModel = new DefaultTableModel();
		customerModel.setColumnIdentifiers(new Object[] { "Ad Soyad", "Telefon" });
		for (Customer cust : customers) {
			customerModel.addRow(new Object[] { cust.getCustomerName(), cust.getCustomerPhone() });
		}
		customerTable.setModel(customerModel);

		operationModel = new DefaultTableModel();
		operationModel.setColumnIdentifiers(new Object[] { "İşlem Adı", "Süre (dk)", "Fiyat" });
		for (Operation op : operations) {
			operationModel
					.addRow(new Object[] { op.getOperationName(), op.getOperationTime(), op.getOperationPrice() });
		}
		operationTable.setModel(operationModel);

		loadReservationData();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		reservationModel = new DefaultTableModel();
		reservationModel.setColumnIdentifiers(new Object[] { "Grup Id", "Çalışan Adı", "Müşteri Adı", "Operasyon Adı",
				"Toplam Operasyon Süresi", "Toplam Operasyon Ücreti", "Rezervasyon Saati", "Rezervasyon Bitiş Saati" });
		for (Reservation re : reservations) {
			String reservationTime = dateFormat.format(re.getReservationTime());
			String endTime = dateFormat.format(re.getEndTime());
			reservationModel.addRow(
					new Object[] { re.getGroupId(), re.getEmployeeName(), re.getCustomerName(), re.getOperationName(),
							re.getTotalOperationTime() + "dk", re.getTotalPrice() + "TL", reservationTime, endTime });
		}
		reservationTable.setModel(reservationModel);
	}
}