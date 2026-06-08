package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UserFrame extends JFrame {

    private JPanel placesContainer;
    private UserAccount currentUser;

    // Retro UI Colors & Fonts
    private final Color bgLight = new Color(210, 180, 140); 
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);
    private final Font markerFont = new Font("Comic Sans MS", Font.BOLD, 15);
    private final Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 18);
    
    // Chunky Retro Borders
    private final javax.swing.border.Border hoverShadow = BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 8, 8, new Color(100, 100, 100)), 
        BorderFactory.createLineBorder(borderBlack, 1)
    );
    private final javax.swing.border.Border retroBorder = BorderFactory.createLineBorder(borderBlack, 2);
    private final javax.swing.border.Border shadowBorder = BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 5, 5, new Color(150, 150, 150)), 
        BorderFactory.createLineBorder(borderBlack, 1)
    );

    public UserFrame(UserAccount user) {
        this.currentUser = user;
        setTitle("KyusiyuSphere - Student Explorer");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        loadPlaces();
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());
        JLabel background = new JLabel();
        
        // Resource Loader
        java.net.URL imgURL = getClass().getResource("/Images/REGISTRATION COMPLETE.gif");
        if (imgURL != null) {
            background.setIcon(new ImageIcon(imgURL));
        }
        background.setLayout(new BorderLayout());

        // ================= TOP NAVBAR (FIXED EXPANDABLE LAYOUT) =================
        JPanel topBar = new JPanel(new GridBagLayout());
        topBar.setBackground(bgLight);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, borderBlack),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;

        // 1. LEFT ZONE: Category Filters
        JPanel leftNav = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftNav.setOpaque(false);
        leftNav.add(createNavBtn("🌎", "All", new Color(173, 216, 230)));
        leftNav.add(createNavBtn("🏀", "Courts", new Color(255, 200, 150)));
        leftNav.add(createNavBtn("💻", "Comps", new Color(150, 255, 150)));
        leftNav.add(createNavBtn("🎭", "Hubs", new Color(230, 190, 255)));
        leftNav.add(createNavBtn("☕", "Cafes", new Color(210, 180, 140)));
        leftNav.add(createNavBtn("❤", "Favs", new Color(255, 0, 0)));

        gbc.gridx = 0; 
        gbc.weightx = 0; 
        topBar.add(leftNav, gbc);

        // 2. CENTER ZONE: Merged Search Bar (THE EXPANDER)
        JPanel searchWrapper = new JPanel(new BorderLayout());
        searchWrapper.setBackground(Color.WHITE);
        searchWrapper.setBorder(retroBorder);

        JTextField txtSearch = new JTextField(" Search places...");
        txtSearch.setFont(mainFont);
        txtSearch.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
        txtSearch.setOpaque(false);

        // Handles the disappearing placeholder text
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtSearch.getText().equals(" Search places...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK); 
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText(" Search places...");
                    txtSearch.setForeground(Color.GRAY);
                }
            }
        });

        JButton btnSearchAction = new JButton("🔍");
        btnSearchAction.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btnSearchAction.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
        btnSearchAction.setContentAreaFilled(false);
        btnSearchAction.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearchAction.addActionListener(e -> filterPlaces(txtSearch.getText().trim()));

        searchWrapper.add(txtSearch, BorderLayout.CENTER);
        searchWrapper.add(btnSearchAction, BorderLayout.EAST);

        gbc.gridx = 1; 
        gbc.weightx = 1.0; 
        gbc.insets = new Insets(0, 20, 0, 20); 
        topBar.add(searchWrapper, gbc);

        // 3. RIGHT ZONE: User Actions
        JPanel rightNav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightNav.setOpaque(false);

        JButton btnInfo = createNavBtn("👤", "", bgLight);
        btnInfo.addActionListener(e -> new ProfileSettingsDialog(this, currentUser).setVisible(true));

        JButton btnReservations = createNavBtn("ℹ", "", bgLight);
        btnReservations.addActionListener(e -> new MyReservationsDialog(this).setVisible(true));

        JButton btnSettings = createNavBtn("⚙", "", bgLight);
        btnSettings.addActionListener(e -> new SettingsDialog(this).setVisible(true));

        rightNav.add(btnInfo);
        rightNav.add(btnReservations);
        rightNav.add(btnSettings);

        gbc.gridx = 2; 
        gbc.weightx = 0; 
        gbc.insets = new Insets(0, 0, 0, 0);
        topBar.add(rightNav, gbc);

        background.add(topBar, BorderLayout.NORTH);

        // ================= PLACES GRID =================
        placesContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 30));
        placesContainer.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(placesContainer);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        background.add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(background);
    }

    private JButton createNavBtn(String icon, String text, Color bgColor) {
        JButton btn = new JButton(icon + (text.isEmpty() ? "" : " " + text)); 
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        btn.setBackground(bgColor);
        btn.setOpaque(true);
        btn.setBorder(retroBorder);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        int width = (text.length() * 10) + 60; 
        btn.setPreferredSize(new Dimension(width, 40));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { btn.setBackground(bgColor); }
        });
        
        if (!text.isEmpty()) {
            btn.addActionListener(e -> filterByCategory(text));
        }
        
        return btn;
    }

    private void loadPlaces() {
        placesContainer.removeAll();
        List<Place> places = DatabaseHelper.getAllPlaces();
        for (Place p : places) {
            addPlaceCard(p);
        }
        placesContainer.revalidate();
        placesContainer.repaint();
    }

    private void filterByCategory(String category) {
        if (category.equals("All") || category.equals("All Places")) { 
            loadPlaces(); 
            return; 
        }
        
        placesContainer.removeAll();
        List<Place> allPlaces = DatabaseHelper.getAllPlaces();
        
        for (Place p : allPlaces) {
            boolean match = false;
            
            if (category.equals("Favs") || category.equals("Favorites")) {
                match = Session.isLoggedIn() && DatabaseHelper.isFavorite(Session.getCurrentUserId(), p.id);
            } else if (p.label != null && p.label.equalsIgnoreCase(category)) {
                match = true;
            }
            
            if (match) {
                addPlaceCard(p);
            }
        }
        placesContainer.revalidate();
        placesContainer.repaint();
    }

    private void filterPlaces(String query) {
        if (query.isEmpty()) { loadPlaces(); return; }
        placesContainer.removeAll();
        List<Place> allPlaces = DatabaseHelper.getAllPlaces();
        for (Place p : allPlaces) {
            if (p.name.toLowerCase().contains(query.toLowerCase())) {
                addPlaceCard(p);
            }
        }
        placesContainer.revalidate();
        placesContainer.repaint();
    }

    private void addPlaceCard(Place place) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(230, 280));
        card.setOpaque(false);
        
        JPanel polaroidFrame = new JPanel(new BorderLayout(0, 10));
        polaroidFrame.setBackground(Color.WHITE);
        polaroidFrame.setBorder(BorderFactory.createCompoundBorder(shadowBorder, BorderFactory.createEmptyBorder(12, 12, 15, 12)));

        JLabel imageLabel = new JLabel();
        imageLabel.setBackground(Color.BLACK);
        imageLabel.setOpaque(true);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        imageLabel.setPreferredSize(new Dimension(200, 160));
        
        ImageIcon icon = ImageManager.loadPlaceImage(place.name, 200, 160);
        if (icon != null) imageLabel.setIcon(icon);
        else { imageLabel.setText("📷 NO IMAGE"); imageLabel.setForeground(Color.WHITE); }

        JPanel infoPanel = new JPanel(new BorderLayout(5, 0));
        infoPanel.setOpaque(false);
        
        // Favorite Logic with Red Color Fix
        boolean isFav = Session.isLoggedIn() && DatabaseHelper.isFavorite(Session.getCurrentUserId(), place.id);
        JButton btnHeart = new JButton(isFav ? "❤" : "🤍"); 
        btnHeart.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        btnHeart.setForeground(isFav ? Color.RED : Color.GRAY); 
        btnHeart.setBorder(null);
        btnHeart.setContentAreaFilled(false);
        btnHeart.addActionListener(e -> {
            if (!Session.isLoggedIn()) return;
            DatabaseHelper.toggleFavorite(Session.getCurrentUserId(), place.id);
            boolean nowFav = DatabaseHelper.isFavorite(Session.getCurrentUserId(), place.id);
            btnHeart.setText(nowFav ? "❤" : "🤍");
            btnHeart.setForeground(nowFav ? Color.RED : Color.GRAY);
        });

        JLabel titleLabel = new JLabel(place.name.length() > 18 ? place.name.substring(0, 15) + ".." : place.name, SwingConstants.CENTER);
        titleLabel.setFont(markerFont);

        JButton btnMap = new JButton("🗺️");
        btnMap.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        btnMap.setBorder(null); btnMap.setContentAreaFilled(false);
        btnMap.addActionListener(e -> openDirectionsInBrowser(place.name));

        infoPanel.add(btnHeart, BorderLayout.WEST);
        infoPanel.add(titleLabel, BorderLayout.CENTER);
        infoPanel.add(btnMap, BorderLayout.EAST);

        polaroidFrame.add(imageLabel, BorderLayout.CENTER);
        polaroidFrame.add(infoPanel, BorderLayout.SOUTH);
        card.add(polaroidFrame, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { polaroidFrame.setBorder(BorderFactory.createCompoundBorder(hoverShadow, BorderFactory.createEmptyBorder(12, 12, 15, 12))); }
            public void mouseExited(MouseEvent e) { polaroidFrame.setBorder(BorderFactory.createCompoundBorder(shadowBorder, BorderFactory.createEmptyBorder(12, 12, 15, 12))); }
            
            public void mouseClicked(MouseEvent e) { new PlaceDetailDialog(UserFrame.this, place).setVisible(true); }
        });

        placesContainer.add(card);
    }

    // MAPS ENGINE
    public static void openDirectionsInBrowser(String destination) {
        try {
            // 1. Set a safe default starting point
            String origin = "Quezon City University"; 
            
            // 2. Dynamically check the logged-in user's campus
            if (Session.isLoggedIn() && Session.getCurrentUser() != null) {
                String campus = Session.getCurrentUser().campus;
                
                if (campus != null) {
                    if (campus.toLowerCase().contains("bartolome")) {
                        origin = "Quezon City University - San Bartolome Branch";
                    } else if (campus.toLowerCase().contains("francisco")) {
                        origin = "Quezon City University - San Francisco Branch";
                    } else if (campus.toLowerCase().contains("batasan")) {
                        origin = "Quezon City University - Batasan Branch";
                    }
                }
            }

            // 3. Build the official Google Maps Directions API URL
            String baseUrl = "https://www.google.com/maps/dir/?api=1";
            String encodedOrigin = java.net.URLEncoder.encode(origin + ", Quezon City, Metro Manila", "UTF-8");
            String encodedDest = java.net.URLEncoder.encode(destination + ", Quezon City, Metro Manila", "UTF-8");
            
            // Combine them into the final link
            String finalUrl = baseUrl + "&origin=" + encodedOrigin + "&destination=" + encodedDest;
            
            // 4. Launch the browser
            java.awt.Desktop.getDesktop().browse(new java.net.URI(finalUrl));
            
        } catch (Exception ex) { 
            System.err.println("Maps error: " + ex.getMessage()); 
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            List<UserAccount> users = DatabaseHelper.getAllUsers();
            if (!users.isEmpty()) new UserFrame(users.get(0)).setVisible(true);
            else new StartingScreen().setVisible(true);
        });
    }
}