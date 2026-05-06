package kyusiyusphere;

public class startingScreen extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(startingScreen.class.getName());

    public startingScreen() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        register = new javax.swing.JButton();
        signIn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("KYUSIYUSPHERE");
        getContentPane().setLayout(null);

        register.setBackground(new java.awt.Color(255, 255, 255));
        register.setFont(new java.awt.Font("Courier New", 1, 30)); // NOI18N
        register.setText("REGISTER");
        register.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        register.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        register.setFocusPainted(false);
        register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerMouseExited(evt);
            }
        });
        register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerActionPerformed(evt);
            }
        });
        getContentPane().add(register);
        register.setBounds(590, 630, 210, 80);

        signIn.setBackground(new java.awt.Color(255, 255, 255));
        signIn.setFont(new java.awt.Font("Courier New", 1, 30)); // NOI18N
        signIn.setText("SIGN IN");
        signIn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        signIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signIn.setFocusPainted(false);
        signIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signInMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signInMouseExited(evt);
            }
        });
        signIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signInActionPerformed(evt);
            }
        });
        getContentPane().add(signIn);
        signIn.setBounds(290, 630, 210, 80);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/sphere.gif"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1080, 770);

        setSize(new java.awt.Dimension(1094, 806));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void signInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signInActionPerformed
      dispose();
        new loginFrame().setVisible(true); 
    }//GEN-LAST:event_signInActionPerformed

    private void registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerActionPerformed
      dispose();
        new registrationSelect().setVisible(true);
    }//GEN-LAST:event_registerActionPerformed

    private void signInMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInMouseEntered
        signIn.setBackground(new java.awt.Color(220, 220, 220));
    }//GEN-LAST:event_signInMouseEntered

    private void signInMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInMouseExited
        signIn.setBackground(java.awt.Color.WHITE);
    }//GEN-LAST:event_signInMouseExited

    private void registerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseEntered
        register.setBackground(new java.awt.Color(220, 220, 220));
    }//GEN-LAST:event_registerMouseEntered

    private void registerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseExited
        register.setBackground(java.awt.Color.WHITE);
    }//GEN-LAST:event_registerMouseExited

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
        java.awt.EventQueue.invokeLater(() -> new startingScreen().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton register;
    private javax.swing.JButton signIn;
    // End of variables declaration//GEN-END:variables
}
