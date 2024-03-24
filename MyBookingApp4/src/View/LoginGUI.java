package View;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import Helper.Helper;
import Model.Manager;
import View.MenuGUI;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_nickname;
	private JTextField fld_password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 844, 710);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblMybookingappUygulamasnaHogeldiniz = new JLabel("MyBookingApp Uygulamasına Hoşgeldiniz");
		lblMybookingappUygulamasnaHogeldiniz.setHorizontalAlignment(SwingConstants.CENTER);
		lblMybookingappUygulamasnaHogeldiniz.setFont(new Font("Yu Gothic Medium", Font.BOLD, 34));
		lblMybookingappUygulamasnaHogeldiniz.setBounds(10, 388, 810, 49);
		w_pane.add(lblMybookingappUygulamasnaHogeldiniz);
		
		JLabel lbl_logo11 = new JLabel(new ImageIcon(getClass().getResource("login.jpg")));
		lbl_logo11.setBounds(206, 0, 419, 378);
		w_pane.add(lbl_logo11);
		
		JLabel lbl_kullaiciAdi = new JLabel("Kullanıcı Adı:");
		lbl_kullaiciAdi.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		lbl_kullaiciAdi.setBounds(205, 466, 138, 36);
		w_pane.add(lbl_kullaiciAdi);
		
		fld_nickname = new JTextField();
		fld_nickname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fld_nickname.setColumns(10);
		fld_nickname.setBounds(355, 463, 280, 36);
		w_pane.add(fld_nickname);
		
		JLabel lbl_kullaiciAdi_1 = new JLabel("Şifre:");
		lbl_kullaiciAdi_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_kullaiciAdi_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_kullaiciAdi_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		lbl_kullaiciAdi_1.setBounds(206, 522, 138, 33);
		w_pane.add(lbl_kullaiciAdi_1);
		
		fld_password = new JTextField();
		fld_password.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fld_password.setColumns(10);
		fld_password.setBounds(355, 519, 280, 36);
		w_pane.add(fld_password);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(150, 440, 573, 210);
		w_pane.add(panel);
		panel.setLayout(null);
		
		JButton btn_login = new JButton("Giriş Yap");
		btn_login.setBounds(226, 131, 150, 58);
		panel.add(btn_login);
		btn_login.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (fld_nickname.getText().length() == 0 || fld_password.getText().length() == 0) {
						Helper.showMsg("fill");
					} else {
						Manager manager = new Manager();
						manager.setId(1);
						manager.setNickname("manager");
						manager.setPassword("1234");

						MenuGUI menuGUI = new MenuGUI(manager);
						menuGUI.setVisible(true);
						dispose();
					}
					
//					} else {
//					try {
//						Connection con = conn.connDB();
//						java.sql.Statement st = con.createStatement();
//						ResultSet rs = st.executeQuery("SELECT * FROM user");
//						while (rs.next()) {
//							if (fld_doktorTc.getText().equals(rs.getString("tcno"))
//									&& fld_doktorPass.getText().equals(rs.getString("password"))) {
//								Bashekim bhBashekim = new Bashekim();
//								bhBashekim.setId(rs.getInt("id"));
//								bhBashekim.setPassword(rs.getString("password"));
//								bhBashekim.setTcno(rs.getString("tcno"));
//								bhBashekim.setName(rs.getString("name"));
//								bhBashekim.setType(rs.getString("type"));
//								BashekimGUI bGUI = new BashekimGUI(bhBashekim);
//								bGUI.setVisible(true);
//								dispose();
//							}
//						}
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
						
				}
			});
		
		btn_login.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
	}
}
