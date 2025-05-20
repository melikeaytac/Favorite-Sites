import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplayVisitFrame extends JFrame {
    private JTextField visitIdField;
    private JTextArea visitInfoArea;

    public DisplayVisitFrame() {
        setTitle("Display Visit");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(226, 215, 198));


        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(1,3 ));
        inputPanel.setBackground(new Color(226, 215, 198));

        JLabel visitIdLabel = new JLabel("Visit ID:");
        visitIdLabel.setForeground(new Color(47 ,79 ,79));
        visitIdLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));

        visitIdField = new JTextField();
        visitIdField.setBackground(new Color(226, 215, 198));
        visitIdField.setFont(new Font("Times New Roman", Font.BOLD, 20));


        JButton displayButton = new JButton("Display Visit");
        displayButton.setBackground(new Color(47 ,79 ,79));
        displayButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        displayButton.setForeground(new Color(226, 215, 198));
        displayButton.addActionListener(new DisplayVisitActionListener());

        inputPanel.add(visitIdLabel);
        inputPanel.add(visitIdField);
        inputPanel.add(displayButton);

        visitInfoArea = new JTextArea();
        visitInfoArea.setBackground(new Color(226, 215, 198));
        visitInfoArea.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        visitInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(visitInfoArea);

        panel.add(inputPanel ,BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    private class DisplayVisitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int visitId = Integer.parseInt(visitIdField.getText());

            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM visits WHERE visit_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, visitId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String visitInfo = "Visit ID: " + resultSet.getInt("visit_id") + "\n"
                            + "Username: " + resultSet.getString("username") + "\n"
                            + "Country: " + resultSet.getString("country_name") + "\n"
                            + "City: " + resultSet.getString("city_name") + "\n"
                            + "Year Visited: " + resultSet.getInt("year_visited") + "\n"
                            + "Season: " + resultSet.getString("season_visited") + "\n"
                            + "Best Feature: " + resultSet.getString("best_feature") + "\n"
                            + "Comments: " + resultSet.getString("comments") + "\n"
                            + "Rating: " + resultSet.getInt("rating");

                    visitInfoArea.setText(visitInfo);
                } else {
                    visitInfoArea.setText("No visit found with ID: " + visitId);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error displaying visit: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
