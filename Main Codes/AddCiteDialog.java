package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AddCiteDialog extends JDialog {

    private String selectedImagePath = "";

    public AddCiteDialog(JFrame parent) {
        super(parent, "Add New Cite", true);
        setSize(620, 600); // 📍 Increased height to fit description
        setLocationRelativeTo(parent);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(245, 245, 250));

        JLabel titleLabel = new JLabel("ADD NEW CITE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBounds(180, 10, 260, 40);
        mainPanel.add(titleLabel);

        int y = 60;

        JTextField txtName = createField(mainPanel, "Name:", y); y += 45;
        JTextField txtDesc = createField(mainPanel, "Description:", y); y += 45; 
        JTextField txtAddress = createField(mainPanel, "Address:", y); y += 45;
        JTextField txtContact = createField(mainPanel, "Contact:", y); y += 45;
        JTextField txtPrice = createField(mainPanel, "Price (₱):", y); y += 45;
        JTextField txtCapacity = createField(mainPanel, "Max Cap:", y); y += 45;

        JLabel lblPreview = new JLabel("Preview", SwingConstants.CENTER);
        lblPreview.setBounds(400, 320, 150, 110);
        lblPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblPreview.setBackground(Color.WHITE);
        lblPreview.setOpaque(true);
        mainPanel.add(lblPreview);

        JButton btnUpload = new JButton("Upload Photo");
        btnUpload.setBounds(180, y, 150, 35);
        btnUpload.addActionListener(e -> uploadPhoto(lblPreview));
        mainPanel.add(btnUpload);

        JButton btnSave = new JButton("SAVE CITE");
        btnSave.setBounds(180, 480, 130, 40);
        btnSave.setBackground(new Color(30, 45, 100));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> {
            try {
                double price = Double.parseDouble(txtPrice.getText().trim());
                int cap = Integer.parseInt(txtCapacity.getText().trim());
                
                // Pass description to the save method
                saveCite(txtName.getText(), txtDesc.getText(), txtAddress.getText(), txtContact.getText(), price, cap);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for Price and Capacity.");
            }
        });
        mainPanel.add(btnSave);

        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(320, 480, 130, 40);
        btnCancel.addActionListener(e -> dispose());
        mainPanel.add(btnCancel);

        add(mainPanel);
    }

    private JTextField createField(JPanel panel, String labelText, int y) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setBounds(50, y, 120, 30);
        panel.add(label);

        JTextField field = new JTextField();
        field.setBounds(180, y, 380, 30);
        panel.add(field);
        return field;
    }

    private void uploadPhoto(JLabel previewLabel) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            selectedImagePath = file.getAbsolutePath();
            ImageIcon icon = new ImageIcon(selectedImagePath);
            Image scaled = icon.getImage().getScaledInstance(150, 110, Image.SCALE_SMOOTH);
            previewLabel.setIcon(new ImageIcon(scaled));
            previewLabel.setText("");
        }
    }

    private void saveCite(String name, String desc, String addr, String cont, double price, int cap) {
        if (name.isEmpty() || addr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Address are required!");
            return;
        }
        
        String finalDesc = desc.isEmpty() ? "No description provided." : desc;
        Place p = new Place(0, name, finalDesc, addr, Session.getCurrentUser(), "General", true, true, cont, price, cap);
        
        if (DatabaseHelper.addPlace(p)) {
            if (!selectedImagePath.isEmpty()) ImageManager.savePlaceImage(selectedImagePath, name);
            JOptionPane.showMessageDialog(this, "Cite added successfully!");
            dispose();
        }
    }
}