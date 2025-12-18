package forms;

import Configuration.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;

public class AgentAddPropertyImagesForm extends JFrame {
    private JTextField propertyIdField, captionField, imagePathField;
    private JButton browseBtn, uploadBtn, closeBtn;
    private File selectedFile;
    private int agentId;

    public AgentAddPropertyImagesForm(int agentId) {
        this.agentId = agentId;

        setTitle("Add Images to My Property - Agent");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        setResizable(false);

        add(new JLabel("Property ID:"));
        propertyIdField = new JTextField();
        add(propertyIdField);

        add(new JLabel("Image Path:"));
        imagePathField = new JTextField();
        imagePathField.setEditable(false);
        add(imagePathField);

        browseBtn = new JButton("Browse...");
        add(browseBtn);
        browseBtn.addActionListener(e -> chooseImage());

        add(new JLabel("Caption:"));
        captionField = new JTextField();
        add(captionField);

        uploadBtn = new JButton("Upload Image");
        add(uploadBtn);
        uploadBtn.addActionListener(e -> uploadImage());

        closeBtn = new JButton("Close");
        add(closeBtn);
        closeBtn.addActionListener(e -> dispose());
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            imagePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void uploadImage() {
        if (propertyIdField.getText().isEmpty() || selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please select property ID and image.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int propertyId;
        try {
            propertyId = Integer.parseInt(propertyIdField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Property ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String caption = captionField.getText().trim();
        String imageUrl = selectedFile.getAbsolutePath();

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            // Ensure property belongs to this agent
            String checkSql = "SELECT * FROM properties WHERE property_id=? AND user_id=?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, propertyId);
            checkStmt.setInt(2, agentId);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "You can only add images to your own properties.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert image
            String sql = "INSERT INTO images (property_id, image_url, caption) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, propertyId);
            pst.setString(2, imageUrl);
            pst.setString(3, caption);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Image uploaded successfully!");
                propertyIdField.setText("");
                captionField.setText("");
                imagePathField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to upload image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Example agentId = 2
        SwingUtilities.invokeLater(() -> new AgentAddPropertyImagesForm(2).setVisible(true));
    }
}
