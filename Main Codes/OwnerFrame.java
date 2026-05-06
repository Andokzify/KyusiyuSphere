package kyusiyusphere;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OwnerFrame extends JFrame {

    private JPanel placesContainer;
    private JTable tblPendingReservations;
    private JPanel reviewsContainer;
    private DatePicker filterDatePicker; 
    
    private final Color bgLight = new Color(240, 240, 240);
    private final Color panelGray = new Color(220, 220, 220);
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);

    public OwnerFrame() {
        setTitle("KyusiyuSphere - Site Owner Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        loadRealData();
    }

    private void initComponents() {
        JLabel background = new JLabel();
        java.net.URL imgURL = getClass().getResource("/Images/REGISTRATION COMPLETE.gif"); 
        if (imgURL != null) {
            background.setIcon(new ImageIcon(imgURL));
        }
        background.setLayout(new BorderLayout(10, 10));

        // --- TOP HEADER ---
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false); 
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titleLbl = new JLabel("SITE OWNER DASHBOARD:");
        titleLbl.setFont(new Font("Impact", Font.PLAIN, 42));
        titleLbl.setForeground(Color.black); 
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        String ownerName = Session.isLoggedIn() ? Session.getCurrentUser().name : "OWNER";
        double avgRating = DatabaseHelper.getOwnerAverageRating(Session.getCurrentUserId());
        
        JPanel subTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        subTitlePanel.setBackground(Color.WHITE);
        subTitlePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        subTitlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitlePanel.setMaximumSize(new Dimension(650, 40)); 

        // The static welcome text
        JLabel lblWelcome = new JLabel("WELCOME, " + ownerName.toUpperCase() + " | REVIEWS: " + String.format("%.1f", avgRating) + "★ | ");
        lblWelcome.setFont(mainFont);
        lblWelcome.setForeground(Color.BLACK);

        // The interactive clickable Settings button
        JLabel lblSettings = new JLabel("⚙ SETTINGS");
        lblSettings.setFont(mainFont);
        lblSettings.setForeground(new Color(0, 50, 150)); // Dark blue link color
        lblSettings.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effects and click logic
        lblSettings.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { lblSettings.setForeground(new Color(100, 150, 255)); }
            public void mouseExited(MouseEvent e) { lblSettings.setForeground(new Color(0, 50, 150)); }
            public void mouseClicked(MouseEvent e) {
                // Launches the exact same Settings dialog used in the Student Explorer
                new ProfileSettingsDialog(OwnerFrame.this, Session.getCurrentUser()).setVisible(true);
            }
        });

        subTitlePanel.add(lblWelcome);
        subTitlePanel.add(lblSettings);

        headerPanel.add(titleLbl);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subTitlePanel); 
        
        background.add(headerPanel, BorderLayout.NORTH); 

        // --- MAIN 3-COLUMN LAYOUT ---
        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 20, 10);
        gbc.weighty = 1.0;

        JPanel leftCol = createLeftPanel();
        leftCol.setPreferredSize(new Dimension(0, 0));
        
        JPanel centerCol = createCenterPanel();
        centerCol.setPreferredSize(new Dimension(0, 0));
        
        JPanel rightCol = createRightPanel();
        rightCol.setPreferredSize(new Dimension(0, 0));

        gbc.gridx = 0; gbc.weightx = 0.30;
        mainContent.add(leftCol, gbc);

        gbc.gridx = 1; gbc.weightx = 0.40;
        mainContent.add(centerCol, gbc);

        gbc.gridx = 2; gbc.weightx = 0.30;
        mainContent.add(rightCol, gbc);

        background.add(mainContent, BorderLayout.CENTER); 

        setContentPane(background);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(panelGray);
        panel.setBorder(BorderFactory.createLineBorder(borderBlack, 3));

        JLabel lblHeader = new JLabel("YOUR SITES", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Courier New", Font.BOLD, 18));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(lblHeader, BorderLayout.NORTH);

        placesContainer = new JPanel();
        placesContainer.setLayout(new BoxLayout(placesContainer, BoxLayout.Y_AXIS));
        placesContainer.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(placesContainer);
        scroll.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, borderBlack));
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnAdd = new JButton("ADD NEW SITE");
        btnAdd.setFont(mainFont);
        btnAdd.setBackground(panelGray);
        btnAdd.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 20, 10, 20),
            BorderFactory.createLineBorder(borderBlack, 2)
        ));
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

        // THE CALENDAR FILTER HEADER
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        filterPanel.setBackground(panelGray);
        filterPanel.add(new JLabel("📅 Filter by Date:"));
        
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("MMMM dd, yyyy");
        filterDatePicker = new DatePicker(dateSettings);
        filterDatePicker.addDateChangeListener(e -> loadRealReservations());
        
        JButton btnClearDate = new JButton("Clear");
        btnClearDate.setBackground(Color.WHITE);
        btnClearDate.setFocusPainted(false);
        btnClearDate.addActionListener(e -> {
            filterDatePicker.clear();
            loadRealReservations();
        });

        filterPanel.add(filterDatePicker);
        filterPanel.add(btnClearDate);
        panel.add(filterPanel, BorderLayout.NORTH);

        // TABLE SETUP
        String[] columns = {"STUDENT", "DATE", "TIME", "SITE", "STATUS"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tblPendingReservations = new JTable(model);
        tblPendingReservations.setRowHeight(40);
        tblPendingReservations.setFillsViewportHeight(true); 
        tblPendingReservations.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblPendingReservations.getTableHeader().setFont(mainFont);
        tblPendingReservations.getTableHeader().setBackground(panelGray);
        tblPendingReservations.getTableHeader().setBorder(BorderFactory.createLineBorder(borderBlack, 1));
        
        // 📍 THE COLOR RENDERER
        tblPendingReservations.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = (String) value;
                setFont(new Font("Courier New", Font.BOLD, 14));
                
                if ("Approved".equalsIgnoreCase(status)) {
                    setForeground(new Color(0, 150, 0)); 
                } else if ("Denied".equalsIgnoreCase(status) || "Cancel".equalsIgnoreCase(status)) {
                    setForeground(Color.RED);
                } else {
                    setForeground(new Color(200, 100, 0)); // Orange for Pending
                }
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        // THE DOUBLE-CLICK LOGIC
        tblPendingReservations.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblPendingReservations.getSelectedRow();
                    if (row != -1) {
                        String student = (String) model.getValueAt(row, 0);
                        String date = (String) model.getValueAt(row, 1);
                        String time = (String) model.getValueAt(row, 2);
                        String place = (String) model.getValueAt(row, 3);
                        String status = (String) model.getValueAt(row, 4);

                        if (!status.equals("Pending")) {
                            JOptionPane.showMessageDialog(OwnerFrame.this, "This reservation is already " + status);
                            return;
                        }

                        String[] options = {"Approve", "Deny", "Cancel"};
                        int choice = JOptionPane.showOptionDialog(OwnerFrame.this,
                                "Update reservation for " + student + " at " + place + "?",
                                "Manage Reservation",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, options, options[0]);

                        if (choice == 0) { 
                            DatabaseHelper.updateReservationStatus(place, date, time, "Approved");
                            loadRealData(); 
                        } else if (choice == 1) { 
                            DatabaseHelper.updateReservationStatus(place, date, time, "Denied");
                            loadRealData(); 
                        }
                    }
                }
            }
        });
        
        JScrollPane scroll = new JScrollPane(tblPendingReservations);
        scroll.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, borderBlack));
        panel.add(scroll, BorderLayout.CENTER);

        // LOGOUT BUTTON
        JPanel bottomWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        bottomWrap.setBackground(bgLight);

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
        lblHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(lblHeader, BorderLayout.NORTH);

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
        placesContainer.revalidate();
        placesContainer.repaint();
        loadRealReservations();
        loadRealReviews();
    }

    private JPanel createCiteListCard(Place p) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(500, 90));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, borderBlack),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(70, 70));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        ImageIcon placeIcon = ImageManager.loadPlaceImage(p.name, 70, 70);
        
        if (placeIcon != null) {
            iconLabel.setIcon(placeIcon);
            iconLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Nice frame for the photo
        } else {
            iconLabel.setText("🏢");
            iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        }
        
        card.add(iconLabel, BorderLayout.WEST);

        String priceFormatted = String.format("₱ %.2f", p.price);
        
        JPanel info = new JPanel(new GridLayout(3, 1));
        info.setOpaque(false);
        info.add(new JLabel("[" + p.name + "]"));
        info.add(new JLabel("Rate: " + priceFormatted));
        info.add(new JLabel("Limit: " + p.maxReservations + " slots")); 

        card.add(info, BorderLayout.CENTER);
        
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    new EditCiteDialog(OwnerFrame.this, p).setVisible(true);
                    loadRealData();
                }
            }
        });
        return card;
    }

    private void loadRealReservations() {
        DefaultTableModel model = (DefaultTableModel) tblPendingReservations.getModel();
        model.setRowCount(0); 
        
        LocalDate selectedDate = filterDatePicker.getDate();
        String formattedFilter = "";
        if (selectedDate != null) {
            formattedFilter = selectedDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
        }
        
        List<Object[]> reservations = DatabaseHelper.getOwnerReservations(Session.getCurrentUserId());
        for (Object[] row : reservations) {
            String rowDate = (String) row[1];
            if (!formattedFilter.isEmpty() && !rowDate.equals(formattedFilter)) {
                continue; 
            }
            model.addRow(row);
        }
    }

    private void loadRealReviews() {
        reviewsContainer.removeAll();
        List<String[]> dbReviews = DatabaseHelper.getOwnerReviews(Session.getCurrentUserId());
        for (String[] r : dbReviews) {
            reviewsContainer.add(createSpeechBubble(r[2], r[0], r[3], r[1]));
            reviewsContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        reviewsContainer.revalidate();
        reviewsContainer.repaint();
    }

    private JPanel createSpeechBubble(String stars, String user, String comment, String place) {
        JPanel bubble = new JPanel(new BorderLayout(5, 5));
        bubble.setBackground(Color.WHITE);
        bubble.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JPanel topHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        topHeader.setOpaque(false);
        
        JLabel lblStars = new JLabel(stars.replace("⭐", "★"));
        lblStars.setForeground(new Color(255, 180, 0));
        lblStars.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        
        JLabel lblMeta = new JLabel("👤 " + user + " @" + place);
        lblMeta.setFont(new Font(Font.DIALOG, Font.BOLD, 11));
        lblMeta.setForeground(Color.DARK_GRAY);
        
        topHeader.add(lblStars);
        topHeader.add(lblMeta);
        
        // COMMENT TEXT AREA
        JTextArea txtComment = new JTextArea(comment);
        txtComment.setFont(new Font("Courier New", Font.PLAIN, 12));
        txtComment.setLineWrap(true);
        txtComment.setWrapStyleWord(true);
        txtComment.setEditable(false);
        txtComment.setBorder(null); 
        
        // Assemble the bubble
        bubble.add(topHeader, BorderLayout.NORTH);
        bubble.add(txtComment, BorderLayout.CENTER);
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(bubble, BorderLayout.NORTH);
        
        return wrapper;
    }
}