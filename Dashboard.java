package forms;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private String role;
    private int userId;

    public Dashboard(String role, int userId) {
        this.role = role;
        this.userId = userId;

        setTitle("Dashboard - " + role);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel header = new JLabel("Welcome to Real Estate System (" + role + ")", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        add(header, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Common buttons
        JButton viewPropertiesBtn = new JButton("View Properties");
        JButton viewLocationsBtn = new JButton("View Locations");
        mainPanel.add(viewPropertiesBtn);
        mainPanel.add(viewLocationsBtn);

        viewPropertiesBtn.addActionListener(e -> new ViewPropertiesForm().setVisible(true));
        viewLocationsBtn.addActionListener(e -> new ViewLocationsForm().setVisible(true));

        
        if (role.equalsIgnoreCase("admin")) {
            JButton manageUsersBtn = new JButton("Manage Users");
            JButton manageTransactionsBtn = new JButton("Manage Transactions");
            JButton manageReviewsBtn = new JButton("Manage Reviews");
            JButton addPropertyImagesBtn = new JButton("Add Property Images");

            mainPanel.add(manageUsersBtn);
            mainPanel.add(manageTransactionsBtn);
            mainPanel.add(manageReviewsBtn);
            mainPanel.add(addPropertyImagesBtn);

            manageUsersBtn.addActionListener(e -> new ManageUsersForm().setVisible(true));
            manageTransactionsBtn.addActionListener(e -> new ManageTransactionsForm().setVisible(true));
            manageReviewsBtn.addActionListener(e -> new ManageReviewsForm().setVisible(true));
            addPropertyImagesBtn.addActionListener(e -> new AddPropertyImagesForm().setVisible(true));

        } else if (role.equalsIgnoreCase("agent")) {
            JButton myPropertiesBtn = new JButton("My Properties");
            JButton addPropertyBtn = new JButton("Add Property");
            JButton addPropertyImagesBtn = new JButton("Add Property Images");

            mainPanel.add(myPropertiesBtn);
            mainPanel.add(addPropertyBtn);
            mainPanel.add(addPropertyImagesBtn);

            myPropertiesBtn.addActionListener(e -> new MyPropertiesForm(userId).setVisible(true));
            addPropertyBtn.addActionListener(e -> new AddPropertyForm(userId).setVisible(true));
            addPropertyImagesBtn.addActionListener(e -> new AddPropertyImagesForm().setVisible(true));

        } else if (role.equalsIgnoreCase("customer")) {
            JButton browsePropertiesBtn = new JButton("Browse Properties");
            JButton myTransactionsBtn = new JButton("My Transactions");

            mainPanel.add(browsePropertiesBtn);
            mainPanel.add(myTransactionsBtn);

            browsePropertiesBtn.addActionListener(e -> new ViewPropertiesForm().setVisible(true));
            myTransactionsBtn.addActionListener(e -> new CustomerMyTransactionsForm(userId).setVisible(true));
        }

        add(mainPanel, BorderLayout.CENTER);

        // Logout
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });
        add(logoutBtn, BorderLayout.SOUTH);
    }

    // For testing independently
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
