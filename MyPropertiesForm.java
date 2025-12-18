package forms;

import Configuration.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MyPropertiesForm extends JFrame {
    private int userId;
    private JTable propertiesTable;
    private DefaultTableModel tableModel;
    private JButton refreshBtn, closeBtn;

    public MyPropertiesForm(int userId) {
        this.userId = userId;

        setTitle("My Properties");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Your Properties", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        propertiesTable = new JTable(tableModel);

        tableModel.addColumn("Property ID");
        tableModel.addColumn("Location ID");
        tableModel.addColumn("Bedrooms");
        tableModel.addColumn("Bathrooms");
        tableModel.addColumn("Property Type");
        tableModel.addColumn("Listing Date");

        JScrollPane scrollPane = new JScrollPane(propertiesTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        refreshBtn = new JButton("Refresh");
        closeBtn = new JButton("Close");
        buttonPanel.add(refreshBtn);
        buttonPanel.add(closeBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadProperties());
        closeBtn.addActionListener(e -> dispose());

        loadProperties(); // load properties at startup
    }

    private void loadProperties() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT property_id, location_id, bedrooms, bathrooms, property_type, listing_date " +
                         "FROM properties WHERE user_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            tableModel.setRowCount(0); // clear table
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("property_id"),
                        rs.getInt("location_id"),
                        rs.getInt("bedrooms"),
                        rs.getInt("bathrooms"),
                        rs.getString("property_type"),
                        rs.getTimestamp("listing_date")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyPropertiesForm(1).setVisible(true)); // example userId
    }
}
