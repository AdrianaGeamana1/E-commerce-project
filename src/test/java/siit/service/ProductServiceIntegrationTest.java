package siit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import siit.model.Product;
import siit.validation.ValidationErrorException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceIntegrationTest {
    ProductService productServiceTest;
    @BeforeEach
    public void setUp(){
        productServiceTest=new ProductService();
    }

    @Test
    void getProductsBy() {
        String term="l";
        Product oneExpectedProduct=new Product();
        oneExpectedProduct.setId(1);
        oneExpectedProduct.setName("Hanlogen lights");
        oneExpectedProduct.setPrice(BigDecimal.valueOf(12));
        oneExpectedProduct.setImage("images/hologen_lights.jpg");
        int expectedListSize=3;

        List<Product>resultList=productServiceTest.getProductsBy(term);

        assertEquals(expectedListSize,resultList.size());
        assertTrue(resultList.contains(oneExpectedProduct));
    }
    @Test
    void getProductById(){
        int productId=1;
        Product expectedProduct=new Product();
        expectedProduct.setId(1);
        expectedProduct.setName("Hanlogen lights");
        expectedProduct.setPrice(BigDecimal.valueOf(12));
        expectedProduct.setImage("images/hologen_lights.jpg");

        Product resultProduct=productServiceTest.getProductById(productId);

        assertEquals(expectedProduct,resultProduct);
    }
    @Test
    void updateStockWhenAdd(){
        int productId=1;
        BigDecimal quantity=BigDecimal.valueOf(10);

        productServiceTest.updateStockWhenAdd(productId,quantity);

        int expectedUpdateStock=90;

        Product updatedProduct=productServiceTest.getProductById(productId);

        assertEquals(expectedUpdateStock,updatedProduct.getStock());
    }
    @Test
    void updateStockWhenDelete(){
        int productId=1;
        BigDecimal quantity=BigDecimal.valueOf(10);

        productServiceTest.updateStockWhenDelete(productId,quantity);

        int expectedUpdateStock=100;

        Product updatedProduct=productServiceTest.getProductById(productId);

        assertEquals(expectedUpdateStock,updatedProduct.getStock());
    }
    @Test
    void getProductsList(){
        Product oneExpectedProduct=new Product();
        oneExpectedProduct.setId(1);
        oneExpectedProduct.setName("Hanlogen lights");
        oneExpectedProduct.setPrice(BigDecimal.valueOf(12));
        oneExpectedProduct.setImage("images/hologen_lights.jpg");

        int expectedListSize=6;

        List<Product>resultList=productServiceTest.getProductsList();

        assertEquals(6,resultList.size());
        assertTrue(resultList.contains(oneExpectedProduct));
    }
    @Test
    void addNewproduct_WhenThrowExistingProductException(){
        // setezi un nume care exista deja in baza de date

        String name="Cat food";
        BigDecimal weight=BigDecimal.valueOf(22);
        BigDecimal price=BigDecimal.valueOf(12);
        String image="images/catfood.jpg";
        int stock=8;

        assertThrows(ValidationErrorException.class,()->{
           productServiceTest.addNewProduct(name,weight,price,image,stock);
        });

    }
    @Test
    void addNewproduct_WhenThrowNameException(){
        // setezi name.lenght<5;

        String name="B";
        BigDecimal weight=BigDecimal.valueOf(22);
        BigDecimal price=BigDecimal.valueOf(12);
        String image="images/catfood.jpg";
        int stock=8;

        assertThrows(ValidationErrorException.class,()->{
            productServiceTest.addNewProduct(name,weight,price,image,stock);
        });

    }
    @Test
    void addNewproduct_WhenThrowWeightProductException(){
        // setezi un weight<0

        String name="Barbie_doll";
        BigDecimal weight=BigDecimal.valueOf(-22);
        BigDecimal price=BigDecimal.valueOf(12);
        String image="images/catfood.jpg";
        int stock=8;

        assertThrows(ValidationErrorException.class,()->{
            productServiceTest.addNewProduct(name,weight,price,image,stock);
        });

    }
    @Test
    void addNewproduct_WhenThrowPriceProductException(){
        // setezi un price<0

        String name="Barbie_doll";
        BigDecimal weight=BigDecimal.valueOf(22);
        BigDecimal price=BigDecimal.valueOf(-12);
        String image="images/catfood.jpg";
        int stock=8;

        assertThrows(ValidationErrorException.class,()->{
            productServiceTest.addNewProduct(name,weight,price,image,stock);
        });

    }
    @Test
    void addNewproduct_WhenThrowStockProductException(){
        // setezi un stock<0

        String name="Barbie_doll";
        BigDecimal weight=BigDecimal.valueOf(22);
        BigDecimal price=BigDecimal.valueOf(12);
        String image="images/catfood.jpg";
        int stock=-8;

        assertThrows(ValidationErrorException.class,()->{
            productServiceTest.addNewProduct(name,weight,price,image,stock);
        });

    }
    @Test
    void updateProduct_WhenNameException(){
        //setezi name.lenght<5
        Product updatedProduct=new Product();
        updatedProduct.setName("n");
        updatedProduct.setPrice(BigDecimal.valueOf(12));
        updatedProduct.setStock(35);

        assertThrows(ValidationErrorException.class,()->{
            productServiceTest.updateProduct(updatedProduct);
        });
    }
    @Test
    void updateProduct_WhenPriceException(){
        //setezi price<0;
        Product updatedProduct=new Product();
        updatedProduct.setName("niki_doll");
        updatedProduct.setPrice(BigDecimal.valueOf(-12));
        updatedProduct.setStock(35);

        assertThrows(ValidationErrorException.class,()->{
            productServiceTest.updateProduct(updatedProduct);
        });
    }
    @Test
    void updateProduct_WhenStockException(){
        //setezi stock<0;
        Product updatedProduct=new Product();
        updatedProduct.setName("niki_doll");
        updatedProduct.setPrice(BigDecimal.valueOf(12));
        updatedProduct.setStock(-35);

        assertThrows(ValidationErrorException.class,()->{
            productServiceTest.updateProduct(updatedProduct);
        });
    }
}