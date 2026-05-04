package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class userframexperi extends JFrame {

    private JPanel placesContainer;

    // Retro UI Colors & Fonts
    private final Color bgLight = new Color(210, 180, 140); // Map background vibe
    private final Color polaroidWhite = Color.WHITE;
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);
    private final Font markerFont = new Font("Comic Sans MS", Font.BOLD, 15);
    
    // Chunky Retro Borders
    private final javax.swing.border.Border hoverShadow = BorderFactory.createCompoundBorder(
    BorderFactory.createMatteBorder(0, 0, 8, 8, new Color(100, 100, 100)), // Deeper, darker shadow
    BorderFactory.createLineBorder(borderBlack, 1)
);
    private final javax.swing.border.Border retroBorder = BorderFactory.createLineBorder(borderBlack, 2);
    private final javax.swing.border.Border shadowBorder = BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 5, 5, new Color(150, 150, 150)), 
        BorderFactory.createLineBorder(borderBlack, 1)
    );

    public userframexperi() {
        setTitle("KyusiyuSphere - Student Explorer");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        loadPlaces();
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());
        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(bgLight);

        // ================= TOP NAVBAR =================
        JPanel topBar = new JPanel(new BorderLayout(10, 10));
        topBar.setBackground(bgLight);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, borderBlack),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Left Icons (Categories)
        JPanel leftNav = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftNav.setOpaque(false);
        
        leftNav.add(createNavBtn("🏢", "All Places"));
        leftNav.add(createNavBtn("⚽", "Sports"));
        leftNav.add(createNavBtn("🖱️", "Esports"));
        leftNav.add(createNavBtn("☕", "Cafes & Chill"));
        leftNav.add(createNavBtn("❤️ Favorites", "Favorites"));
        topBar.add(leftNav, BorderLayout.WEST);

        // Right Icons (Search & Settings)
        JPanel rightNav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightNav.setOpaque(false);

        JTextField txtSearch = new JTextField(15);
        txtSearch.setFont(mainFont);
        txtSearch.setBorder(retroBorder);
        txtSearch.setPreferredSize(new Dimension(200, 35));
        
        JButton btnSearch = new JButton("🔍");
        btnSearch.setFont(mainFont);
        btnSearch.setBackground(bgLight);
        btnSearch.setBorder(retroBorder);
        btnSearch.setFocusPainted(false);
        btnSearch.addActionListener(e -> filterPlaces(txtSearch.getText().trim()));
        txtSearch.addActionListener(e -> filterPlaces(txtSearch.getText().trim()));

        JButton btnProfile = new JButton("👤");
        btnProfile.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btnProfile.setBackground(bgLight);
        btnProfile.setBorder(retroBorder);
        btnProfile.setToolTipText("My Reservations");
        btnProfile.addActionListener(e -> new MyReservationsDialog(this).setVisible(true));

        JButton btnLogout = new JButton("⚙️");
        btnLogout.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btnLogout.setBackground(bgLight);
        btnLogout.setBorder(retroBorder);
        btnLogout.setToolTipText("Logout");
        btnLogout.addActionListener(e -> logout());

        rightNav.add(txtSearch);
        rightNav.add(btnSearch);
        rightNav.add(btnProfile);
        rightNav.add(btnLogout);
        topBar.add(rightNav, BorderLayout.EAST);

        background.add(topBar, BorderLayout.NORTH);

        // ================= PLACES GRID =================
        placesContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 30));
        placesContainer.setBackground(bgLight);

        JScrollPane scrollPane = new JScrollPane(placesContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        background.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(background);
    }

    private JButton createNavBtn(String text, String category) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btn.setBackground(bgLight);
        btn.setBorder(retroBorder);
        btn.setFocusPainted(false);
        btn.setToolTipText(category);
        btn.setPreferredSize(new Dimension(text.length() > 5 ? 120 : 45, 35));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { btn.setBackground(bgLight); }
        });
        btn.addActionListener(e -> filterByCategory(category));
        return btn;
    }

    private void loadPlaces() {
        placesContainer.removeAll();
        List<Place> places = DatabaseHelper.getAllPlaces();

        if (places.isEmpty()) {
            JLabel empty = new JLabel("No places available. Waiting for owners to add cites!");
            empty.setFont(mainFont);
            placesContainer.add(empty);
        } else {
            for (Place p : places) {
                addPlaceCard(p.id, p.name, p.description != null ? p.description : "", p.label != null ? p.label : "General");
            }
        }
        placesContainer.revalidate();
        placesContainer.repaint();
    }

    private void filterPlaces(String query) {
        if (query.isEmpty()) { loadPlaces(); return; }
        placesContainer.removeAll();
        List<Place> allPlaces = DatabaseHelper.getAllPlaces();
        String q = query.toLowerCase();
        boolean found = false;

        for (Place p : allPlaces) {
            if (p.name.toLowerCase().contains(q) || (p.description != null && p.description.toLowerCase().contains(q))) {
                addPlaceCard(p.id, p.name, p.description != null ? p.description : "", p.label != null ? p.label : "General");
                found = true;
            }
        }
        if (!found) placesContainer.add(new JLabel("No results found."));
        placesContainer.revalidate();
        placesContainer.repaint();
    }

    private void filterByCategory(String category) {
        if (category.equals("All Places")) { loadPlaces(); return; }
        
        // NEW: Real Favorites Logic!
        if (category.equals("Favorites")) {
            if (!Session.isLoggedIn()) {
                JOptionPane.showMessageDialog(this, "Please login to view your favorites!");
                return;
            }
            placesContainer.removeAll();
            List<Place> allPlaces = DatabaseHelper.getAllPlaces();
            int userId = Session.getCurrentUserId();
            boolean found = false;
            
            for (Place p : allPlaces) {
                // Only show places that the database says are favorited by this user
                if (DatabaseHelper.isFavorite(userId, p.id)) {
                    addPlaceCard(p.id, p.name, p.description != null ? p.description : "", p.label != null ? p.label : "General");
                    found = true;
                }
            }
            if (!found) {
                JLabel empty = new JLabel("  You haven't saved any favorites yet. Click the heart on a place to save it!");
                empty.setFont(mainFont);
                placesContainer.add(empty);
            }
            placesContainer.revalidate();
            placesContainer.repaint();
            return;
        }

        placesContainer.removeAll();
        List<Place> allPlaces = DatabaseHelper.getAllPlaces();
        for (Place p : allPlaces) {
            String placeLabel = p.label != null ? p.label : "General";
            if (placeLabel.equalsIgnoreCase(category)) {
                addPlaceCard(p.id, p.name, p.description != null ? p.description : "", placeLabel);
            }
        }
        placesContainer.revalidate();
        placesContainer.repaint();
    }

    // Creates the Polaroid-style cards
    private void addPlaceCard(int placeId, String title, String description, String category) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setPreferredSize(new Dimension(220, 260));
        card.setBackground(polaroidWhite);
        card.setBorder(BorderFactory.createCompoundBorder(
            shadowBorder, 
            BorderFactory.createEmptyBorder(10, 10, 35, 10) // 35px on the bottom!
        ));

        // Image Area
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(retroBorder);
        imageLabel.setPreferredSize(new Dimension(200, 150));
        
        ImageIcon icon = ImageManager.loadPlaceImage(title, 200, 150);
        if (icon != null) imageLabel.setIcon(icon);
        else {
            imageLabel.setText("📷 NO IMAGE");
            imageLabel.setFont(mainFont);
        }
        card.add(imageLabel, BorderLayout.CENTER);

        // Bottom Info Area
        // Bottom Info Area
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(polaroidWhite);
        
        // NEW: Check DB for current favorite status
        boolean isFav = false;
        if (Session.isLoggedIn()) {
            isFav = DatabaseHelper.isFavorite(Session.getCurrentUserId(), placeId);
        }
        
        // NEW: Interactive Heart Button
        JButton btnHeart = new JButton(isFav ? "💖 " : "🤍 ");
        btnHeart.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        btnHeart.setBorder(BorderFactory.createEmptyBorder());
        btnHeart.setContentAreaFilled(false);
        btnHeart.setFocusPainted(false);
        btnHeart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnHeart.addActionListener(e -> {
            if (!Session.isLoggedIn()) {
                JOptionPane.showMessageDialog(userframexperi.this, "Please login to save favorites!");
                return;
            }
            // Toggle in database, then update the button visually
            DatabaseHelper.toggleFavorite(Session.getCurrentUserId(), placeId);
            boolean nowFav = DatabaseHelper.isFavorite(Session.getCurrentUserId(), placeId);
            btnHeart.setText(nowFav ? "💖 " : "🤍 ");
        });
        
        // Truncate long titles to fit the polaroid
        String displayTitle = title;
        if(title.length() > 15) displayTitle = title.substring(0, 13) + "..";
        
        JLabel titleLabel = new JLabel(displayTitle);
        titleLabel.setFont(markerFont); // Apply it only to the Polaroid text

        infoPanel.add(btnHeart, BorderLayout.WEST); // Replaced JLabel with JButton
        infoPanel.add(titleLabel, BorderLayout.CENTER);

        // Hover & Click Actions
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
    @Override public void mouseEntered(MouseEvent e) {
        // Swap to the deep shadow
        card.setBorder(BorderFactory.createCompoundBorder(hoverShadow, BorderFactory.createEmptyBorder(10, 10, 35, 10)));
        card.setLocation(card.getX() - 2, card.getY() - 2); // Physically shifts the card up slightly
    }
    @Override public void mouseExited(MouseEvent e) {
        // Swap back to the flat shadow
        card.setBorder(BorderFactory.createCompoundBorder(shadowBorder, BorderFactory.createEmptyBorder(10, 10, 35, 10)));
        card.setLocation(card.getX() + 2, card.getY() + 2); // Drops it back down
    }
    @Override public void mouseClicked(MouseEvent e) {
        new PlaceDetailDialog(userframexperi.this, title, description, placeId).setVisible(true);
    }
});

        placesContainer.add(card);
    }

    private void logout() {
        if (JOptionPane.showConfirmDialog(this, "Log out?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Session.logout();
            dispose();
            new startingScreen().setVisible(true);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new userframexperi().setVisible(true));
    }
}