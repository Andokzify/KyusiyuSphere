package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class registrationSelect extends JFrame {

    private JPanel pnlStudent;
    private JPanel pnlSiteOwner;

    public registrationSelect() {
        setTitle("KyusiSphere - Registration");
        setSize(1080, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        initComponents();
    }

    private void initComponents() {
        getContentPane().setBackground(new Color(240, 240, 245));

        // Top Title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(204, 204, 204));
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        titlePanel.setBounds(330, 70, 470, 110);
        titlePanel.setLayout(null);

        JLabel titleLabel = new JLabel("REGISTRATION", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 40));
        titleLabel.setBounds(0, 30, 470, 50);
        titlePanel.add(titleLabel);
        add(titlePanel);

        // Left Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(204, 204, 204));
        sidebar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sidebar.setBounds(0, 0, 70, 740);
        add(sidebar);

        // Student Panel
        pnlStudent = createRolePanel("🎓", "STUDENT", "For QCU Students");
        pnlStudent.setBounds(240, 250, 260, 270);
        add(pnlStudent);

        pnlStudent.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                pnlStudent.setBackground(new Color(180, 200, 255));
                pnlStudent.setBorder(BorderFactory.createLineBorder(new Color(30, 60, 180), 3));
            }
            public void mouseExited(MouseEvent e) {
                pnlStudent.setBackground(new Color(225, 229, 245));
                pnlStudent.setBorder(BorderFactory.createLineBorder(new Color(55, 80, 160), 2));
            }
            public void mouseClicked(MouseEvent e) {
                dispose();
                new studentRegFrame().setVisible(true);
            }
        });

        // Site Owner Panel
        pnlSiteOwner = createRolePanel("💼", "SITE OWNER", "For Facility Owners");
        pnlSiteOwner.setBounds(630, 250, 260, 270);
        add(pnlSiteOwner);

        pnlSiteOwner.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                pnlSiteOwner.setBackground(new Color(180, 200, 255));
                pnlSiteOwner.setBorder(BorderFactory.createLineBorder(new Color(30, 60, 180), 3));
            }
            public void mouseExited(MouseEvent e) {
                pnlSiteOwner.setBackground(new Color(225, 229, 245));
                pnlSiteOwner.setBorder(BorderFactory.createLineBorder(new Color(55, 80, 160), 2));
            }
            public void mouseClicked(MouseEvent e) {
                dispose();
                new ownerRegFrame().setVisible(true);
            }
        });

        // ================= SECRET ADMIN LINK (Hidden) =================
        // To access: Right-click on the background or click 5 times on the title
        JLabel adminLink = new JLabel("Admin Only", SwingConstants.CENTER);
        adminLink.setFont(new Font("Courier New", Font.PLAIN, 14));
        adminLink.setForeground(new Color(100, 100, 100)); // Very subtle
        adminLink.setBounds(850, 680, 180, 30);
        adminLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(adminLink);

        // Secret activation: Click the label 5 times quickly
        final int[] clickCount = {0};
        adminLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                clickCount[0]++;
                if (clickCount[0] >= 5) {
                    dispose();
                    new adminRegFrame().setVisible(true);
                    clickCount[0] = 0;
                }
            }
        });

        // Back to Login Button
        JButton btnBack = new JButton("← Back to Login");
        btnBack.setBackground(new Color(30, 45, 100));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Courier New", Font.BOLD, 18));
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setBounds(430, 620, 240, 50);
        btnBack.addActionListener(e -> {
            dispose();
            new loginFrame().setVisible(true);
        });
        add(btnBack);
    }

    private JPanel createRolePanel(String emoji, String title, String subtitle) {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(225, 229, 245));
        panel.setBorder(BorderFactory.createLineBorder(new Color(55, 80, 160), 2));

        JLabel emojiLabel = new JLabel(emoji, SwingConstants.CENTER);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        emojiLabel.setBounds(80, 50, 100, 100);
        panel.add(emojiLabel);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 24));
        titleLabel.setBounds(20, 160, 220, 40);
        panel.add(titleLabel);

        JLabel subLabel = new JLabel(subtitle, SwingConstants.CENTER);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setBounds(20, 200, 220, 30);
        panel.add(subLabel);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new registrationSelect().setVisible(true));
    }
}