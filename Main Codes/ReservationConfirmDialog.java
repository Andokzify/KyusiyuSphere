package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReservationConfirmDialog extends JDialog {

    private final Color bgLight = new Color(240, 240, 240);
    private final Color labelGray = new Color(190, 190, 190);
    private final Color valueGray = new Color(220, 220, 220);
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);
    private final Font titleFont = new Font("Impact", Font.PLAIN, 36); 
    
    private final javax.swing.border.Border thickBorder = BorderFactory.createLineBorder(borderBlack, 2);

    // CHANGED: parent is now 'Window' to prevent casting crashes
    public ReservationConfirmDialog(Window parent, String studentName, String placeName, int placeId, String date, String time, String note) {
        super(parent, "Reservation Details", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(650, 450);
        setLocationRelativeTo(parent);
        setUndecorated(true); 

        JPanel rootPanel = new JPanel(new BorderLayout(10, 10));
        rootPanel.setBackground(bgLight);
        rootPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 2),
            BorderFactory.createMatteBorder(3, 3, 3, 3, Color.WHITE) 
        ));

        // --- RETRO TITLE BAR ---
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(0, 0, 170));
        titleBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        JLabel windowTitle = new JLabel("Reservation Confirmation");
        windowTitle.setForeground(Color.WHITE);
        windowTitle.setFont(new Font("Courier New", Font.BOLD, 12));
        
        JButton btnClose = new JButton("X");
        btnClose.setFont(new Font("Courier New", Font.BOLD, 12));
        btnClose.setBackground(bgLight);
        btnClose.setBorder(BorderFactory.createRaisedBevelBorder());
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(e -> dispose());
        
        titleBar.add(windowTitle, BorderLayout.WEST);
        titleBar.add(btnClose, BorderLayout.EAST);
        rootPanel.add(titleBar, BorderLayout.NORTH);

        // --- MAIN CONTENT ---
        JPanel mainContent = new JPanel(new BorderLayout(15, 15));
        mainContent.setBackground(bgLight);
        mainContent.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel lblHeader = new JLabel("RESERVATION DETAILS:", SwingConstants.CENTER);
        lblHeader.setFont(titleFont);
        mainContent.add(lblHeader, BorderLayout.NORTH);

        JPanel centerSplit = new JPanel(new BorderLayout(15, 0));
        centerSplit.setOpaque(false);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 0, 8));
        formPanel.setOpaque(false);
        
        formPanel.add(createRetroRow("NAME", studentName));
        formPanel.add(createRetroRow("CITE NAME", placeName));
        formPanel.add(createRetroRow("DATE", date));
        formPanel.add(createRetroRow("TIME", time));
        
        centerSplit.add(formPanel, BorderLayout.CENTER);

        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setBorder(thickBorder);
        imgLabel.setPreferredSize(new Dimension(200, 150));
        
        ImageIcon icon = ImageManager.loadPlaceImage(placeName, 200, 150);
        if (icon != null) imgLabel.setIcon(icon);
        else {
            imgLabel.setText("NO IMAGE");
            imgLabel.setFont(mainFont);
        }
        centerSplit.add(imgLabel, BorderLayout.EAST);

        mainContent.add(centerSplit, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 5));
        bottomPanel.setOpaque(false);

        JLabel lblComment = new JLabel("COMMENT / NOTE FOR CITE OWNER:");
        lblComment.setFont(new Font("Courier New", Font.BOLD, 12));
        bottomPanel.add(lblComment, BorderLayout.NORTH);

        JTextArea txtComment = new JTextArea(note);
        txtComment.setFont(new Font("Courier New", Font.PLAIN, 12));
        txtComment.setLineWrap(true);
        txtComment.setWrapStyleWord(true);
        txtComment.setEditable(true);
        txtComment.setBackground(Color.WHITE);
        txtComment.setBorder(BorderFactory.createCompoundBorder(
            thickBorder,
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        txtComment.setPreferredSize(new Dimension(0, 80));
        bottomPanel.add(txtComment, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        
        JButton btnConfirm = new JButton("CONFIRM");
        btnConfirm.setFont(new Font("Courier New", Font.BOLD, 16));
        btnConfirm.setForeground(new Color(0, 200, 100)); 
        btnConfirm.setBackground(labelGray);
        btnConfirm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 3, Color.GRAY), 
            thickBorder
        ));
        btnConfirm.setFocusPainted(false);
        btnConfirm.setPreferredSize(new Dimension(140, 35));
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnConfirm.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnConfirm.setBackground(new Color(170, 170, 170)); }
            public void mouseExited(MouseEvent e) { btnConfirm.setBackground(labelGray); }
        });
        
        // Wrapped in try-catch to ensure we see any DB errors
        // Inside ReservationConfirmDialog constructor
btnConfirm.addActionListener(e -> {
    try {
        // Grab the text the user actually typed
        String finalStudentNote = txtComment.getText(); 
        
        // Execute the reservation
        boolean success = DatabaseHelper.createReservation(
            Session.getCurrentUserId(), 
            placeId, 
            date, 
            time
            // Note: If you want to save 'finalStudentNote', add it to your SQL method!
        );
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Reservation Sent to Owner!");
            dispose();
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
});
        
        btnPanel.add(btnConfirm);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        mainContent.add(bottomPanel, BorderLayout.SOUTH);
        rootPanel.add(mainContent, BorderLayout.CENTER);

        add(rootPanel);
    }

    private JPanel createRetroRow(String labelText, String valueText) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setPreferredSize(new Dimension(0, 35));

        JLabel lbl = new JLabel(labelText, SwingConstants.CENTER);
        lbl.setFont(mainFont);
        lbl.setBackground(labelGray);
        lbl.setOpaque(true);
        lbl.setPreferredSize(new Dimension(120, 35));
        lbl.setBorder(thickBorder);

        JLabel val = new JLabel("  " + valueText);
        val.setFont(mainFont);
        val.setBackground(valueGray);
        val.setOpaque(true);
        val.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 2, borderBlack)); 

        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.CENTER);

        return row;
    }
}