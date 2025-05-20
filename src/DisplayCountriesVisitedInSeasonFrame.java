import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayCountriesVisitedInSeasonFrame extends JFrame {
    private JComboBox<String> seasonComboBox;
    private JTextArea displayArea;

    public DisplayCountriesVisitedInSeasonFrame() {
        setTitle("Countries Visited Only in Selected Season");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(226, 215, 198));

        JPanel inputPanel = new JPanel(new GridLayout(1,3));
        inputPanel.setBackground(new Color(226, 215, 198));

        JLabel seasonLabel = new JLabel("Season:");
        seasonLabel.setForeground(new Color(47 ,79 ,79));
        seasonLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));


        String[] seasons = {"spring", "summer", "autumn", "winter"};
        seasonComboBox = new JComboBox<>(seasons);
        seasonComboBox.setBackground(new Color(47 ,79 ,79));
        seasonComboBox.setFont(new Font("Times New Roman", Font.BOLD, 20));
        seasonComboBox.setForeground(new Color(226, 215, 198));

        JButton displayButton = new JButton("Display Countries");
        displayButton.setBackground(new Color(47 ,79 ,79));
        displayButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        displayButton.setForeground(new Color(226, 215, 198));

        inputPanel.add(seasonLabel);
        inputPanel.add(seasonComboBox);
        inputPanel.add(displayButton);

        displayArea = new JTextArea();
        displayArea.setBackground(new Color(226, 215, 198));
        displayArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCountriesVisitedInSeason();
            }
        });
    }

    private void displayCountriesVisitedInSeason() {
        String selectedSeason = (String) seasonComboBox.getSelectedItem();
        StringBuilder result = new StringBuilder();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT country_name " +
                             "FROM visits " +
                             "WHERE season_visited = '" + selectedSeason + "' " +
                             "GROUP BY country_name " +
                             "HAVING COUNT(*) = SUM(CASE WHEN season_visited = '" + selectedSeason +
                             "' THEN 1 ELSE 0 END)")) {

            while (resultSet.next()) {
                result.append("Country: ").append(resultSet.getString("country_name")).append("\n");
            }

            if (result.length() == 0) {
                result.append("No countries visited only in ").append(selectedSeason).append(".");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving data",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        displayArea.setText(result.toString());
    }

}
