package forms;



import javax.swing.*;

import Configuration.DBConnection;

import java.awt.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginForm() {
        setTitle("Real Estate Analytics System - Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel header = new JLabel("REAL ESTATE ANALYTICS SYSTEM", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        add(header, BorderLayout.NORTH);

        
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Username or Email:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        add(panel, BorderLayout.CENTER);

       
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);

    
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> {
            dispose();
            new RegistrationForm().setVisible(true);
        });
    }

    private void login() {
        String usernameOrEmail = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (usernameOrEmail.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username/email and password.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT user_id, role FROM users WHERE (username=? OR email=?) AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, usernameOrEmail);
            pst.setString(2, usernameOrEmail);
            pst.setString(3, password);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String role = rs.getString("role");

                JOptionPane.showMessageDialog(this, "Login successful!   Welcome! And let us directs you to the Dashboard");
                dispose(); // close login form
                new Dashboard(role, userId).setVisible(true); // pass role and user_id
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Email or Password");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
