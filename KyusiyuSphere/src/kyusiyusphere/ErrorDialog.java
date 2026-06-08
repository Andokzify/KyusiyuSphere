package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ErrorDialog extends JDialog {

    // Retro UI Colors & Fonts
    private final Color cardGray = new Color(200, 200, 200);
    private final Color borderBlack = Color.BLACK;
    private final Color errorRed = new Color(200, 40, 40); 
    private final Color bgTransparent = new Color(0, 0, 0, 150);
    
    private final Font titleFont = new Font("Courier New", Font.BOLD, 24);
    private final Font mainFont = new Font("Courier New", Font.BOLD, 16);

    public ErrorDialog(JFrame parent, String errorMessage) {
        super(parent, true);
        setUndecorated(true);
        setSize(450, 300);
        setLocationRelativeTo(parent);
        setBackground(new Color(0, 0, 0, 0)); 

        initComponents(errorMessage);
    }

    private void initComponents(String errorMessage) {
        // Main wrapper with padding and dark overlay
        JPanel mainWrapper = new JPanel(new BorderLayout(0, 0));
        mainWrapper.setBackground(bgTransparent);
        mainWrapper.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // The actual dialog box
        JPanel dialogBox = new JPanel(new BorderLayout());
        dialogBox.setBackground(cardGray);
        dialogBox.setBorder(BorderFactory.createLineBorder(borderBlack, 6)); // Extra thick border

        // --- TOP: Error Title ---
        JLabel lblTitle = new JLabel(" ⚠ ACCESS DENIED ", SwingConstants.CENTER);
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBackground(errorRed);
        lblTitle.setOpaque(true);
        lblTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, borderBlack));
        
        // --- MIDDLE: Error Message ---
        // Using HTML allows the text to automatically wrap if it gets too long
        JLabel lblMessage = new JLabel("<html><center>" + errorMessage + "</center></html>", SwingConstants.CENTER);
        lblMessage.setFont(mainFont);
        lblMessage.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- BOTTOM: Retry Button ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(cardGray);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton btnOk = new JButton("RETRY");
        btnOk.setFont(mainFont);
        btnOk.setBackground(Color.WHITE);
        // Chunky retro shadow on the button
        btnOk.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 4, 4, Color.GRAY), 
            BorderFactory.createLineBorder(borderBlack, 2)
        ));
        btnOk.setPreferredSize(new Dimension(120, 40));
        btnOk.setFocusPainted(false);
        btnOk.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Button hover effects
        btnOk.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnOk.setBackground(new Color(230, 230, 230)); }
            public void mouseExited(MouseEvent e) { btnOk.setBackground(Color.WHITE); }
        });
        
        // Close dialog on click
        btnOk.addActionListener(e -> dispose());

        bottomPanel.add(btnOk);

        // Assemble the Box
        dialogBox.add(lblTitle, BorderLayout.NORTH);
        dialogBox.add(lblMessage, BorderLayout.CENTER);
        dialogBox.add(bottomPanel, BorderLayout.SOUTH);

        mainWrapper.add(dialogBox, BorderLayout.CENTER);
        getContentPane().add(mainWrapper);
    }
}