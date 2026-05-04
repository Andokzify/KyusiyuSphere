package kyusiyusphere;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboard extends JFrame {
    
    private JTable userTable, placeTable;
    private DefaultTableModel userModel, placeModel;
    
    // CardLayout for swapping tables in the center column
    private JPanel tableCards;
    private CardLayout cardLayout;

    // Stat Labels that need to update on refresh
    private JLabel lblTotalUsersNum;
    private JLabel lblTotalPlacesNum;

    // Retro/Wireframe UI Colors
    private final Color bgLight = new Color(240, 240, 240); 
    private final Color panelGray = new Color(210, 210, 210);
    private final Color insetGray = new Color(190, 190, 190);
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);
    private final Font digitalFontHuge = new Font("Courier New", Font.BOLD, 72);
    private final Font digitalFontMed = new Font("Courier New", Font.BOLD, 36);

    public AdminDashboard() {
        setTitle("KyusiyuSphere - System Administration");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        refreshAll(); 
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(bgLight);
        
        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setBackground(bgLight);
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weighty = 1.0;

        // 1. LEFT COLUMN: PROFILE & NAVIGATION
        gbc.gridx = 0;
        gbc.weightx = 0.20;
        mainContent.add(createLeftPanel(), gbc);

        // 2. CENTER COLUMN: STATS & TABLES
        gbc.gridx = 1;
        gbc.weightx = 0.55;
        mainContent.add(createCenterPanel(), gbc);

        // 3. RIGHT COLUMN: QUICK ACTIONS
        gbc.gridx = 2;
        gbc.weightx = 0.25;
        mainContent.add(createRightPanel(), gbc);

        getContentPane().add(mainContent, BorderLayout.CENTER);
    }

    // ================= LEFT PANEL =================
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        // Top Box: Welcome & Profile
        JPanel profileBox = new JPanel(new BorderLayout());
        profileBox.setBackground(panelGray);
        profileBox.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        
        JLabel lblWelcome = new JLabel(" WELCOME ADMIN!!", SwingConstants.LEFT);
        lblWelcome.setFont(mainFont);
        lblWelcome.setOpaque(true);
        lblWelcome.setBackground(new Color(60, 60, 60));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setPreferredSize(new Dimension(0, 30));
        profileBox.add(lblWelcome, BorderLayout.NORTH);
        
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        infoPanel.setOpaque(false);
        JLabel avatar = new JLabel("👤");
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        avatar.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        
        String adminName = Session.isLoggedIn() ? Session.getCurrentUser().name : "ROOT_ADMIN";
        JLabel nameTag = new JLabel("<html><b>" + adminName.toUpperCase() + "</b><br/>ID: 8154125</html>");
        nameTag.setFont(mainFont);
        
        infoPanel.add(avatar);
        infoPanel.add(nameTag);
        profileBox.add(infoPanel, BorderLayout.CENTER);

        // Bottom Box: Navigation
        JPanel navBox = new JPanel();
        navBox.setLayout(new BoxLayout(navBox, BoxLayout.Y_AXIS));
        navBox.setBackground(panelGray);
        navBox.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        
        String[] menuItems = {"Manage Users", "Manage Places", "Suspended", "Roles", "Logout"};
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setFont(mainFont);
            btn.setBackground(panelGray);
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, borderBlack));
            btn.setFocusPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(500, 50));

            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { btn.setBackground(insetGray); }
                public void mouseExited(MouseEvent e) { btn.setBackground(panelGray); }
            });
            
            btn.addActionListener(e -> {
                if (item.equals("Manage Users")) cardLayout.show(tableCards, "USERS");
                else if (item.equals("Manage Places")) cardLayout.show(tableCards, "PLACES");
                else if (item.equals("Logout")) logout();
                else JOptionPane.showMessageDialog(this, item + " module offline.");
            });

            navBox.add(btn);
        }

        panel.add(profileBox, BorderLayout.NORTH);
        panel.add(navBox, BorderLayout.CENTER);
        return panel;
    }

    // ================= CENTER PANEL =================
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setOpaque(false);

        // Top Box: Server Chart / Stats
        JPanel statsBox = new JPanel(new BorderLayout());
        statsBox.setBackground(panelGray);
        statsBox.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        
        JLabel lblChart = new JLabel(" SERVER CHART:", SwingConstants.LEFT);
        lblChart.setFont(mainFont);
        lblChart.setOpaque(true);
        lblChart.setBackground(new Color(60, 60, 60));
        lblChart.setForeground(Color.WHITE);
        lblChart.setPreferredSize(new Dimension(0, 30));
        statsBox.add(lblChart, BorderLayout.NORTH);

        JPanel digitsPanel = new JPanel(new BorderLayout(10, 10));
        digitsPanel.setOpaque(false);
        digitsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Big Stat (Total Users)
        JPanel pnlBigStat = new JPanel(new BorderLayout());
        pnlBigStat.setBackground(insetGray);
        pnlBigStat.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        JLabel lblTotalUsersTitle = new JLabel(" TOTAL USERS:");
        lblTotalUsersTitle.setFont(mainFont);
        lblTotalUsersNum = new JLabel("0", SwingConstants.CENTER);
        lblTotalUsersNum.setFont(digitalFontHuge);
        pnlBigStat.add(lblTotalUsersTitle, BorderLayout.NORTH);
        pnlBigStat.add(lblTotalUsersNum, BorderLayout.CENTER);
        digitsPanel.add(pnlBigStat, BorderLayout.NORTH);

        // Two Medium Stats
        JPanel pnlSubStats = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlSubStats.setOpaque(false);
        
        JPanel pnlPlaces = new JPanel(new BorderLayout());
        pnlPlaces.setBackground(insetGray);
        pnlPlaces.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        pnlPlaces.add(new JLabel(" TOTAL CITES:"), BorderLayout.NORTH);
        lblTotalPlacesNum = new JLabel("0", SwingConstants.CENTER);
        lblTotalPlacesNum.setFont(digitalFontMed);
        pnlPlaces.add(lblTotalPlacesNum, BorderLayout.CENTER);
        
        JPanel pnlActive = new JPanel(new BorderLayout());
        pnlActive.setBackground(insetGray);
        pnlActive.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        pnlActive.add(new JLabel(" SYSTEM HEALTH:"), BorderLayout.NORTH);
        JLabel lblHealth = new JLabel("GOOD", SwingConstants.CENTER);
        lblHealth.setFont(digitalFontMed);
        pnlActive.add(lblHealth, BorderLayout.CENTER);
        
        pnlSubStats.add(pnlPlaces);
        pnlSubStats.add(pnlActive);
        digitsPanel.add(pnlSubStats, BorderLayout.CENTER);
        
        statsBox.add(digitsPanel, BorderLayout.CENTER);

        // Bottom Box: Data Tables (Swappable via CardLayout)
        cardLayout = new CardLayout();
        tableCards = new JPanel(cardLayout);
        tableCards.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        
        tableCards.add(createTableWrapper("USER DATABASE", createUsersTable()), "USERS");
        tableCards.add(createTableWrapper("CITE DATABASE", createPlacesTable()), "PLACES");

        panel.add(statsBox, BorderLayout.NORTH);
        panel.add(tableCards, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTableWrapper(String title, JTable table) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(panelGray);
        
        JLabel lblTitle = new JLabel(" " + title, SwingConstants.LEFT);
        lblTitle.setFont(mainFont);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(60, 60, 60));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setPreferredSize(new Dimension(0, 30));
        wrapper.add(lblTitle, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    // ================= RIGHT PANEL =================
    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        // Top Box: Logo Placeholder
        JPanel logoBox = new JPanel(new BorderLayout());
        logoBox.setBackground(panelGray);
        logoBox.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        
        JLabel lblQuick = new JLabel(" QUICK ACTIONS:", SwingConstants.LEFT);
        lblQuick.setFont(mainFont);
        lblQuick.setOpaque(true);
        lblQuick.setBackground(new Color(60, 60, 60));
        lblQuick.setForeground(Color.WHITE);
        lblQuick.setPreferredSize(new Dimension(0, 30));
        logoBox.add(lblQuick, BorderLayout.NORTH);

        JLabel lblLogo = new JLabel("<html><center><h1>🌍</h1><b>KYUSIYU<br>SPHERE</b></center></html>", SwingConstants.CENTER);
        lblLogo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20),
            BorderFactory.createLineBorder(borderBlack, 2)
        ));
        lblLogo.setBackground(Color.WHITE);
        lblLogo.setOpaque(true);
        
        lblLogo.addMouseListener(new MouseAdapter() {
        int clickCount = 0;
        @Override
        public void mouseClicked(MouseEvent e) {
            clickCount++;
            if (clickCount >= 5) {
                clickCount = 0; 
                triggerHardReset();
            }
        }
    });
    // --------------------------------
    
    logoBox.add(lblLogo, BorderLayout.CENTER);
        panel.add(logoBox, BorderLayout.NORTH);

        // Bottom Box: Action Grid
        JPanel actionBox = new JPanel(new GridLayout(2, 2, 5, 5));
        actionBox.setBackground(panelGray);
        actionBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        actionBox.add(createActionButton("🗑 User", e -> deleteSelectedUser()));
        actionBox.add(createActionButton("🗑 Cite", e -> deleteSelectedPlace()));
        actionBox.add(createActionButton("🔄 Refresh", e -> refreshAll()));
        actionBox.add(createActionButton("📢 Announce", e -> JOptionPane.showMessageDialog(this, "Global Announcement Sent!")));

        panel.add(actionBox, BorderLayout.CENTER);
        return panel;
    }

    private JButton createActionButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(mainFont);
        btn.setBackground(panelGray);
        btn.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        btn.setFocusPainted(false);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(insetGray); }
            public void mouseExited(MouseEvent e) { btn.setBackground(panelGray); }
        });
        btn.addActionListener(action);
        return btn;
    }

    // ================= TABLE CREATION & STYLING =================
    private JTable createUsersTable() {
        String[] columns = {"Name", "Email", "Role"};
        userModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        userTable = new JTable(userModel);
        styleTable(userTable);
        return userTable;
    }

    private JTable createPlacesTable() {
        String[] columns = {"Place Name", "Category", "Contact"};
        placeModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        placeTable = new JTable(placeModel);
        styleTable(placeTable);
        return placeTable;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(mainFont);
        table.getTableHeader().setBackground(panelGray);
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(borderBlack, 1));
        table.setShowGrid(true);
        table.setGridColor(borderBlack);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    // ================= DATABASE LOGIC =================
    private void deleteSelectedUser() {
        cardLayout.show(tableCards, "USERS");
        int row = userTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user from the table first.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String name = (String) userModel.getValueAt(row, 0);
        String email = (String) userModel.getValueAt(row, 1);
        String role = (String) userModel.getValueAt(row, 2);
        
        if (role.equals("Admin")) {
            JOptionPane.showMessageDialog(this, "You cannot delete another Admin!", "Permission Denied", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (JOptionPane.showConfirmDialog(this, "Delete user: " + name + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (DatabaseHelper.deleteUserByEmail(email)) {
                refreshAll();
            }
        }
    }

    private void deleteSelectedPlace() {
        cardLayout.show(tableCards, "PLACES");
        int row = placeTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a facility from the table first.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String placeName = (String) placeModel.getValueAt(row, 0);
        
        if (JOptionPane.showConfirmDialog(this, "Delete facility: " + placeName + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (DatabaseHelper.deletePlaceByName(placeName)) {
                refreshAll(); 
            }
        }
    }

    private void refreshAll() {
        // 1. Refresh User Table
        userModel.setRowCount(0);
        java.util.List<UserAccount> users = DatabaseHelper.getAllUsers();
        for (UserAccount u : users) {
            String roleStr = u.role == 1 ? "Student" : u.role == 2 ? "Owner" : "Admin";
            userModel.addRow(new Object[]{u.name, u.email, roleStr});
        }

        // 2. Refresh Places Table
        placeModel.setRowCount(0);
        java.util.List<Place> places = DatabaseHelper.getAllPlaces();
        for (Place p : places) {
            placeModel.addRow(new Object[]{
                p.name, 
                p.label != null ? p.label : "General", 
                p.contact != null ? p.contact : "N/A"
            });
        }

        // 3. Refresh the Digital Stats
        if (lblTotalUsersNum != null) lblTotalUsersNum.setText(String.valueOf(users.size()));
        if (lblTotalPlacesNum != null) lblTotalPlacesNum.setText(String.valueOf(places.size()));
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Session.logout();
            dispose();
            new startingScreen().setVisible(true); 
        }
    }
    
    private void triggerHardReset() {
    // Level 1 Confirmation
    int firstConfirm = JOptionPane.showConfirmDialog(this, 
        "SECRET COMMAND DETECTED: Wipe all system data?", 
        "SYSTEM OVERRIDE", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    
    if (firstConfirm == JOptionPane.YES_OPTION) {
        // Level 2 Confirmation (Double Check)
        String entry = JOptionPane.showInputDialog(this, "Type 'ERASE' to confirm total database destruction:");
        
        if (entry != null && entry.equalsIgnoreCase("ERASE")) {
            DatabaseHelper.wipeAndReset();
            JOptionPane.showMessageDialog(this, "SYSTEM RESET COMPLETE. All accounts and cites have been purged.");
            
            // Force logout because the current admin account no longer exists in the DB!
            dispose();
            new startingScreen().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Reset Aborted.");
        }
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}