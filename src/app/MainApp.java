//package app;
//
//import app.util.DBUtil;
//import app.model.Product;
//import java.sql.Connection;
//
///**
// * MainApp.java
// * -----------------------
// * This is the entry point for the Local Vendor Inventory Tracker system.
// *
// * Purpose:
// * - To launch and test application components step by step
// * - Here, we are testing the initial database connection
// */
//public class MainApp {
//    public static void main(String[] args) {
//        Product testProduct = new Product(101,"Test Laptop",10,75000.0);
//        System.out.println("Product Details : ");
//        System.out.println(testProduct);
//
//    }
//}
package app;

import app.dao.ProductDAO;
import app.model.Product;

public class MainApp {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        // Add sample products
        productDAO.addProduct(new Product(1, "Laptop",10, 55000.00));
        productDAO.addProduct(new Product(2, "Mouse",10, 500.00));
        productDAO.addProduct(new Product(3, "Keyboard",10,1500.00));

        // Display all products before removal
        System.out.println("All Products Before Removal:");
        for (Product p : productDAO.getAllProducts()) {
            System.out.println(p);
        }

        // Remove product with ID 2
        boolean removed = productDAO.removeProductById(2);
        System.out.println("\nProduct with ID 2 removed? " + removed);

        // Display all products after removal
        System.out.println("\nAll Products After Removal:");
        for (Product p : productDAO.getAllProducts()) {
            System.out.println(p);
        }
    }
}
