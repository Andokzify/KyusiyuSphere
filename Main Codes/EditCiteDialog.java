package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class EditCiteDialog extends JDialog {
    private Place place;
    private String selectedImagePath = ""; 

    public EditCiteDialog(JFrame parent, Place place) {
        super(parent, "Edit Cite Details", true);
        this.place = place;
        setSize(550, 680); 
        setLocationRelativeTo(parent);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(245, 245, 250));

        JLabel titleLabel = new JLabel("EDIT CITE DETAILS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBounds(145, 10, 260, 40);
        mainPanel.add(titleLabel);

        int y = 60;
        JTextField txtDesc = createField(mainPanel, "Description:", y, place.description); y += 45;
        JTextField txtPrice = createField(mainPanel, "Price (₱):", y, String.valueOf(place.price)); y += 45;
        JTextField txtCap = createField(mainPanel, "Max Cap:", y, String.valueOf(place.maxReservations)); y += 45;
        JTextField txtContact = createField(mainPanel, "Contact:", y, place.contact); y += 45;

        // 📍 PHOTO PREVIEW SECTION
        JLabel lblPhotoPreview = new JLabel("Loading image...", SwingConstants.CENTER);
        lblPhotoPreview.setBounds(135, y, 280, 150);
        lblPhotoPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        lblPhotoPreview.setBackground(Color.WHITE);
        lblPhotoPreview.setOpaque(true);
        
        ImageIcon currentIcon = ImageManager.loadPlaceImage(place.name, 280, 150);
        if (currentIcon != null) {
            lblPhotoPreview.setIcon(currentIcon);
            lblPhotoPreview.setText("");
        } else {
            lblPhotoPreview.setText("NO IMAGE FOUND");
        }
        mainPanel.add(lblPhotoPreview);
        y += 160;

        JButton btnChangePhoto = new JButton("Change Photo");
        btnChangePhoto.setBounds(200, y, 150, 30);
        btnChangePhoto.addActionListener(e -> uploadPhoto(lblPhotoPreview));
        mainPanel.add(btnChangePhoto);
        y += 60; // Added padding before save buttons

        JButton btnSave = new JButton("UPDATE DETAILS");
        btnSave.setBounds(120, y, 140, 40);
        btnSave.setBackground(new Color(30, 100, 45));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> {
            try {
                double newPrice = Double.parseDouble(txtPrice.getText().trim());
                int newCap = Integer.parseInt(txtCap.getText().trim());
                
                if (!selectedImagePath.isEmpty()) {
                    ImageManager.savePlaceImage(selectedImagePath, place.name);
                }

                boolean success = DatabaseHelper.updatePlaceDetails(
                    place.id, txtDesc.getText().trim(), newPrice, newCap, txtContact.getText().trim()
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Site details updated successfully!");
                    dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Price or Capacity format.");
            }
        });
        mainPanel.add(btnSave);

        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(280, y, 140, 40);
        btnCancel.addActionListener(e -> dispose());
        mainPanel.add(btnCancel);

        add(mainPanel);
    }

    private void uploadPhoto(JLabel previewLabel) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            selectedImagePath = file.getAbsolutePath();
            ImageIcon icon = new ImageIcon(selectedImagePath);
            Image scaled = icon.getImage().getScaledInstance(280, 150, Image.SCALE_SMOOTH);
            previewLabel.setIcon(new ImageIcon(scaled));
            previewLabel.setText("");
        }
    }

    private JTextField createField(JPanel panel, String labelText, int y, String value) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setBounds(40, y, 100, 30);
        panel.add(label);

        JTextField field = new JTextField(value);
        field.setBounds(135, y, 330, 30);
        panel.add(field);
        return field;
    }
}