package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Manager;
import Model.Product;
import Model.Reservation;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.Icon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.LineBorder;

public class MenuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	static Manager manager = new Manager();
	Reservation reservation = new Reservation();
	Product product = new Product();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuGUI frame = new MenuGUI();
					frame.pack();
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					int height = screenSize.height;
					int width = screenSize.width;
					frame.setSize(width, height);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 700);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JPanel panelMainComponents = new JPanel();
		panelMainComponents.setBounds(0, 0, 836, 663);
		panelMainComponents.setLayout(null);
		panelMainComponents.setBackground(Color.WHITE);
		w_pane.add(panelMainComponents);

		JPanel panel_menuItems = new JPanel();
		panel_menuItems.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_menuItems.setBounds(10, 61, 390, 569);
		panelMainComponents.add(panel_menuItems);
		panel_menuItems.setLayout(null);

		JMenuItem menu_reservation = new JMenuItem("Rezervasyon Yönetimi");
		menu_reservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ReservationManagementGUI reservationManagement = new ReservationManagementGUI();
					reservationManagement.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		menu_reservation.setBackground(new Color(192, 192, 192));
		menu_reservation.setHorizontalTextPosition(SwingConstants.CENTER);
		menu_reservation.setHorizontalAlignment(SwingConstants.CENTER);
		menu_reservation.setFont(new Font("Yu Gothic Medium", Font.BOLD, 24));
		menu_reservation.setBorderPainted(true);
		menu_reservation.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		menu_reservation.setBounds(30, 10, 328, 77);
		panel_menuItems.add(menu_reservation);

		JButton menu_product = new JButton("Ürün Yönetimi");
		menu_product.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductManagementGUI prodManagementGUI = new ProductManagementGUI(manager);
				prodManagementGUI.setVisible(true);
				dispose();
			}
		});
		menu_product.setHorizontalAlignment(SwingConstants.CENTER);
		menu_product.setFont(new Font("Yu Gothic Medium", Font.BOLD, 24));
		menu_product.setBackground(new Color(192, 192, 192));
		menu_product.setBorderPainted(true);
		menu_product.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		menu_product.setBounds(30, 97, 328, 77);
		panel_menuItems.add(menu_product);

		JMenuItem menu_employee = new JMenuItem("Çalışan Yönetimi");
		menu_employee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					EmployeeManagementGUI employeeManagement = new EmployeeManagementGUI();
					employeeManagement.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		menu_employee.setBackground(new Color(192, 192, 192));
		menu_employee.setHorizontalAlignment(SwingConstants.CENTER);
		menu_employee.setFont(new Font("Yu Gothic Medium", Font.BOLD, 24));
		menu_employee.setBorderPainted(true);
		menu_employee.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		menu_employee.setBounds(30, 184, 328, 77);
		panel_menuItems.add(menu_employee);
		menu_employee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OperationManagementGUI operationManagement = new OperationManagementGUI();
					operationManagement.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		JMenuItem menu_operation = new JMenuItem("Operasyon Yönetimi");
		menu_operation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OperationManagementGUI operationManagement = new OperationManagementGUI();
					operationManagement.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		menu_operation.setHorizontalAlignment(SwingConstants.CENTER);
		menu_operation.setFont(new Font("Yu Gothic Medium", Font.BOLD, 24));
		menu_operation.setBorderPainted(true);
		menu_operation.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		menu_operation.setBackground(new Color(192, 192, 192));
		menu_operation.setBounds(30, 271, 328, 77);
		panel_menuItems.add(menu_operation);

		JButton menu_customer = new JButton("Müşteri Yönetimi");
		menu_customer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CustomerManagementGUI customerManagement = new CustomerManagementGUI();
					customerManagement.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		menu_customer.setHorizontalAlignment(SwingConstants.CENTER);
		menu_customer.setFont(new Font("Yu Gothic Medium", Font.BOLD, 24));
		menu_customer.setBorderPainted(true);
		menu_customer.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		menu_customer.setBackground(new Color(192, 192, 192));
		menu_customer.setBounds(30, 358, 328, 77);
		panel_menuItems.add(menu_customer);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		infoPanel.setBounds(410, 61, 416, 569);
		panelMainComponents.add(infoPanel);
		JLabel lbl_todaySell;
		JLabel lbl_todayProductIncome;
		try {
			JPanel productInfoPanel = new JPanel();
			productInfoPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
			productInfoPanel.setBounds(10, 305, 396, 243);
			infoPanel.add(productInfoPanel);
			productInfoPanel.setLayout(null);
			lbl_todaySell = new JLabel("Bugün satılan ürün sayısı: " + product.getTodaySoldProductCount());
			lbl_todaySell.setBounds(10, 71, 370, 33);
			productInfoPanel.add(lbl_todaySell);
			lbl_todaySell.setOpaque(true);
			lbl_todaySell.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_todaySell.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
			lbl_todayProductIncome = new JLabel(
					"Bugün elde edilen toplam kar : " + product.getTodayTotalProfit() + " TL");
			lbl_todayProductIncome.setBounds(10, 114, 370, 33);
			productInfoPanel.add(lbl_todayProductIncome);
			lbl_todayProductIncome.setOpaque(true);
			lbl_todayProductIncome.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_todayProductIncome.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));

			JLabel productPanelTitle = new JLabel("Ürün Bilgi Paneli");
			productPanelTitle.setOpaque(true);
			productPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
			productPanelTitle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
			productPanelTitle.setBounds(10, 10, 376, 33);
			productInfoPanel.add(productPanelTitle);

			JLabel lbl_monthsIncomeForProduct = new JLabel(
					"Bu ay elde edilen toplam kar : " + product.getThisMonthTotalProfit());
			lbl_monthsIncomeForProduct.setOpaque(true);
			lbl_monthsIncomeForProduct.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_monthsIncomeForProduct.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
			lbl_monthsIncomeForProduct.setBounds(10, 200, 370, 33);
			productInfoPanel.add(lbl_monthsIncomeForProduct);

			JLabel lbl_monthsSell = new JLabel("Bu ay satılan ürün sayısı : " + product.getThisMonthSoldProductCount());
			lbl_monthsSell.setOpaque(true);
			lbl_monthsSell.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_monthsSell.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
			lbl_monthsSell.setBounds(10, 157, 370, 33);
			productInfoPanel.add(lbl_monthsSell);

			JPanel reservationInfoPanel = new JPanel();
			reservationInfoPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
			reservationInfoPanel.setBounds(10, 20, 396, 259);
			infoPanel.add(reservationInfoPanel);
			reservationInfoPanel.setLayout(null);

			JLabel reservationPanelTitle = new JLabel("Rezervasyon Bilgi Paneli");
			reservationPanelTitle.setOpaque(true);
			reservationPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
			reservationPanelTitle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
			reservationPanelTitle.setBounds(10, 10, 376, 33);
			reservationInfoPanel.add(reservationPanelTitle);

			JLabel lbl_todaysCustomer = new JLabel();
			lbl_todaysCustomer.setBounds(10, 111, 370, 27);
			reservationInfoPanel.add(lbl_todaysCustomer);
			lbl_todaysCustomer.setText("Bugün karşılanacak müşteri sayısı : " + reservation.getTodayReservationCount());

			lbl_todaysCustomer.setOpaque(true);
			lbl_todaysCustomer.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_todaysCustomer.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));

			JLabel lbl_monthsCustomer = new JLabel(
					"Bu ay karşılanan müşteri sayısı : " + reservation.getCurrentMonthReservationCount());
			lbl_monthsCustomer.setOpaque(true);
			lbl_monthsCustomer.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_monthsCustomer.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
			lbl_monthsCustomer.setBounds(10, 185, 370, 27);
			reservationInfoPanel.add(lbl_monthsCustomer);

			JLabel lbl_monthsIncome = new JLabel(
					"Bugüne kadar karşılanan müşteri sayısı : " + reservation.getTotalCustomerCount());
			lbl_monthsIncome.setOpaque(true);
			lbl_monthsIncome.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_monthsIncome.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
			lbl_monthsIncome.setBounds(10, 222, 370, 27);
			reservationInfoPanel.add(lbl_monthsIncome);

			JLabel lbl_todaysIncome = new JLabel();
			lbl_todaysIncome.setText("Bugün elde edilen gelir : " + reservation.getTotalRevenueFromReservations());
			lbl_todaysIncome.setOpaque(true);
			lbl_todaysIncome.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_todaysIncome.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
			lbl_todaysIncome.setBounds(10, 148, 370, 27);
			reservationInfoPanel.add(lbl_todaysIncome);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JLabel lbl_loggedInUser = new JLabel("Hosgeldiniz, Sayın <dynamic>");
		lbl_loggedInUser.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_loggedInUser.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		lbl_loggedInUser.setBounds(10, 15, 390, 36);
		panelMainComponents.add(lbl_loggedInUser);

		JButton btn_exit = new JButton("Çıkış Yap");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI = new LoginGUI();
				loginGUI.setVisible(true);
				dispose();
			}
		});
		btn_exit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_exit.setBounds(645, 15, 181, 27);
		panelMainComponents.add(btn_exit);
	}
}