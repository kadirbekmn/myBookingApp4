package View;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import Helper.DBConnection;
import Helper.Helper;
import Model.Manager;
import View.MenuGUI;
import javax.swing.Icon;
import java.awt.BorderLayout;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_userNickname;
	private JTextField fld_userPassword;
	private Manager manager;
	private DBConnection conn = new DBConnection();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					  LoginGUI frame = new LoginGUI();
					  frame.pack();
					  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					  int height = screenSize.height;
					  int width = screenSize.width;
					  frame.setSize(width, height);			  
					  frame.setVisible(true);
				      frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public LoginGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 700);
        w_pane = new JPanel();
        w_pane.setBackground(Color.WHITE);
        w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(w_pane);
        w_pane.setLayout(new BorderLayout(0, 0));

        JPanel panelMainComponents = new JPanel();
        panelMainComponents.setBackground(Color.WHITE);
        w_pane.add(panelMainComponents);
        panelMainComponents.setLayout(null);

        JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("login.jpg")));
        lbl_logo.setBounds(51, 58, 712, 336);
        panelMainComponents.add(lbl_logo);

        JLabel lblMybookingappUygulamasnaHogeldiniz = new JLabel("MyBookingApp Uygulamasına Hoşgeldiniz");
        lblMybookingappUygulamasnaHogeldiniz.setHorizontalAlignment(SwingConstants.CENTER);
        lblMybookingappUygulamasnaHogeldiniz.setFont(new Font("Yu Gothic Medium", Font.BOLD, 34));
        lblMybookingappUygulamasnaHogeldiniz.setBounds(60, 5, 703, 55);
        panelMainComponents.add(lblMybookingappUygulamasnaHogeldiniz);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setBounds(132, 444, 576, 209);
        panelMainComponents.add(panel);
        
        JButton btn_login = new JButton("Giriş Yap");
        btn_login.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
        btn_login.setBounds(226, 131, 150, 58);
        panel.add(btn_login);
        
        btn_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fld_userNickname.getText().isEmpty() || fld_userPassword.getText().isEmpty()) {
                    Helper.showMsg("fill");
	                } else {
	                    try {
	                        Connection con = conn.connectDB();
	                        java.sql.Statement st = con.createStatement();
	                        ResultSet rs = st.executeQuery("SELECT * FROM manager");
	                        while (rs.next()) {
	                        	if (fld_userNickname.getText().equals(rs.getString("nickname")) && fld_userPassword.getText().equals(rs.getString("password"))) {
	                                manager = new Manager();
	                                manager.setId(rs.getInt("id"));
	                                manager.setNickname(rs.getString("nickname"));
	                                manager.setPassword(rs.getString("password"));
	                                MenuGUI menuGUI = new MenuGUI(manager);
	                                menuGUI.setVisible(true);
	                                dispose();
	                        } else {
	                            Helper.showMsg("invalid");
	                        }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        
        JLabel lbl_kullaiciAdi = new JLabel("Kullanıcı Adı:");
        lbl_kullaiciAdi.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
        lbl_kullaiciAdi.setBounds(49, 34, 138, 36);
        panel.add(lbl_kullaiciAdi);
        
        fld_userNickname = new JTextField();
        fld_userNickname.setFont(new Font("Tahoma", Font.PLAIN, 15));
        fld_userNickname.setColumns(10);
        fld_userNickname.setBounds(199, 31, 280, 36);
        panel.add(fld_userNickname);
        
        JLabel lbl_kullaiciAdi_1 = new JLabel("Şifre:");
        lbl_kullaiciAdi_1.setVerticalAlignment(SwingConstants.BOTTOM);
        lbl_kullaiciAdi_1.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_kullaiciAdi_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
        lbl_kullaiciAdi_1.setBounds(50, 90, 138, 33);
        panel.add(lbl_kullaiciAdi_1);
        
        fld_userPassword = new JPasswordField();
        fld_userPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        fld_userPassword.setColumns(10);
        fld_userPassword.setBounds(199, 87, 280, 36);
        panel.add(fld_userPassword);
    }
}