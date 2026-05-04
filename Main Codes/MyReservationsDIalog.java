package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyReservationsDialog extends JDialog {

    private final Color bgDark = new Color(15, 15, 30);
    private final Color panelDark = new Color(25, 25, 45);
    private final Color accentBlue = new Color(0, 150, 255);
    private final Color textMuted = new Color(180, 180, 190);

    public MyReservationsDialog(JFrame parent) {
        super(parent, "My Reservations", true); // Modal dialog
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgDark);

        // Title Area
        JLabel titleLabel = new JLabel("MY RESERVATIONS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Scrollable List Area
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(bgDark);

        // Fetch data from database
        List<String> reservations = DatabaseHelper.getUserReservations(Session.getCurrentUserId());

        if (reservations.isEmpty()) {
            JLabel emptyLabel = new JLabel("You have no reservations yet.");
            emptyLabel.setForeground(textMuted);
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(Box.createVerticalStrut(100)); // Push it down a bit
            listPanel.add(emptyLabel);
        } else {
            // Build a visual card for each reservation
            for (String res : reservations) {
                listPanel.add(createReservationCard(res));
                listPanel.add(Box.createVerticalStrut(15)); // Spacing between cards
            }
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scrollPane.setBackground(bgDark);
        scrollPane.getViewport().setBackground(bgDark);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Close Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bgDark);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JButton btnClose = new JButton("CLOSE");
        btnClose.setBackground(new Color(70, 70, 90));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(e -> dispose());
        bottomPanel.add(btnClose);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createReservationCard(String data) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBackground(panelDark);
        card.setMaximumSize(new Dimension(420, 90));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 70), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Parse the string returned by DatabaseHelper: "PlaceName - Date at Time (Status)"
        String placeName = "Unknown Place";
        String details = data;
        String status = "Pending";

        if (data.contains(" - ")) {
            String[] parts = data.split(" - ", 2);
            placeName = parts[0];
            details = parts[1];
        }
        if (details.contains("(")) {
            int start = details.indexOf("(");
            int end = details.indexOf(")");
            if (end > start) {
                status = details.substring(start + 1, end);
            }
        }

        // Left Icon
        JLabel lblIcon = new JLabel("📅", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        card.add(lblIcon, BorderLayout.WEST);

        // Center Text
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);

        JLabel lblName = new JLabel(placeName);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblName.setForeground(Color.WHITE);
        textPanel.add(lblName);

        JLabel lblDetails = new JLabel(details.replace("(" + status + ")", "").trim());
        lblDetails.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDetails.setForeground(textMuted);
        textPanel.add(lblDetails);

        card.add(textPanel, BorderLayout.CENTER);

        // Right Status Badge
        JLabel lblStatus = new JLabel(status.toUpperCase(), SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Color code based on status
        if (status.equalsIgnoreCase("Pending")) lblStatus.setForeground(new Color(255, 150, 0));
        else if (status.equalsIgnoreCase("Approved")) lblStatus.setForeground(new Color(0, 200, 100));
        else lblStatus.setForeground(new Color(200, 50, 50)); // Denied or Cancelled

        card.add(lblStatus, BorderLayout.EAST);

        return card;
    }
}