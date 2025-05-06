package app.view;

import app.dao.UserDAO;
import app.model.User;

import javax.swing.*;
import java.awt.*;

/**
 * RegisterUserWindow.java
 * ----------------------------------------------------
 * A UI window that allows an admin to register a new user.
 * Uses UserDAO to insert the new user into the 'users' table.
 *
 * Author: Saurabh Pandey
 * Date: 05 May 2025
 */
public class RegisterUserWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    // Constructor: sets up the frame properties and calls UI builder
    public RegisterUserWindow() {
        setTitle("Register New User");
        setSize(400, 250);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window

        initUI(); // Build and show the form
        setVisible(true);
    }

    // Builds the form layout and UI components
    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Labels and input fields
        JLabel userLabel = new JLabel("New Username:");
        JLabel passLabel = new JLabel("New Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        // Register button setup
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(41, 128, 185)); // Blue
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);

        // On click: call method to register user
        registerBtn.addActionListener(e -> registerUser());

        // Add components to panel
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty cell
        panel.add(registerBtn);

        add(panel); // Add panel to frame
    }

    // Logic to register the user using UserDAO
    private void registerUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill both username and password.");
            return;
        }

        // Create User object and try to insert into DB
        User newUser = new User(username, password);
        UserDAO userDAO = new UserDAO();

        boolean success = userDAO.addUser(newUser);
        if (success) {
            JOptionPane.showMessageDialog(this, "✅ User registered successfully!");
            dispose(); // Close window after success
        } else {
            JOptionPane.showMessageDialog(this, "❌ Failed to register user (maybe username exists).");
        }
    }
}