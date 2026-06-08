package kyusiyusphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReviewDialog extends JDialog {
    private final Color bgGray = new Color(220, 220, 220);
    private final Color titleBlue = new Color(0, 0, 128);
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);

    public ReviewDialog(JDialog parent, int placeId) {
        super(parent, true);
        setUndecorated(true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        main.setBackground(bgGray);

        // --- TITLE BAR ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(titleBlue);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, borderBlack),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        
        JLabel title = new JLabel(" WRITE A REVIEW", JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Courier New", Font.BOLD, 14));
        header.add(title, BorderLayout.WEST);
        main.add(header, BorderLayout.NORTH);

        // --- CONTENT ---
        JPanel content = new JPanel(new BorderLayout(5, 5));
        content.setBackground(bgGray);
        content.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JPanel topContent = new JPanel(new BorderLayout());
        topContent.setBackground(bgGray);
        topContent.add(new JLabel("RATING: "), BorderLayout.WEST);
        
        String[] stars = {"5 - Excellent", "4 - Good", "3 - Average", "2 - Poor", "1 - Terrible"};
        JComboBox<String> starBox = new JComboBox<>(stars);
        starBox.setFont(mainFont);
        starBox.setBackground(Color.WHITE);
        starBox.setBorder(BorderFactory.createLineBorder(borderBlack, 1));
        topContent.add(starBox, BorderLayout.CENTER);
        
        content.add(topContent, BorderLayout.NORTH);

        JPanel midContent = new JPanel(new BorderLayout());
        midContent.setBackground(bgGray);
        midContent.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        midContent.add(new JLabel("COMMENT:"), BorderLayout.NORTH);
        
        JTextArea txtComment = new JTextArea();
        txtComment.setFont(new Font("Courier New", Font.PLAIN, 12));
        txtComment.setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        txtComment.setLineWrap(true);
        txtComment.setWrapStyleWord(true);
        midContent.add(new JScrollPane(txtComment), BorderLayout.CENTER);

        content.add(midContent, BorderLayout.CENTER);
        main.add(content, BorderLayout.CENTER);

        // --- BUTTONS ---
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttons.setBackground(bgGray);

        JButton btnSubmit = createRetroButton("POST REVIEW");
        btnSubmit.addActionListener(e -> {
            int ratingInt = 5 - starBox.getSelectedIndex(); 
            String ratingStr = ratingInt + ".0 " + "★".repeat(ratingInt);
            String currentUserName = Session.getCurrentUser().name;
            
            DatabaseHelper.addReview(placeId, currentUserName, ratingStr, txtComment.getText().trim());
            JOptionPane.showMessageDialog(this, "Review posted successfully!");
            dispose();
        });

        JButton btnCancel = createRetroButton("CANCEL");
        btnCancel.addActionListener(e -> dispose());

        buttons.add(btnSubmit);
        buttons.add(btnCancel);
        main.add(buttons, BorderLayout.SOUTH);

        add(main);
    }

    private JButton createRetroButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(mainFont);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(240, 240, 240)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(Color.WHITE); }
        });
        return btn;
    }
}