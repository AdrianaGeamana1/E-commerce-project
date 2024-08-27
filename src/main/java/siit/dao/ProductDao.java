package siit.dao;

import siit.config.DataBaseManager;
import siit.model.Customer;
import siit.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDao {

    private static Logger logger4;

    static {


        logger4 = Logger.getLogger(ProductDao.class.getName());
    }
    public List<Product> getProductsBy(String term) {
        List<Product> products = new ArrayList<>();

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM products WHERE LOWER(name) LIKE ? ")
        ) {
            stmt.setString(1, "%" + term.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(extractProduct(rs));
            }
        } catch (SQLException e) {
            logger4.log(Level.SEVERE,e.getMessage(),e);
        }

        return products;
    }

    private Product extractProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setWeight(rs.getBigDecimal("weight"));
        product.setStock(rs.getInt("stoc"));
        product.setImage(rs.getString("image"));

        return product;
    }
    public Product getProductById(int id) {
        Product product=new Product();

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM products WHERE id=? ")
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
           return product=extractProduct(rs);

        } catch (SQLException e) {
            logger4.log(Level.SEVERE,e.getMessage(),e);
        }

        return null;
    }

    public void updateStock(int productId, Integer updateStock) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("UPDATE products SET stoc = ? WHERE id = ?");
        ) {
            stmt.setInt(1, updateStock);
            stmt.setInt(2, productId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger4.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    public List<Product> getProductsList() {
        List<Product> productsList = new ArrayList<>();

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM products")
        ) {
            while (rs.next()) {
                productsList.add(extractProduct(rs));
            }

        } catch (SQLException e) {
            logger4.log(Level.SEVERE,e.getMessage(),e);
        }

        return productsList;
    }

    public void addNewProduct(String name, BigDecimal weight, BigDecimal price, String image, int stock) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO products(name,weight,price,stoc,image)VALUES(?,?,?,?,?)");
        ) {
            stmt.setString(1,name);
            stmt.setBigDecimal(2,weight);
            stmt.setBigDecimal(3,price);
            stmt.setInt(4,stock);
            stmt.setString(5,image);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger4.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    public Product findProductByName(String name) {
        Product product=new Product();

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM products WHERE name=? ")
        ) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return product=extractProduct(rs);

        } catch (SQLException e) {
            logger4.log(Level.SEVERE,e.getMessage(),e);
        }

        return null;
    }

    public void deleteProductBy(int productId) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM products WHERE id=?");
        ) {
            stmt.setInt(1,productId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger4.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    public void updateProduct(Product updatedProduct) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("UPDATE products SET name=?,price=?,stoc=? WHERE id=?");
        ) {
            stmt.setString(1,updatedProduct.getName());
            stmt.setBigDecimal(2,updatedProduct.getPrice());
            stmt.setInt(3,updatedProduct.getStock());
            stmt.setInt(4,updatedProduct.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger4.log(Level.SEVERE,e.getMessage(),e);
        }
    }
}
