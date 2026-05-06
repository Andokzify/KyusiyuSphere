package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaceDetailDialog extends JDialog {

    private final Color bgLight = new Color(240, 240, 245); 
    private final Color titleBlue = new Color(15, 60, 180);  
    private final Color retroGrey = new Color(210, 210, 210);
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);
    private final Font titleFont = new Font("Courier New", Font.BOLD, 24);

    private int placeId;

    public PlaceDetailDialog(JFrame parent, Place place) {
        super(parent, true);
        this.placeId = place.id;
        
        setUndecorated(true);
        setSize(800, 550); 
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        mainPanel.setBackground(bgLight);

        // ================= HEADER =================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(titleBlue);
        header.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel lblTitleBar = new JLabel("PLACE EXPLORER DETAILS");
        lblTitleBar.setForeground(Color.WHITE);
        lblTitleBar.setFont(new Font("Courier New", Font.BOLD, 14));
        
        JLabel btnClose = new JLabel(" X ", SwingConstants.CENTER);
        btnClose.setForeground(Color.BLACK);
        btnClose.setBackground(retroGrey);
        btnClose.setOpaque(true);
        btnClose.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        
        header.add(lblTitleBar, BorderLayout.WEST);
        header.add(btnClose, BorderLayout.EAST);
        mainPanel.add(header, BorderLayout.NORTH);

        // ================= MAIN CONTENT SPLIT =================
        JPanel contentSplit = new JPanel(new BorderLayout());
        contentSplit.setBackground(bgLight);

        // --- LEFT AREA ---
        JPanel leftArea = new JPanel(new GridBagLayout());
        leftArea.setBackground(bgLight);
        leftArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // 1. MAIN TITLE
        JLabel lblTitle = new JLabel(place.name, SwingConstants.CENTER); 
        lblTitle.setFont(titleFont);
        lblTitle.setBackground(Color.WHITE);
        lblTitle.setOpaque(true); 
        lblTitle.setPreferredSize(new Dimension(0, 50)); 
        lblTitle.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.weighty = 0.05;
        leftArea.add(lblTitle, gbc);

        // 📍 NEW: THE INFO STRIP (Price & Contact)
        JPanel infoStrip = new JPanel(new GridLayout(1, 2));
        infoStrip.setBackground(borderBlack); 
        infoStrip.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        
        String priceText = (place.price <= 0.0) ? "FREE" : String.format("₱ %.2f", place.price);
        JLabel lblPrice = new JLabel(" COST: " + priceText);
        lblPrice.setFont(new Font("Courier New", Font.BOLD, 14));
        lblPrice.setForeground(Color.WHITE);
        
        String contactText = (place.contact == null || place.contact.isEmpty()) ? "N/A" : place.contact;
        JLabel lblContact = new JLabel("CONTACT: " + contactText + " ", SwingConstants.RIGHT);
        lblContact.setFont(new Font("Courier New", Font.BOLD, 14));
        lblContact.setForeground(Color.WHITE);
        
        infoStrip.add(lblPrice);
        infoStrip.add(lblContact);
        
        gbc.gridy = 1; gbc.weighty = 0.02;
        leftArea.add(infoStrip, gbc);

        // 2. POLAROID IMAGE
        JPanel polaroid = new JPanel(new BorderLayout());
        polaroid.setBackground(Color.WHITE);
        polaroid.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = ImageManager.loadPlaceImage(place.name, 250, 200);
        if (icon != null) imgLabel.setIcon(icon);
        else imgLabel.setText("NO IMAGE AVAILABLE");
        polaroid.add(imgLabel, BorderLayout.CENTER);

        // Heart Icon (Red Color Toggle Logic)
        JButton btnFav = new JButton("❤");
        btnFav.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        btnFav.setForeground(DatabaseHelper.isFavorite(Session.getCurrentUserId(), placeId) ? Color.RED : Color.LIGHT_GRAY);
        btnFav.setBorder(null); btnFav.setContentAreaFilled(false);
        btnFav.addActionListener(e -> {
            if (Session.isLoggedIn()) {
                DatabaseHelper.toggleFavorite(Session.getCurrentUserId(), placeId);
                btnFav.setForeground(DatabaseHelper.isFavorite(Session.getCurrentUserId(), placeId) ? Color.RED : Color.LIGHT_GRAY);
            }
        });
        JPanel heartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        heartPanel.setOpaque(false); heartPanel.add(btnFav);
        polaroid.add(heartPanel, BorderLayout.NORTH);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0.6; gbc.weighty = 0.5;
        leftArea.add(polaroid, gbc);

        // 3. STACKED CONTROLS
        JPanel controls = new JPanel(new GridLayout(3, 1, 0, 15));
        controls.setOpaque(false);
        
        JButton btnMap = createBlockButton("CHECK MAP");
        btnMap.addActionListener(e -> userframexperi.openDirectionsInBrowser(place.name));
        
        JButton btnBook = createBlockButton("BOOK SITE");
        btnBook.addActionListener(e -> {
            if (!Session.isLoggedIn()) JOptionPane.showMessageDialog(this, "Login required.");
            else new BookingDialog(this, placeId, place.name).setVisible(true);
        });
        
        JButton btnReview = createBlockButton("REVIEW");
        btnReview.addActionListener(e -> {
            if (!Session.isLoggedIn()) JOptionPane.showMessageDialog(this, "Login required.");
            else new ReviewDialog(this, placeId).setVisible(true);
        });

        controls.add(btnMap);
        controls.add(btnBook);
        controls.add(btnReview);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.4;
        leftArea.add(controls, gbc);

        // 4. DESCRIPTION
        JTextArea txtDesc = new JTextArea(place.description != null && !place.description.isEmpty() ? place.description : "No description provided.");
        txtDesc.setFont(new Font("Courier New", Font.PLAIN, 14));
        txtDesc.setLineWrap(true); txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false); txtDesc.setBackground(bgLight);
        
        JScrollPane descScroll = new JScrollPane(txtDesc);
        descScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borderBlack, 2), " DESCRIPTION "));
        descScroll.setOpaque(false); descScroll.getViewport().setOpaque(false);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weighty = 0.3;
        leftArea.add(descScroll, gbc);

        contentSplit.add(leftArea, BorderLayout.CENTER);

        // --- RIGHT SIDE (Ratings Sidebar) ---
        JPanel rightSidebar = new JPanel(new BorderLayout());
        rightSidebar.setPreferredSize(new Dimension(260, 0));
        rightSidebar.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, borderBlack));

        JLabel lblRatingsHead = new JLabel(" RATINGS:", SwingConstants.CENTER);
        lblRatingsHead.setForeground(Color.WHITE);
        lblRatingsHead.setBackground(titleBlue);
        lblRatingsHead.setOpaque(true);
        lblRatingsHead.setFont(new Font("Courier New", Font.BOLD, 18));
        lblRatingsHead.setPreferredSize(new Dimension(0, 40));
        rightSidebar.add(lblRatingsHead, BorderLayout.NORTH);

        JPanel reviewsList = new JPanel();
        reviewsList.setLayout(new BoxLayout(reviewsList, BoxLayout.Y_AXIS));
        reviewsList.setBackground(retroGrey);
        reviewsList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        java.util.List<String[]> reviews = DatabaseHelper.getReviewsForPlace(placeId);
        if (reviews.isEmpty()) {
            JLabel noRev = new JLabel("No reviews yet.");
            noRev.setFont(mainFont); reviewsList.add(noRev);
        } else {
            for (String[] rev : reviews) {
                reviewsList.add(createRetroSpeechBubble(rev[0], rev[1], rev[2]));
                reviewsList.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        JScrollPane scroll = new JScrollPane(reviewsList);
        scroll.setBorder(null);
        rightSidebar.add(scroll, BorderLayout.CENTER);

        contentSplit.add(rightSidebar, BorderLayout.EAST);
        mainPanel.add(contentSplit, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton createBlockButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Courier New", Font.BOLD, 16));
        btn.setBackground(retroGrey);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 4, 4, Color.GRAY),
            BorderFactory.createLineBorder(Color.BLACK, 2)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { btn.setBackground(retroGrey); }
        });
        return btn;
    }

    private JPanel createRetroSpeechBubble(String stars, String user, String comment) {
        JPanel bubble = new JPanel(new BorderLayout(5, 5));
        bubble.setBackground(Color.WHITE);
        bubble.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 4, 4, Color.GRAY),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderBlack, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            )
        ));
        
        String safeStars = stars.replace("⭐", "★"); 
        JLabel lblStars = new JLabel(safeStars);
        lblStars.setForeground(new Color(255, 180, 0));
        lblStars.setFont(new Font(Font.DIALOG, Font.BOLD, 18)); 
        
        JTextArea txtComment = new JTextArea(comment);
        txtComment.setFont(new Font("Courier New", Font.PLAIN, 12));
        txtComment.setLineWrap(true); 
        txtComment.setWrapStyleWord(true);
        txtComment.setEditable(false); 
        txtComment.setBorder(null);
        
        JLabel lblUser = new JLabel("👤 " + user);
        lblUser.setFont(new Font(Font.DIALOG, Font.BOLD, 12)); 
        lblUser.setForeground(Color.BLACK);
        
        JPanel userWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userWrap.setOpaque(false); 
        userWrap.add(lblUser);
        
        // Assemble the bubble
        bubble.add(lblStars, BorderLayout.NORTH);
        bubble.add(txtComment, BorderLayout.CENTER);
        bubble.add(userWrap, BorderLayout.SOUTH);
        
        return bubble;
    }
}