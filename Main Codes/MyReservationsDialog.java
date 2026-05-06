package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyReservationsDialog extends JDialog {

    // Syncing with userframexperi theme
    private final Color bgLight = new Color(210, 180, 140);
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);
    private final Font titleFont = new Font("Courier New", Font.BOLD, 22);

    public MyReservationsDialog(JFrame parent) {
        super(parent, "My Reservations", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setUndecorated(true); // Retro popup feel

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgLight);
        mainPanel.setBorder(BorderFactory.createLineBorder(borderBlack, 4));

        // --- TOP BAR ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(borderBlack);
        JLabel titleLabel = new JLabel(" 🎟️ MY RESERVATIONS", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnCloseX = new JButton("X ");
        btnCloseX.setForeground(Color.WHITE);
        btnCloseX.setContentAreaFilled(false);
        btnCloseX.setBorderPainted(false);
        btnCloseX.setFocusPainted(false);
        btnCloseX.addActionListener(e -> dispose());
        
        header.add(titleLabel, BorderLayout.CENTER);
        header.add(btnCloseX, BorderLayout.EAST);
        mainPanel.add(header, BorderLayout.NORTH);

        // --- LIST AREA ---
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(bgLight);
        listPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<String> reservations = DatabaseHelper.getUserReservations(Session.getCurrentUserId());

        if (reservations.isEmpty()) {
            JLabel emptyLabel = new JLabel("<html><center>No reservations found.<br>Start exploring!</center></html>", SwingConstants.CENTER);
            emptyLabel.setFont(mainFont);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(Box.createVerticalStrut(100));
            listPanel.add(emptyLabel);
        } else {
            for (String res : reservations) {
                listPanel.add(createReservationTicket(res));
                listPanel.add(Box.createVerticalStrut(15));
            }
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel createReservationTicket(String data) {
        JPanel ticket = new JPanel(new BorderLayout(15, 5));
        ticket.setBackground(Color.WHITE);
        ticket.setMaximumSize(new Dimension(440, 100));
        ticket.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Parsing logic from your original file[cite: 7]
        String placeName = data.split(" - ")[0];
        String details = data.contains(" - ") ? data.split(" - ")[1] : data;
        String status = "PENDING";
        if (data.contains("(")) {
            status = data.substring(data.indexOf("(") + 1, data.indexOf(")")).toUpperCase();
        }

        JLabel lblIcon = new JLabel("📍");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        
        JLabel lblName = new JLabel(placeName.toUpperCase());
        lblName.setFont(new Font("Courier New", Font.BOLD, 16));
        
        JLabel lblDetails = new JLabel(details.replace("(" + status.toLowerCase() + ")", "").trim());
        lblDetails.setFont(new Font("Courier New", Font.PLAIN, 12));

        textPanel.add(lblName);
        textPanel.add(lblDetails);

        JLabel lblStatus = new JLabel(status);
        lblStatus.setFont(new Font("Courier New", Font.BOLD, 12));
        if (status.equals("APPROVED")) lblStatus.setForeground(new Color(0, 150, 0));
        else if (status.equals("DENIED")) lblStatus.setForeground(Color.RED);

        ticket.add(lblIcon, BorderLayout.WEST);
        ticket.add(textPanel, BorderLayout.CENTER);
        ticket.add(lblStatus, BorderLayout.SOUTH);

        return ticket;
    }
}