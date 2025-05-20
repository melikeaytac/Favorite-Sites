import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayMostVisitedCountryFrame extends JFrame {
    private JTextArea displayArea;

    public DisplayMostVisitedCountryFrame() {
        setTitle("Most Visited Country");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(226, 215, 198));


        displayArea = new JTextArea();
        displayArea.setBackground(new Color(226, 215, 198));
        displayArea.setFont(new Font("Times New Roman", Font.ITALIC, 16));

        displayArea.setEditable(false);

        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        displayMostVisitedCountry();
    }

    private void displayMostVisitedCountry() {
        StringBuilder result = new StringBuilder();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT country_name, COUNT(*) AS visit_count " +
                             "FROM visits " +
                             "GROUP BY country_name " +
                             "ORDER BY visit_count DESC " +
                             "LIMIT 1")) {

            if (resultSet.next()) {
                result.append("Most Visited Country: ").append(resultSet.getString("country_name"))
                        .append("\nVisits: ").append(resultSet.getInt("visit_count"));
            } else {
                result.append("No visits recorded.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving data",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        displayArea.setText(result.toString());
    }
}
