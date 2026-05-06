package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SuccessDialog extends JDialog {

    // Retro UI Colors & Fonts
    private final Color cardGray = new Color(200, 200, 200);
    private final Color borderBlack = Color.BLACK;
    private final Color successGreen = new Color(40, 160, 60); 
    private final Color bgTransparent = new Color(0, 0, 0, 150); 
    
    private final Font titleFont = new Font("Courier New", Font.BOLD, 24);
    private final Font mainFont = new Font("Courier New", Font.BOLD, 16);

    public SuccessDialog(JFrame parent, String userName) {
        super(parent, true); 
        setUndecorated(true);
        setSize(450, 300);
        setLocationRelativeTo(parent);
        setBackground(new Color(0, 0, 0, 0)); 

        initComponents(userName);
    }

    private void initComponents(String userName) {
        // Main wrapper with padding and dark overlay
        JPanel mainWrapper = new JPanel(new BorderLayout());
        mainWrapper.setBackground(bgTransparent);
        mainWrapper.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // The actual dialog box
        JPanel dialogBox = new JPanel(new BorderLayout());
        dialogBox.setBackground(cardGray);
        dialogBox.setBorder(BorderFactory.createLineBorder(borderBlack, 6));

        // --- TOP: Success Title ---
        JLabel lblTitle = new JLabel(" ✅ ACCESS GRANTED ", SwingConstants.CENTER);
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBackground(successGreen);
        lblTitle.setOpaque(true);
        lblTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, borderBlack));
        
        // --- MIDDLE: Welcome Message ---
        JLabel lblMessage = new JLabel("<html><center>Login Successful!<br><br>Welcome back,<br><b>" + userName + "</b></center></html>", SwingConstants.CENTER);
        lblMessage.setFont(mainFont);
        lblMessage.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- BOTTOM: Enter Button ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(cardGray);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton btnOk = new JButton("ENTER");
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
        
        // Close dialog on click so the app can move to the dashboard
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