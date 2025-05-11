package app.view;

import app.dao.UserDAO;
import app.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * LoginWindow.java
 * -----------------------------------------
 * UI window for user login to Inventory Management System.
 * Features:
 * - Username and password input fields
 * - Show/hide password toggle with eye icon
 * - Authentication via UserDAO
 * <p>
 * Author: Saurabh Pandey
 * Date: 06 May 2025
 */
public class LoginWindow extends JFrame {

    // UI Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel togglePasswordIcon;

    // State
    private boolean passwordVisible = false;

    // DAO for user authentication
    private final UserDAO userDAO;

    private final String EYE_CLOSED_PATH = "resources/icons/eye-closed.png";

    /**
     * Constructor - Initializes the login window
     */
    public LoginWindow() {
        userDAO = new UserDAO();
        setTitle("Customer Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI(); // Setup UI components
        setVisible(true);
    }

    /**
     * Initializes all UI components and layout.
     */
    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(40, 50, 60)); // Dark background

        // Username label
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(50, 30, 300, 20);

        // Username input field
        usernameField = new JTextField();
        usernameField.setBounds(50, 50, 300, 35);
        styleField(usernameField);

        // Password label
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 100, 300, 20);

        // Password input field
        passwordField = new JPasswordField();
        passwordField.setBounds(50, 120, 240, 35);
        styleField(passwordField);

        // Toggle password icon (eye)
        togglePasswordIcon = new JLabel(loadIcon(EYE_CLOSED_PATH));
        togglePasswordIcon.setBounds(295, 120, 30, 35);
        togglePasswordIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add mouse click listener to toggle password visibility
        togglePasswordIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                togglePasswordVisibility();
            }
        });

        // Login button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(150, 180, 100, 35);
        loginBtn.setBackground(new Color(39, 174, 96));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        // Add action listener for login button
        loginBtn.addActionListener(e -> handleLogin());

        // Add components to panel
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(togglePasswordIcon);
        panel.add(loginBtn);

        add(panel);
    }

    /**
     * Toggles password field visibility and updates eye icon.
     */
    private void togglePasswordVisibility() {
        if (passwordVisible) {
            passwordField.setEchoChar('*'); // star
            togglePasswordIcon.setIcon(loadIcon(EYE_CLOSED_PATH));
        } else {
            passwordField.setEchoChar((char) 0); // Plain text
            // Icon paths
            String EYE_OPEN_PATH = "resources/icons/eye-open.png";
            togglePasswordIcon.setIcon(loadIcon(EYE_OPEN_PATH));
        }
        passwordVisible = !passwordVisible;
    }

    /**
     * Applies styling to input fields (common for username and password).
     *
     * @param field JTextField or JPasswordField to style
     */
    private void styleField(JTextField field) {
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setCaretColor(Color.BLACK);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    /**
     * Loads and scales icon from file path.
     *
     * @param resourcePath relative path to icon
     * @return ImageIcon instance
     */
    private ImageIcon loadIcon(String resourcePath) {
        try {
            // Load from classpath
            URL iconURL = getClass().getClassLoader().getResource(resourcePath);
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } else {
                System.err.println("Icon not found: " + resourcePath);
                return new ImageIcon(); // fallback
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ImageIcon(); // fallback
        }
    }

    /**
     * Handles login logic: validates inputs and authenticates using DAO.
     */
    private void handleLogin() {
        String email = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        User user = userDAO.authenticate(email, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getUsername());

            // Open different windows based on user role
            String role = user.getRole().toLowerCase(); // e.g., "admin", "user", etc.

            SwingUtilities.invokeLater(() -> {
                dispose(); // close login window

                switch (role) {
                    case "admin":
                        new AdminWindow(); // pass user if needed
                        break;
//                    case "manager":
//                        new ManagerDashboardWindow(user);
//                        break;
                    case "user":
                    default:
                        new UserWindow();
                        break;
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }

    /**
     * Entry point for the login window.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginWindow::new);
    }
}