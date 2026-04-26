package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class adminRegFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(adminRegFrame.class.getName());

    public adminRegFrame() {
        initComponents();
        styleFormFields();
        styleErrorLabels();
        addHoverEffect();
    }
    
    private void styleFormFields() {
        JTextField[] fields = {txtName, txtEmail, txtContact};
        JPasswordField[] passFields = {txtPassword, txtConfirm};

        for (JTextField f : fields) {
            f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            f.setBackground(new Color(245, 246, 252));
        }
        for (JPasswordField p : passFields) {
            p.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            p.setBackground(new Color(245, 246, 252));
        }
    }

    private void styleErrorLabels() {
        JLabel[] errors = {lblErrName, lblErrEmail, lblErrPassword, lblErrConfirm, lblErrContact};
        for (JLabel lbl : errors) {
            lbl.setForeground(new Color(200, 30, 30));
            lbl.setFont(new Font("Segoe UI", Font.ITALIC, 11));
            lbl.setText("");
        }
    }

    private void addHoverEffect() {
        btnRegister.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnRegister.setBackground(new Color(80, 110, 200));
            }
            public void mouseExited(MouseEvent e) {
                btnRegister.setBackground(new Color(30, 45, 100));
            }
        });
    }

    private void setError(JLabel errorLabel, String message) {
        errorLabel.setText(message);
    }
    
    private void clearAllErrors() {
        setError(lblErrName, "");
        setError(lblErrEmail, "");
        setError(lblErrPassword, "");
        setError(lblErrConfirm, "");
        setError(lblErrContact, "");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        txtPassword = new javax.swing.JPasswordField();
        txtEmail = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtContact = new javax.swing.JTextField();
        lblErrEmail = new javax.swing.JLabel();
        lblErrName = new javax.swing.JLabel();
        lblErrPassword = new javax.swing.JLabel();
        lblErrConfirm = new javax.swing.JLabel();
        lblErrContact = new javax.swing.JLabel();
        txtConfirm = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(null);

        jButton2.setFont(new java.awt.Font("Segoe UI Emoji", 0, 19)); // NOI18N
        jButton2.setText("👤");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);
        jButton2.setBounds(10, 30, 60, 50);

        jButton1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 19)); // NOI18N
        jButton1.setText("✏");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(10, 100, 60, 50);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 80, 740);

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
        jPanel8.setBounds(130, 570, 170, 40);

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
        txtEmail.setBounds(320, 570, 330, 40);

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

        lblErrEmail.setForeground(java.awt.Color.red);
        getContentPane().add(lblErrEmail);
        lblErrEmail.setBounds(330, 610, 210, 30);

        lblErrName.setForeground(java.awt.Color.red);
        getContentPane().add(lblErrName);
        lblErrName.setBounds(330, 336, 170, 20);

        lblErrPassword.setForeground(java.awt.Color.red);
        getContentPane().add(lblErrPassword);
        lblErrPassword.setBounds(330, 400, 260, 30);

        lblErrConfirm.setForeground(java.awt.Color.red);
        getContentPane().add(lblErrConfirm);
        lblErrConfirm.setBounds(330, 470, 220, 30);

        lblErrContact.setForeground(java.awt.Color.red);
        getContentPane().add(lblErrContact);
        lblErrContact.setBounds(330, 540, 190, 30);

        txtConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConfirmActionPerformed(evt);
            }
        });
        getContentPane().add(txtConfirm);
        txtConfirm.setBounds(320, 430, 330, 40);

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

        jButton3.setBackground(new java.awt.Color(78, 80, 102));
        jButton3.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("← BACK");
        jButton3.setBorder(new javax.swing.border.MatteBorder(null));
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(790, 650, 150, 40);

        jPanel3.setBackground(new java.awt.Color(230, 232, 240));
        jPanel3.setLayout(null);
        getContentPane().add(jPanel3);
        jPanel3.setBounds(80, 0, 990, 740);

        setSize(new java.awt.Dimension(1080, 780));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JOptionPane.showMessageDialog(this, "You are already on the Login screen.");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        new registrationSelect().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained
        txtEmail.setBackground(new java.awt.Color(245, 246, 252));
        txtEmail.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 160, 195), 1),
            javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        txtEmail.setBackground(new java.awt.Color(245, 246, 252));
        txtEmail.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 160, 195), 1),
            javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyTyped
        setError(lblErrEmail, ""); // clears error as they type
    }//GEN-LAST:event_txtEmailKeyTyped

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

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyTyped
        setError(lblErrName, ""); // clears error as they type
    }//GEN-LAST:event_txtNameKeyTyped

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

    private void txtContactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactKeyTyped
        setError(lblErrContact, ""); // clears error as they type
    }//GEN-LAST:event_txtContactKeyTyped

    private void txtConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConfirmActionPerformed
        setError(lblErrConfirm, "");
    }//GEN-LAST:event_txtConfirmActionPerformed

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

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        clearAllErrors();

        String name     = txtName.getText().trim();
        String email    = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirm  = new String(txtConfirm.getPassword());
        String contact  = txtContact.getText().trim();

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

        if (hasError) return;

        // Register as ADMIN (role = 3)
        KyusiyuSphere.registerFromGUI(name, email, 0, password, 3, contact, "");

        JOptionPane.showMessageDialog(this, 
            "Admin Registered Successfully!\nYou can now login as Administrator.", 
            "Success", JOptionPane.INFORMATION_MESSAGE);

        dispose();
        new loginFrame().setVisible(true);
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
        new registrationSelect().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
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
        java.awt.EventQueue.invokeLater(() -> new adminRegFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegister;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblErrConfirm;
    private javax.swing.JLabel lblErrContact;
    private javax.swing.JLabel lblErrEmail;
    private javax.swing.JLabel lblErrName;
    private javax.swing.JLabel lblErrPassword;
    private javax.swing.JPasswordField txtConfirm;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
