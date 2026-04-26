package kyusiyusphere;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OwnerFrame extends javax.swing.JFrame {

    private JPanel placesContainer;
    private JTable tblPendingReservations;

    // Theme Colors
    private final Color bgDark = new Color(15, 15, 30);
    private final Color panelDark = new Color(25, 25, 45);
    private final Color accentBlue = new Color(0, 150, 255);
    private final Color textMuted = new Color(180, 180, 190);

    public OwnerFrame() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("KyusiyuSphere - Site Owner Dashboard");
        loadSampleData();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(bgDark);

        // ==================== WEST: SIDEBAR ====================
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(new Color(20, 20, 35));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(50, 50, 70)));

        // Logo / Title
        JLabel logo = new JLabel("KyusiSphere", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        logo.setForeground(accentBlue);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));
        sidebar.add(logo);

        // Navigation Menu
        String[] navItems = {"Dashboard", "My Places", "Reservations", "Reviews", "Logout"};
        for (String item : navItems) {
            JButton btn = new JButton(item);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
            btn.setForeground(textMuted);
            btn.setBackground(new Color(20, 20, 35));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 45));
            
            // Hover Effects
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    btn.setForeground(Color.WHITE);
                    btn.setBackground(panelDark);
                }
                public void mouseExited(MouseEvent e) {
                    btn.setForeground(textMuted);
                    btn.setBackground(new Color(20, 20, 35));
                }
            });
            
            // Pushing Logout to the bottom
            if(item.equals("Logout")) {
                sidebar.add(Box.createVerticalGlue());
                btn.addActionListener(e -> {
                    dispose();
                    new loginFrame().setVisible(true);
                });
            }
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        getContentPane().add(sidebar, BorderLayout.WEST);

        // ==================== CENTER: MAIN CONTENT ====================
        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(bgDark);

        // --- TOP BAR ---
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(panelDark);
        topBar.setPreferredSize(new Dimension(0, 70));
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        JLabel lblWelcome = new JLabel("Owner Dashboard");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblWelcome.setForeground(Color.WHITE);
        topBar.add(lblWelcome, BorderLayout.WEST);

        JButton btnAddPlace = new JButton("+ Add New Place");
        btnAddPlace.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAddPlace.setBackground(accentBlue);
        btnAddPlace.setForeground(Color.WHITE);
        btnAddPlace.setFocusPainted(false);
        btnAddPlace.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnAddPlace.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddPlace.addActionListener(e -> new AddCiteDialog(this).setVisible(true)); // Connects to your existing dialog
        
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 15));
        topRightPanel.setOpaque(false);
        topRightPanel.add(btnAddPlace);
        topBar.add(topRightPanel, BorderLayout.EAST);

        mainArea.add(topBar, BorderLayout.NORTH);

        // --- SCROLLABLE CONTENT AREA ---
        JPanel contentWrapper = new JPanel();
        contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.Y_AXIS));
        contentWrapper.setBackground(bgDark);
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 1. My Managed Places Section
        JLabel section1Title = new JLabel("My Managed Places");
        section1Title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        section1Title.setForeground(Color.WHITE);
        section1Title.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentWrapper.add(section1Title);
        contentWrapper.add(Box.createRigidArea(new Dimension(0, 15)));

        placesContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        placesContainer.setBackground(bgDark);
        placesContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentWrapper.add(placesContainer);
        
        contentWrapper.add(Box.createRigidArea(new Dimension(0, 40)));

        // 2. Reservations Table Section
        JLabel section2Title = new JLabel("Recent Reservations");
        section2Title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        section2Title.setForeground(Color.WHITE);
        section2Title.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentWrapper.add(section2Title);
        contentWrapper.add(Box.createRigidArea(new Dimension(0, 15)));

        String[] columns = {"Student Name", "Date", "Time", "CITE Place", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tblPendingReservations = new JTable(model);
        tblPendingReservations.setRowHeight(40);
        tblPendingReservations.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblPendingReservations.setBackground(panelDark);
        tblPendingReservations.setForeground(Color.WHITE);
        tblPendingReservations.setGridColor(new Color(50, 50, 70));
        tblPendingReservations.setSelectionBackground(accentBlue);
        tblPendingReservations.setSelectionForeground(Color.WHITE);
        tblPendingReservations.setShowVerticalLines(false);

        // Style the Table Header
        JTableHeader header = tblPendingReservations.getTableHeader();
        header.setBackground(new Color(35, 35, 55));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 45));
        header.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 70)));

        JScrollPane tableScroll = new JScrollPane(tblPendingReservations);
        tableScroll.getViewport().setBackground(bgDark);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 70)));
        tableScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableScroll.setPreferredSize(new Dimension(1000, 300));
        tableScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        contentWrapper.add(tableScroll);

        // Wrap the whole content inside a ScrollPane
        JScrollPane mainScroll = new JScrollPane(contentWrapper);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        mainScroll.setBorder(null);
        mainArea.add(mainScroll, BorderLayout.CENTER);

        getContentPane().add(mainArea, BorderLayout.CENTER);
    }

    private void loadSampleData() {
        placesContainer.removeAll();
        
        // Add sample Place Cards
        placesContainer.add(createDarkPlaceCard("Main Court", "Sports", "Available"));
        placesContainer.add(createDarkPlaceCard("Dance Studio A", "Dance", "Occupied"));
        placesContainer.add(createDarkPlaceCard("NetCafe Hub", "Esports", "Available"));
        placesContainer.add(createDarkPlaceCard("Open Park", "Study", "Maintenance"));
        
        placesContainer.revalidate();
        placesContainer.repaint();

        // Add sample Table Data
        DefaultTableModel model = (DefaultTableModel) tblPendingReservations.getModel();
        model.addRow(new Object[]{"John Doe", "2026-04-28", "10:00 AM", "Main Court", "Pending"});
        model.addRow(new Object[]{"Maria Santos", "2026-04-29", "02:00 PM", "Dance Studio A", "Approved"});
        model.addRow(new Object[]{"Peter Cruz", "2026-04-30", "05:00 PM", "NetCafe Hub", "Pending"});
    }

    // Helper method to generate standard UI Cards for Owner
    private JPanel createDarkPlaceCard(String title, String category, String status) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(panelDark);
        card.setPreferredSize(new Dimension(240, 180));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 70), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel icon = new JLabel("📍", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(icon);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel nameLbl = new JLabel(title, SwingConstants.CENTER);
        nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 17));
        nameLbl.setForeground(Color.WHITE);
        nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nameLbl);
        
        card.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel catLbl = new JLabel(category, SwingConstants.CENTER);
        catLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        catLbl.setForeground(textMuted);
        catLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(catLbl);
        
        card.add(Box.createVerticalGlue());
        
        JLabel statusLbl = new JLabel(status, SwingConstants.CENTER);
        statusLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLbl.setForeground(status.equals("Available") ? new Color(0, 200, 100) : (status.equals("Occupied") ? new Color(255, 150, 0) : new Color(255, 80, 80)));
        statusLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(statusLbl);

        // Hover effect for interactivity
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(accentBlue, 2),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(50, 50, 70), 2),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
        });

        return card;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new OwnerFrame().setVisible(true));
    }
}