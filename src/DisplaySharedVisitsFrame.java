import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DisplaySharedVisitsFrame extends JFrame {
    private JTextField yourUsernameField;
    private JTextArea displayArea;

    public DisplaySharedVisitsFrame() {
        setTitle("Shared Visits");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(226, 215, 198));
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(1,3));
        inputPanel.setBackground(new Color(226, 215, 198));
        JLabel yourUsernameLabel = new JLabel("Your Username:");
        yourUsernameLabel.setForeground(new Color(47 ,79 ,79));
        yourUsernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));

        yourUsernameField = new JTextField(15);
        yourUsernameField.setBackground(new Color(226, 215, 198));
        yourUsernameField.setFont(new Font("Times New Roman", Font.BOLD, 20));


        JButton fetchButton = new JButton("Fetch Shared Visits");
        fetchButton.setBackground(new Color(47 ,79 ,79));
        fetchButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        fetchButton.setForeground(new Color(226, 215, 198));


        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySharedVisits();
            }
        });

        inputPanel.add(yourUsernameLabel);
        inputPanel.add(yourUsernameField);
        inputPanel.add(fetchButton);

        displayArea = new JTextArea();
        displayArea.setBackground(new Color(226, 215, 198));
        displayArea.setEditable(false);


        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
    }

    private void displaySharedVisits() {
        String yourUsername = yourUsernameField.getText();
        StringBuilder result = new StringBuilder();

        if (yourUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your username is required",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT s.friend_username, s.visit_id, v.country_name, v.city_name, v.season_visited, v.best_feature " +
                             "FROM sharedvisits s " +
                             "JOIN visits v ON s.visit_id = v.visit_id " +
                             "WHERE s.username = ?")) {

            statement.setString(1, yourUsername);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.append("Friend: ").append(resultSet.getString("friend_username")).append("\n")
                            .append("Visit ID: ").append(resultSet.getInt("visit_id")).append("\n")
                            .append("Country: ").append(resultSet.getString("country_name")).append("\n")
                            .append("City: ").append(resultSet.getString("city_name")).append("\n")
                            .append("Season: ").append(resultSet.getString("season_visited")).append("\n")
                            .append("Best Feature: ").append(resultSet.getString("best_feature")).append("\n")
                            .append("------\n");
                }

                if (result.length() == 0) {
                    result.append("No visits shared with you.");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving shared visits",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        displayArea.setText(result.toString());
    }
}
