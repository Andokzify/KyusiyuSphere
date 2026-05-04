package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AddCiteDialog extends JDialog {

    private String selectedImagePath = "";

    public AddCiteDialog(JFrame parent) {
        super(parent, "Add New Cite", true); // Modal dialog
        setSize(620, 520);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(245, 245, 250));

        // Title
        JLabel titleLabel = new JLabel("ADD NEW CITE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBounds(180, 20, 260, 40);
        mainPanel.add(titleLabel);

        // Form Fields
        int y = 90;

        // Cite Name
        addLabel(mainPanel, "Cite Name:", 50, y);
        JTextField txtCiteName = new JTextField();
        txtCiteName.setBounds(180, y, 380, 35);
        mainPanel.add(txtCiteName);
        y += 60;

        // Address
        addLabel(mainPanel, "Address:", 50, y);
        JTextField txtAddress = new JTextField();
        txtAddress.setBounds(180, y, 380, 35);
        mainPanel.add(txtAddress);
        y += 60;

        // Contact
        addLabel(mainPanel, "Contact No:", 50, y);
        JTextField txtContact = new JTextField();
        txtContact.setBounds(180, y, 380, 35);
        mainPanel.add(txtContact);
        y += 60;

        // Photo Upload Section
        JLabel photoLabel = new JLabel("Upload Photo of Cite:");
        photoLabel.setBounds(50, y, 200, 25);
        mainPanel.add(photoLabel);

        JButton btnUpload = new JButton("Choose Image");
        btnUpload.setBounds(250, y, 140, 35);
        btnUpload.addActionListener(e -> uploadPhoto());
        mainPanel.add(btnUpload);

        // Photo Preview
        JLabel lblPhotoPreview = new JLabel("No image selected", SwingConstants.CENTER);
        lblPhotoPreview.setBounds(410, y-10, 160, 120);
        lblPhotoPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        lblPhotoPreview.setBackground(Color.WHITE);
        lblPhotoPreview.setOpaque(true);
        mainPanel.add(lblPhotoPreview);

        // Buttons at bottom
        JButton btnSave = new JButton("SAVE CITE");
        btnSave.setBounds(180, 400, 140, 45);
        btnSave.setBackground(new Color(30, 45, 100));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.addActionListener(e -> saveCite(txtCiteName, txtAddress, txtContact, lblPhotoPreview));
        mainPanel.add(btnSave);

        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(340, 400, 140, 45);
        btnCancel.setBackground(new Color(120, 120, 130));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(e -> dispose());
        mainPanel.add(btnCancel);

        add(mainPanel);
    }

    private void addLabel(JPanel panel, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setBounds(x, y, 120, 30);
        panel.add(label);
    }

    private void uploadPhoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            selectedImagePath = file.getAbsolutePath();

            // Show preview
            try {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image scaled = icon.getImage().getScaledInstance(150, 110, Image.SCALE_SMOOTH);
                JOptionPane.showMessageDialog(this, "Image selected: " + file.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to load image.");
            }
        }
    }

    private void saveCite(JTextField txtName, JTextField txtAddress, JTextField txtContact, JLabel preview) {
    String name = txtName.getText().trim();
    String address = txtAddress.getText().trim();
    String contact = txtContact.getText().trim();

    if (name.isEmpty() || address.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Cite Name and Address are required!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

   
    UserAccount currentOwner = Session.getCurrentUser();

    // 1. Create a new Place object with the gathered data
    Place newPlace = new Place(
        0,             // Database ID (auto-generated later)
        name, 
        "No description provided.", 
        address, 
        currentOwner,  // FIXED: Now properly linked to the logged-in owner!
        "General",     // Label
        true,          // Reservation Available
        true,          // Walk-in Available
        contact, 
        "TBD"          // Price
    );

    // 2. Save it using the DatabaseHelper
    DatabaseHelper.addPlace(newPlace);

    // 3. Save the actual image file to our local directory
    if (!selectedImagePath.isEmpty()) {
        ImageManager.savePlaceImage(selectedImagePath, name);
    }

    // 4. Success Feedback
    JOptionPane.showMessageDialog(this, name + " has been added to the system!");

    dispose(); // Close the dialog
}

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> 
            new AddCiteDialog(new JFrame()).setVisible(true));
    }
}