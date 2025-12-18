package forms;

import Configuration.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CustomerMyTransactionsForm extends JFrame {
    private JTable transactionsTable;
    private DefaultTableModel tableModel;
    private JButton refreshBtn, closeBtn;
    private int customerId;

    public CustomerMyTransactionsForm(int customerId) {
        this.customerId = customerId;

        setTitle("My Transactions - Customer");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("My Transactions", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        transactionsTable = new JTable(tableModel);

        tableModel.addColumn("Transaction ID");
        tableModel.addColumn("Property ID");
        tableModel.addColumn("Seller ID");
        tableModel.addColumn("Transaction Date");
        tableModel.addColumn("Sale Price");

        JScrollPane scrollPane = new JScrollPane(transactionsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        refreshBtn = new JButton("Refresh");
        closeBtn = new JButton("Close");
        buttonPanel.add(refreshBtn);
        buttonPanel.add(closeBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadTransactions());
        closeBtn.addActionListener(e -> dispose());

        loadTransactions(); // load transactions at startup
    }

    private void loadTransactions() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            String sql = "SELECT transaction_id, property_id, seller_id, transaction_date, sale_price " +
                         "FROM transactions WHERE buyer_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, customerId);
            ResultSet rs = pst.executeQuery();

            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("transaction_id"),
                        rs.getInt("property_id"),
                        rs.getInt("seller_id"),
                        rs.getDate("transaction_date"),
                        rs.getInt("sale_price")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Example customerId = 3
        SwingUtilities.invokeLater(() -> new CustomerMyTransactionsForm(3).setVisible(true));
    }
}
