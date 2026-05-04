package kyusiyusphere;

public class Session {
    private static UserAccount currentUser = null;

    public static void login(UserAccount user) {
        currentUser = user;
        System.out.println("[Session] Logged in: " + user.name + " (Role " + user.role + ")");
    }

    public static UserAccount getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
    }

    public static int getCurrentUserId() {
        // Returns the actual DB ID instead of hardcoded 1
        return currentUser != null ? currentUser.id : 0; 
    }
}