package kyusiyusphere;

import javax.swing.*;
import java.awt.*;

public class PlaceDetailDialog extends JDialog {

    public PlaceDetailDialog(JFrame parent, String placeName, String description) {
        super(parent, true); // Modal
        setTitle(placeName);
        setSize(820, 580);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(30, 30, 45));

        // ==================== LEFT SIDE ====================
        JLabel imgLabel = new JLabel();
        imgLabel.setBounds(30, 30, 340, 220);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Safe image loading with fallback
        ImageIcon icon = loadImage("/Images/" + placeName.toLowerCase().replace(" ", "") + ".jpg");
        if (icon != null) {
            imgLabel.setIcon(icon);
        } else {
            // Fallback placeholder
            imgLabel.setText("📷 No Image Available");
            imgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 50));
            imgLabel.setForeground(new Color(100, 180, 255));
        }
        mainPanel.add(imgLabel);

        JLabel nameLabel = new JLabel(placeName);
        nameLabel.setBounds(30, 270, 340, 40);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setForeground(Color.WHITE);
        mainPanel.add(nameLabel);

        JLabel heart = new JLabel("❤️");
        heart.setBounds(340, 275, 40, 40);
        heart.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        mainPanel.add(heart);

        JLabel locationLabel = new JLabel("📍 Block 2345678890, Quezon City");
        locationLabel.setBounds(30, 320, 340, 25);
        locationLabel.setForeground(new Color(180, 180, 200));
        mainPanel.add(locationLabel);

        JTextArea descArea = new JTextArea(description);
        descArea.setBounds(30, 360, 340, 120);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(new Color(40, 40, 55));
        descArea.setForeground(Color.WHITE);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(descArea);

        JButton btnBook = new JButton("BOOK");
        btnBook.setBounds(30, 500, 150, 45);
        btnBook.setBackground(new Color(0, 180, 100));
        btnBook.setForeground(Color.WHITE);
        btnBook.setFont(new Font("Segoe UI", Font.BOLD, 16));
        mainPanel.add(btnBook);

        JButton btnReview = new JButton("REVIEW");
        btnReview.setBounds(200, 500, 150, 45);
        btnReview.setBackground(new Color(70, 130, 255));
        btnReview.setForeground(Color.WHITE);
        btnReview.setFont(new Font("Segoe UI", Font.BOLD, 16));
        mainPanel.add(btnReview);

        // ==================== RIGHT SIDE - RATINGS ====================
        JPanel ratingsPanel = new JPanel(null);
        ratingsPanel.setBounds(410, 20, 380, 520);
        ratingsPanel.setBackground(new Color(25, 25, 40));
        ratingsPanel.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 100), 2));

        JLabel ratingsTitle = new JLabel("RATINGS:");
        ratingsTitle.setBounds(20, 15, 200, 30);
        ratingsTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        ratingsTitle.setForeground(new Color(255, 215, 0));
        ratingsPanel.add(ratingsTitle);

        // Sample reviews
        String[][] reviews = {
            {"4.8 ★★★★★", "DawingpossiCats", "Very cozy place! Love the cats here."},
            {"4.2 ★★★★☆", "BonbonMarkus", "Good WiFi and snacks. Will come back."},
            {"5.0 ★★★★★", "Kinalwot aku :(", "Best study spot near QCU!"},
            {"3.9 ★★★★☆", "AnonimHouse", "Nice ambiance but can get crowded."}
        };

        int y = 60;
        for (String[] r : reviews) {
            JLabel ratingLabel = new JLabel(r[0] + "   " + r[1]);
            ratingLabel.setBounds(20, y, 340, 25);
            ratingLabel.setForeground(new Color(255, 215, 0));
            ratingLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            ratingsPanel.add(ratingLabel);

            JTextArea comment = new JTextArea(r[2]);
            comment.setBounds(20, y + 28, 340, 45);
            comment.setLineWrap(true);
            comment.setWrapStyleWord(true);
            comment.setEditable(false);
            comment.setBackground(new Color(35, 35, 50));
            comment.setForeground(Color.WHITE);
            comment.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            ratingsPanel.add(comment);

            y += 85;
        }

        mainPanel.add(ratingsPanel);
        add(mainPanel);

        // Button actions (placeholders)
        btnBook.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Booking calendar will open here (Week 3)"));

        btnReview.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Review form coming soon!"));
    }

    // Safe image loader
    private ImageIcon loadImage(String path) {
        try {
            java.net.URL url = getClass().getResource(path);
            if (url != null) {
                return new ImageIcon(url);
            }
        } catch (Exception ignored) {}
        return null;
    }
}