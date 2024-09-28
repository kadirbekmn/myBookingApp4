package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Model.Manager;

public class Reports extends JFrame {

    private JPanel contentPane;
    private JPanel reportPanel;
    private ProductReportPanel productReportPanel = new ProductReportPanel();
    private JButton btn_exit;
    private static Manager manager;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Reports frame = new Reports(manager);
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
    public Reports(Manager manager) {
    	this.manager = manager;
        setTitle("Raporlar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1111, 682);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        contentPane.add(buttonPanel, BorderLayout.NORTH);

        JPanel leftButtonPanel = new JPanel();
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);

        JButton btnReport1 = new JButton("Satılan Ürünlerin Raporu");
        btnReport1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reportPanel = productReportPanel;
                contentPane.add(reportPanel, BorderLayout.CENTER);
                contentPane.revalidate();
                contentPane.repaint();
            }
        });
        leftButtonPanel.add(btnReport1);

        JButton btnReport2 = new JButton("Rapor 2");
        btnReport2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayReport("Rapor 2 içeriği burada...");
            }
        });
        leftButtonPanel.add(btnReport2);

        JButton btnReport3 = new JButton("Rapor 3");
        btnReport3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayReport("Rapor 3 içeriği burada...");
            }
        });
        leftButtonPanel.add(btnReport3);

        JButton btnReport4 = new JButton("Rapor 4");
        btnReport4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayReport("Rapor 4 içeriği burada...");
            }
        });
        leftButtonPanel.add(btnReport4);

        btn_exit = new JButton("Menüye Geri Dön");
        btn_exit.addActionListener(e -> {
        MenuGUI menuGUI = new MenuGUI(manager);
        menuGUI.setVisible(true);
        dispose();
        });
        buttonPanel.add(btn_exit, BorderLayout.EAST);
    }

    private void displayReport(String reportContent) {
        reportPanel.removeAll();
        JTextArea textArea = new JTextArea(reportContent);
        textArea.setEditable(false);
        reportPanel.add(textArea, BorderLayout.CENTER);
        reportPanel.revalidate();
        reportPanel.repaint();
    }

    /**
     * Show the ProductReport panel inside the Reports frame.
     */
    private void showProductReport() {
        reportPanel.removeAll();
        reportPanel.add(productReportPanel, BorderLayout.CENTER);
        reportPanel.revalidate();
        reportPanel.repaint();
    }
}

class ProductReport1 extends JPanel {
    public ProductReport1() {
        setLayout(new BorderLayout(0, 0));

        JPanel top_panel = new JPanel();
        add(top_panel, BorderLayout.CENTER);
    }
}
