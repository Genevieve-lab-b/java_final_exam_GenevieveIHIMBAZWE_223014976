package forms;

import Configuration.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddPropertyForm extends JFrame {
    private int userId;
    private JTextField locationIdField, bedroomsField, bathroomsField, propertyTypeField;
    private JButton addBtn, closeBtn;

    public AddPropertyForm(int userId) {
        this.userId = userId;

        setTitle("Add New Property");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Location ID:"));
        locationIdField = new JTextField();
        add(locationIdField);

        add(new JLabel("Bedrooms:"));
        bedroomsField = new JTextField();
        add(bedroomsField);

        add(new JLabel("Bathrooms:"));
        bathroomsField = new JTextField();
        add(bathroomsField);

        add(new JLabel("Property Type:"));
        propertyTypeField = new JTextField();
        add(propertyTypeField);

        addBtn = new JButton("Add Property");
        closeBtn = new JButton("Close");
        add(addBtn);
        add(closeBtn);

        addBtn.addActionListener(e -> addProperty());
        closeBtn.addActionListener(e -> dispose());
    }

    private void addProperty() {
        try (Connection conn = DBConnection.getConnection()) {
            int locationId = Integer.parseInt(locationIdField.getText().trim());
            int bedrooms = Integer.parseInt(bedroomsField.getText().trim());
            int bathrooms = Integer.parseInt(bathroomsField.getText().trim());
            String type = propertyTypeField.getText().trim();

            if (type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Property type cannot be empty.");
                return;
            }

            String sql = "INSERT INTO properties (user_id, location_id, bedrooms, bathrooms, property_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setInt(2, locationId);
            pst.setInt(3, bedrooms);
            pst.setInt(4, bathrooms);
            pst.setString(5, type);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Property added successfully!");
                locationIdField.setText("");
                bedroomsField.setText("");
                bathroomsField.setText("");
                propertyTypeField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add property.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Location ID, Bedrooms, and Bathrooms.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddPropertyForm(1).setVisible(true)); // example userId
    }
}
