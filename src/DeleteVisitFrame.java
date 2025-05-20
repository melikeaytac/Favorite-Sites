import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteVisitFrame extends JFrame {
    private JTextField visitIdField;

    public DeleteVisitFrame() {
        setTitle("Delete Visit");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(226, 215, 198));

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setBackground(new Color(226, 215, 198));
        JLabel visitIdLabel = new JLabel("Visit ID:");
        visitIdLabel.setForeground(new Color(47 ,79 ,79));
        visitIdLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));

        visitIdField = new JTextField();
        visitIdField.setBackground(new Color(226, 215, 198));
        visitIdField.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JButton deleteButton = new JButton("Delete Visit");
        deleteButton.setBackground(new Color(47 ,79 ,79));
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        deleteButton.setForeground(new Color(226, 215, 198));
        deleteButton.addActionListener(new DeleteVisitActionListener());

        panel.add(visitIdLabel);
        panel.add(visitIdField);
        panel.add(new JLabel());
        panel.add(deleteButton);

        add(panel);
    }

    private class DeleteVisitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int visitId = Integer.parseInt(visitIdField.getText());

            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "DELETE FROM visits WHERE visit_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, visitId);

                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Visit deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Visit ID not found.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error deleting visit: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
