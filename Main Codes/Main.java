package kyusiyusphere;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        
        // 1. BOOT UP THE VAULT: Initialize the database before any UI loads
        System.out.println("System Booting...");
        DatabaseHelper.initializeDatabase();
        
        // 2. LAUNCH THE STOREFRONT: Safely load the very first screen
        SwingUtilities.invokeLater(() -> {
            new startingScreen().setVisible(true); 
        });
    }
}