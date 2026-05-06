package kyusiyusphere;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BookingDialog extends JDialog {
    private final Color bgGray = new Color(220, 220, 220); 
    private final Color titleBlue = new Color(0, 0, 128);  
    private final Color borderBlack = Color.BLACK;
    private final Font mainFont = new Font("Courier New", Font.BOLD, 14);
    
    private String placeName;
    private int placeId;
    private int maxCap;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private JTextArea txtAvailability; 

    public BookingDialog(JDialog parent, int placeId, String placeName) {
        super(parent, true);
        this.placeName = placeName;
        this.placeId = placeId;
        this.maxCap = DatabaseHelper.getPlaceMaxCapacity(placeId); // Fetch limit
        
        setUndecorated(true);
        setSize(680, 360); 
        setLocationRelativeTo(parent);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createLineBorder(borderBlack, 3));
        main.setBackground(bgGray);

        // ================= TITLE BAR =================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(titleBlue);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, borderBlack),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JLabel title = new JLabel(" BOOK RESERVATION: " + placeName.toUpperCase(), JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Courier New", Font.BOLD, 14));
        header.add(title, BorderLayout.WEST);
        main.add(header, BorderLayout.NORTH);

        // ================= SPLIT CONTENT =================
        JPanel splitPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        splitPanel.setBackground(bgGray);
        splitPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));

        // --- LEFT COLUMN: INPUTS ---
        JPanel leftContent = new JPanel();
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));
        leftContent.setBackground(bgGray);

        JLabel lblDate = new JLabel("1. SELECT TARGET DATE:");
        lblDate.setFont(mainFont);
        leftContent.add(lblDate);
        leftContent.add(Box.createRigidArea(new Dimension(0, 5)));

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("MMMM dd, yyyy");
        dateSettings.setAllowEmptyDates(false);
        datePicker = new DatePicker(dateSettings);
        datePicker.getComponentDateTextField().setFont(mainFont);
        datePicker.getComponentDateTextField().setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        datePicker.getComponentToggleCalendarButton().setBackground(Color.WHITE);
        datePicker.getComponentToggleCalendarButton().setText("...");
        leftContent.add(datePicker);

        leftContent.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel lblTime = new JLabel("2. SELECT TARGET TIME:");
        lblTime.setFont(mainFont);
        leftContent.add(lblTime);
        leftContent.add(Box.createRigidArea(new Dimension(0, 5)));

        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setAllowEmptyTimes(false);
        timeSettings.setFormatForDisplayTime("hh:mm a");
        timeSettings.setFormatForMenuTimes("hh:mm a");
        timePicker = new TimePicker(timeSettings);
        timePicker.getComponentTimeTextField().setFont(mainFont);
        timePicker.getComponentTimeTextField().setBorder(BorderFactory.createLineBorder(borderBlack, 2));
        timePicker.getComponentToggleTimeMenuButton().setBackground(Color.WHITE);
        leftContent.add(timePicker);

        splitPanel.add(leftContent);

        // --- RIGHT COLUMN: LIVE AVAILABILITY VIEWER ---
        JPanel rightContent = new JPanel(new BorderLayout(0, 5));
        rightContent.setBackground(bgGray);
        
        JLabel lblAvail = new JLabel("📅 OCCUPIED SLOTS:", SwingConstants.LEFT);
        lblAvail.setFont(mainFont);
        rightContent.add(lblAvail, BorderLayout.NORTH);
        
        txtAvailability = new JTextArea("\n\n  Please pick a DATE on the\n  calendar to see what times\n  are already booked.");
        txtAvailability.setFont(new Font("Courier New", Font.BOLD, 13));
        txtAvailability.setBackground(Color.WHITE);
        txtAvailability.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 4, 4, Color.GRAY),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderBlack, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            )
        ));
        txtAvailability.setEditable(false);
        rightContent.add(new JScrollPane(txtAvailability), BorderLayout.CENTER);

        datePicker.addDateChangeListener(e -> {
            LocalDate newDate = e.getNewDate();
            if (newDate != null) {
                String dateStr = newDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
                List<String> slots = DatabaseHelper.getOccupiedSlotsForDate(placeId, dateStr);
                
                if (slots.isEmpty()) {
                    txtAvailability.setText("DATE: " + dateStr + "\n\n✅ ALL TIMES AVAILABLE\nNo reservations yet.\n\nFacility Limit: " + maxCap + " slots");
                } else {
                    StringBuilder sb = new StringBuilder("DATE: " + dateStr + "\nFacility Limit: " + maxCap + " slots\n\n[ BOOKED SLOTS ]\n");
                    for (String s : slots) {
                        sb.append("• ").append(s).append("\n");
                    }
                    txtAvailability.setText(sb.toString());
                }
            }
        });

        splitPanel.add(rightContent);
        main.add(splitPanel, BorderLayout.CENTER);

        // ================= BUTTONS =================
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttons.setBackground(bgGray);

        JButton btnSubmit = createRetroButton("PROCEED TO CHECKOUT");
        btnSubmit.addActionListener(e -> {
            LocalDate selectedDate = datePicker.getDate(); 
            LocalTime selectedTime = timePicker.getTime(); 

            if (selectedDate == null || selectedTime == null) {
                JOptionPane.showMessageDialog(this, "Please select both date and time!");
                return;
            }

            String dateStr = selectedDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
            String timeStr = selectedTime.format(DateTimeFormatter.ofPattern("hh:mm a"));

            dispose(); 

            new ReservationConfirmDialog(
                SwingUtilities.getWindowAncestor(this), 
                Session.getCurrentUser().name, 
                this.placeName, 
                placeId, 
                dateStr, 
                timeStr, 
                "" 
            ).setVisible(true);
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