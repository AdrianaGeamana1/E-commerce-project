package siit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import siit.dao.ProductDao;
import siit.model.Product;
import siit.validation.ValidationErrorException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    ProductDao productDaoMock;
    @InjectMocks
    ProductService productServiceTest;

    @BeforeEach
    public void setUp() {
        productDaoMock = mock(ProductDao.class);
        productServiceTest = new ProductService(productDaoMock);
    }

    private List<Product> createMockList() {
        Product product1 = new Product();
        product1.setName("notebook");
        product1.setPrice(BigDecimal.valueOf(50));
        product1.setImage("images/notebook.jpg");
        product1.setStock(60);
        Product product2 = new Product();
        product2.setName("coffee cup");
        product2.setPrice(BigDecimal.valueOf(35));
        product2.setImage("images/coffee_cup.jpg");
        product2.setStock(30);
        Product product3 = new Product();
        product3.setName("barbie doll");
        product3.setPrice(BigDecimal.valueOf(45));
        product3.setImage("images/barbie_doll.jpg");
        product3.setStock(100);
        return new ArrayList<>(List.of(product1, product2, product3));
    }

    @Test
    void getProductsBy() {
        String term = "ll";
        List<Product> expectedList = this.createMockList();

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        when(productDaoMock.getProductsBy(argumentCaptor.capture())).thenReturn(expectedList);

        List<Product> resultList = productServiceTest.getProductsBy(term);

        verify(productDaoMock, Mockito.times(1)).getProductsBy(argumentCaptor.capture());

        assertEquals(expectedList.size(), resultList.size());
        assertEquals(expectedList.get(0), resultList.get(0));
        assertEquals(expectedList.get(1), resultList.get(1));
        assertEquals(expectedList.get(2), resultList.get(2));
        assertEquals(term, argumentCaptor.getValue());

    }

    @Test
    void getProductById() {
        int productId = 15;
        Product expectedProduct = new Product();
        expectedProduct.setName("notebook");
        expectedProduct.setPrice(BigDecimal.valueOf(50));
        expectedProduct.setImage("images/notebook.jpg");
        expectedProduct.setStock(60);

        when(productDaoMock.getProductById(productId)).thenReturn(expectedProduct);

        Product resultProduct = productServiceTest.getProductById(productId);

        assertEquals(expectedProduct, resultProduct);
    }

    @Test
    void updateStockWhenAdd() {
        int productId = 15;
        BigDecimal quantity = BigDecimal.valueOf(7);
        Product mockedProduct = new Product();
        mockedProduct.setName("notebook");
        mockedProduct.setPrice(BigDecimal.valueOf(50));
        mockedProduct.setImage("images/notebook.jpg");
        mockedProduct.setStock(60);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);


        when(productDaoMock.getProductById(argumentCaptor.capture())).thenReturn(mockedProduct);
        doNothing().when(productDaoMock).updateStock(argumentCaptor.capture(), any(Integer.class));

        productServiceTest.updateStockWhenAdd(productId, quantity);

        verify(productDaoMock, Mockito.times(1)).getProductById(argumentCaptor.capture());
        verify(productDaoMock, Mockito.times(1)).updateStock(argumentCaptor.capture(), any(Integer.class));

        assertEquals(productId, argumentCaptor.getValue());

    }

    @Test
    void updateStockWhenDelete() {
        int productId = 15;
        BigDecimal quantity = BigDecimal.valueOf(7);
        Product mockedProduct = new Product();
        mockedProduct.setName("notebook");
        mockedProduct.setPrice(BigDecimal.valueOf(50));
        mockedProduct.setImage("images/notebook.jpg");
        mockedProduct.setStock(60);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);


        when(productDaoMock.getProductById(argumentCaptor.capture())).thenReturn(mockedProduct);
        doNothing().when(productDaoMock).updateStock(argumentCaptor.capture(), any(Integer.class));

        productServiceTest.updateStockWhenDelete(productId, quantity);

        verify(productDaoMock, Mockito.times(1)).getProductById(argumentCaptor.capture());
        verify(productDaoMock, Mockito.times(1)).updateStock(argumentCaptor.capture(), any(Integer.class));

        assertEquals(productId, argumentCaptor.getValue());

    }

    @Test
    void getProductsList() {
        List<Product> expectedList = this.createMockList();

        when(productDaoMock.getProductsList()).thenReturn(expectedList);

        List<Product> resultList = productServiceTest.getProductsList();

        verify(productDaoMock, Mockito.times(1)).getProductsList();

        assertEquals(expectedList.size(), resultList.size());
        assertEquals(expectedList.get(0), resultList.get(0));
        assertEquals(expectedList.get(1), resultList.get(1));
        assertEquals(expectedList.get(2), resultList.get(2));


    }

    @Test
    void addNewProduct() {
        String name = "Barbie doll";
        BigDecimal weight = BigDecimal.valueOf(25);
        BigDecimal price = BigDecimal.valueOf(55);
        String image = "images/barbie.jpg";
        int stock = 70;

        when(productDaoMock.findProductByName(name)).thenReturn(null);
        doNothing().when(productDaoMock).addNewProduct(name, weight, price, image, stock);

        Product resultProduct = productServiceTest.addNewProduct(name, weight, price, image, stock);

        verify(productDaoMock, Mockito.times(2)).findProductByName(name);
        verify(productDaoMock, Mockito.times(1)).addNewProduct(name, weight, price, image, stock);

        assertNull(resultProduct);
    }

    @Test
    void addNewProduct_WhenThrowExistingProductException() {
        String name = "Barbie doll";
        BigDecimal weight = BigDecimal.valueOf(25);
        BigDecimal price = BigDecimal.valueOf(55);
        String image = "images/barbie.jpg";
        int stock = 70;

        Product existingProduct = new Product();
        existingProduct.setName("Barbie doll");
        existingProduct.setWeight(BigDecimal.valueOf(55));
        existingProduct.setPrice(BigDecimal.valueOf(55));
        existingProduct.setImage("images/barbie.jpg");
        existingProduct.setStock(70);

        when(productDaoMock.findProductByName(name)).thenReturn(existingProduct);
        doThrow(new ValidationErrorException("Mocked message")).when(productDaoMock).addNewProduct(name, weight, price, image, stock);

        assertThrows(ValidationErrorException.class, () -> {
            Product resultProduct = productServiceTest.addNewProduct(name, weight, price, image, stock);
        });

        verify(productDaoMock, Mockito.times(1)).findProductByName(name);
        verify(productDaoMock, Mockito.times(0)).addNewProduct(name, weight, price, image, stock);

    }

    @Test
    void addNewProduct_WhenThrowNameException() {

        //setezi name cu lenght<5;
        String name = "B";
        BigDecimal weight = BigDecimal.valueOf(25);
        BigDecimal price = BigDecimal.valueOf(55);
        String image = "images/barbie.jpg";
        int stock = 70;


        when(productDaoMock.findProductByName(name)).thenReturn(null);
        doThrow(new ValidationErrorException("Mocked message")).when(productDaoMock).addNewProduct(name, weight, price, image, stock);

        assertThrows(ValidationErrorException.class, () -> {
            Product resultProduct = productServiceTest.addNewProduct(name, weight, price, image, stock);
        });

        verify(productDaoMock, Mockito.times(1)).findProductByName(name);
        verify(productDaoMock, Mockito.times(0)).addNewProduct(name, weight, price, image, stock);

    }
    @Test
    void addNewProduct_WhenThrowWeightException() {

        //setezi weight <0;
        String name = "Barbie_doll";
        BigDecimal weight = BigDecimal.valueOf(-9);
        BigDecimal price = BigDecimal.valueOf(55);
        String image = "images/barbie.jpg";
        int stock = 70;


        when(productDaoMock.findProductByName(name)).thenReturn(null);
        doThrow(new ValidationErrorException("Mocked message")).when(productDaoMock).addNewProduct(name, weight, price, image, stock);

        assertThrows(ValidationErrorException.class, () -> {
            Product resultProduct = productServiceTest.addNewProduct(name, weight, price, image, stock);
        });

        verify(productDaoMock, Mockito.times(1)).findProductByName(name);
        verify(productDaoMock, Mockito.times(0)).addNewProduct(name, weight, price, image, stock);

    }
    @Test
    void addNewProduct_WhenThrowPriceException() {

        //setezi price <0;
        String name = "Barbie_doll";
        BigDecimal weight = BigDecimal.valueOf(9);
        BigDecimal price = BigDecimal.valueOf(-100);
        String image = "images/barbie.jpg";
        int stock = 70;


        when(productDaoMock.findProductByName(name)).thenReturn(null);
        doThrow(new ValidationErrorException("Mocked message")).when(productDaoMock).addNewProduct(name, weight, price, image, stock);

        assertThrows(ValidationErrorException.class, () -> {
            Product resultProduct = productServiceTest.addNewProduct(name, weight, price, image, stock);
        });

        verify(productDaoMock, Mockito.times(1)).findProductByName(name);
        verify(productDaoMock, Mockito.times(0)).addNewProduct(name, weight, price, image, stock);

    }
    @Test
    void addNewProduct_WhenThrowStockException() {

        //setezi stock <0;
        String name = "Barbie_doll";
        BigDecimal weight = BigDecimal.valueOf(9);
        BigDecimal price = BigDecimal.valueOf(100);
        String image = "images/barbie.jpg";
        int stock = -5;


        when(productDaoMock.findProductByName(name)).thenReturn(null);
        doThrow(new ValidationErrorException("Mocked message")).when(productDaoMock).addNewProduct(name, weight, price, image, stock);

        assertThrows(ValidationErrorException.class, () -> {
            Product resultProduct = productServiceTest.addNewProduct(name, weight, price, image, stock);
        });

        verify(productDaoMock, Mockito.times(1)).findProductByName(name);
        verify(productDaoMock, Mockito.times(0)).addNewProduct(name, weight, price, image, stock);

    }
    @Test
    void deleteProductByProductId(){
        int productId=100;

        ArgumentCaptor<Integer>argumentCaptor=ArgumentCaptor.forClass(Integer.class);

        doNothing().when(productDaoMock).deleteProductBy(argumentCaptor.capture());

        productServiceTest.deleteProductBy(productId);

        verify(productDaoMock,Mockito.times(1)).deleteProductBy(argumentCaptor.capture());

        assertEquals(productId,argumentCaptor.getValue());
    }
    @Test
    void updateProduct(){
        Product updatedMockedProduct = new Product();
        updatedMockedProduct.setName("Barbie doll");
        updatedMockedProduct.setWeight(BigDecimal.valueOf(55));
        updatedMockedProduct.setPrice(BigDecimal.valueOf(55));
        updatedMockedProduct.setImage("images/barbie.jpg");
        updatedMockedProduct.setStock(70);

        ArgumentCaptor<Product>argumentCaptor=ArgumentCaptor.forClass(Product.class);

        doNothing().when(productDaoMock).updateProduct(argumentCaptor.capture());

        productServiceTest.updateProduct(updatedMockedProduct);

        verify(productDaoMock,Mockito.times(1)).updateProduct(argumentCaptor.capture());

        assertEquals(updatedMockedProduct,argumentCaptor.getValue());
    }
    @Test
    void updateProduct_WhenThrowNameException(){
        //setezi name.lenght<5
        Product updatedMockedProduct = new Product();
        updatedMockedProduct.setName("B");
        updatedMockedProduct.setWeight(BigDecimal.valueOf(55));
        updatedMockedProduct.setPrice(BigDecimal.valueOf(55));
        updatedMockedProduct.setImage("images/barbie.jpg");
        updatedMockedProduct.setStock(70);


        doNothing().when(productDaoMock).updateProduct(updatedMockedProduct);

        assertThrows(ValidationErrorException.class,()->{productServiceTest.updateProduct(updatedMockedProduct);});

        verify(productDaoMock,Mockito.times(0)).updateProduct(updatedMockedProduct);

    }
    @Test
    void updateProduct_WhenThrowStockException(){
        //setezi stock<0;
        Product updatedMockedProduct = new Product();
        updatedMockedProduct.setName("Barbie_doll");
        updatedMockedProduct.setWeight(BigDecimal.valueOf(55));
        updatedMockedProduct.setPrice(BigDecimal.valueOf(9));
        updatedMockedProduct.setImage("images/barbie.jpg");
        updatedMockedProduct.setStock(-7);


        doNothing().when(productDaoMock).updateProduct(updatedMockedProduct);

        assertThrows(ValidationErrorException.class,()->{productServiceTest.updateProduct(updatedMockedProduct);});

        verify(productDaoMock,Mockito.times(0)).updateProduct(updatedMockedProduct);

    }
    @Test
    void updateProduct_WhenThrowPriceException(){
        //setezi price<0;
        Product updatedMockedProduct = new Product();
        updatedMockedProduct.setName("Barbie_doll");
        updatedMockedProduct.setWeight(BigDecimal.valueOf(55));
        updatedMockedProduct.setPrice(BigDecimal.valueOf(-9));
        updatedMockedProduct.setImage("images/barbie.jpg");
        updatedMockedProduct.setStock(70);


        doNothing().when(productDaoMock).updateProduct(updatedMockedProduct);

        assertThrows(ValidationErrorException.class,()->{productServiceTest.updateProduct(updatedMockedProduct);});

        verify(productDaoMock,Mockito.times(0)).updateProduct(updatedMockedProduct);

    }
}