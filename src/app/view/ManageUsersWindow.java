package app.ui;

import app.dao.UserDAO;
import app.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ManageUsersWindow.java
 * --------------------------
 * Displays a list of all users in a table format.
 *
 * Author: Saurabh Pandey
 * Date: 06 May 2025
 */
public class ManageUsersWindow extends JFrame {

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
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Get all users from the database
        UserDAO dao = new UserDAO();
        List<User> users = dao.getAllUsers();

        for (User user : users) {
            Object[] row = {user.getId(), user.getUsername(), user.getRole()};
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
    }
}
