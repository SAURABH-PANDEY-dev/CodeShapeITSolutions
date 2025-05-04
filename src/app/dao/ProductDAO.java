package app.dao;


import app.model.Product;
import java.util.ArrayList;
import java.util.List;
/**
 *ProductDAO.java
 * ------------------
 * Acts as a Data Access Object for managing product data.
 * Stores product data in-memory using a list (mock database).
 *
 * Author : Saurabh Pandey
 * Date : 04 May 2025
 *
 */
public class ProductDAO {

    // List to act as our in-memory database
    private List<Product> productList;

    // Constructor
    public ProductDAO(){
        productList = new ArrayList<>();
    }

    // Add a new Product
    public void addProduct(Product product){
        productList.add(product);
    }

    // Get all products
    public List<Product> getAllProducts(){
        return productList;
    }

    // Find Product by ID
    public Product getProductById(int id){
        for (Product p : productList){
            if (p.getId() == id){
                return p;
            }
        }
        return null; // Not Found
    }
    //Remove Product by ID
    public boolean removeProductById(int id){
        return productList.removeIf(p -> p.getId() == id);
    }

}
