package siit.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import siit.config.DataBaseManager;
import siit.model.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ProductDaoIntegrationTest {
    ProductDao productDaoTest;

    @BeforeEach
    public void init() {
        productDaoTest = new ProductDao();
    }
    @AfterEach
    public  void setDown(){
        productDaoTest=null;
    }

    @Test
    void getProductsBy() {
        String term="l";

        List<Product> resultList=productDaoTest.getProductsBy(term);

        assertEquals(3,resultList.size());

    }
    @Test
    void getProductById(){
        int productId=6;

        Product expectedProduct=new Product();
        expectedProduct.setId(productId);
        expectedProduct.setName("Logitech Mouse");
        expectedProduct.setWeight(BigDecimal.valueOf(500));
        expectedProduct.setPrice(BigDecimal.valueOf(24));
        expectedProduct.setImage("images/logintech_mouse.jpeg");
        expectedProduct.setStock(26);

        Product resultProduct=productDaoTest.getProductById(productId);

        assertEquals(expectedProduct,resultProduct);
    }
    @Test
    void updateStock(){
        int productId=1;
        int updateStock=100;

        productDaoTest.updateStock(productId,updateStock);

        Product resultProduct=productDaoTest.getProductById(productId);

        assertEquals(updateStock,resultProduct.getStock());
    }
    @Test
    void getProductsList(){

       Product expectedProduct=new Product();
       expectedProduct.setName("Hanlogen lights");
       expectedProduct.setId(1);
       expectedProduct.setWeight(BigDecimal.valueOf(400));
       expectedProduct.setPrice(BigDecimal.valueOf(12));
       expectedProduct.setImage("images/hologen_lights.jpg");
       expectedProduct.setStock(100);

       List<Product> resultList=productDaoTest.getProductsList();

       assertEquals(6,resultList.size());
       assertTrue(resultList.contains(expectedProduct));
    }
    @Test
    void createNewProduct(){
        String name = "barbie_doll";
        BigDecimal weight = BigDecimal.valueOf(32);
        BigDecimal price = BigDecimal.valueOf(12);
        String image = "images/barbie_doll.jpg";
        int stock = 23;

        productDaoTest.addNewProduct(name,weight,price,image,stock);

        Product resultProduct=productDaoTest.findProductByName(name);

        assertEquals(name,resultProduct.getName());
        assertEquals(weight,resultProduct.getWeight());
        assertEquals(price,resultProduct.getPrice());
        assertEquals(image,resultProduct.getImage());
        assertEquals(stock,resultProduct.getStock());

        this.deleteProductByName(name);

        Product resultProductAfterDelete=productDaoTest.findProductByName(name);

        assertNull(resultProductAfterDelete);
    }
    @Test
    void findProductByName(){
        String name="Cat food";

        Product expectedProduct=new Product();
        expectedProduct.setName("Cat food");
        expectedProduct.setWeight(BigDecimal.valueOf(1000));
        expectedProduct.setPrice(BigDecimal.valueOf(5));
        expectedProduct.setImage("images/cat_food.jpeg");
        expectedProduct.setStock(48);

        Product resultProduct=productDaoTest.findProductByName(name);

        assertEquals(expectedProduct,resultProduct);


    }
    @Test
    void updateProduct(){
        Product updatedProduct=new Product();
        updatedProduct.setName("Cat food updated");
        updatedProduct.setPrice(BigDecimal.valueOf(10));
        updatedProduct.setStock(50);
        updatedProduct.setId(3);

        productDaoTest.updateProduct(updatedProduct);
        Product resultProduct=productDaoTest.findProductByName(updatedProduct.getName());

        assertEquals(updatedProduct.getName(),resultProduct.getName());

        Product updatedBackProduct=new Product();
        updatedBackProduct.setName("Cat food");
        updatedBackProduct.setPrice(BigDecimal.valueOf(5));
        updatedBackProduct.setStock(48);
        updatedBackProduct.setId(3);

        this.updateProductBack(updatedBackProduct);
        Product resultProduct2=productDaoTest.findProductByName(updatedBackProduct.getName());

        assertEquals(updatedBackProduct.getName(),resultProduct2.getName());
    }
    private void deleteProductByName(String name){
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM products WHERE name=?");
        ) {
            stmt.setString(1,name);

            stmt.executeUpdate();

        } catch (SQLException e) {
            //oops
        }
    }
    private void updateProductBack(Product updatedProduct){
        productDaoTest.updateProduct(updatedProduct);
    }
}