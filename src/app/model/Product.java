package app.model;

/**
 * Product.java
 * -----------------
 * This class represents a product in the local vendor inventory system.
 * It holds data like product ID, name, quantity and price.
 *
 * Author : Saurabh Pandey
 * Date : 04 May 2025
 */
public class Product {
    // ---Fields ---
    private int id;             // Unique identifiers for the product
    private String name;        // Product Name
    private int quantity;       // Available quantity in Stock
    private double price;       // price per unit

    // --- Constructors---

    // Default Constructor
    public Product() {}

    // Parameterized Constructor
    public Product(int id, String name, int quantity, double price){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // --- Getters and Setters ----

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String Name){
        this.name = name;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    // -- toString() Method for Debugging ---
    @Override
    public String toString(){
        return "product {" + "id = " + id + ", Name ='" + name + '\'' + ", quantity = " + quantity + ", price = " + price + '}';
    }
}
