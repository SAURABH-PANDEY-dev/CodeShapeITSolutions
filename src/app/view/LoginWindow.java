package app.view;

import app.dao.UserDAO;

import javax.swing.*;
import java.awt.*;

/**
 * LoginWindow.java
 * ---------------------------------
 * UI window for logging into the Inventory Management System.
 * Validates credentials using UserDAO.
 *
 * Author: Saurabh Pandey
 * Date: 05 May 2025
 */
public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginWindow() {
        setTitle("Login - Inventory System");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
        setVisible(true);
    }

    /**
     * Initializes UI components for login form.
     */
    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(39, 174, 96));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        loginBtn.addActionListener(e -> performLogin());

        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty cell
        panel.add(loginBtn);

        add(panel);
    }

    /**
     * Validates credentials using UserDAO.
     * On success: closes login and launches main window.
     */
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        UserDAO userDAO = new UserDAO(); // Creates table & test admin if needed
        boolean authenticated = userDAO.authenticate(username, password);

        if (authenticated) {
            JOptionPane.showMessageDialog(this, "✅ Login successful!");
            dispose(); // Close login window
            new MainWindow().createAndShowGUI(); // Launch main app
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid credentials. Try again.");
        }
    }
}