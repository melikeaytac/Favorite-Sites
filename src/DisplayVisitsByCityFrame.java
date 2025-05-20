import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayVisitsByCityFrame extends JFrame {
    private JTextField cityField;
    private JTextArea resultArea;

    public DisplayVisitsByCityFrame() {
        setTitle("Display Visits by City");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(226, 215, 198));
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(1,2));
        inputPanel.setBackground(new Color(226, 215, 198));

        JLabel cityLabel = new JLabel("Enter City:");
        cityLabel.setForeground(new Color(47 ,79 ,79));
        cityLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));

        cityField = new JTextField();
        cityField.setBackground(new Color(226, 215, 198));
        cityField.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(47 ,79 ,79));
        searchButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        searchButton.setForeground(new Color(226, 215, 198));

        inputPanel.add(cityLabel);
        inputPanel.add(cityField);
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);


        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Times New Roman", Font.BOLD, 16));
        resultArea.setBackground(new Color(226, 215, 198));
        resultArea.setForeground(new Color(47 ,79 ,79));
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayVisitsByCity(cityField.getText());
            }
        });
    }

    private void displayVisitsByCity(String city) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM visits WHERE city_name = '" + city + "'";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                StringBuilder results = new StringBuilder();
                while (rs.next()) {
                    int visitId = rs.getInt("visit_id");
                    String country = rs.getString("country_name");
                    String year = rs.getString("year_visited");
                    String season = rs.getString("season_visited");
                    String bestFeature = rs.getString("best_feature");
                    String comment = rs.getString("comments");
                    String rating = rs.getString("rating");
                    results.append("Visit ID: ").append(visitId).append("\n")
                            .append("Country: ").append(country).append("\n")
                            .append("City: ").append(city).append("\n")
                            .append("Year: ").append(year).append("\n")
                            .append("Season: ").append(season).append("\n")
                            .append("Best Feature: ").append(bestFeature).append("\n")
                            .append("Comments: ").append(comment).append("\n")
                            .append("Rating: ").append(rating).append("\n\n");
                }
                if (results.length() == 0) {
                    resultArea.setText("No visits found for the city: " + city);
                } else {
                    resultArea.setText(results.toString());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resultArea.setText("Error retrieving visit data: " + ex.getMessage());
        }
    }

}
