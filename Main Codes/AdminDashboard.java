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
    private JLabel lblBookingsNum;

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
        // 1. Create the Background Label with your GIF
        JLabel backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("/Images/REGISTRATION COMPLETE.gif")));
        backgroundLabel.setLayout(new BorderLayout(10, 10));
        
        // 2. Set this label as the absolute base of the frame
        setContentPane(backgroundLabel); 
        
        // 3. Create your main container, but make it TRANSPARENT
        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setOpaque(false); 
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

        // Add the main content directly to the background label
        getContentPane().add(mainContent, BorderLayout.CENTER);
    }

    // ================= LEFT PANEL =================
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setOpaque(false);

        // 1. Top Box: Admin ID Badge Aesthetic
        JPanel profileBox = new JPanel(new BorderLayout());
        profileBox.setBackground(Color.WHITE); // Crisp white background
        profileBox.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        
        JLabel lblWelcome = new JLabel(" [ ADMIN CLEARANCE ]", SwingConstants.LEFT);
        lblWelcome.setFont(new Font("Courier New", Font.BOLD, 14));
        lblWelcome.setOpaque(true);
        lblWelcome.setBackground(borderBlack);
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setPreferredSize(new Dimension(0, 30));
        profileBox.add(lblWelcome, BorderLayout.NORTH);
        
        JPanel infoPanel = new JPanel(new BorderLayout(10, 10));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        JLabel avatar = new JLabel("👤", SwingConstants.CENTER);
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        avatar.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        avatar.setPreferredSize(new Dimension(65, 65));
        
        String adminName = Session.isLoggedIn() ? Session.getCurrentUser().name : "ROOT_ADMIN";
        JLabel nameTag = new JLabel("<html><span style='font-size:15px;'><b>" + adminName.toUpperCase() + "</b></span><br/>SYS_OP: ACTIVE</html>");
        nameTag.setFont(new Font("Courier New", Font.PLAIN, 12));
        nameTag.setForeground(borderBlack);
        
        infoPanel.add(avatar, BorderLayout.WEST);
        infoPanel.add(nameTag, BorderLayout.CENTER);
        profileBox.add(infoPanel, BorderLayout.CENTER);

        // 2. Bottom Box: Navigation Menu
        JPanel navBox = new JPanel();
        navBox.setLayout(new BoxLayout(navBox, BoxLayout.Y_AXIS));
        navBox.setBackground(Color.WHITE);
        navBox.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        
        JLabel lblNav = new JLabel(" SYSTEM MENU:", SwingConstants.LEFT);
        lblNav.setFont(mainFont);
        lblNav.setOpaque(true);
        lblNav.setBackground(borderBlack);
        lblNav.setForeground(Color.WHITE);
        lblNav.setPreferredSize(new Dimension(0, 30));
        lblNav.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        lblNav.setAlignmentX(Component.CENTER_ALIGNMENT);
        navBox.add(lblNav);
        
        String[] menuItems = {"Manage Users", "Manage Places", "Logout"};
        String[] menuIcons = {"👥", "🏢", "🚪"}; 

        for (int i = 0; i < menuItems.length; i++) {
            String item = menuItems[i];
            String icon = menuIcons[i];

            JButton btn = new JButton(icon + "  " + item);
            btn.setFont(new Font("Courier New", Font.BOLD, 16));
            btn.setBackground(Color.WHITE);
            btn.setForeground(borderBlack);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, borderBlack),
                BorderFactory.createEmptyBorder(15, 20, 15, 10)
            ));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

            // Hover Effect
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { 
                    btn.setBackground(borderBlack); 
                    btn.setForeground(Color.WHITE);
                }
                public void mouseExited(MouseEvent e) { 
                    btn.setBackground(Color.WHITE); 
                    btn.setForeground(borderBlack);
                }
            });
            
            btn.addActionListener(e -> {
                if (item.equals("Manage Users")) cardLayout.show(tableCards, "USERS");
                else if (item.equals("Manage Places")) cardLayout.show(tableCards, "PLACES");
                else if (item.equals("Logout")) logout();
            });

            navBox.add(btn);
        }

        // Wrapper to push navigation to the top so it doesn't stretch weirdly
        JPanel navWrapper = new JPanel(new BorderLayout());
        navWrapper.setOpaque(false);
        navWrapper.add(navBox, BorderLayout.NORTH);

        panel.add(profileBox, BorderLayout.NORTH);
        panel.add(navWrapper, BorderLayout.CENTER);
        return panel;
    }
    
    // ================= CENTER PANEL =================
    
    // Helper method to generate uniform retro stat cards
    private JPanel createStatCard(String title, JLabel labelToUpdate) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE); 
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 3), 
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblTitle = new JLabel(" " + title);
        lblTitle.setFont(new Font("Courier New", Font.BOLD, 16));
        lblTitle.setForeground(Color.BLACK);
        
        labelToUpdate.setFont(new Font("Courier New", Font.BOLD, 48)); 
        labelToUpdate.setHorizontalAlignment(SwingConstants.CENTER);
        labelToUpdate.setForeground(new Color(30, 30, 30));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(labelToUpdate, BorderLayout.CENTER);
        
        return card;
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

        // ---  THE 1x3 WHITE CARD GRID ---
        JPanel digitsPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        digitsPanel.setOpaque(false);
        digitsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Initialize the labels
        lblTotalUsersNum = new JLabel("0");
        lblTotalPlacesNum = new JLabel("0");
        lblBookingsNum = new JLabel("0"); 

        // Three white cards
        digitsPanel.add(createStatCard("TOTAL USERS:", lblTotalUsersNum));
        digitsPanel.add(createStatCard("TOTAL SITES:", lblTotalPlacesNum));
        digitsPanel.add(createStatCard("APPROVED BOOKINGS:", lblBookingsNum));

        statsBox.add(digitsPanel, BorderLayout.CENTER);

        // Bottom Box: Data Tables (Swappable via CardLayout)
        cardLayout = new CardLayout();
        tableCards = new JPanel(cardLayout);
        tableCards.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        
        tableCards.add(createTableWrapper("USER DATABASE", createUsersTable()), "USERS");
        tableCards.add(createTableWrapper("SITE DATABASE", createPlacesTable()), "PLACES");

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

        JLabel lblLogo = new JLabel(new ImageIcon(getClass().getResource("/Images/QA Logo.gif")));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Matches the border styling from your retro mockup
        lblLogo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15),
            BorderFactory.createLineBorder(borderBlack, 2)
        ));
        lblLogo.setBackground(Color.WHITE);
        lblLogo.setOpaque(true);
        
        // 5 Clicks to trigger the Hard Reset
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
        JPanel actionBox = new JPanel(new GridLayout(2, 1, 10, 15)); 
        actionBox.setBackground(panelGray);
        actionBox.setOpaque(false); // Let the GIF shine through!
        actionBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 3),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        actionBox.add(createActionButton("<html><center><b>[ DELETE ]</b><br>USER</center></html>", e -> deleteSelectedUser()));
        actionBox.add(createActionButton("<html><center><b>[ DELETE ]</b><br>SITE</center></html>", e -> deleteSelectedPlace()));

        panel.add(actionBox, BorderLayout.CENTER);
        return panel;
    }

    private JButton createActionButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Courier New", Font.BOLD, 18));
        btn.setBackground(new Color(30, 30, 30)); 
        btn.setForeground(Color.WHITE);           
        btn.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover Effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
                btn.setBackground(new Color(150, 40, 40)); 
            }
            public void mouseExited(MouseEvent e) { 
                btn.setBackground(new Color(30, 30, 30)); 
            }
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
        if (lblTotalUsersNum != null) {
            lblTotalUsersNum.setText(String.valueOf(DatabaseHelper.getTotalPopulation()));
        }
        if (lblTotalPlacesNum != null) {
            lblTotalPlacesNum.setText(String.valueOf(DatabaseHelper.getTotalFacilities()));
        }
        if (lblBookingsNum != null) {
            lblBookingsNum.setText(String.valueOf(DatabaseHelper.getBookingVolume()));
        }
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
            JOptionPane.showMessageDialog(this, "SYSTEM RESET COMPLETE. All accounts and sites have been purged.");
            
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