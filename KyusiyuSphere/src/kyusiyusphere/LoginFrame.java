package kyusiyusphere;

import javax.swing.JOptionPane;

public class LoginFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginFrame.class.getName());

   public LoginFrame() {
        initComponents();
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        txtUsername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("KyusiyuSphere | Login");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(102, 255, 0));
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(null);

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

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 80, 740);

        jPanel1.setBackground(new java.awt.Color(230, 232, 240));
        jPanel1.setLayout(null);

        jPanel3.setBackground(java.awt.Color.white);
        jPanel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(70, 70, 90), 2)));
        jPanel3.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Courier New", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(15, 35, 100));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("LOGIN");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(160, 30, 110, 37);

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(230, 224, 224));
        jTextField2.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("PASSWORD");
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jTextField2.setFocusable(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField2);
        jTextField2.setBounds(30, 150, 140, 40);

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(230, 224, 224));
        jTextField3.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setText("USERNAME");
        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jTextField3.setFocusable(false);
        jPanel3.add(jTextField3);
        jTextField3.setBounds(30, 90, 140, 40);

        jButton3.setBackground(new java.awt.Color(30, 45, 100));
        jButton3.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("LOGIN");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);
        jButton3.setBounds(120, 220, 190, 40);

        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("No account? Register here!");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel4);
        jLabel4.setBounds(110, 266, 210, 30);

        txtPass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassFocusLost(evt);
            }
        });
        jPanel3.add(txtPass);
        txtPass.setBounds(180, 150, 210, 40);

        txtUsername.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsernameFocusLost(evt);
            }
        });
        jPanel3.add(txtUsername);
        txtUsername.setBounds(180, 90, 210, 40);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(290, 250, 420, 330);

        jLabel1.setFont(new java.awt.Font("Impact", 0, 42)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 35, 100));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("KyusiyuSphere");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(370, 140, 270, 60);

        jLabel2.setBackground(new java.awt.Color(80, 85, 130));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(80, 85, 130));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("QCU Facility Locator and Reservation System");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(380, 200, 250, 18);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/REGISTRATION COMPLETE.gif"))); // NOI18N
        jPanel1.add(jLabel5);
        jLabel5.setBounds(0, 0, 990, 740);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(80, 0, 990, 740);

        setSize(new java.awt.Dimension(1080, 780));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      dispose();
        new RegistrationSelect().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JOptionPane.showMessageDialog(this, "You are already on the Login screen.");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       String username = txtUsername.getText().trim();
        String password = new String(txtPass.getPassword());

        // 1. Check for empty fields
        if (username.isEmpty() || password.isEmpty()) {
            new ErrorDialog(this, "FIELDS EMPTY.<br>Please enter both username and password.").setVisible(true);
            return;
        }

        // 2. Use DatabaseHelper to fetch the real user from SQLite
        UserAccount loggedInUser = DatabaseHelper.loginUser(username, password);

        // 3. Handle Login Results
        if (loggedInUser != null) {
            // Establish the global session
            Session.login(loggedInUser);

            // Show success animation/dialog
            new SuccessDialog(this, loggedInUser.name).setVisible(true);

            this.dispose(); // Close login window

            // 4. Route to the correct dashboard via KyusiyuSphere launchers
            if (loggedInUser.role == 1) {
                KyusiyuSphere.userMenu(loggedInUser); // Launches Student Explorer
            } else if (loggedInUser.role == 2) {
                KyusiyuSphere.ownerMenu(loggedInUser); // Launches Owner Dashboard
            } else if (loggedInUser.role == 3) {
                KyusiyuSphere.adminMenu(loggedInUser); // Launches Admin Dashboard
            }
        } else {
            // Failed login: Access Denied
            new ErrorDialog(this, "ACCESS DENIED.<br>Invalid username or password.").setVisible(true);
            txtPass.setText(""); 
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
     dispose();
        new RegistrationSelect().setVisible(true);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void txtUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusGained
        txtUsername.setBackground(new java.awt.Color(232, 236, 255));
        txtUsername.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 90, 200), 2),
        javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtUsernameFocusGained

    private void txtUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusLost
        txtUsername.setBackground(new java.awt.Color(245, 246, 252));
        txtUsername.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 160, 195), 1),
        javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtUsernameFocusLost

    private void txtPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassFocusGained
        txtPass.setBackground(new java.awt.Color(232, 236, 255));
        txtPass.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 90, 200), 2),
        javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtPassFocusGained

    private void txtPassFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassFocusLost
        txtPass.setBackground(new java.awt.Color(245, 246, 252));
        txtPass.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 160, 195), 1),
        javax.swing.BorderFactory.createEmptyBorder(3, 8, 3, 8)));
    }//GEN-LAST:event_txtPassFocusLost

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
        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
