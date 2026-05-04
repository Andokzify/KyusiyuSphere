package kyusiyusphere;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    
    private static final String DB_URL = "jdbc:sqlite:kyusiyusphere.db";
    
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    role INTEGER NOT NULL,
                    contact TEXT,
                    age INTEGER
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
                    price TEXT,
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
                    time TEXT,
                    status TEXT DEFAULT 'Pending'
                )
            """);
            
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS reviews (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    place_id INTEGER,
                    user_name TEXT,
                    rating TEXT,
                    comment TEXT
                )
            """);
            
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS favorites (
                    user_id INTEGER,
                    place_id INTEGER,
                    PRIMARY KEY (user_id, place_id)
                )
            """);
            
            System.out.println("✅ Database initialized successfully!");
        } catch (Exception e) {
            System.err.println("Database init error: " + e.getMessage());
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
        initializeDatabase(); // Re-creates your tables fresh
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // ================= USER METHODS =================
    
    public static boolean saveUser(String name, String email, String password, 
                                   int role, String contact, String extra) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                 "INSERT INTO users (name, email, password, role, contact) VALUES (?, ?, ?, ?, ?)")) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setInt(4, role);
            pstmt.setString(5, contact);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static UserAccount loginUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                 "SELECT * FROM users WHERE name = ? AND password = ?")) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new UserAccount(
                    rs.getInt("id"), // Pulling the real ID from the database
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("age"),
                    rs.getString("password"),
                    rs.getInt("role"),
                    rs.getString("contact")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<UserAccount> getAllUsers() {
        List<UserAccount> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            
            while (rs.next()) {
                list.add(new UserAccount(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("age"),
                    rs.getString("password"),
                    rs.getInt("role"),
                    rs.getString("contact")
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
                     
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                UserAccount owner = null;
                if (rs.getInt("owner_id") != 0) {
                    owner = new UserAccount(
                        rs.getInt("owner_id"),
                        rs.getString("owner_name"),
                        rs.getString("owner_email"),
                        0, "", rs.getInt("owner_role"), "N/A"
                    );
                }

                Place place = new Place(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("location"),
                    owner,
                    rs.getString("label"),
                    rs.getBoolean("reservation_available"),
                    rs.getBoolean("walkin_available"),
                    rs.getString("contact"),
                    rs.getString("price")
                );
                list.add(place);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static boolean updatePlaceDetails(int placeId, String newDesc, String newPrice) {
        String sql = "UPDATE places SET description = ?, price = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newDesc);
            pstmt.setString(2, newPrice);
            pstmt.setInt(3, placeId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) { 
            return false; 
        }
    }

    public static boolean addPlace(Place place) {
        String sql = "INSERT INTO places (name, description, location, label, reservation_available, walkin_available, contact, price, owner_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, place.name);
            pstmt.setString(2, place.description);
            pstmt.setString(3, place.location);
            pstmt.setString(4, place.label);
            pstmt.setBoolean(5, place.reservationAvailable);
            pstmt.setBoolean(6, place.walkinAvailable);
            pstmt.setString(7, place.contact);
            pstmt.setString(8, place.price);
            pstmt.setInt(9, place.owner != null ? place.owner.id : 0);
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= RESERVATION METHODS =================
    
    public static boolean createReservation(int userId, int placeId, String date, String time) {
        String sql = "INSERT INTO reservations (user_id, place_id, date, time, status) VALUES (?, ?, ?, ?, 'Pending')";
        try (Connection conn = DriverManager.getConnection(DB_URL);
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

    public static List<String> getUserReservations(int userId) {
        List<String> list = new ArrayList<>();
        // Join to get the actual Place Name for the UI
        String sql = "SELECT r.date, r.time, r.status, p.name AS place_name " +
                     "FROM reservations r JOIN places p ON r.place_id = p.id " +
                     "WHERE r.user_id = ?";
                     
        try (Connection conn = DriverManager.getConnection(DB_URL);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static List<Object[]> getOwnerReservations(int ownerId) {
        List<Object[]> list = new ArrayList<>();
        // SQL JOIN to link reservations to places, and places to the owner, while grabbing the student's name
        String sql = "SELECT u.name AS student_name, r.date, r.time, p.name AS place_name, r.status " +
                     "FROM reservations r " +
                     "JOIN places p ON r.place_id = p.id " +
                     "LEFT JOIN users u ON r.user_id = u.id " + 
                     "WHERE p.owner_id = ?";
                     
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String studentName = rs.getString("student_name");
                if (studentName == null) studentName = "Unknown Student";
                
                // Add the row data exactly as the OwnerFrame table expects it
                list.add(new Object[]{
                    studentName,
                    rs.getString("date"),
                    rs.getString("time"),
                    rs.getString("place_name"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // ================= OWNER MANAGEMENT METHODS =================
    
    public static boolean updateReservationStatus(String placeName, String date, String time, String newStatus) {
        // We match the reservation based on the place, date, and time
        String sql = "UPDATE reservations SET status = ? " +
                     "WHERE date = ? AND time = ? AND place_id IN (SELECT id FROM places WHERE name = ?)";
                     
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newStatus);
            pstmt.setString(2, date);
            pstmt.setString(3, time);
            pstmt.setString(4, placeName);
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
                // We extract the rating string (e.g., "5.0 ★★★★★") and split it just to get the stars
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
            return rs.next(); // Returns true if a record exists
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
}