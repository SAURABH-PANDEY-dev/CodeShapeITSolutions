package app;

import app.util.DBUtil;
import app.model.Product;
import java.sql.Connection;

/**
 * MainApp.java
 * -----------------------
 * This is the entry point for the Local Vendor Inventory Tracker system.
 *
 * Purpose:
 * - To launch and test application components step by step
 * - Here, we are testing the initial database connection
 */
public class MainApp {
    public static void main(String[] args) {
        Product testProduct = new Product(101,"Test Laptop",10,75000.0);
        System.out.println("Product Details : ");
        System.out.println(testProduct);

    }
}
