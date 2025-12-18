package forms;

import Configuration.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ManageReviewsForm extends JFrame {
    private JTable reviewsTable;
    private DefaultTableModel tableModel;
    private JButton refreshBtn, deleteBtn, closeBtn;

    public ManageReviewsForm() {
        setTitle("Manage Reviews - Admin");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Property Reviews Management", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        reviewsTable = new JTable(tableModel);

        tableModel.addColumn("Review ID");
        tableModel.addColumn("Property ID");
        tableModel.addColumn("User ID");
        tableModel.addColumn("Rating");
        tableModel.addColumn("Comment");
        tableModel.addColumn("Review Date");

        JScrollPane scrollPane = new JScrollPane(reviewsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        refreshBtn = new JButton("Refresh");
        deleteBtn = new JButton("Delete Review");
        closeBtn = new JButton("Close");

        buttonPanel.add(refreshBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(closeBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event handlers
        refreshBtn.addActionListener(e -> loadReviews());
        deleteBtn.addActionListener(e -> deleteReview());
        closeBtn.addActionListener(e -> dispose());

        loadReviews(); // load reviews at startup
    }

    private void loadReviews() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            String sql = "SELECT review_id, property_id, user_id, rating, comment, review_date FROM reviews";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            tableModel.setRowCount(0); // clear table
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("review_id"),
                        rs.getInt("property_id"),
                        rs.getInt("user_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("review_date")
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void deleteReview() {
        int selectedRow = reviewsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a review to delete.");
            return;
        }

        int reviewId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this review?");
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            String sql = "DELETE FROM reviews WHERE review_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, reviewId);
            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Review deleted successfully.");
                loadReviews();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete review.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageReviewsForm().setVisible(true));
    }
}
