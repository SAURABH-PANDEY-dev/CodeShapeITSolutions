package app.view;

import app.dao.UserDAO;
import app.model.User;

import javax.swing.*;
import java.awt.*;

/**
 * AddUserDialog.java
 * --------------------------
 * A modal dialog to add a new user (username, password, role).
 * Author: Saurabh Pandey
 * Date: 06 May 2025
 */
public class AddUserDialog extends JDialog {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JComboBox<String> roleCombo;

    public AddUserDialog(ManageUsersWindow parent) {
        super(parent, "Add New User", true);
        setSize(300, 220);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Username
        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        // Password
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Role
        formPanel.add(new JLabel("Role:"));
        roleCombo = new JComboBox<>(new String[]{"user", "admin"});
        formPanel.add(roleCombo);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add User");
        addButton.addActionListener(_ -> {
            addUser();
            parent.refreshUsers();  // refresh table in ManageUsersWindow
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(_ -> dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Handles adding the new user using UserDAO
     */
    private void addUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = (String) roleCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty.");
            return;
        }

        User user = new User(username, password, role);
        UserDAO dao = new UserDAO();
        boolean success = dao.addUser(user);

        if (success) {
            JOptionPane.showMessageDialog(this, "User added successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add user. Username might already exist.");
        }
    }
}
