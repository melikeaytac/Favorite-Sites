import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplayCountriesByRatingFrame extends JFrame {
    private JTextArea countriesArea;

    public DisplayCountriesByRatingFrame() {
        setTitle("Countries Sorted by Food Rating");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        countriesArea = new JTextArea();
        countriesArea.setBackground(new Color(226,215,198));
        countriesArea.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        countriesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(countriesArea);

        add(scrollPane, BorderLayout.CENTER);

        displayCountriesByRating();
    }

    private void displayCountriesByRating() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT country_name, AVG(rating) as average_rating FROM visits WHERE best_feature = 'food' GROUP BY country_name" +
                    " ORDER BY average_rating DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            StringBuilder sb = new StringBuilder();

            while (resultSet.next()) {
                String country = resultSet.getString("country_name");
                double avgRating = resultSet.getDouble("average_rating");
                sb.append(String.format("%s: %.2f\n", country, avgRating));
            }

            countriesArea.setText(sb.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error displaying countries: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
