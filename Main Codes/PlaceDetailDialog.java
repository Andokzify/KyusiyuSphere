package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.text.*;

public class PlaceDetailDialog extends JDialog {

    private final int placeId;
    private final String placeName;

    private final Color bgLight = new Color(230, 230, 230); 
    private final Color retroBlue = new Color(0, 0, 170); 
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);
    private final Font smallFont = new Font("Courier New", Font.PLAIN, 12);
    
    private final javax.swing.border.Border retroBorder = BorderFactory.createLineBorder(borderBlack, 2);

    public PlaceDetailDialog(Window parent, String placeName, String description, int placeId) {
        super(parent, "Place Details", Dialog.ModalityType.APPLICATION_MODAL);
        this.placeId = placeId;
        this.placeName = placeName;
        
        setUndecorated(true); 
        setSize(750, 450);
        setLocationRelativeTo(parent);

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 2),
            BorderFactory.createMatteBorder(2, 2, 2, 2, Color.WHITE) 
        ));
        rootPanel.setBackground(bgLight);

        // --- RETRO TITLE BAR ---
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(retroBlue);
        titleBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        
        JLabel windowTitle = new JLabel(placeName + " - Place Details");
        windowTitle.setForeground(Color.WHITE);
        windowTitle.setFont(new Font("Courier New", Font.BOLD, 14));
        
        JButton btnClose = new JButton("X");
        btnClose.setFont(new Font("Courier New", Font.BOLD, 12));
        btnClose.setBackground(bgLight);
        btnClose.setBorder(BorderFactory.createRaisedBevelBorder());
        btnClose.setFocusPainted(false);
        btnClose.setMargin(new Insets(0, 4, 0, 4));
        btnClose.addActionListener(e -> dispose());

        titleBar.add(windowTitle, BorderLayout.WEST);
        titleBar.add(btnClose, BorderLayout.EAST);
        rootPanel.add(titleBar, BorderLayout.NORTH);

        // --- MAIN CONTENT ---
        JPanel mainContent = new JPanel(new BorderLayout(15, 10));
        mainContent.setBackground(bgLight);
        mainContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(400, 0));

        JPanel topInfo = new JPanel(new BorderLayout());
        topInfo.setOpaque(false);
        JLabel lblTitleBox = new JLabel(" " + placeName + " ");
        lblTitleBox.setFont(new Font("Courier New", Font.BOLD, 22));
        lblTitleBox.setBorder(retroBorder);
        lblTitleBox.setBackground(Color.WHITE);
        lblTitleBox.setOpaque(true);
        
        JLabel lblLoc = new JLabel("<html><center>📍<br>Check Map</center></html>", SwingConstants.CENTER);
        lblLoc.setFont(smallFont);
        
        topInfo.add(lblTitleBox, BorderLayout.WEST);
        topInfo.add(lblLoc, BorderLayout.EAST);
        leftPanel.add(topInfo, BorderLayout.NORTH);

        JPanel midInfo = new JPanel(new BorderLayout(10, 10));
        midInfo.setOpaque(false);
        
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 4, 4, Color.GRAY), 
            retroBorder
        ));
        imgLabel.setPreferredSize(new Dimension(200, 150));
        imgLabel.setBackground(Color.WHITE);
        imgLabel.setOpaque(true);
        
        ImageIcon icon = ImageManager.loadPlaceImage(placeName, 200, 150);
        if (icon != null) imgLabel.setIcon(icon);
        else imgLabel.setText("NO IMAGE");

        JTextArea descArea = new JTextArea(description);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(bgLight);
        descArea.setFont(smallFont);
        
        midInfo.add(imgLabel, BorderLayout.WEST);
        midInfo.add(descArea, BorderLayout.CENTER);
        leftPanel.add(midInfo, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);
        
        JButton btnBook = createRetroButton("BOOK");
        JButton btnReview = createRetroButton("REVIEW");
        
        actionPanel.add(btnReview);
        actionPanel.add(btnBook);
        leftPanel.add(actionPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(retroBorder);
        
        JLabel revTitle = new JLabel(" RATINGS:", SwingConstants.LEFT);
        revTitle.setFont(mainFont);
        revTitle.setBackground(retroBlue);
        revTitle.setForeground(Color.WHITE);
        revTitle.setOpaque(true);
        rightPanel.add(revTitle, BorderLayout.NORTH);

        JPanel reviewsList = new JPanel();
        reviewsList.setLayout(new BoxLayout(reviewsList, BoxLayout.Y_AXIS));
        reviewsList.setBackground(Color.WHITE);

        java.util.List<String[]> dbReviews = DatabaseHelper.getReviewsForPlace(placeId);
        if (dbReviews.isEmpty()) {
            JLabel empty = new JLabel("  No reviews yet.");
            empty.setFont(smallFont);
            reviewsList.add(empty);
        } else {
            for (String[] r : dbReviews) {
                JPanel revBox = new JPanel(new BorderLayout());
                revBox.setBackground(Color.WHITE);
                revBox.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                
                JLabel starLabel = new JLabel("⭐ " + r[0].charAt(0) + "/5 - " + r[1]);
                starLabel.setFont(new Font("Courier New", Font.BOLD, 12));
                
                JTextArea comment = new JTextArea(r[2]);
                comment.setLineWrap(true);
                comment.setWrapStyleWord(true);
                comment.setEditable(false);
                comment.setFont(smallFont);
                
                revBox.add(starLabel, BorderLayout.NORTH);
                revBox.add(comment, BorderLayout.CENTER);
                reviewsList.add(revBox);
            }
        }
        
        JScrollPane scroll = new JScrollPane(reviewsList);
        scroll.setBorder(null);
        rightPanel.add(scroll, BorderLayout.CENTER);

        mainContent.add(leftPanel, BorderLayout.CENTER);
        mainContent.add(rightPanel, BorderLayout.EAST);
        rootPanel.add(mainContent, BorderLayout.CENTER);

        add(rootPanel);

        // Wrapped in try-catch to reveal silent crashes
        btnBook.addActionListener(e -> {
            try {
                openRetroBookingWindow();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        btnReview.addActionListener(e -> openRetroReviewWindow());
    }

    private JButton createRetroButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(mainFont);
        btn.setBackground(bgLight);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 3, Color.GRAY), 
            retroBorder
        ));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 35));
        return btn;
    }

    private void openRetroBookingWindow() {
        if (!Session.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Please login first!");
            return;
        }

        JDialog bookDialog = new JDialog(this, "RESERVATION DETAILS", Dialog.ModalityType.APPLICATION_MODAL);
        bookDialog.setSize(500, 300);
        bookDialog.setLocationRelativeTo(this);
        bookDialog.setLayout(new BorderLayout(10, 10));
        bookDialog.getContentPane().setBackground(bgLight);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(bgLight);

        formPanel.add(new JLabel("CITE NAME:"));
        JLabel lblPlace = new JLabel(placeName);
        lblPlace.setFont(mainFont);
        formPanel.add(lblPlace);

        formPanel.add(new JLabel("DATE (YYYY-MM-DD):"));
        
        JPanel datePanel = new JPanel(new GridLayout(1, 3, 5, 0));
        datePanel.setOpaque(false);
        
        String[] years = {"2026", "2027", "2028"};
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] days = new String[31];
        for(int i=1; i<=31; i++) days[i-1] = String.format("%02d", i);

        JComboBox<String> cbYear = new JComboBox<>(years);
        JComboBox<String> cbMonth = new JComboBox<>(months);
        JComboBox<String> cbDay = new JComboBox<>(days);
        
        cbYear.setBorder(retroBorder);
        cbMonth.setBorder(retroBorder);
        cbDay.setBorder(retroBorder);
        
        datePanel.add(cbYear);
        datePanel.add(cbMonth);
        datePanel.add(cbDay);
        formPanel.add(datePanel);

        formPanel.add(new JLabel("TIME (HH:MM):"));
        String[] times = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
        JComboBox<String> cbTime = new JComboBox<>(times);
        cbTime.setBorder(retroBorder);
        formPanel.add(cbTime);

        JButton btnConfirm = createRetroButton("REVIEW"); 
        btnConfirm.setBackground(new Color(150, 255, 150));
        
        btnConfirm.addActionListener(e -> {
            try {
                String safeDate = cbYear.getSelectedItem() + "-" + cbMonth.getSelectedItem() + "-" + cbDay.getSelectedItem();
                String safeTime = (String) cbTime.getSelectedItem();
                
                // Bulletproof user check
                String studentName = "Student";
                if (Session.getCurrentUser() != null && Session.getCurrentUser().name != null) {
                    studentName = Session.getCurrentUser().name;
                }
                
                String note = "";
                
                bookDialog.dispose();
                
                // Perfectly safe instantiation using 'PlaceDetailDialog.this' directly
                // Inside openRetroBookingWindow action listener
            ReservationConfirmDialog confirmWindow = new ReservationConfirmDialog(
                PlaceDetailDialog.this, 
                studentName, 
                placeName, 
                placeId, // Passing the actual ID from the class field
                safeDate, 
                safeTime, 
                "" // Start with a blank note as requested
            );
            confirmWindow.setVisible(true);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "System Error: " + ex.getMessage());
                        }
                    });

        bookDialog.add(formPanel, BorderLayout.CENTER);
        
        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botPanel.setBackground(bgLight);
        botPanel.add(btnConfirm);
        bookDialog.add(botPanel, BorderLayout.SOUTH);

        bookDialog.setVisible(true);
    }

    private void openRetroReviewWindow() {
        if (!Session.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Please login to leave a review!");
            return;
        }

        JPanel reviewPanel = new JPanel(new BorderLayout(5, 5));
        reviewPanel.add(new JLabel("Max 150 characters. Letters, numbers, and basic punctuation only."), BorderLayout.SOUTH);
        
        String[] ratings = {"5.0 ★★★★★", "4.0 ★★★★☆", "3.0 ★★★☆☆", "2.0 ★★☆☆☆", "1.0 ★☆☆☆☆"};
        JComboBox<String> ratingBox = new JComboBox<>(ratings);
        
        JTextArea commentBox = new JTextArea(4, 20);
        commentBox.setBorder(retroBorder);
        commentBox.setLineWrap(true);
        commentBox.setWrapStyleWord(true);

        ((AbstractDocument) commentBox.getDocument()).setDocumentFilter(new DocumentFilter() {
            private final int MAX_CHARS = 150;

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                String safeText = string.replaceAll("[^a-zA-Z0-9 \\.,!\\?'-]", "");
                if ((fb.getDocument().getLength() + safeText.length()) <= MAX_CHARS) {
                    super.insertString(fb, offset, safeText, attr);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) return;
                String safeText = text.replaceAll("[^a-zA-Z0-9 \\.,!\\?'-]", "");
                if ((fb.getDocument().getLength() + safeText.length() - length) <= MAX_CHARS) {
                    super.replace(fb, offset, length, safeText, attrs);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });

        reviewPanel.add(ratingBox, BorderLayout.NORTH);
        reviewPanel.add(new JScrollPane(commentBox), BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, reviewPanel, "Leave a Review", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION && !commentBox.getText().trim().isEmpty()) {
            String selectedRating = (String) ratingBox.getSelectedItem();
            if (DatabaseHelper.addReview(placeId, Session.getCurrentUser().name, selectedRating, commentBox.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Review submitted! Reopen to see it.");
                dispose();
            }
        }
    }
}