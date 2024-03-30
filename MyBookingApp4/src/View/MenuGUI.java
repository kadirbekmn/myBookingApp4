package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Manager;
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

public class MenuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	static Manager manager = new Manager();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuGUI frame = new MenuGUI(manager);
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

	public MenuGUI(Manager manager) {
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
		
		JMenuItem menu_rezervation = new JMenuItem("Rezervasyon Yönetimi");
		menu_rezervation.setBackground(Color.WHITE);
		menu_rezervation.setHorizontalTextPosition(SwingConstants.CENTER);
		menu_rezervation.setHorizontalAlignment(SwingConstants.CENTER);
		menu_rezervation.setFont(new Font("Yu Gothic Medium", Font.BOLD, 25));
		menu_rezervation.setBorderPainted(true);
		menu_rezervation.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		menu_rezervation.setBounds(30, 95, 328, 77);
		panel_menuItems.add(menu_rezervation);
		
		JButton menu_product = new JButton("Ürün Yönetimi");
		menu_product.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					ProductManagementGUI prodManagementGUI = new ProductManagementGUI(manager);
					prodManagementGUI.setVisible(true);
					dispose();
			}
		});
		
		menu_product.setHorizontalAlignment(SwingConstants.CENTER);
		menu_product.setFont(new Font("Yu Gothic Medium", Font.BOLD, 25));
		menu_product.setBackground(Color.WHITE);
		menu_product.setBorderPainted(true);
		menu_product.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		menu_product.setBounds(30, 216, 328, 77);
		panel_menuItems.add(menu_product);
		
		JMenuItem menu_employee = new JMenuItem("Çalışan Yönetimi");
		menu_employee.setBackground(Color.WHITE);
		menu_employee.setHorizontalAlignment(SwingConstants.CENTER);
		menu_employee.setFont(new Font("Yu Gothic Medium", Font.BOLD, 25));
		menu_employee.setBorderPainted(true);
		menu_employee.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		menu_employee.setBounds(30, 356, 328, 77);
		panel_menuItems.add(menu_employee);
		
		JPanel panel_menuInfos = new JPanel();
		panel_menuInfos.setLayout(null);
		panel_menuInfos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_menuInfos.setBounds(436, 61, 390, 569);
		panelMainComponents.add(panel_menuInfos);
		
		JLabel lbl_loggedInUser_1_1 = new JLabel("Bugün rezervasyonu olan kişi \r\nsayısı: 12");
		lbl_loggedInUser_1_1.setOpaque(true);
		lbl_loggedInUser_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_loggedInUser_1_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		lbl_loggedInUser_1_1.setBounds(10, 90, 370, 143);
		panel_menuInfos.add(lbl_loggedInUser_1_1);
		
		JLabel lbl_loggedInUser_1_1_1 = new JLabel("Bu hafta karşılanan müşteri sayısı : 45");
		lbl_loggedInUser_1_1_1.setOpaque(true);
		lbl_loggedInUser_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_loggedInUser_1_1_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		lbl_loggedInUser_1_1_1.setBounds(10, 282, 370, 143);
		panel_menuInfos.add(lbl_loggedInUser_1_1_1);
		
		JLabel lbl_loggedInUser = new JLabel("Kullanıcı: Ercan Bey");
		lbl_loggedInUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_loggedInUser.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		lbl_loggedInUser.setBounds(536, 10, 290, 36);
		panelMainComponents.add(lbl_loggedInUser);
		
		JLabel lbl_loggedInUser_1 = new JLabel("Menü");
		lbl_loggedInUser_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_loggedInUser_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		lbl_loggedInUser_1.setBounds(10, 10, 94, 36);
		panelMainComponents.add(lbl_loggedInUser_1);
	}
}