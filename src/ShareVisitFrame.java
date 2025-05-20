import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ShareVisitFrame extends JFrame {
    private JTextField visitIdField;
    private JTextField friendUsernameField;
    private JTextField yourUsernameField;

    public ShareVisitFrame() {
        setTitle("Share Visit");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(226, 215, 198));
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel visitIdLabel = new JLabel("Visit ID:");
        visitIdField = new JTextField();

        JLabel friendUsernameLabel = new JLabel("Friend's Username:");
        friendUsernameField = new JTextField();

        JLabel yourUsernameLabel = new JLabel("Your Username:");
        yourUsernameField = new JTextField();

        JTextField[] fields = {
               visitIdField,friendUsernameField,yourUsernameField
        };

        for (JTextField field : fields){
            field.setBackground(new Color(226, 215, 198));
            field.setFont(new Font("Times New Roman", Font.BOLD, 20));
        }

        JLabel[] labels ={
                visitIdLabel,friendUsernameLabel,yourUsernameLabel};

        for (JLabel label : labels){
            label.setForeground(new Color(47 ,79 ,79));
            label.setFont(new Font("Times New Roman", Font.BOLD, 20));
        }

        JButton shareButton = new JButton("Share");
        shareButton.setBackground(new Color(47 ,79 ,79));
        shareButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        shareButton.setForeground(new Color(226, 215, 198));


        shareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shareVisit();
            }
        });

        add(visitIdLabel);
        add(visitIdField);
        add(friendUsernameLabel);
        add(friendUsernameField);
        add(yourUsernameLabel);
        add(yourUsernameField);
        add(new JLabel());
        add(shareButton);
    }

    private void shareVisit() {
        String visitId = visitIdField.getText();
        String friendUsername = friendUsernameField.getText();
        String yourUsername = yourUsernameField.getText();

        if (visitId.isEmpty() || friendUsername.isEmpty() || yourUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO sharedvisits (visit_id, friend_username, username) VALUES (?, ?, ?)")) {

            statement.setInt(1, Integer.parseInt(visitId));
            statement.setString(2, friendUsername);
            statement.setString(3, yourUsername);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Visit shared successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error sharing visit", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error sharing visit", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
