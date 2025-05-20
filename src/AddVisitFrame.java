import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddVisitFrame extends JFrame {
    private JTextField countryField, cityField,
            yearField, seasonField, featureField,
            commentsField, ratingField;

    public AddVisitFrame() {
        setTitle("Add Visit");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(226, 215, 198));

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.setBackground(new Color(226, 215, 198));

        JLabel countryLabel = new JLabel("Country Name:");
        JLabel cityLabel = new JLabel("City Name:");
        JLabel yearLabel = new JLabel("Year Visited:");
        JLabel seasonLabel = new JLabel("Season Visited:");
        JLabel featureLabel = new JLabel("Best Feature:");
        JLabel commentsLabel = new JLabel("Comments:");
        JLabel ratingLabel = new JLabel("Rating:");

        JLabel[] labels ={
                countryLabel,cityLabel,yearLabel,seasonLabel,
                featureLabel,commentsLabel,ratingLabel};

        for (JLabel label : labels){
            label.setForeground(new Color(47 ,79 ,79));
            label.setFont(new Font("Times New Roman", Font.BOLD, 20));
        }

        countryField = new JTextField();
        cityField = new JTextField();
        yearField = new JTextField();
        seasonField = new JTextField();
        featureField = new JTextField();
        commentsField = new JTextField();
        ratingField = new JTextField();

        JTextField[] fields = {
                countryField,cityField,yearField,seasonField,
                featureField,commentsField,ratingField
        };

        for (JTextField field : fields){
            field.setBackground(new Color(226, 215, 198));
            field.setFont(new Font("Times New Roman", Font.BOLD, 20));
        }

        JButton addButton = new JButton("Add Visit");
        addButton.setBackground(new Color(47 ,79 ,79));
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        addButton.setForeground(new Color(226, 215, 198));
        addButton.addActionListener(new AddVisitActionListener());

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
        panel.add(addButton);

        add(panel);
    }

    private class AddVisitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String user = LoginFrame.getUsernameField().getText();
            String country = countryField.getText();
            String city = cityField.getText();
            int year = Integer.parseInt(yearField.getText());
            String season = seasonField.getText();
            String feature = featureField.getText();
            String comments = commentsField.getText();
            int rating = Integer.parseInt(ratingField.getText());

            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO visits" +
                        " (username, country_name, city_name, year_visited, season_visited, best_feature, comments, rating)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, user);
                preparedStatement.setString(2, country);
                preparedStatement.setString(3, city);
                preparedStatement.setInt(4, year);
                preparedStatement.setString(5, season);
                preparedStatement.setString(6, feature);
                preparedStatement.setString(7, comments);
                preparedStatement.setInt(8, rating);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Visit added successfully!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding visit: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
