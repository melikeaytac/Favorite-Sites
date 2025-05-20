import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {


    private static JTextField usernameField;
    private JPasswordField passwordField;
    public static JTextField getUsernameField() {
        return usernameField;
    }

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(226, 215, 198));
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBackground(new Color(226, 215, 198));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(new Color(47 ,79 ,79));
        usernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));


        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(new Color(47 ,79 ,79));
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));

        usernameField = new JTextField();
        usernameField.setBackground(new Color(226, 215, 198));
        usernameField.setFont(new Font("Times New Roman", Font.BOLD, 20));

        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(226, 215, 198));
        passwordField.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(47 ,79 ,79));
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        loginButton.setForeground(new Color(226, 215, 198));
        loginButton.addActionListener(new LoginActionListener());

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM userinfo WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    new MainFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.",
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
