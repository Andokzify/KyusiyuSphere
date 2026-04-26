package kyusiyusphere;

import javax.swing.*;
import java.awt.*;

public class CiteCard extends JPanel {

    public CiteCard(String citeName, String address, String regDate, ImageIcon photo) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 250));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190), 2),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        // Image at the top
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        if (photo != null) {
            // Scale image to fit nicely
            Image scaled = photo.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaled));
        } else {
            imgLabel.setText("📷");
            imgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 50));
            imgLabel.setForeground(new Color(180, 180, 190));
        }
        add(imgLabel, BorderLayout.NORTH);

        // Text information
        JPanel textPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        textPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(citeName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JLabel addressLabel = new JLabel(address, SwingConstants.CENTER);
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel regLabel = new JLabel("Reg Date: " + regDate, SwingConstants.CENTER);
        regLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        regLabel.setForeground(new Color(100, 100, 120));

        textPanel.add(nameLabel);
        textPanel.add(addressLabel);
        textPanel.add(regLabel);

        add(textPanel, BorderLayout.CENTER);
    }
}