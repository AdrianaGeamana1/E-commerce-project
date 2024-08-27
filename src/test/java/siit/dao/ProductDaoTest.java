package siit.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import siit.model.Order;
import siit.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductDaoTest {
    ProductDao productDaoTest;
    ProductDao productDaoMock;

    @BeforeEach
    public void init() {
        productDaoTest = new ProductDao();
        productDaoMock=mock(ProductDao.class);
    }
    @AfterEach
    public  void setDown(){
        productDaoTest=null;
    }

    @Test
    void getProductsBy() {
        String term="$$";

        List<Product>resultList=productDaoTest.getProductsBy(term);

        assertTrue(resultList.isEmpty());
    }
    @Test
    void getProductBy(){
        int productId=0;

        Product resultProduct=productDaoTest.getProductById(productId);

        assertNull(resultProduct);
    }
    @Test
    void updateStock(){
        ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> valueCapture2 = ArgumentCaptor.forClass(Integer.class);

        doNothing().when(productDaoMock).updateStock(valueCapture.capture(),valueCapture2.capture());

        productDaoMock.updateStock(2,90);

        assertEquals(2, valueCapture.getValue());
        assertEquals(90, valueCapture2.getValue());
    }
    @Test
    void getProductsList(){
        List<Product> productsMockList=new ArrayList<>();
        when(productDaoMock.getProductsList()).thenReturn(productsMockList);

        List<Product> resultList=productDaoMock.getProductsList();

        assertTrue(resultList.isEmpty());
    }
    @Test
    void createNewProduct() {
        String name = "barbie_doll";
        BigDecimal weight = BigDecimal.valueOf(32);
        BigDecimal price = BigDecimal.valueOf(12);
        String image = "images/barbie_doll.jpg";
        int stock = 23;

        ArgumentCaptor<String> argumentCaptorString1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<BigDecimal> argumentCaptorDecimal1 = ArgumentCaptor.forClass(BigDecimal.class);
        ArgumentCaptor<BigDecimal> argumentCaptorDecimal2 = ArgumentCaptor.forClass(BigDecimal.class);
        ArgumentCaptor<String> argumentCaptorString2 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        doNothing().when(productDaoMock).addNewProduct(argumentCaptorString1.capture(), argumentCaptorDecimal1.capture(), argumentCaptorDecimal2.capture(), argumentCaptorString2.capture(), integerArgumentCaptor.capture());

        productDaoMock.addNewProduct(name, weight, price, image, stock);

        assertEquals(name, argumentCaptorString1.getValue());
        assertEquals(weight, argumentCaptorDecimal1.getValue());
        assertEquals(price, argumentCaptorDecimal2.getValue());
        assertEquals(image, argumentCaptorString2.getValue());
        assertEquals(stock, integerArgumentCaptor.getValue());
    }
    @Test
    void findProductByName(){
        String name="anything";

        Product expectedProduct=new Product();
        expectedProduct.setName("barbie_doll");
        expectedProduct.setWeight(BigDecimal.valueOf(20));
        expectedProduct.setPrice(BigDecimal.valueOf(15));
        expectedProduct.setImage("barbie.jpg");
        expectedProduct.setStock(30);

        when(productDaoMock.findProductByName(name)).thenReturn(expectedProduct);

        Product resultProduct=productDaoMock.findProductByName(name);

        assertEquals(expectedProduct,resultProduct);
    }
    @Test
    void deleteProduct(){
        int productId=100;

        ArgumentCaptor<Integer>argumentCaptor=ArgumentCaptor.forClass(Integer.class);

        doNothing().when(productDaoMock).deleteProductBy(argumentCaptor.capture());

        productDaoMock.deleteProductBy(productId);

        assertEquals(productId,argumentCaptor.getValue());
    }
    @Test
    void updateProduct(){
        Product updatedMockProduct=new Product();
        updatedMockProduct.setName("barbie_doll");
        updatedMockProduct.setWeight(BigDecimal.valueOf(20));
        updatedMockProduct.setPrice(BigDecimal.valueOf(15));
        updatedMockProduct.setImage("barbie.jpg");
        updatedMockProduct.setStock(30);

        ArgumentCaptor<Product>argumentCaptor=ArgumentCaptor.forClass(Product.class);

        doNothing().when(productDaoMock).updateProduct(argumentCaptor.capture());

        productDaoMock.updateProduct(updatedMockProduct);

        assertEquals(updatedMockProduct,argumentCaptor.getValue());
    }
}