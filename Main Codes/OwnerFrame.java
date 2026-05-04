package kyusiyusphere;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class OwnerFrame extends JFrame {

    private JPanel placesContainer;
    private JTable tblPendingReservations;
    private JPanel reviewsContainer;
    
    // Retro/Wireframe UI Colors
    private final Color bgLight = new Color(240, 240, 240);
    private final Color panelGray = new Color(220, 220, 220);
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);
    private final Font headerFont = new Font("Courier New", Font.BOLD, 28);

    public OwnerFrame() {
        setTitle("KyusiyuSphere - Cite Owner Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        loadRealData();
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(bgLight);
        
        // --- TOP HEADER ---
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(bgLight);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titleLbl = new JLabel("CITE OWNER DASHBOARD:");
        titleLbl.setFont(new Font("Impact", Font.PLAIN, 42));
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        String ownerName = Session.isLoggedIn() ? Session.getCurrentUser().name : "OWNER";
        JLabel subTitleLbl = new JLabel("WELCOME, " + ownerName.toUpperCase() + " | REVIEWS: 4.8★ | SETTINGS");
        subTitleLbl.setFont(mainFont);
        subTitleLbl.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        subTitleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLbl);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subTitleLbl);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // --- MAIN 3-COLUMN LAYOUT ---
        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setBackground(bgLight);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 20, 10);
        gbc.weighty = 1.0;

        // 1. LEFT COLUMN: YOUR CITES
        gbc.gridx = 0;
        gbc.weightx = 0.25;
        mainContent.add(createLeftPanel(), gbc);

        // 2. CENTER COLUMN: PENDING RESERVATIONS
        gbc.gridx = 1;
        gbc.weightx = 0.50;
        mainContent.add(createCenterPanel(), gbc);

        // 3. RIGHT COLUMN: STUDENT REVIEWS
        gbc.gridx = 2;
        gbc.weightx = 0.25;
        mainContent.add(createRightPanel(), gbc);

        getContentPane().add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(panelGray);
        panel.setBorder(BorderFactory.createLineBorder(borderBlack, 3));

        JLabel lblHeader = new JLabel("YOUR CITES", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Courier New", Font.BOLD, 18));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(lblHeader, BorderLayout.NORTH);

        placesContainer = new JPanel();
        placesContainer.setLayout(new BoxLayout(placesContainer, BoxLayout.Y_AXIS));
        placesContainer.setBackground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(placesContainer);
        scroll.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, borderBlack));
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnAdd = new JButton("ADD NEW CITE");
        btnAdd.setFont(mainFont);
        btnAdd.setBackground(panelGray);
        btnAdd.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 20, 10, 20),
            BorderFactory.createLineBorder(borderBlack, 2)
        ));
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> {
            new AddCiteDialog(this).setVisible(true);
            loadRealData();
        });
        
        JPanel bottomWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomWrap.setBackground(panelGray);
        bottomWrap.add(btnAdd);
        panel.add(bottomWrap, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(panelGray);
        panel.setBorder(BorderFactory.createLineBorder(borderBlack, 3));

        JLabel lblHeader = new JLabel("PENDING RESERVATIONS", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Courier New", Font.BOLD, 18));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(lblHeader, BorderLayout.NORTH);

        String[] columns = {"STUDENT NAME", "DATE", "TIME", "CITE", "STATUS (Click to Manage)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tblPendingReservations = new JTable(model);
        tblPendingReservations.setRowHeight(50);
        tblPendingReservations.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblPendingReservations.getTableHeader().setFont(mainFont);
        tblPendingReservations.getTableHeader().setBackground(panelGray);
        tblPendingReservations.getTableHeader().setBorder(BorderFactory.createLineBorder(borderBlack, 1));
        tblPendingReservations.setShowGrid(true);
        tblPendingReservations.setGridColor(borderBlack);

        // Center align table contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblPendingReservations.getColumnCount(); i++) {
            tblPendingReservations.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Action Listener for Management
        tblPendingReservations.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblPendingReservations.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    String studentName = (String) tblPendingReservations.getValueAt(row, 0);
                    String date = (String) tblPendingReservations.getValueAt(row, 1);
                    String time = (String) tblPendingReservations.getValueAt(row, 2);
                    String placeName = (String) tblPendingReservations.getValueAt(row, 3);
                    String currentStatus = (String) tblPendingReservations.getValueAt(row, 4);

                    if (!currentStatus.equals("Pending")) {
                        JOptionPane.showMessageDialog(OwnerFrame.this, "This reservation is already " + currentStatus + ".");
                        return;
                    }

                    String[] options = {"APPROVE", "DENY", "CANCEL"};
                    int choice = JOptionPane.showOptionDialog(OwnerFrame.this,
                            "Action for " + studentName + " at " + placeName, "Manage",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    if (choice == 0) {
                        if (DatabaseHelper.updateReservationStatus(placeName, date, time, "Approved")) loadRealReservations();
                    } else if (choice == 1) {
                        if (DatabaseHelper.updateReservationStatus(placeName, date, time, "Rejected")) loadRealReservations();
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tblPendingReservations);
        scroll.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, borderBlack));
        panel.add(scroll, BorderLayout.CENTER);

        // Bottom Buttons
        JPanel bottomWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomWrap.setBackground(bgLight); // Overspill matches background

        JButton btnExport = new JButton("EXPORT LOGS");
        btnExport.setFont(mainFont);
        btnExport.setBackground(panelGray);
        btnExport.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        btnExport.setPreferredSize(new Dimension(140, 35));

        JButton btnRefresh = new JButton("REFRESH DATA");
        btnRefresh.setFont(mainFont);
        btnRefresh.setBackground(panelGray);
        btnRefresh.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        btnRefresh.setPreferredSize(new Dimension(140, 35));
        btnRefresh.addActionListener(e -> loadRealData());
        
        JButton btnLogout = new JButton("LOGOUT");
        btnLogout.setFont(mainFont);
        btnLogout.setBackground(new Color(255, 100, 100));
        btnLogout.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        btnLogout.setPreferredSize(new Dimension(100, 35));
        btnLogout.addActionListener(e -> {
            Session.logout();
            dispose();
            new startingScreen().setVisible(true);
        });

        bottomWrap.add(btnExport);
        bottomWrap.add(btnRefresh);
        bottomWrap.add(btnLogout);
        panel.add(bottomWrap, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(panelGray);
        panel.setBorder(BorderFactory.createLineBorder(borderBlack, 3));

        JLabel lblHeader = new JLabel("STUDENT REVIEWS", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Courier New", Font.BOLD, 18));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        
        JPanel controlPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        controlPanel.setBackground(panelGray);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sortPanel.setOpaque(false);
        sortPanel.add(new JLabel("SORT BY:"));
        sortPanel.add(new JButton("Newest"));
        sortPanel.add(new JButton("Highest"));
        
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setOpaque(false);
        searchPanel.add(new JLabel("KEYWORD:"), BorderLayout.WEST);
        JTextField txtSearch = new JTextField();
        txtSearch.setBorder(BorderFactory.createLineBorder(borderBlack, 1));
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        
        controlPanel.add(sortPanel);
        controlPanel.add(searchPanel);

        JPanel topWrap = new JPanel(new BorderLayout());
        topWrap.setOpaque(false);
        topWrap.add(lblHeader, BorderLayout.NORTH);
        topWrap.add(controlPanel, BorderLayout.CENTER);
        panel.add(topWrap, BorderLayout.NORTH);

        reviewsContainer = new JPanel();
        reviewsContainer.setLayout(new BoxLayout(reviewsContainer, BoxLayout.Y_AXIS));
        reviewsContainer.setBackground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(reviewsContainer);
        scroll.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, borderBlack));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void loadRealData() {
        placesContainer.removeAll();
        List<Place> allPlaces = DatabaseHelper.getAllPlaces();
        
        for (Place p : allPlaces) {
            if (p.owner != null && p.owner.id == Session.getCurrentUserId()) {
                placesContainer.add(createCiteListCard(p));
            }
        }

        if (placesContainer.getComponentCount() == 0) {
            JLabel empty = new JLabel("  No cites found.");
            empty.setFont(mainFont);
            placesContainer.add(empty);
        }

        placesContainer.revalidate();
        placesContainer.repaint();

        loadRealReservations();
        loadRealReviews();
    }

    private void loadRealReservations() {
        DefaultTableModel model = (DefaultTableModel) tblPendingReservations.getModel();
        model.setRowCount(0); 

        int currentOwnerId = Session.getCurrentUserId();
        List<Object[]> reservations = DatabaseHelper.getOwnerReservations(currentOwnerId);

        if (reservations.isEmpty()) {
            model.addRow(new Object[]{"-", "-", "-", "No reservations", "-"});
        } else {
            for (Object[] row : reservations) {
                // Tweak format slightly to fit the new table layout
                String originalDetails = (String) row[1]; // Date/time chunk
                String date = "-", time = "-";
                if(originalDetails != null && originalDetails.contains("at")) {
                    String[] dt = originalDetails.split("at");
                    date = dt[0].trim();
                    time = dt[1].trim();
                }
                
                model.addRow(new Object[]{
                    row[0], // Name
                    date,
                    time,
                    row[3], // Place
                    row[4]  // Status
                });
            }
        }
    }

    private JPanel createCiteListCard(Place p) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(500, 80));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, borderBlack),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel icon = new JLabel("🏢");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        card.add(icon, BorderLayout.WEST);

        JPanel info = new JPanel(new GridLayout(3, 1));
        info.setOpaque(false);
        
        JLabel lblName = new JLabel("[" + p.name + "]");
        lblName.setFont(new Font("Courier New", Font.BOLD, 16));
        
        JLabel lblAddress = new JLabel(p.location != null ? p.location : "No address");
        lblAddress.setFont(new Font("Courier New", Font.PLAIN, 12));
        
        JLabel lblReg = new JLabel("Label: " + p.label);
        lblReg.setFont(new Font("Courier New", Font.PLAIN, 12));

        info.add(lblName);
        info.add(lblAddress);
        info.add(lblReg);
        
        card.add(info, BorderLayout.CENTER);
        
        // Double-click to delete/edit
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String[] options = {"Edit Details", "Delete Facility", "Cancel"};
                    int choice = JOptionPane.showOptionDialog(OwnerFrame.this, 
                        "Manage Facility: " + p.name, "Manage Place",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    if (choice == 0) { 
                        String newDesc = JOptionPane.showInputDialog(OwnerFrame.this, "Enter new description:");
                        String newPrice = JOptionPane.showInputDialog(OwnerFrame.this, "Enter new price:");
                        if (newDesc != null && newPrice != null) {
                            DatabaseHelper.updatePlaceDetails(p.id, newDesc, newPrice);
                            loadRealData();
                        }
                    } else if (choice == 1) { 
                        if (JOptionPane.showConfirmDialog(OwnerFrame.this, "Delete " + p.name + "?", "Delete", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            DatabaseHelper.deletePlaceById(p.id);
                            loadRealData();
                        }
                    }
                }
            }
        });

        return card;
    }

    private void loadRealReviews() {
        reviewsContainer.removeAll();
        
        // Fetch real reviews from the DB using the logged-in owner's ID
        int currentOwnerId = Session.getCurrentUserId();
        List<String[]> dbReviews = DatabaseHelper.getOwnerReviews(currentOwnerId);

        if (dbReviews.isEmpty()) {
            JLabel empty = new JLabel("  No student reviews yet.");
            empty.setFont(mainFont);
            reviewsContainer.add(empty);
        } else {
            for (String[] r : dbReviews) {
                JPanel card = new JPanel(new BorderLayout(10, 5));
                card.setBackground(Color.WHITE);
                card.setMaximumSize(new Dimension(500, 100));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, borderBlack),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));

                JLabel avatar = new JLabel("👤"); 
                avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
                card.add(avatar, BorderLayout.WEST);

                JPanel content = new JPanel(new BorderLayout());
                content.setOpaque(false);
                
                JPanel topText = new JPanel(new BorderLayout());
                topText.setOpaque(false);
                
                // r[0] is User Name, r[1] is Place Name
                JLabel name = new JLabel(r[0]);
                name.setFont(new Font("Courier New", Font.BOLD, 14));
                JLabel placeTag = new JLabel("@" + r[1]); 
                placeTag.setFont(new Font("Courier New", Font.PLAIN, 11));
                placeTag.setForeground(new Color(100, 100, 100));
                
                topText.add(name, BorderLayout.WEST);
                topText.add(placeTag, BorderLayout.EAST);
                
                // r[2] is Stars, r[3] is Comment
                JLabel stars = new JLabel(r[2]);
                stars.setForeground(new Color(220, 180, 0));
                
                JTextArea comment = new JTextArea(r[3]);
                comment.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                comment.setLineWrap(true);
                comment.setWrapStyleWord(true);
                comment.setEditable(false);
                comment.setBorder(BorderFactory.createLineBorder(borderBlack, 1));

                JPanel middleText = new JPanel(new BorderLayout());
                middleText.setOpaque(false);
                middleText.add(topText, BorderLayout.NORTH);
                middleText.add(stars, BorderLayout.CENTER);

                content.add(middleText, BorderLayout.NORTH);
                content.add(comment, BorderLayout.CENTER);

                card.add(content, BorderLayout.CENTER);
                reviewsContainer.add(card);
            }
        }
        reviewsContainer.revalidate();
        reviewsContainer.repaint();
    }
}