package kyusiyusphere;

public class Session {
    
    private static UserAccount currentUser = null;

    // Call this when the login is successful
    public static void login(UserAccount user) {
        currentUser = user;
    }

    // Call this for the logout button logic
    public static void logout() {
        currentUser = null;
    }

    // Check if anyone is logged in
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // Get the whole user object (for names, emails, etc.)
    public static UserAccount getCurrentUser() {
        return currentUser;
    }

    // Quick helper to get just the ID for database queries
    public static int getCurrentUserId() {
        return (currentUser != null) ? currentUser.id : -1;
    }
}