package forms;

import Configuration.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewLocationsForm extends JFrame {
    private JTable locationsTable;
    private DefaultTableModel tableModel;
    private JButton refreshBtn, closeBtn;

    public ViewLocationsForm() {
        setTitle("View Locations");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Locations", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        locationsTable = new JTable(tableModel);

        tableModel.addColumn("Location ID");
        tableModel.addColumn("Sector");
        tableModel.addColumn("District");
        tableModel.addColumn("City/Province");
        tableModel.addColumn("Country");

        JScrollPane scrollPane = new JScrollPane(locationsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        refreshBtn = new JButton("Refresh");
        closeBtn = new JButton("Close");
        buttonPanel.add(refreshBtn);
        buttonPanel.add(closeBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadLocations());
        closeBtn.addActionListener(e -> dispose());

        loadLocations(); // load locations at startup
    }

    private void loadLocations() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT location_id, Sector, District, City_Or_Province, country FROM locations";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            tableModel.setRowCount(0); // clear table
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("location_id"),
                        rs.getString("Sector"),
                        rs.getString("District"),
                        rs.getString("City_Or_Province"),
                        rs.getString("country")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewLocationsForm().setVisible(true));
    }
}
