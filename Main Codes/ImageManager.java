package kyusiyusphere;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ImageManager {
    // This folder will be created automatically in your project folder
    private static final String UPLOAD_DIR = "AppData_Images/";

    public static void init() {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs(); // Creates the folder if it doesn't exist
        }
    }

    // Copies the uploaded facility photo and renames it to match the Place Name
    public static void savePlaceImage(String sourcePath, String placeName) {
        if (sourcePath == null || sourcePath.isEmpty()) return;
        init();
        try {
            File source = new File(sourcePath);
            // We force it to be a .jpg and strip spaces so it's easy to look up later
            String safeName = placeName.toLowerCase().replace(" ", "") + ".jpg";
            File dest = new File(UPLOAD_DIR + safeName);
            
            Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Error saving facility image: " + e.getMessage());
        }
    }

    // Loads the facility photo from our local folder
    public static ImageIcon loadPlaceImage(String placeName, int width, int height) {
        String safeName = placeName.toLowerCase().replace(" ", "") + ".jpg";
        File file = new File(UPLOAD_DIR + safeName);
        
        if (file.exists()) {
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }
        return null; // Return null if the owner didn't upload a picture
    }
    
    // Copies the uploaded profile picture
    // 📍 THE FIX: Changed 'void' to 'String' so it returns the path back to the registration screen
    public static String saveProfilePicture(String sourcePath, String username) {
         if (sourcePath == null || sourcePath.isEmpty()) return null;
         init();
         try {
             File source = new File(sourcePath);
             String safeName = "user_" + username.toLowerCase().replace(" ", "") + ".jpg";
             File dest = new File(UPLOAD_DIR + safeName);
             
             Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
             
             return dest.getAbsolutePath(); // <-- This is the magic line we needed!
             
         } catch(Exception e) {
             System.err.println("Error saving profile picture: " + e.getMessage());
             return null;
         }
    }
}