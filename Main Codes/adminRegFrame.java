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
        JTextField[] fields = {txtName};
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
        JLabel[] errors = {lblErrName, lblErrPassword, lblErrConfirm};
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
        setError(lblErrPassword, "");
        setError(lblErrConfirm, "");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtConfirm = new javax.swing.JPasswordField();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblErrConfirm = new javax.swing.JLabel();
        lblErrPassword = new javax.swing.JLabel();
        lblErrName = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();

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

        jPanel3.setBackground(new java.awt.Color(230, 232, 240));
        jPanel3.setLayout(null);

        jPanel16.setBackground(new java.awt.Color(204, 204, 204));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel16.setLayout(null);

        jLabel6.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("NAME");
        jPanel16.add(jLabel6);
        jLabel6.setBounds(80, 30, 90, 20);

        jPanel3.add(jPanel16);
        jPanel16.setBounds(180, 230, 260, 80);

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
        jPanel3.add(txtName);
        txtName.setBounds(470, 240, 350, 60);

        txtConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConfirmActionPerformed(evt);
            }
        });
        jPanel3.add(txtConfirm);
        txtConfirm.setBounds(470, 470, 350, 60);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel6.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("C. PASSWORD");
        jPanel6.add(jLabel8);
        jLabel8.setBounds(50, 30, 170, 20);

        jPanel3.add(jPanel6);
        jPanel6.setBounds(180, 460, 260, 80);

        lblErrConfirm.setForeground(java.awt.Color.red);
        jPanel3.add(lblErrConfirm);
        lblErrConfirm.setBounds(470, 530, 260, 20);

        lblErrPassword.setForeground(java.awt.Color.red);
        jPanel3.add(lblErrPassword);
        lblErrPassword.setBounds(470, 420, 260, 20);

        lblErrName.setForeground(java.awt.Color.red);
        jPanel3.add(lblErrName);
        lblErrName.setBounds(470, 300, 260, 20);

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
        jPanel3.add(btnRegister);
        btnRegister.setBounds(370, 600, 220, 40);

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
        jPanel3.add(jButton3);
        jButton3.setBounds(400, 650, 150, 40);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel4.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("PASSWORD");
        jPanel4.add(jLabel7);
        jLabel7.setBounds(70, 30, 120, 20);

        jPanel3.add(jPanel4);
        jPanel4.setBounds(180, 350, 260, 80);
        jPanel3.add(txtPassword);
        txtPassword.setBounds(470, 360, 350, 60);

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

        String name = txtName.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirm = new String(txtConfirm.getPassword());

        boolean hasError = false;

        if (name.isEmpty()) {
            setError(lblErrName, "! Name is required.");
            hasError = true;
        }
        if (password.isEmpty() || password.length() < 8) {
            setError(lblErrPassword, "! Password must be at least 8 characters.");
            hasError = true;
        }
        if (!password.equals(confirm)) {
            setError(lblErrConfirm, "! Passwords do not match.");
            hasError = true;
        }

        if (hasError) return;

        // Save Admin (role = 3)
        boolean success = DatabaseHelper.saveUser(name, name + "@admin.com", password, 3, "", "");  // email is auto-generated for simplicity

        if (success) {
            JOptionPane.showMessageDialog(this,
                "Admin Registered Successfully!\nYou can now login as Administrator.",
                "Success", JOptionPane.INFORMATION_MESSAGE);

            dispose();
            new loginFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
        }
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblErrConfirm;
    private javax.swing.JLabel lblErrName;
    private javax.swing.JLabel lblErrPassword;
    private javax.swing.JPasswordField txtConfirm;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
