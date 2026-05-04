package kyusiyusphere;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class studentRegFrame extends javax.swing.JFrame {
    
    private String selectedImagePath = ""; // stores the chosen photo path
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(studentRegFrame.class.getName());

public studentRegFrame() {
        initComponents();
        styleFormFields();
        styleErrorLabels();
    }

    private void styleFormFields() {
        // Your existing styling (keep as is)
        javax.swing.JComponent[] fields = {txtName, txtPassword, txtConfirm, txtContact, txtStudentNo, txtEmail};
        for (javax.swing.JComponent field : fields) {
            field.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
            field.setBackground(new java.awt.Color(245, 246, 252));
            field.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 160, 195), 1),
                javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)
            ));
        }
    }

    private void styleErrorLabels() {
        javax.swing.JLabel[] errorLabels = {lblErrName, lblErrPassword, lblErrConfirm, lblErrContact, lblErrStudentNo, lblErrEmail};
        for (javax.swing.JLabel lbl : errorLabels) {
            lbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.ITALIC, 11));
            lbl.setForeground(new java.awt.Color(200, 30, 30));
            lbl.setText("");
        }
    }

    private void setError(javax.swing.JLabel errorLabel, String message) {
        errorLabel.setText(message);
    }
    private void clearAllErrors() {
        setError(lblErrName, "");
        setError(lblErrPassword, "");
        setError(lblErrConfirm, "");
        setError(lblErrContact, "");
        setError(lblErrStudentNo, "");
        setError(lblErrEmail, "");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        btnRegister = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lblPhotoPreview = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtEmail = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtContact = new javax.swing.JTextField();
        txtStudentNo = new javax.swing.JTextField();
        lblErrEmail = new javax.swing.JLabel();
        lblErrName = new javax.swing.JLabel();
        lblErrPassword = new javax.swing.JLabel();
        lblErrConfirm = new javax.swing.JLabel();
        lblErrContact = new javax.swing.JLabel();
        lblErrStudentNo = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtConfirm = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("KyusiSphere");
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(230, 232, 240));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Courier New", 1, 40)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRATION");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(60, 30, 360, 50);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(330, 50, 470, 110);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel4.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("PASSWORD");
        jPanel4.add(jLabel7);
        jLabel7.setBounds(40, 10, 90, 20);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(130, 360, 170, 40);

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel5.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("STUDENT NO.");
        jPanel5.add(jLabel10);
        jLabel10.setBounds(10, 10, 150, 20);

        getContentPane().add(jPanel5);
        jPanel5.setBounds(130, 570, 170, 40);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel6.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("C. PASSWORD");
        jPanel6.add(jLabel8);
        jLabel8.setBounds(10, 10, 150, 20);

        getContentPane().add(jPanel6);
        jPanel6.setBounds(130, 430, 170, 40);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel7.setLayout(null);

        jLabel9.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("CONTACT NO.");
        jPanel7.add(jLabel9);
        jLabel9.setBounds(10, 10, 150, 20);

        getContentPane().add(jPanel7);
        jPanel7.setBounds(130, 500, 170, 40);

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel8.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("EMAIL");
        jPanel8.add(jLabel11);
        jLabel11.setBounds(10, 10, 150, 20);

        getContentPane().add(jPanel8);
        jPanel8.setBounds(130, 640, 170, 40);

        jPanel15.setBackground(new java.awt.Color(204, 204, 204));
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel15.setLayout(null);
        getContentPane().add(jPanel15);
        jPanel15.setBounds(130, 640, 170, 40);

        jPanel23.setBackground(new java.awt.Color(230, 232, 240));
        jPanel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel23.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Upload your Picture");
        jPanel23.add(jLabel4);
        jLabel4.setBounds(30, -10, 230, 60);

        getContentPane().add(jPanel23);
        jPanel23.setBounds(720, 280, 290, 40);

        jPanel22.setBackground(new java.awt.Color(230, 232, 240));
        jPanel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel22.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("STUDENT");
        jPanel22.add(jLabel3);
        jLabel3.setBounds(50, 0, 200, 50);

        getContentPane().add(jPanel22);
        jPanel22.setBounds(420, 180, 290, 50);

        jPanel16.setBackground(new java.awt.Color(204, 204, 204));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel16.setLayout(null);

        jLabel6.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("NAME");
        jPanel16.add(jLabel6);
        jLabel6.setBounds(40, 10, 90, 20);

        getContentPane().add(jPanel16);
        jPanel16.setBounds(130, 290, 170, 40);

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel14.setLayout(null);
        getContentPane().add(jPanel14);
        jPanel14.setBounds(130, 500, 170, 40);

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel9.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Courier New", 1, 40)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("REGISTRATION");
        jPanel9.add(jLabel2);
        jLabel2.setBounds(60, 30, 360, 50);

        getContentPane().add(jPanel9);
        jPanel9.setBounds(330, 50, 470, 110);

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel13.setLayout(null);
        getContentPane().add(jPanel13);
        jPanel13.setBounds(130, 430, 170, 40);

        jPanel11.setBackground(new java.awt.Color(204, 204, 204));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel11.setLayout(null);
        getContentPane().add(jPanel11);
        jPanel11.setBounds(130, 360, 170, 40);

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel12.setLayout(null);
        getContentPane().add(jPanel12);
        jPanel12.setBounds(130, 570, 170, 40);

        btnRegister.setBackground(new java.awt.Color(30, 45, 100));
        btnRegister.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setText("REGISTER");
        btnRegister.setBorder(new javax.swing.border.MatteBorder(null));
        btnRegister.setBorderPainted(false);
        btnRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegister.setFocusPainted(false);
        btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegisterMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegisterMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnRegisterMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnRegisterMouseReleased(evt);
            }
        });
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegister);
        btnRegister.setBounds(760, 600, 220, 40);

        jButton1.setBackground(new java.awt.Color(78, 80, 102));
        jButton1.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("← BACK");
        jButton1.setBorder(new javax.swing.border.MatteBorder(null));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(790, 650, 150, 40);

        lblPhotoPreview.setBackground(new java.awt.Color(255, 255, 255));
        lblPhotoPreview.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        lblPhotoPreview.setForeground(java.awt.Color.darkGray);
        lblPhotoPreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPhotoPreview.setText("↑");
        lblPhotoPreview.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray, 2));
        lblPhotoPreview.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPhotoPreview.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPhotoPreview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPhotoPreviewMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblPhotoPreviewMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblPhotoPreviewMouseExited(evt);
            }
        });
        getContentPane().add(lblPhotoPreview);
        lblPhotoPreview.setBounds(750, 350, 230, 220);
        getContentPane().add(txtPassword);
        txtPassword.setBounds(320, 360, 330, 40);

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailKeyTyped(evt);
            }
        });
        getContentPane().add(txtEmail);
        txtEmail.setBounds(320, 640, 330, 40);

        txtName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNameKeyTyped(evt);
            }
        });
        getContentPane().add(txtName);
        txtName.setBounds(320, 290, 330, 40);

        txtContact.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContact.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContactFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtContactFocusLost(evt);
            }
        });
        txtContact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContactKeyTyped(evt);
            }
        });
        getContentPane().add(txtContact);
        txtContact.setBounds(320, 500, 330, 40);

        txtStudentNo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtStudentNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStudentNoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStudentNoFocusLost(evt);
            }
        });
        txtStudentNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStudentNoKeyTyped(evt);
            }
        });
        getContentPane().add(txtStudentNo);
        txtStudentNo.setBounds(320, 570, 330, 40);
        getContentPane().add(lblErrEmail);
        lblErrEmail.setBounds(330, 680, 210, 30);
        getContentPane().add(lblErrName);
        lblErrName.setBounds(330, 336, 170, 20);
        getContentPane().add(lblErrPassword);
        lblErrPassword.setBounds(330, 400, 260, 30);
        getContentPane().add(lblErrConfirm);
        lblErrConfirm.setBounds(330, 470, 220, 30);
        getContentPane().add(lblErrContact);
        lblErrContact.setBounds(330, 540, 190, 30);
        getContentPane().add(lblErrStudentNo);
        lblErrStudentNo.setBounds(330, 610, 200, 30);

        jButton3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 19)); // NOI18N
        jButton3.setText("✏");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(10, 100, 60, 50);

        jButton2.setFont(new java.awt.Font("Segoe UI Emoji", 0, 19)); // NOI18N
        jButton2.setText("👤");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(10, 30, 60, 50);

        txtConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConfirmActionPerformed(evt);
            }
        });
        getContentPane().add(txtConfirm);
        txtConfirm.setBounds(320, 430, 330, 40);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 80, 740);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/REGISTRATIONCOMPLETE-ezgif.com-resize.gif"))); // NOI18N
        jLabel5.setText("jLabel5");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(80, 0, 990, 740);

        setSize(new java.awt.Dimension(1080, 780));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
     clearAllErrors();

        String name       = txtName.getText().trim();
        String email      = txtEmail.getText().trim();
        String password   = new String(txtPassword.getPassword());
        String confirm    = new String(txtConfirm.getPassword());
        String contact    = txtContact.getText().trim();
        String studentNo  = txtStudentNo.getText().trim();

        boolean hasError = false;

        if (name.isEmpty())    { setError(lblErrName, "! Name is required."); hasError = true; }
        if (email.isEmpty())   { setError(lblErrEmail, "! Email is required."); hasError = true; }
        if (password.isEmpty() || password.length() < 8) {
            setError(lblErrPassword, "! Password must be at least 8 characters."); hasError = true;
        }
        if (!password.equals(confirm)) {
            setError(lblErrConfirm, "! Passwords do not match."); hasError = true;
        }
        if (contact.isEmpty()) { setError(lblErrContact, "! Contact number is required."); hasError = true; }
        if (studentNo.isEmpty()) { setError(lblErrStudentNo, "! Student number is required."); hasError = true; }

        if (hasError) return;

        // Use DatabaseHelper (role = 1 for student)
        boolean success = DatabaseHelper.saveUser(name, email, password, 1, contact, studentNo);

        if (success) {
            if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
                ImageManager.saveProfilePicture(selectedImagePath, name);
            }
            JOptionPane.showMessageDialog(this, 
                "Student Registered Successfully!\nYou can now login.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);

            dispose();
            new loginFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
        }
        
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        new registrationSelect().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void lblPhotoPreviewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPhotoPreviewMouseClicked
            // 1. Open a file chooser window
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();

    // 2. Only allow image files
    chooser.setFileFilter(
        new javax.swing.filechooser.FileNameExtensionFilter(
            "Image files (jpg, png, gif)", "jpg", "jpeg", "png", "gif"
        )
    );
    chooser.setDialogTitle("Choose your profile picture");

    // 3. If the user picked a file (didn't cancel)
    int result = chooser.showOpenDialog(this);
    if (result == javax.swing.JFileChooser.APPROVE_OPTION) {

        java.io.File selectedFile = chooser.getSelectedFile();

        // 4. Load the image and scale it to fit the label
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(
            selectedFile.getAbsolutePath()
        );
        java.awt.Image scaled = icon.getImage().getScaledInstance(
            lblPhotoPreview.getWidth(),   // fit the label's width
            lblPhotoPreview.getHeight(),  // fit the label's height
            java.awt.Image.SCALE_SMOOTH
        );

        // 5. Show the image inside the label
        lblPhotoPreview.setIcon(new javax.swing.ImageIcon(scaled));
        lblPhotoPreview.setText(""); // hide the ↑ arrow
        
        selectedImagePath = selectedFile.getAbsolutePath();
    }
    }//GEN-LAST:event_lblPhotoPreviewMouseClicked

    private void lblPhotoPreviewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPhotoPreviewMouseEntered
         if (lblPhotoPreview.getIcon() == null) { // only if no photo yet
        lblPhotoPreview.setBackground(new java.awt.Color(240, 242, 255));
    }
    }//GEN-LAST:event_lblPhotoPreviewMouseEntered

    private void lblPhotoPreviewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPhotoPreviewMouseExited
        if (lblPhotoPreview.getIcon() == null) {
        lblPhotoPreview.setBackground(java.awt.Color.WHITE);
    }
    }//GEN-LAST:event_lblPhotoPreviewMouseExited

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusGained
   txtName.setBackground(new java.awt.Color(232, 236, 255));
    txtName.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 90, 200), 2),
        javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtNameFocusGained

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost
   txtName.setBackground(new java.awt.Color(245, 246, 252));
    txtName.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 160, 195), 1),
        javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtNameFocusLost

    private void txtContactFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContactFocusGained
        txtContact.setBackground(new java.awt.Color(232, 236, 255));
    txtContact.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 90, 200), 2),
        javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtContactFocusGained

    private void txtContactFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContactFocusLost
          txtContact.setBackground(new java.awt.Color(245, 246, 252));
    txtContact.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 160, 195), 1),
        javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtContactFocusLost

    private void txtStudentNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStudentNoFocusGained
        txtStudentNo.setBackground(new java.awt.Color(232, 236, 255));
        txtStudentNo.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 90, 200), 2),
            javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtStudentNoFocusGained

    private void txtStudentNoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStudentNoFocusLost
       txtStudentNo.setBackground(new java.awt.Color(245, 246, 252));
        txtStudentNo.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 160, 195), 1),
            javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtStudentNoFocusLost

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained
        txtStudentNo.setBackground(new java.awt.Color(245, 246, 252));
    txtStudentNo.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 160, 195), 1),
        javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
         txtEmail.setBackground(new java.awt.Color(245, 246, 252));
    txtEmail.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 160, 195), 1),
        javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtEmailFocusLost

    private void btnRegisterMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegisterMouseEntered
        btnRegister.setBackground(new java.awt.Color(80, 110, 200));
    btnRegister.setCursor(
        java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btnRegisterMouseEntered

    private void btnRegisterMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegisterMouseExited
        btnRegister.setBackground(new java.awt.Color(50, 75, 160));
    }//GEN-LAST:event_btnRegisterMouseExited

    private void btnRegisterMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegisterMousePressed
        btnRegister.setBackground(new java.awt.Color(30, 50, 130));
    }//GEN-LAST:event_btnRegisterMousePressed

    private void btnRegisterMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegisterMouseReleased
       btnRegister.setBackground(new java.awt.Color(80, 110, 200));
    }//GEN-LAST:event_btnRegisterMouseReleased

    private void txtNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyTyped
        setError(lblErrName, ""); // clears error as they type
    }//GEN-LAST:event_txtNameKeyTyped

    private void txtContactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactKeyTyped
        setError(lblErrContact, ""); // clears error as they type
    }//GEN-LAST:event_txtContactKeyTyped

    private void txtStudentNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStudentNoKeyTyped
        setError(lblErrStudentNo, ""); // clears error as they type
    }//GEN-LAST:event_txtStudentNoKeyTyped

    private void txtEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyTyped
        setError(lblErrEmail, ""); // clears error as they type
    }//GEN-LAST:event_txtEmailKeyTyped

    private void txtConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConfirmActionPerformed
        setError(lblErrConfirm, "");
    }//GEN-LAST:event_txtConfirmActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
        new registrationSelect().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
        new loginFrame().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new studentRegFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegister;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblErrConfirm;
    private javax.swing.JLabel lblErrContact;
    private javax.swing.JLabel lblErrEmail;
    private javax.swing.JLabel lblErrName;
    private javax.swing.JLabel lblErrPassword;
    private javax.swing.JLabel lblErrStudentNo;
    private javax.swing.JLabel lblPhotoPreview;
    private javax.swing.JPasswordField txtConfirm;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtStudentNo;
    // End of variables declaration//GEN-END:variables
}
