import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayVisitsByYearFrame extends JFrame {
    private JTextField yearField;
    private JTextArea displayArea;

    public DisplayVisitsByYearFrame() {
        setTitle("Display Visits by Year");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(226, 215, 198));

        JPanel inputPanel = new JPanel(new GridLayout(1,3));
        inputPanel.setBackground(new Color(226, 215, 198));

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setForeground(new Color(47 ,79 ,79));
        yearLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));

        yearField = new JTextField(10);
        yearField.setBackground(new Color(226, 215, 198));
        yearField.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JButton displayButton = new JButton("Display Visits");
        displayButton.setBackground(new Color(47 ,79 ,79));
        displayButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        displayButton.setForeground(new Color(226, 215, 198));

        inputPanel.add(yearLabel);
        inputPanel.add(yearField);
        inputPanel.add(displayButton);

        displayArea = new JTextArea();
        displayArea.setBackground(new Color(226, 215, 198));
        displayArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);


        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayVisits();
            }
        });
    }

    private void displayVisits() {
        String year = yearField.getText();
        StringBuilder result = new StringBuilder();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM visits WHERE year_visited = " + year)) {

            while (resultSet.next()) {
                result.append("Visit ID: ").append(resultSet.getInt("visit_id")).append("\n")
                        .append("Country: ").append(resultSet.getString("country_name")).append("\n")
                        .append("City: ").append(resultSet.getString("city_name")).append("\n")
                        .append("Year: ").append(resultSet.getInt("year_visited")).append("\n")
                        .append("Season: ").append(resultSet.getString("season_visited")).append("\n")
                        .append("Best Feature: ").append(resultSet.getString("best_feature")).append("\n")
                        .append("Comments: ").append(resultSet.getString("comments")).append("\n")
                        .append("Rating: ").append(resultSet.getInt("rating")).append("\n\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving visits", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        displayArea.setText(result.toString());
    }
}
