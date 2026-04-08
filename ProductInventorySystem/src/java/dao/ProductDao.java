/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author DING WEI QI
 */
import model.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/productinventorysystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "admin";
    private Connection connection;   
    
    public ProductDao() {
        System.out.println("=== ProductDao Initialized ===");
        // Test connection immediately
        testConnectionOnStart();
    }
    
    private void testConnectionOnStart() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Database connection successful!");
                System.out.println("Connected to: " + conn.getMetaData().getURL());
                System.out.println("Database version: " + conn.getMetaData().getDatabaseProductVersion());
            } else {
                System.out.println("❌ Failed to establish database connection");
            }
        } catch (SQLException e) {
            System.out.println("❌ Connection test failed: " + e.getMessage());
        }
    }
    
    protected Connection getConnection() {
        try {
            // Load driver (optional for newer JDBC versions)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Create connection
            Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            
            if (conn != null) {
                System.out.println("✅ Connection created successfully");
            }
            
            return conn;
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Driver not found!");
            System.err.println("Add mysql-connector-java.jar to WEB-INF/lib");
            e.printStackTrace();
            return null;
            
        } catch (SQLException e) {
            System.err.println("❌ SQL Connection Error: " + e.getMessage());
            
            // Provide helpful error messages
            if (e.getMessage().contains("Access denied")) {
                System.err.println("→ Fix: Check your MySQL username and password");
                System.err.println("  Username: " + jdbcUsername);
                System.err.println("  Password: " + (jdbcPassword != null ? "****" : "null"));
            } else if (e.getMessage().contains("Unknown database")) {
                System.err.println("→ Fix: Database 'productinventorysystem' doesn't exist");
                System.err.println("  Run: CREATE DATABASE productinventorysystem;");
            } else if (e.getMessage().contains("Connection refused")) {
                System.err.println("→ Fix: MySQL server not reachable");
                System.err.println("  Check if MySQL is running on port 3306/3307");
                System.err.println("  Current URL: " + jdbcURL);
            }
            
            e.printStackTrace();
            return null;
        }
    }
    
    public void insertProduct(Product product){
        String sql = "INSERT INTO products (id, name, category, price, quantity) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getCategory());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setInt(5, product.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Product selectProduct(int id){
        Product product = null;
        String sql = "SELECT id, name, category, price, quantity FROM products WHERE id =?";
        
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
                String name = rs.getString("name");
                String category = rs.getString("category");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                product = new Product(id, name, category, price, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return product;
    }
    
    public List<Product> selectAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String category = rs.getString("category");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    products.add(new Product(id, name, category, price, quantity));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }
    
    public boolean updateProduct(Product product) {
        boolean rowUpdated = false;
        String sql = "UPDATE products SET name = ?, category = ?, price = ?, quantity = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setInt(5, product.getId());
            
            rowUpdated = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }
    
    public boolean deleteProduct(int id) {
        boolean rowDeleted = false;
        String sql = "DELETE FROM products WHERE id = ?";
        
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            rowDeleted = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}

