package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class userframexperi extends javax.swing.JFrame {

    private JPanel placesContainer;

    // Theme Colors (consistent with OwnerFrame)
    private final Color bgDark = new Color(15, 15, 30);
    private final Color panelDark = new Color(25, 25, 45);
    private final Color accentBlue = new Color(0, 150, 255);
    private final Color textMuted = new Color(180, 180, 190);

    public userframexperi() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setTitle("KyusiyuSphere - Student Dashboard");
        
        loadSamplePlaces();
    }

    private void loadSamplePlaces() {
        placesContainer.removeAll();

        // Add sample cards (will automatically flow into 3 columns)
        addPlaceCard("Cinema 76 Cafe", "Cafe with cozy seats near campus", "Cafes & Chill");
        addPlaceCard("Zus Coffee", "Great for group study and meetings", "Cafes & Chill");
        addPlaceCard("PC Corner", "Esports & Gaming PCs available", "Esports");
        addPlaceCard("Covered Court", "Basketball & Sports Training", "Sports");
        addPlaceCard("Habit Coffee", "Good for dance practice & chill", "Dance");
        addPlaceCard("Study Lounge", "Quiet area near main building", "General");
        addPlaceCard("Open Park", "Outdoor activities & sports", "Sports");
        addPlaceCard("Dance Studio A", "Dedicated dance practice space", "Dance");
        addPlaceCard("Computer Shop B", "Budget gaming PCs", "Esports");

        placesContainer.revalidate();
        placesContainer.repaint();
    }

    private void addPlaceCard(String title, String description, String category) {
        JPanel card = new JPanel(new BorderLayout(12, 12));
        card.setPreferredSize(new Dimension(300, 260));
        card.setBackground(panelDark);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 70), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Image / Icon
        JLabel imageLabel = new JLabel("📍", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        imageLabel.setForeground(accentBlue);
        card.add(imageLabel, BorderLayout.NORTH);

        // Title
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        card.add(titleLabel, BorderLayout.CENTER);

        // Category and Description
        JPanel infoPanel = new JPanel(new BorderLayout(0, 8));
        infoPanel.setOpaque(false);

        JLabel categoryLabel = new JLabel(category, SwingConstants.CENTER);
        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        categoryLabel.setForeground(textMuted);

        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(210, 210, 220));

        infoPanel.add(categoryLabel, BorderLayout.NORTH);
        infoPanel.add(descLabel, BorderLayout.CENTER);

        card.add(infoPanel, BorderLayout.SOUTH);

        // Hover Effect
        card.addMouseListener(new MouseAdapter() {
            Color original = card.getBackground();

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(35, 35, 60));
                card.setBorder(BorderFactory.createLineBorder(accentBlue, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(original);
                card.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 70), 2));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new PlaceDetailDialog(userframexperi.this, title, description).setVisible(true);
            }
        });

        placesContainer.add(card);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(bgDark);

        // ==================== TOP BAR ====================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(panelDark);
        topBar.setPreferredSize(new Dimension(0, 70));

        JPanel leftTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        leftTop.setOpaque(false);

        JTextField searchField = new JTextField("Search places or activities...");
        searchField.setPreferredSize(new Dimension(500, 38));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JButton searchBtn = new JButton("🔍");
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));

        leftTop.add(searchField);
        leftTop.add(searchBtn);

        JPanel rightTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        rightTop.setOpaque(false);

        JButton favoritesBtn = new JButton("❤️ Favorites");
        JButton settingsBtn = new JButton("⚙️ Settings");

        rightTop.add(favoritesBtn);
        rightTop.add(settingsBtn);

        topBar.add(leftTop, BorderLayout.WEST);
        topBar.add(rightTop, BorderLayout.EAST);

        background.add(topBar, BorderLayout.NORTH);

        // ==================== SIDEBAR ====================
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(20, 20, 35));
        sidebar.setPreferredSize(new Dimension(220, 0));

        JLabel logo = new JLabel("KyusiyuSphere", SwingConstants.CENTER);
        logo.setFont(new Font("Courier New", Font.BOLD, 22));
        logo.setForeground(accentBlue);
        logo.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(logo);

        String[] menuItems = {"☕ Cafes & Chill", "🖱️ Esports", "⚽ Sports", "🕺 Dance", "📍 General Spaces", "❤️ Favorites"};

        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(200, 50));
            btn.setBackground(new Color(30, 30, 50));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Courier New", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);

            sidebar.add(Box.createVerticalStrut(8));
            sidebar.add(btn);
        }

        background.add(sidebar, BorderLayout.WEST);

        // ==================== 3-COLUMN PLACES AREA ====================
        placesContainer = new JPanel(new GridLayout(0, 3, 25, 25));   // 3 columns, auto rows
        placesContainer.setBackground(bgDark);
        placesContainer.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JScrollPane scrollPane = new JScrollPane(placesContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        background.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(background);
        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new userframexperi().setVisible(true));
    }
}