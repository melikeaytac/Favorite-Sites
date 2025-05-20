import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditVisitFrame extends JFrame {
    private JTextField visitIdField, countryField,
            cityField, yearField, seasonField,
            featureField, commentsField, ratingField;

    public EditVisitFrame() {
        setTitle("Edit Visit");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(226, 215, 198));


        JPanel panel = new JPanel(new GridLayout(10, 2));
        panel.setBackground(new Color(226, 215, 198));


        JLabel visitIdLabel = new JLabel("Visit ID:");
        JLabel countryLabel = new JLabel("Country Name:");
        JLabel cityLabel = new JLabel("City Name:");
        JLabel yearLabel = new JLabel("Year Visited:");
        JLabel seasonLabel = new JLabel("Season Visited:");
        JLabel featureLabel = new JLabel("Best Feature:");
        JLabel commentsLabel = new JLabel("Comments:");
        JLabel ratingLabel = new JLabel("Rating:");

        JLabel[] labels ={
                visitIdLabel,countryLabel,cityLabel,yearLabel,seasonLabel,
                featureLabel,commentsLabel,ratingLabel};

        for (JLabel label : labels){
            label.setForeground(new Color(47 ,79 ,79));
            label.setFont(new Font("Times New Roman", Font.BOLD, 20));
        }

        visitIdField = new JTextField();
        countryField = new JTextField();
        cityField = new JTextField();
        yearField = new JTextField();
        seasonField = new JTextField();
        featureField = new JTextField();
        commentsField = new JTextField();
        ratingField = new JTextField();

        JTextField[] fields = {
                visitIdField,countryField,cityField,yearField,seasonField,
                featureField,commentsField,ratingField
        };

        for (JTextField field : fields){
            field.setBackground(new Color(226, 215, 198));
            field.setFont(new Font("Times New Roman", Font.BOLD, 20));
        }

        JButton searchButton = new JButton("Search Visit");
        searchButton.setBackground(new Color(47 ,79 ,79));
        searchButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        searchButton.setForeground(new Color(226, 215, 198));
        searchButton.addActionListener(new SearchVisitActionListener());

        JButton updateButton = new JButton("Update Visit");
        updateButton.setBackground(new Color(47 ,79 ,79));
        updateButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        updateButton.setForeground(new Color(226, 215, 198));
        updateButton.addActionListener(new UpdateVisitActionListener());

        panel.add(visitIdLabel);
        panel.add(visitIdField);
        panel.add(new JLabel());
        panel.add(searchButton);
        panel.add(countryLabel);
        panel.add(countryField);
        panel.add(cityLabel);
        panel.add(cityField);
        panel.add(yearLabel);
        panel.add(yearField);
        panel.add(seasonLabel);
        panel.add(seasonField);
        panel.add(featureLabel);
        panel.add(featureField);
        panel.add(commentsLabel);
        panel.add(commentsField);
        panel.add(ratingLabel);
        panel.add(ratingField);
        panel.add(new JLabel());
        panel.add(updateButton);

        add(panel);
    }

    private class SearchVisitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int visitId = Integer.parseInt(visitIdField.getText());

            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM visits WHERE visit_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, visitId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    countryField.setText(resultSet.getString("country_name"));
                    cityField.setText(resultSet.getString("city_name"));
                    yearField.setText(String.valueOf(resultSet.getInt("year_visited")));
                    seasonField.setText(resultSet.getString("season_visited"));
                    featureField.setText(resultSet.getString("best_feature"));
                    commentsField.setText(resultSet.getString("comments"));
                    ratingField.setText(String.valueOf(resultSet.getInt("rating")));
                } else {
                    JOptionPane.showMessageDialog(null, "Visit ID not found.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error searching visit: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class UpdateVisitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int visitId = Integer.parseInt(visitIdField.getText());
            String country = countryField.getText();
            String city = cityField.getText();
            int year = Integer.parseInt(yearField.getText());
            String season = seasonField.getText();
            String feature = featureField.getText();
            String comments = commentsField.getText();
            int rating = Integer.parseInt(ratingField.getText());

            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "UPDATE visits SET country_name = ?, city_name = ?, year_visited = ?," +
                        " season_visited = ?, best_feature = ?, comments = ?, rating = ? WHERE visit_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, country);
                preparedStatement.setString(2, city);
                preparedStatement.setInt(3, year);
                preparedStatement.setString(4, season);
                preparedStatement.setString(5, feature);
                preparedStatement.setString(6, comments);
                preparedStatement.setInt(7, rating);
                preparedStatement.setInt(8, visitId);

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Visit updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Visit ID not found.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating visit: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
