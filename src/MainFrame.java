import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Favorite Sites - Main");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(226, 215, 198));
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        buttonPanel.setBackground(new Color(226, 215, 198));

        JButton addVisitButton = new JButton("Add Visit");
        JButton deleteVisitButton = new JButton("Delete Visit");
        JButton displayVisitButton = new JButton("Display Visit");
        JButton editVisitButton = new JButton("Edit Visit");
        JButton displayCountriesByRatingButton = new JButton("Countries by Food Rating");
        JButton displayImageButton = new JButton("Display Image");
        JButton displayVisitsByYearButton = new JButton("Display Visits by Year");
        JButton displayMostVisitedCountryButton = new JButton("Most Visited Country");
        JButton displayCountriesVisitedInSeasonButton = new JButton("Display Visits in Season");
        JButton shareVisitButton = new JButton("Share Visit");
        JButton displaySharedVisitsButton = new JButton("Display Shared Visits");
        JButton displayVisitsByCityButton = new JButton("Display Visits By City");

        addVisitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddVisitFrame().setVisible(true);
            }
        });

        deleteVisitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteVisitFrame().setVisible(true);
            }
        });

        displayVisitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisplayVisitFrame().setVisible(true);
            }
        });

        editVisitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditVisitFrame().setVisible(true);
            }
        });

        displayCountriesByRatingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisplayCountriesByRatingFrame().setVisible(true);
            }
        });

        displayImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisplayImageFrame().setVisible(true);
            }
        });

        displayVisitsByYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisplayVisitsByYearFrame().setVisible(true);
            }
        });

        displayMostVisitedCountryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisplayMostVisitedCountryFrame().setVisible(true);
            }
        });

        displayCountriesVisitedInSeasonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisplayCountriesVisitedInSeasonFrame().setVisible(true);
            }
        });

        shareVisitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShareVisitFrame().setVisible(true);
            }
        });

        displaySharedVisitsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisplaySharedVisitsFrame().setVisible(true);
            }
        });

        displayVisitsByCityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisplayVisitsByCityFrame().setVisible(true);
            }
        });


        JButton[] buttons = {
                addVisitButton, deleteVisitButton, displayVisitButton, editVisitButton,
                displayCountriesByRatingButton, displayImageButton, displayVisitsByYearButton,
                displayMostVisitedCountryButton, displayCountriesVisitedInSeasonButton,shareVisitButton
                ,displaySharedVisitsButton,displayVisitsByCityButton
        };

        for (JButton button : buttons) {
            button.setBackground(new Color(47 ,79 ,79));
            button.setFont(new Font("Times New Roman", Font.BOLD, 20));
            button.setForeground(new Color(226, 215, 198));
        }

        buttonPanel.add(addVisitButton);
        buttonPanel.add(deleteVisitButton);
        buttonPanel.add(displayVisitButton);
        buttonPanel.add(editVisitButton);
        buttonPanel.add(displayCountriesByRatingButton);
        buttonPanel.add(displayImageButton);
        buttonPanel.add(displayVisitsByYearButton);
        buttonPanel.add(displayMostVisitedCountryButton);
        buttonPanel.add(displayCountriesVisitedInSeasonButton);
        buttonPanel.add(displayVisitsByCityButton);
        buttonPanel.add(shareVisitButton);
        buttonPanel.add(displaySharedVisitsButton);


        add(buttonPanel, BorderLayout.CENTER);


        JLabel welcomeLabel = new JLabel("Welcome to Favorite Sites "+LoginFrame.getUsernameField().getText()+ "!",
                JLabel.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        welcomeLabel.setForeground(new Color(47 ,79 ,79));
        add(welcomeLabel, BorderLayout.NORTH);
    }
}
