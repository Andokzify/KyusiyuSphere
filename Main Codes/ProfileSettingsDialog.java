package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProfileSettingsDialog extends JDialog {
    private final Color bgLight = new Color(210, 180, 140);
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);
    
    private UserAccount user;
    private JLabel avatar;
    private JLabel lblName;
    private JLabel lblRole;

    public ProfileSettingsDialog(JFrame parent, UserAccount user) {
        super(parent, true);
        this.user = user;
        setUndecorated(true);
        setSize(400, 580); 
        setLocationRelativeTo(parent);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(bgLight);
        main.setBorder(BorderFactory.createLineBorder(borderBlack, 4));

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(borderBlack);
        header.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
        
        JLabel title = new JLabel("USER PROFILE", JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Courier New", Font.BOLD, 16));
        
        JLabel close = new JLabel("X", JLabel.RIGHT);
        close.setForeground(Color.WHITE);
        close.setFont(new Font("Courier New", Font.BOLD, 16));
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        
        header.add(title, BorderLayout.WEST);
        header.add(close, BorderLayout.EAST);
        main.add(header, BorderLayout.NORTH);

        // --- CENTER CONTENT ---
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(25, 20, 20, 20));

        // 1. Circular Avatar
        avatar = new JLabel();
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(avatar);
        content.add(Box.createRigidArea(new Dimension(0, 15)));

        // 2. User Info
        lblName = new JLabel(user.name.toUpperCase());
        lblName.setFont(new Font("Courier New", Font.BOLD, 26)); 
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        String roleStr = user.role == 1 ? "STUDENT" : user.role == 2 ? "OWNER" : "ADMIN";
        lblRole = new JLabel(roleStr + " | " + user.campus.toUpperCase());
        lblRole.setFont(new Font("Courier New", Font.PLAIN, 12));
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblEmail = new JLabel("✉ " + user.email);
        lblEmail.setFont(new Font("Courier New", Font.PLAIN, 13));
        lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(lblName);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(lblRole);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(lblEmail);
        content.add(Box.createRigidArea(new Dimension(0, 25)));

        // 3. Stats Panel
        JPanel statsWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        statsWrapper.setOpaque(false);
        
        int resCount = 0;
        if (Session.isLoggedIn()) {
            java.util.List<String> res = DatabaseHelper.getUserReservations(Session.getCurrentUserId());
            resCount = res.size();
        }
        
        statsWrapper.add(createStatBox(String.valueOf(resCount), "RESERVATIONS"));
        statsWrapper.add(createStatBox("0", "FAVORITES")); 
        
        content.add(statsWrapper);
        content.add(Box.createRigidArea(new Dimension(0, 35))); 

        // 4. Action Buttons
        JButton btnEdit = createMenuBtn("EDIT PROFILE");
        btnEdit.addActionListener(e -> {
            new EditProfileDialog(this, user).setVisible(true);
            if(Session.isLoggedIn()) {
                this.user = Session.getCurrentUser(); 
            }
            refreshUI(); 
        });
        
        JButton btnPass = createMenuBtn("CHANGE PASSWORD");
        btnPass.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Change Password dialog coming soon!");
        });

        content.add(btnEdit);
        content.add(Box.createRigidArea(new Dimension(0, 12)));
        content.add(btnPass);

        main.add(content, BorderLayout.CENTER);
        add(main);
        
        refreshUI();
    }

    private void refreshUI() {
        lblName.setText(user.name.toUpperCase());
        String roleStr = user.role == 1 ? "STUDENT" : user.role == 2 ? "OWNER" : "ADMIN";
        lblRole.setText(roleStr + " | " + user.campus.toUpperCase());

        System.out.println("📸 Loading Profile Picture Path: " + user.profilePicPath); // Debug line

        ImageIcon profileIcon = getCircularIcon(user.profilePicPath, 140); 
        if (profileIcon != null) {
            avatar.setIcon(profileIcon);
            avatar.setText("");
            avatar.setBorder(null); 
        } else {
            avatar.setIcon(null);
            avatar.setText("👤");
            avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 90));
            avatar.setBorder(null);
        }
        revalidate();
        repaint();
    }

    private ImageIcon getCircularIcon(String path, int size) {
        if (path == null || path.trim().isEmpty() || path.equalsIgnoreCase("null")) {
            System.out.println("⚠️ DEBUG: No photo path found for this user in memory.");
            return null;
        }

        try {
            java.io.File file = new java.io.File(path);
            if (!file.exists()) {
                System.out.println("⚠️ ERROR: Cannot find the image file at -> " + path);
                return null;
            }

            java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(file);
            
            if (img == null) {
                System.out.println("⚠️ ERROR: File exists, but Java can't read it as an image.");
                return null;
            }

            java.awt.image.BufferedImage masked = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = masked.createGraphics();
            
            // High quality rendering
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            
            // Cut the circle
            g2.fill(new java.awt.geom.Ellipse2D.Double(0, 0, size, size));
            g2.setComposite(AlphaComposite.SrcIn);
            g2.drawImage(img, 0, 0, size, size, null);
            g2.dispose();
            
            return new ImageIcon(masked);
            
        } catch (Exception e) {
            System.out.println("⚠️ CRASH during image loading: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private JPanel createStatBox(String number, String label) {
        JPanel box = new JPanel(new BorderLayout());
        box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(140, 75)); 
        box.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 2),
            BorderFactory.createEmptyBorder(10, 5, 10, 5) 
        ));
        
        JLabel num = new JLabel(number, SwingConstants.CENTER);
        num.setFont(new Font("Courier New", Font.BOLD, 28)); 
        
        JLabel lbl = new JLabel(label, SwingConstants.CENTER);
        lbl.setFont(new Font("Courier New", Font.PLAIN, 11)); 
        lbl.setForeground(new Color(80, 80, 80)); 
        
        box.add(num, BorderLayout.CENTER);
        box.add(lbl, BorderLayout.SOUTH);
        return box;
    }

    private JButton createMenuBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(mainFont);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(300, 40));
        btn.setMaximumSize(new Dimension(300, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(230, 230, 230)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(Color.WHITE); }
        });
        return btn;
    }
}