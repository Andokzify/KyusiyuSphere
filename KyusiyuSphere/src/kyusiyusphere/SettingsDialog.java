package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsDialog extends JDialog {
    // Theme
    private final Color bgLight = new Color(210, 180, 140);
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);

    public SettingsDialog(JFrame parent) {
        super(parent, true);
        setUndecorated(true);
        setSize(250, 180);
        setLocationRelativeTo(parent);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(bgLight);
        main.setBorder(BorderFactory.createLineBorder(borderBlack, 4));

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(borderBlack);
        JLabel title = new JLabel(" SYSTEM SETTINGS", JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Courier New", Font.BOLD, 14));
        
        JLabel close = new JLabel(" X ", JLabel.RIGHT);
        close.setForeground(Color.WHITE);
        close.setFont(new Font("Courier New", Font.BOLD, 14));
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        
        header.add(title, BorderLayout.WEST);
        header.add(close, BorderLayout.EAST);
        main.add(header, BorderLayout.NORTH);

        // --- CONTENT / BUTTONS ---
        JPanel content = new JPanel(new GridLayout(2, 1, 10, 15));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. About Button
        JButton btnAbout = createMenuBtn("ABOUT APP");
        btnAbout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "KyusiyuSphere v1.0\nQCU Practice Space & Activity Locator", 
                "About", JOptionPane.INFORMATION_MESSAGE);
        });

        // 2. Logout Button
        JButton btnLogout = createMenuBtn("LOGOUT");
        btnLogout.setForeground(new Color(200, 0, 0)); 
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                Session.logout();
                parent.dispose();
                dispose();        
                new StartingScreen().setVisible(true); 
            }
        });

        content.add(btnAbout);
        content.add(btnLogout);

        main.add(content, BorderLayout.CENTER);
        add(main);
    }

    private JButton createMenuBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(mainFont);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(230, 230, 230)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(Color.WHITE); }
        });
        return btn;
    }
}