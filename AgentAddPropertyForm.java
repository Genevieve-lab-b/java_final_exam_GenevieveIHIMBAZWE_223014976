package forms;

import Configuration.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AgentAddPropertyForm extends JFrame {
    private JTextField locationIdField, bedroomsField, bathroomsField, propertyTypeField;
    private JButton addBtn, closeBtn;
    private int agentId;

    public AgentAddPropertyForm(int agentId) {
        this.agentId = agentId;

        setTitle("Add New Property - Agent");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        setResizable(false);

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
        if (locationIdField.getText().isEmpty() || bedroomsField.getText().isEmpty() ||
            bathroomsField.getText().isEmpty() || propertyTypeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int locationId, bedrooms, bathrooms;
        String propertyType = propertyTypeField.getText().trim();

        try {
            locationId = Integer.parseInt(locationIdField.getText().trim());
            bedrooms = Integer.parseInt(bedroomsField.getText().trim());
            bathrooms = Integer.parseInt(bathroomsField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Location, bedrooms, and bathrooms must be numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            String sql = "INSERT INTO properties (user_id, location_id, bedrooms, bathrooms, property_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, agentId);
            pst.setInt(2, locationId);
            pst.setInt(3, bedrooms);
            pst.setInt(4, bathrooms);
            pst.setString(5, propertyType);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Property added successfully!");
                locationIdField.setText("");
                bedroomsField.setText("");
                bathroomsField.setText("");
                propertyTypeField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add property.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Example agentId = 2
        SwingUtilities.invokeLater(() -> new AgentAddPropertyForm(2).setVisible(true));
    }
}
