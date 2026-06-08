package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReservationConfirmDialog extends JDialog {

    private final Color bgWhite = Color.WHITE;
    private final Color retroGrey = new Color(200, 200, 200);
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.PLAIN, 14);
    private final Font boldFont = new Font("Courier New", Font.BOLD, 14);
    private final Font titleFont = new Font("Courier New", Font.BOLD, 36); 

    public ReservationConfirmDialog(Window parent, String studentName, String placeName, int placeId, String date, String time, String note) {
        super(parent, "Reservation Details", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(700, 480);
        setLocationRelativeTo(parent);
        setUndecorated(true); 

        JPanel rootPanel = new JPanel(new BorderLayout(15, 15));
        rootPanel.setBackground(bgWhite);
        rootPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(30, 80, 200), 5), 
            BorderFactory.createLineBorder(borderBlack, 2)        
        ));

        // --- RETRO TITLE BAR ---
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Color.LIGHT_GRAY);
        titleBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, borderBlack));
        
        JLabel windowTitle = new JLabel(" RESERVATION CONFIRMATION");
        windowTitle.setFont(boldFont);
        
        JLabel btnClose = new JLabel(" X ", SwingConstants.CENTER);
        btnClose.setFont(boldFont);
        btnClose.setBorder(BorderFactory.createLineBorder(borderBlack, 1));
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        
        titleBar.add(windowTitle, BorderLayout.WEST);
        titleBar.add(btnClose, BorderLayout.EAST);
        rootPanel.add(titleBar, BorderLayout.NORTH);

        // --- MAIN CONTENT ---
        JPanel mainContent = new JPanel(new BorderLayout(15, 15));
        mainContent.setBackground(bgWhite);
        mainContent.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // HUGE TITLE
        JLabel lblHeader = new JLabel("RESERVATION DETAILS:", SwingConstants.CENTER);
        lblHeader.setFont(titleFont);
        mainContent.add(lblHeader, BorderLayout.NORTH);

        JPanel centerSplit = new JPanel(new BorderLayout(15, 0));
        centerSplit.setBackground(bgWhite);

        // PILL-SHAPED DATA FORM
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        formPanel.setBackground(bgWhite);
        
        formPanel.add(createPillRow("NAME", studentName));
        formPanel.add(createPillRow("CITE NAME", placeName));
        formPanel.add(createPillRow("DATE", date));
        formPanel.add(createPillRow("TIME", time));
        
        centerSplit.add(formPanel, BorderLayout.CENTER);

        // IMAGE
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        imgLabel.setPreferredSize(new Dimension(200, 150));
        ImageIcon icon = ImageManager.loadPlaceImage(placeName, 200, 150);
        if (icon != null) imgLabel.setIcon(icon);
        else imgLabel.setText("NO IMAGE");
        centerSplit.add(imgLabel, BorderLayout.EAST);

        mainContent.add(centerSplit, BorderLayout.CENTER);

        // BOTTOM: COMMENT & CONFIRM
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 5));
        bottomPanel.setBackground(bgWhite);

        JPanel commentWrapper = new JPanel(new BorderLayout());
        commentWrapper.setBackground(bgWhite);
        JLabel lblComment = new JLabel("COMMENT / NOTE FOR CITE OWNER:");
        lblComment.setFont(boldFont);
        commentWrapper.add(lblComment, BorderLayout.NORTH);

        JTextArea txtComment = new JTextArea(note);
        txtComment.setFont(mainFont);
        txtComment.setLineWrap(true);
        txtComment.setWrapStyleWord(true);
        txtComment.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 3),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        txtComment.setPreferredSize(new Dimension(400, 80));
        commentWrapper.add(txtComment, BorderLayout.CENTER);

        // PILL CONFIRM BUTTON
        JButton btnConfirm = new JButton("CONFIRM");
        btnConfirm.setFont(new Font("Courier New", Font.BOLD, 18));
        btnConfirm.setForeground(new Color(0, 150, 0)); 
        btnConfirm.setBackground(retroGrey);
        btnConfirm.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 3, true));
        btnConfirm.setFocusPainted(false);
        btnConfirm.setPreferredSize(new Dimension(160, 40));
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnConfirm.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnConfirm.setBackground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { btnConfirm.setBackground(retroGrey); }
        });
        
        btnConfirm.addActionListener(e -> {
            try {
                String status = DatabaseHelper.checkBookingAvailability(placeId, date, time);
                
                if (status.equals("FULL")) {
                    // Trigger the Warning Dialog
                    JOptionPane.showMessageDialog(this, 
                        "⚠ MAXIMUM CAPACITY REACHED!\nAll slots for " + time + " are currently booked.\nPlease select a different time.", 
                        "Slot Unavailable", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean success = DatabaseHelper.createReservation(Session.getCurrentUserId(), placeId, date, time);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Reservation Sent to Owner!");
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(bgWhite);
        btnPanel.add(btnConfirm);

        bottomPanel.add(commentWrapper, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.EAST);

        mainContent.add(bottomPanel, BorderLayout.SOUTH);
        rootPanel.add(mainContent, BorderLayout.CENTER);

        add(rootPanel);
    }

    // Helper to fake the Pill-shaped rows using rounded Swing borders
    private JPanel createPillRow(String labelText, String valueText) {
        JPanel row = new JPanel(new BorderLayout(5, 0));
        row.setBackground(bgWhite);

        // Rounded grey label
        JLabel lbl = new JLabel(labelText, SwingConstants.CENTER);
        lbl.setFont(boldFont);
        lbl.setBackground(retroGrey);
        lbl.setOpaque(true);
        lbl.setPreferredSize(new Dimension(120, 35));
        lbl.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 2, true)); 

        // Square grey value box
        JLabel val = new JLabel("  " + valueText);
        val.setFont(mainFont);
        val.setBackground(retroGrey);
        val.setOpaque(true);
        val.setBorder(BorderFactory.createLineBorder(borderBlack, 2)); 

        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.CENTER);

        return row;
    }
}