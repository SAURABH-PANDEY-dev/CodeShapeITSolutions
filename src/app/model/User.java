package app.model;

/**
 * User.java
 * ---------------------
 * Represents a user in the system, with attributes for username, password, and role.
 *
 * Author: Saurabh Pandey
 * Date: 05 May 2025
 */
public class User {
    private String username;
    private String password;
    private String role;
    private int id; // Unique user ID

    // Default constructor
    public User() {}

    // Constructor with two arguments: username and password, default role as "user"
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "user"; // Default role
    }
    // Used when fetching users from DB (id, username, role only)
    public User(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }


    // Constructor with all attributes (username, password, role)
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter and Setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
