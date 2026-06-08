package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistrationSuccessDialog extends JDialog {

    private final Color cardGray = new Color(200, 200, 200);
    private final Color borderBlack = Color.BLACK;
    private final Color bgTransparent = new Color(0, 0, 0, 150); 
    
    private final Font titleFont = new Font("Courier New", Font.BOLD, 28);
    private final Font subtitleFont = new Font("Courier New", Font.BOLD, 22);
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);
    private final Font smallFont = new Font("Courier New", Font.BOLD, 12);

    public RegistrationSuccessDialog(JFrame parent, String userName, String role, String imagePath, String campus) {
        super(parent, true);
        setUndecorated(true); 
        setSize(700, 450);
        setLocationRelativeTo(parent);
        setBackground(new Color(0, 0, 0, 0)); 

        initComponents(userName, role, imagePath, campus);
    }

    private void initComponents(String userName, String role, String imagePath, String campus) {
        // Main wrapper to hold everything with a little padding
        JPanel mainWrapper = new JPanel(new BorderLayout(0, 20));
        mainWrapper.setBackground(bgTransparent);
        mainWrapper.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // ================= 1. TOP TITLE =================
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createLineBorder(borderBlack, 4));
        
        JLabel lblTitle = new JLabel("REGISTRATION COMPLETE");
        lblTitle.setFont(titleFont);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        titlePanel.add(lblTitle);

        // ================= 2. USER PROFILE SUBTITLE =================
        JLabel lblSubtitle = new JLabel("USER PROFILE:", SwingConstants.CENTER);
        lblSubtitle.setFont(subtitleFont);
        lblSubtitle.setForeground(Color.WHITE); 

        JPanel topSection = new JPanel(new BorderLayout(0, 10));
        topSection.setOpaque(false);
        topSection.add(titlePanel, BorderLayout.NORTH);
        topSection.add(lblSubtitle, BorderLayout.SOUTH);

        // ================= 3. THE ID CARD =================
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(cardGray);
        cardPanel.setBorder(BorderFactory.createLineBorder(borderBlack, 6)); 

        // --- LEFT SIDE: Photo & Name ---
        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        leftPanel.setBackground(cardGray);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 4, borderBlack), 
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        leftPanel.setPreferredSize(new Dimension(200, 0));

        JLabel lblPhoto = new JLabel();
        lblPhoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhoto.setBackground(Color.WHITE);
        lblPhoto.setOpaque(true);
        lblPhoto.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        lblPhoto.setPreferredSize(new Dimension(150, 150));

        if (imagePath != null && !imagePath.isEmpty()) {
            // Load and scale the user's actual photo
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaled = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblPhoto.setIcon(new ImageIcon(scaled));
        } else {
            // Fallback if they didn't upload a picture
            lblPhoto.setText("NO PHOTO");
            lblPhoto.setFont(mainFont);
        }
        
        if (role.equalsIgnoreCase("ADMIN")) {
            lblPhoto.setVisible(false); 
        }

        JLabel lblName = new JLabel("<html><center>" + userName + "<br>NEW USER</center></html>", SwingConstants.CENTER);
        lblName.setFont(mainFont);

        leftPanel.add(lblPhoto, BorderLayout.CENTER);
        leftPanel.add(lblName, BorderLayout.SOUTH);

        // --- RIGHT SIDE: Stats & Details ---
        JPanel rightPanel = new JPanel(new BorderLayout(0, 15));
        rightPanel.setBackground(cardGray);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblRole = new JLabel("👤 " + role.toUpperCase(), SwingConstants.CENTER);
        lblRole.setFont(subtitleFont);

        // Grid for the 3 data rows
        JPanel statsGrid = new JPanel(new GridLayout(3, 1, 0, 10));
        statsGrid.setBackground(cardGray);

        // Generate Current Date/Time
        String currentDate = new java.text.SimpleDateFormat("MM/dd/yyyy (hh:mma)").format(new java.util.Date());

        String dynamicLabel = role.equalsIgnoreCase("OWNER") ? "CONTACT NO." : "CAMPUS";

        statsGrid.add(createDataRow(dynamicLabel, campus.toUpperCase()));
        statsGrid.add(createDataRow("CITES VISITED", "NONE"));
        statsGrid.add(createDataRow("ACCOUNT CREATED", currentDate));

        rightPanel.add(lblRole, BorderLayout.NORTH);
        rightPanel.add(statsGrid, BorderLayout.CENTER);

        // Assemble the Card
        cardPanel.add(leftPanel, BorderLayout.WEST);
        cardPanel.add(rightPanel, BorderLayout.CENTER);

        // Add everything to the wrapper
        mainWrapper.add(topSection, BorderLayout.NORTH);
        mainWrapper.add(cardPanel, BorderLayout.CENTER);
        
        // Add a helpful "Click to close" label at the bottom
        JLabel lblClose = new JLabel("Click anywhere to continue...", SwingConstants.CENTER);
        lblClose.setFont(smallFont);
        lblClose.setForeground(Color.LIGHT_GRAY);
        mainWrapper.add(lblClose, BorderLayout.SOUTH);

        // Allow clicking anywhere on the dialog to close it
        mainWrapper.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // Closes the popup
            }
        });

        getContentPane().add(mainWrapper);
    }

    // Helper method to create the pill-shaped retro data rows
    private JPanel createDataRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(cardGray);
        row.setBorder(BorderFactory.createLineBorder(borderBlack, 2));

        JLabel lblLeft = new JLabel("  " + label);
        lblLeft.setFont(smallFont);
        lblLeft.setPreferredSize(new Dimension(130, 0));
        lblLeft.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, borderBlack));

        JLabel lblRight = new JLabel(value, SwingConstants.CENTER);
        lblRight.setFont(smallFont);

        row.add(lblLeft, BorderLayout.WEST);
        row.add(lblRight, BorderLayout.CENTER);
        return row;
    }
}