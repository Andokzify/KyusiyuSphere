package kyusiyusphere;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    
    private static final String DB_URL = "jdbc:sqlite:kyusiyusphere.db";
    
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = connect()) {
            Statement stmt = conn.createStatement();

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    role INTEGER NOT NULL,
                    campus TEXT,
                    profile_pic_path TEXT
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS places (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    description TEXT,
                    location TEXT,
                    label TEXT,
                    reservation_available BOOLEAN DEFAULT 1,
                    walkin_available BOOLEAN DEFAULT 1,
                    contact TEXT,
                    price REAL, 
                    max_reservations INTEGER DEFAULT 5,
                    image_path TEXT,
                    owner_id INTEGER
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS reservations (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER,
                    place_id INTEGER,
                    date TEXT,
                    start_time TEXT,
                    end_time TEXT,
                    status TEXT DEFAULT 'Pending'
                )
            """);
            
            stmt.execute("CREATE TABLE IF NOT EXISTS reviews (id INTEGER PRIMARY KEY AUTOINCREMENT, place_id INTEGER, user_name TEXT, rating TEXT, comment TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS favorites (user_id INTEGER, place_id INTEGER, PRIMARY KEY (user_id, place_id))");
            
            System.out.println("✅ Database synced!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void wipeAndReset() {
    // List of tables in reverse order of dependency to avoid foreign key errors
    String[] tables = {"favorites", "reviews", "reservations", "places", "users"};
    
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
        
        // Disable foreign keys temporarily to avoid deletion conflicts
        stmt.execute("PRAGMA foreign_keys = OFF;"); 
        
        for (String table : tables) {
            stmt.execute("DROP TABLE IF EXISTS " + table);
        }
        
        System.out.println("System Reset: Database Wiped and Re-initialized.");
        initializeDatabase(); // Re-creates the tables fresh
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // ================= USER METHODS =================
    public static boolean saveUser(String name, String email, String password, int role, String campus) {
        // 📍 1. Intercept and hash the password!
        String securePassword = hashPassword(password); 

        String sql = "INSERT INTO users(name, email, password, role, campus) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, securePassword); // 📍 2. Save the encrypted hash here!
            pstmt.setInt(4, role);
            pstmt.setString(5, campus); 

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
            return false;
        }
    }

// Method 1: For Name and Campus updates
    public static boolean updateUser(int id, String newName, String newCampus) {
        String sql = "UPDATE users SET name = ?, campus = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newName);
            pstmt.setString(2, newCampus);
            pstmt.setInt(3, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method 2: Specifically for the Profile Picture path
    public static boolean updateUserPhoto(int id, String path) {
        String sql = "UPDATE users SET profile_pic_path = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, path);
            pstmt.setInt(2, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static String checkDuplicateUser(String name, String email) {
        String sql = "SELECT name, email FROM users WHERE name = ? OR email = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // If a match is found, figure out which one it was.
                if (rs.getString("name").equalsIgnoreCase(name)) return "NAME";
                if (rs.getString("email").equalsIgnoreCase(email)) return "EMAIL";
            }
        } catch (SQLException e) {
            System.out.println("Duplicate Check Error: " + e.getMessage());
            return "ERROR";
        }
        return "NONE"; // Clear to proceed.
    }
    
    public static UserAccount loginUser(String username, String password) {
        // 📍 1. Hash the typed password so it matches the database format.
        String hashedInput = hashPassword(password);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                 "SELECT * FROM users WHERE name = ? AND password = ?")) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, hashedInput); // 📍 2. Compare using the hashed input.
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new UserAccount(
                    rs.getInt("id"), 
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getInt("role"),
                    rs.getString("campus"),
                    rs.getString("profile_pic_path")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Login failed
    }

    public static java.util.List<UserAccount> getAllUsers() {
        java.util.List<UserAccount> list = new java.util.ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            
            while (rs.next()) {
                list.add(new UserAccount(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getInt("role"),
                    rs.getString("campus"),
                    rs.getString("profile_pic_path")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= PLACE METHODS =================
    
    public static List<Place> getAllPlaces() {
        List<Place> list = new ArrayList<>();
        String sql = "SELECT p.*, u.name AS owner_name, u.email AS owner_email, u.role AS owner_role " +
                     "FROM places p LEFT JOIN users u ON p.owner_id = u.id";
                     
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UserAccount owner = null;
                if (rs.getInt("owner_id") != 0) {
                    owner = new UserAccount(rs.getInt("owner_id"), rs.getString("owner_name"), 
                        rs.getString("owner_email"), "", rs.getInt("owner_role"), "N/A", null);
                }

                Place place = new Place(
                    rs.getInt("id"), rs.getString("name"), rs.getString("description"),
                    rs.getString("location"), owner, rs.getString("label"),
                    rs.getBoolean("reservation_available"), rs.getBoolean("walkin_available"),
                    rs.getString("contact"), 
                    rs.getDouble("price"), 
                    rs.getInt("max_reservations") 
                );
                list.add(place);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public static boolean deletePlaceById(int placeId) {
        String sql = "DELETE FROM places WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, placeId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) { 
            return false; 
        }
    }

public static boolean updatePlaceDetails(int placeId, String newDesc, double newPrice, int newMaxCap, String newContact) {
    String sql = "UPDATE places SET description = ?, price = ?, max_reservations = ?, contact = ? WHERE id = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, newDesc);
        pstmt.setDouble(2, newPrice);
        pstmt.setInt(3, newMaxCap);
        pstmt.setString(4, newContact);
        pstmt.setInt(5, placeId);
        
        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) { 
        e.printStackTrace();
        return false; 
    }
}

    public static boolean addPlace(Place place) {
        String sql = "INSERT INTO places (name, description, location, label, reservation_available, walkin_available, contact, price, max_reservations, owner_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, place.name);
            pstmt.setString(2, place.description);
            pstmt.setString(3, place.location);
            pstmt.setString(4, place.label);
            pstmt.setBoolean(5, true);
            pstmt.setBoolean(6, true);
            pstmt.setString(7, place.contact);
            pstmt.setDouble(8, place.price);
            pstmt.setInt(9, place.maxReservations);
            pstmt.setInt(10, place.owner != null ? place.owner.id : 0);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ================= RESERVATION METHODS =================
   
    public static boolean createReservation(int userId, int placeId, String date, String time) {
    String sql = "INSERT INTO reservations (user_id, place_id, date, start_time, status) VALUES (?, ?, ?, ?, 'Pending')";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, userId);
        pstmt.setInt(2, placeId);
        pstmt.setString(3, date);
        pstmt.setString(4, time); 
        pstmt.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public static java.util.List<String> getUserReservations(int userId) {
    java.util.List<String> list = new java.util.ArrayList<>();
    // 📍 FIX: Pull start_time and alias it as time
    String sql = "SELECT r.date, r.start_time AS time, r.status, p.name AS place_name " +
                 "FROM reservations r JOIN places p ON r.place_id = p.id " +
                 "WHERE r.user_id = ?";
                 
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            String detail = String.format("%s - %s at %s (%s)", 
                rs.getString("place_name"),
                rs.getString("date"),
                rs.getString("time"), 
                rs.getString("status")
            );
            list.add(detail);
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return list;
}
    
    public static java.util.List<Object[]> getOwnerReservations(int ownerId) {
    java.util.List<Object[]> list = new java.util.ArrayList<>();
    String sql = "SELECT u.name AS student_name, r.date, r.start_time AS time, p.name AS place_name, r.status " +
                 "FROM reservations r " +
                 "JOIN places p ON r.place_id = p.id " +
                 "LEFT JOIN users u ON r.user_id = u.id " + 
                 "WHERE p.owner_id = ?";
                 
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, ownerId);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            String studentName = rs.getString("student_name");
            if (studentName == null) studentName = "Unknown Student";
            
            list.add(new Object[]{
                studentName,
                rs.getString("date"),
                rs.getString("time"),
                rs.getString("place_name"),
                rs.getString("status")
            });
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return list;
}
    
    public static double getOwnerAverageRating(int ownerId) {
        String sql = "SELECT AVG(CAST(rating AS REAL)) FROM reviews r JOIN places p ON r.place_id = p.id WHERE p.owner_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0.0;
    }
    
    // ================= OWNER MANAGEMENT METHODS =================
    public static boolean updateReservationStatus(String placeName, String date, String time, String newStatus) {
    // 📍 FIX: Changed 'time = ?' to 'start_time = ?'
    String sql = "UPDATE reservations SET status = ? " +
                 "WHERE date = ? AND start_time = ? AND " +
                 "place_id = (SELECT id FROM places WHERE name = ?)";
    
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
         
        pstmt.setString(1, newStatus);
        pstmt.setString(2, date);
        pstmt.setString(3, time);
        pstmt.setString(4, placeName);
        
        return pstmt.executeUpdate() > 0;
        
    } catch (SQLException e) {
        System.err.println("Error updating status: " + e.getMessage());
        return false;
    }
}
    
   public static String checkBookingAvailability(int placeId, String date, String time) {
        // 1. Fetch the maximum capacity for this specific place
        int maxCapacity = 5; // Default fallback
        String capQuery = "SELECT max_reservations FROM places WHERE id = ?";
        try (java.sql.Connection conn = connect(); 
             java.sql.PreparedStatement pstmt = conn.prepareStatement(capQuery)) {
            pstmt.setInt(1, placeId);
            java.sql.ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                maxCapacity = rs.getInt("max_reservations");
            }
        } catch (java.sql.SQLException e) { e.printStackTrace(); }

        // 2. Count how many 'Approved' reservations exist for this exact date and time
        String countQuery = "SELECT COUNT(*) FROM reservations WHERE place_id = ? AND date = ? AND start_time = ? AND status = 'Approved'";
        try (java.sql.Connection conn = connect(); 
             java.sql.PreparedStatement pstmt = conn.prepareStatement(countQuery)) {
            pstmt.setInt(1, placeId);
            pstmt.setString(2, date);
            pstmt.setString(3, time);
            java.sql.ResultSet rs = pstmt.executeQuery();
            
            // If current approved slots are equal to or greater than the limit, reject it
            if (rs.next() && rs.getInt(1) >= maxCapacity) {
                return "FULL";
            }
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
        
        return "AVAILABLE";
    }
    
    // ================= REVIEW METHODS =================
    public static boolean addReview(int placeId, String userName, String rating, String comment) {
        String sql = "INSERT INTO reviews (place_id, user_name, rating, comment) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, placeId);
            pstmt.setString(2, userName);
            pstmt.setString(3, rating);
            pstmt.setString(4, comment);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String[]> getOwnerReviews(int ownerId) {
        List<String[]> list = new ArrayList<>();
        // Fetch reviews by joining the places table to verify ownership
        String sql = "SELECT r.user_name, p.name AS place_name, r.rating, r.comment " +
                     "FROM reviews r " +
                     "JOIN places p ON r.place_id = p.id " +
                     "WHERE p.owner_id = ? " +
                     "ORDER BY r.id DESC"; // Newest first
                     
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                // Extract the rating string (e.g., "5.0 ★★★★★") and split it just to get the stars
                String fullRating = rs.getString("rating");
                String starsOnly = fullRating;
                if (fullRating.contains(" ")) {
                    starsOnly = fullRating.split(" ")[1]; 
                }

                list.add(new String[]{
                    rs.getString("user_name"),
                    rs.getString("place_name"),
                    starsOnly,
                    rs.getString("comment")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static boolean deleteUser(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) { return false; }
    }

    public static boolean deletePlace(String placeName) {
        String sql = "DELETE FROM places WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, placeName);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) { return false; }
    }
    
    // ================= ADMIN MANAGEMENT METHODS =================
    
    public static boolean deleteUserByEmail(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) { 
            return false; 
        }
    }

    public static boolean deletePlaceByName(String placeName) {
        String sql = "DELETE FROM places WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, placeName);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) { 
            return false; 
        }
    }
    
    // ================= FAVORITES METHODS =================
    
    public static boolean isFavorite(int userId, int placeId) {
        String sql = "SELECT 1 FROM favorites WHERE user_id = ? AND place_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, placeId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) { return false; }
    }

    public static void toggleFavorite(int userId, int placeId) {
        if (isFavorite(userId, placeId)) {
            // If it's already a favorite, remove it
            String sql = "DELETE FROM favorites WHERE user_id = ? AND place_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, placeId);
                pstmt.executeUpdate();
            } catch (SQLException e) { e.printStackTrace(); }
        } else {
            // If it's not a favorite, add it
            String sql = "INSERT INTO favorites (user_id, place_id) VALUES (?, ?)";
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, placeId);
                pstmt.executeUpdate();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    
    // ================= STUDENT REVIEWS FETCH =================
    public static java.util.List<String[]> getReviewsForPlace(int placeId) {
        java.util.List<String[]> list = new java.util.ArrayList<>();
        // Fetch reviews, newest first
        String sql = "SELECT user_name, rating, comment FROM reviews WHERE place_id = ? ORDER BY id DESC LIMIT 4";
                     
        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(DB_URL);
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, placeId);
            java.sql.ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("rating"),
                    rs.getString("user_name"),
                    rs.getString("comment")
                });
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // ================= LIVE AVAILABILITY CHECKERS =================
    
    public static int getPlaceMaxCapacity(int placeId) {
        String sql = "SELECT max_reservations FROM places WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, placeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 5; // Default fallback
    }

    public static java.util.List<String> getOccupiedSlotsForDate(int placeId, String date) {
        java.util.List<String> list = new java.util.ArrayList<>();
        // Query to get count of APPROVED reservations grouped by time slot
        String sql = "SELECT start_time, COUNT(*) as count FROM reservations " +
                     "WHERE place_id = ? AND date = ? AND status = 'Approved' " +
                     "GROUP BY start_time ORDER BY start_time";
        
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, placeId);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String time = rs.getString("start_time");
                int count = rs.getInt("count");
                list.add(time + "  |  [" + count + " booked]");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    // 📍 THE ENCRYPTION ENGINE
    public static String hashPassword(String password) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) { 
            e.printStackTrace();
            return password;
        }
    }
    
    // ================= ADMIN DASHBOARD METRICS =================
    
    public static int getTotalPopulation() {
        // Counts all Students (1) and Owners (2), ignores Admins (3)
        String sql = "SELECT COUNT(*) FROM users WHERE role IN (1, 2)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static int getTotalFacilities() {
        String sql = "SELECT COUNT(*) FROM places";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static int getBookingVolume() {
        String sql = "SELECT COUNT(*) FROM reservations WHERE status = 'Approved'";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }   
}