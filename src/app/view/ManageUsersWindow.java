package app.view;

import app.dao.UserDAO;
import app.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ManageUsersWindow.java
 * --------------------------
 * Displays a list of all users in a table format and allows admin to delete users.
 *
 * Author: Saurabh Pandey
 * Date: 06 May 2025
 */
public class ManageUsersWindow extends JFrame {

    private DefaultTableModel model;  // To hold and update table data

    public ManageUsersWindow() {
        setTitle("Manage Users");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel and layout
        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        // Table Column Names
        String[] columns = {"ID", "Username", "Role"};

        // Table Model
        model = new DefaultTableModel(columns, 0);

        // Get all users from the database and populate the table
        loadUsers();

        // Create a JTable with the model
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

// Delete button
        JButton deleteButton = new JButton("Delete User");
        deleteButton.addActionListener(e -> deleteUser(table));
        buttonPanel.add(deleteButton);

// Add User button
        JButton addButton = new JButton("Add User");
        addButton.addActionListener(e -> {
            AddUserDialog dialog = new AddUserDialog(this);
            dialog.setVisible(true);
        });
        buttonPanel.add(addButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

    }

    /**
     * Fetches users from the database and updates the table.
     */
    private void loadUsers() {
        UserDAO dao = new UserDAO();
        List<User> users = dao.getAllUsers();

        // Clear the existing rows in the table
        model.setRowCount(0);

        // Add users to the table
        for (User user : users) {
            Object[] row = {user.getId(), user.getUsername(), user.getRole()};
            model.addRow(row);
        }
    }

    /**
     * Deletes the selected user by ID.
     */
    private void deleteUser(JTable table) {
        int row = table.getSelectedRow();
        if (row != -1) {
            // Get the user ID from the selected row
            int userId = (int) table.getValueAt(row, 0);

            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Delete the user from the database
                UserDAO dao = new UserDAO();
                boolean success = dao.deleteUserById(userId);

                if (success) {
                    JOptionPane.showMessageDialog(this, "User deleted successfully!");
                    loadUsers();  // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete user.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        }
    }

    public void refreshUsers() {
        loadUsers();
    }

}
