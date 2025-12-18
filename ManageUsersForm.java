package forms;

import Configuration.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ManageUsersForm extends JFrame {
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private JButton addBtn, editBtn, deleteBtn, refreshBtn, closeBtn;

    public ManageUsersForm() {
        setTitle("Manage Users - Admin");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Users Management", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        usersTable = new JTable(tableModel);

        tableModel.addColumn("User ID");
        tableModel.addColumn("Username");
        tableModel.addColumn("Full Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Role");
        tableModel.addColumn("Created At");

        JScrollPane scrollPane = new JScrollPane(usersTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addBtn = new JButton("Add User");
        editBtn = new JButton("Edit User");
        deleteBtn = new JButton("Delete User");
        refreshBtn = new JButton("Refresh");
        closeBtn = new JButton("Close");

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(closeBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addUser());
        editBtn.addActionListener(e -> editUser());
        deleteBtn.addActionListener(e -> deleteUser());
        refreshBtn.addActionListener(e -> loadUsers());
        closeBtn.addActionListener(e -> dispose());

        loadUsers(); 
    }

    private void loadUsers() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            String sql = "SELECT user_id, username, full_name, email, phone_number, role, created_at FROM users";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            tableModel.setRowCount(0); // clear table
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("role"),
                        rs.getTimestamp("created_at")
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void addUser() {
        JTextField usernameField = new JTextField();
        JTextField fullNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField roleField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {
                "Username:", usernameField,
                "Full Name:", fullNameField,
                "Email:", emailField,
                "Phone:", phoneField,
                "Role:", roleField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add New User", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO users (username, full_name, email, phone_number, role, password) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, usernameField.getText());
                pst.setString(2, fullNameField.getText());
                pst.setString(3, emailField.getText());
                pst.setString(4, phoneField.getText());
                pst.setString(5, roleField.getText());
                pst.setString(6, new String(passwordField.getPassword()));

                int rows = pst.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "User added successfully!");
                    loadUsers();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding user: " + ex.getMessage());
            }
        }
    }


    private void editUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String username = (String) tableModel.getValueAt(selectedRow, 1);
        String fullName = (String) tableModel.getValueAt(selectedRow, 2);
        String email = (String) tableModel.getValueAt(selectedRow, 3);
        String phone = (String) tableModel.getValueAt(selectedRow, 4);
        String role = (String) tableModel.getValueAt(selectedRow, 5);

        JTextField usernameField = new JTextField(username);
        JTextField fullNameField = new JTextField(fullName);
        JTextField emailField = new JTextField(email);
        JTextField phoneField = new JTextField(phone);
        JTextField roleField = new JTextField(role);

        Object[] fields = {
                "Username:", usernameField,
                "Full Name:", fullNameField,
                "Email:", emailField,
                "Phone:", phoneField,
                "Role:", roleField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Edit User", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "UPDATE users SET username=?, full_name=?, email=?, phone_number=?, role=? WHERE user_id=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, usernameField.getText());
                pst.setString(2, fullNameField.getText());
                pst.setString(3, emailField.getText());
                pst.setString(4, phoneField.getText());
                pst.setString(5, roleField.getText());
                pst.setInt(6, userId);

                int rows = pst.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "User updated successfully!");
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error updating user: " + ex.getMessage());
            }
        }
    }


    private void deleteUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to delete.");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?");
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            String sql = "DELETE FROM users WHERE user_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "User deleted successfully.");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageUsersForm().setVisible(true));
    }
}
