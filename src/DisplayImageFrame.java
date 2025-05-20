import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DisplayImageFrame extends JFrame {
    private JTextField visitIdField;
    private JLabel imageLabel;

    public DisplayImageFrame() {
        setTitle("Display Location Image");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(226, 215, 198));


        JPanel inputPanel = new JPanel(new GridLayout(1,3));
        inputPanel.setBackground(new Color(226, 215, 198));

        JLabel visitIdLabel = new JLabel("Visit ID:");
        visitIdLabel.setForeground(new Color(47 ,79 ,79));
        visitIdLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));

        visitIdField = new JTextField(10);
        visitIdField.setBackground(new Color(226, 215, 198));
        visitIdField.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JButton displayButton = new JButton("Display Image");
        displayButton.setBackground(new Color(47 ,79 ,79));
        displayButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        displayButton.setForeground(new Color(226, 215, 198));

        inputPanel.add(visitIdLabel);
        inputPanel.add(visitIdField);
        inputPanel.add(displayButton);

        imageLabel = new JLabel();
        imageLabel.setBackground(new Color(226, 215, 198));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayImage();
            }
        });
    }

    private void displayImage() {
        String visitId = visitIdField.getText();
        String imagePath = "Location" + visitId + ".jpg";
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            imageLabel.setIcon(imageIcon);
        } else {
            JOptionPane.showMessageDialog(this, "Image not found for Visit ID: " + visitId,
                    "Error", JOptionPane.ERROR_MESSAGE);
            imageLabel.setIcon(null);
        }
    }
}
