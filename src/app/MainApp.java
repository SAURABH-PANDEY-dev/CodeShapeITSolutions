package app;

import app.view.MainWindow;
import app.view.LoginWindow;
import javax.swing.*;

/**
 * MainApp.java
 * Entry point of the Inventory Management Application
 * Author : Saurabh Pandey.
 */
public class MainApp {
    public static void main(String[] args) {
        // Start the application from login screen
        SwingUtilities.invokeLater(LoginWindow::new);
    }
}