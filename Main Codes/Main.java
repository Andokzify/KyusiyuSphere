package kyusiyusphere;

public class Main {
    public static void main(String[] args) {
        DatabaseHelper.initializeDatabase();  // Important!
        new startingScreen().setVisible(true);
    }
}