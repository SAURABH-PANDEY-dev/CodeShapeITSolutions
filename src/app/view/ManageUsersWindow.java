package app.view;

import app.dao.UserDAO;
import app.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * ManageUsersWindow.java
 * --------------------------
 * Displays a list of all users in a table format and allows admin to delete users.
 * Now enhanced with a modern dark theme for consistent UI.
 * <p>
 * Author: Saurabh Pandey
 * Date: 06 May 2025
 */
public class ManageUsersWindow extends JFrame {

    private final DefaultTableModel model;

    public ManageUsersWindow() {
        setTitle("Manage Users");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Dark background panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 52, 54));
        add(panel);

        // Table column names
        String[] columns = {"ID", "Username", "Role"};
        model = new DefaultTableModel(columns, 0);

        // Load users from DB
        loadUsers();

        // JTable styling
        JTable table = new JTable(model);
        table.setBackground(new Color(55, 61, 63));
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.GRAY);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(35, 40, 42));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(45, 52, 54));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(45, 52, 54));

        // Load icons
        ImageIcon addUserIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/icons/add-user.png")));
        ImageIcon deleteIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/icons/delete-user.png")));
        ImageIcon backIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/icons/back.png")));

// Icon-only button: Add User
        JButton addButton = new JButton(addUserIcon);
        addButton.setToolTipText("Add New User");
        addButton.setVisible(true);
        styleIconButton(addButton);
        addButton.addActionListener(e -> {
            AddUserDialog dialog = new AddUserDialog(this);
            dialog.setVisible(true);
        });

// Icon-only button: Delete User
        JButton deleteButton = new JButton(deleteIcon);
        deleteButton.setToolTipText("Delete Selected User");
        styleIconButton(deleteButton);
        deleteButton.addActionListener(e -> deleteUser(table));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Styles icon-only buttons with transparent background and no borders.
     */
    private void styleIconButton(JButton button) {
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }


    /**
     * Loads users from database and refreshes the table.
     */
    private void loadUsers() {
        UserDAO dao = new UserDAO();
        List<User> users = dao.getAllUsers();
        model.setRowCount(0);
        for (User user : users) {
            model.addRow(new Object[]{user.getId(), user.getUsername(), user.getRole()});
        }
    }

    /**
     * Deletes selected user by ID after confirmation.
     */
    private void deleteUser(JTable table) {
        int row = table.getSelectedRow();
        if (row != -1) {
            int userId = (int) table.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                UserDAO dao = new UserDAO();
                if (dao.deleteUserById(userId)) {
                    JOptionPane.showMessageDialog(this, "User deleted successfully!");
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete user.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        }
    }

    /**
     * Applies dark button styling.
     */
    private void styleButton(JButton button) {
        button.setBackground(new Color(33, 140, 116));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
    }

    /**
     * Refreshes the users list externally.
     */
    public void refreshUsers() {
        loadUsers();
    }


}