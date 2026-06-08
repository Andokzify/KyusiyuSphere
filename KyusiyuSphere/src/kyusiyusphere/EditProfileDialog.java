package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditProfileDialog extends JDialog {
    private final Color bgLight = new Color(210, 180, 140);
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);

    public EditProfileDialog(JDialog parent, UserAccount user) {
        super(parent, "Edit Profile", true);
        setSize(350, 340);
        setLocationRelativeTo(parent);
        setUndecorated(true);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(bgLight);
        main.setBorder(BorderFactory.createLineBorder(borderBlack, 4));

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(borderBlack);
        header.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JLabel title = new JLabel(" EDIT PROFILE", JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Courier New", Font.BOLD, 14));
        header.add(title, BorderLayout.CENTER);

        // --- INPUT FIELDS ---
        JPanel content = new JPanel(new GridLayout(6, 1, 5, 5));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JTextField txtName = new JTextField(user.name);
        txtName.setFont(new Font("Courier New", Font.BOLD, 14));
        txtName.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        
        JTextField txtCampus = new JTextField(user.campus.equals("N/A") ? "" : user.campus);
        txtCampus.setFont(new Font("Courier New", Font.BOLD, 14));
        txtCampus.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderBlack, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        
        // PHOTO UPLOAD BUTTON
        JButton btnUpload = new JButton("📷 CHANGE PROFILE PHOTO");
        btnUpload.setFont(mainFont);
        btnUpload.setBackground(Color.WHITE);
        btnUpload.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        btnUpload.setFocusPainted(false);
        btnUpload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnUpload.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnUpload.setBackground(new Color(230, 230, 230)); }
            public void mouseExited(MouseEvent e) { btnUpload.setBackground(Color.WHITE); }
        });
        
        btnUpload.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
            
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String selectedPath = chooser.getSelectedFile().getAbsolutePath();
                
                // Save immediately to DB
                if (DatabaseHelper.updateUserPhoto(user.id, selectedPath)) {
                    user.profilePicPath = selectedPath;
                    JOptionPane.showMessageDialog(this, "Photo updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save photo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        content.add(btnUpload);
        content.add(new JLabel(" "));
        
        JLabel lblName = new JLabel("NEW NAME:");
        lblName.setFont(new Font("Courier New", Font.BOLD, 12));
        content.add(lblName);
        content.add(txtName);
        
        JLabel lblCampus = new JLabel("NEW CAMPUS / ORG:");
        lblCampus.setFont(new Font("Courier New", Font.BOLD, 12));
        content.add(lblCampus);
        content.add(txtCampus);

        // --- BUTTONS ---
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttons.setOpaque(false);

        JButton btnSave = new JButton("SAVE DETAILS");
        btnSave.setFont(new Font("Courier New", Font.BOLD, 12));
        btnSave.setBackground(Color.WHITE);
        btnSave.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        btnSave.setPreferredSize(new Dimension(130, 35));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnSave.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
                btnSave.setBackground(borderBlack); 
                btnSave.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) { 
                btnSave.setBackground(Color.WHITE); 
                btnSave.setForeground(borderBlack);
            }
        });
        
        btnSave.addActionListener(e -> {
            String newName = txtName.getText().trim();
            String newCampus = txtCampus.getText().trim();
            
            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty.");
                return;
            }
            
            if (DatabaseHelper.updateUser(user.id, newName, newCampus)) {
                user.name = newName;
                user.campus = newCampus;
                dispose();
            }
        });

        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setFont(new Font("Courier New", Font.BOLD, 12));
        btnCancel.setBackground(new Color(255, 100, 100));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        btnCancel.setPreferredSize(new Dimension(90, 35));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCancel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnCancel.setBackground(new Color(200, 50, 50)); }
            public void mouseExited(MouseEvent e) { btnCancel.setBackground(new Color(255, 100, 100)); }
        });
        
        btnCancel.addActionListener(e -> dispose());

        buttons.add(btnSave);
        buttons.add(btnCancel);

        main.add(header, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);
        main.add(buttons, BorderLayout.SOUTH);
        add(main);
    }
}