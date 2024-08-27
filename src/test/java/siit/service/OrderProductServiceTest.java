package siit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import siit.dao.OrderProductDao;
import siit.model.OrderProduct;
import siit.model.Product;
import siit.validation.ValidationErrorException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderProductServiceTest {
    @Mock
    OrderProductDao orderProductDaoMock;
    @Mock
    ProductService productServiceMock;
    @InjectMocks
    OrderProductService orderProductServiceTest;
    @BeforeEach
    public void setUp(){
        orderProductDaoMock=mock(OrderProductDao.class);
        productServiceMock=mock(ProductService.class);
        orderProductServiceTest=new OrderProductService(orderProductDaoMock,productServiceMock);
    }
    private List<OrderProduct> createMockList(){
        Product product1=new Product();
        product1.setName("coffee");
        product1.setPrice(BigDecimal.valueOf(10));
        product1.setImage("images/coffee.jpg");
        OrderProduct orderProduct1=new OrderProduct();
        orderProduct1.setQuantity(BigDecimal.valueOf(5));
        orderProduct1.setValue(BigDecimal.valueOf(50));
        orderProduct1.setProduct(product1);

        Product product2=new Product();
        product2.setName("paint cup");
        product2.setPrice(BigDecimal.valueOf(5));
        product2.setImage("images/paint_cup.jpg");
        OrderProduct orderProduct2=new OrderProduct();
        orderProduct2.setQuantity(BigDecimal.valueOf(10));
        orderProduct2.setValue(BigDecimal.valueOf(50));
        orderProduct2.setProduct(product2);

        return new ArrayList<>(List.of(orderProduct1,orderProduct2));
    }

    @Test
    void getOrderProducts() {
        int customerId=9;
        int orderId=3;
        List<OrderProduct>expectedList=this.createMockList();

        ArgumentCaptor<Integer>argumentCaptor1=ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer>argumentCaptor2=ArgumentCaptor.forClass(Integer.class);

        when(orderProductDaoMock.getOrderProducts(argumentCaptor1.capture(),argumentCaptor2.capture())).thenReturn(expectedList);

        List<OrderProduct>resultList=orderProductServiceTest.getOrderProducts(customerId,orderId);

        verify(orderProductDaoMock, Mockito.times(1)).getOrderProducts(argumentCaptor1.capture(),argumentCaptor2.capture());

        assertEquals(customerId,argumentCaptor1.getValue());
        assertEquals(orderId,argumentCaptor2.getValue());
        assertEquals(expectedList.size(),resultList.size());
        assertEquals(expectedList.get(0),resultList.get(0));
        assertEquals(expectedList.get(1),resultList.get(1));
    }
    @Test
    void addOrderProduct_WhenExistOrderProduct(){
        int orderId=8;
        int productId=5;
        BigDecimal quantity=BigDecimal.valueOf(7);

        //Pui stocul produsului mockuit mai mare ca si quantity ca sa nu ti intre metoda pe aruncarea exceptiei
        Product mockedProduct=new Product();
        mockedProduct.setName("coffee");
        mockedProduct.setPrice(BigDecimal.valueOf(10));
        mockedProduct.setImage("images/coffee.jpg");
        mockedProduct.setStock(50);
        OrderProduct mockedOrderProduct=new OrderProduct();
        mockedOrderProduct.setQuantity(BigDecimal.valueOf(5));
        mockedOrderProduct.setValue(BigDecimal.valueOf(50));
        mockedOrderProduct.setProduct(mockedProduct);

        //setezi totalQuantity pt update
        BigDecimal totalQuantity=mockedOrderProduct.getQuantity().add(quantity);

        when(productServiceMock.getProductById(productId)).thenReturn(mockedProduct);
        when(orderProductDaoMock.findBy(orderId,productId)).thenReturn(mockedOrderProduct);
        doNothing().when(orderProductDaoMock).addOrderProduct(orderId,productId,quantity);
        doNothing().when(orderProductDaoMock).updateOrderProduct(orderId,productId,totalQuantity);

        OrderProduct resultOrderProduct=orderProductServiceTest.addOrderProduct(orderId,productId,quantity);

        verify(productServiceMock,Mockito.times(1)).getProductById(productId);
        verify(orderProductDaoMock,Mockito.times(2)).findBy(orderId,productId);
        //pui 0 times pt ca metoda nu va intra in acel if
        verify(orderProductDaoMock,Mockito.times(0)).addOrderProduct(orderId,productId,quantity);
        verify(orderProductDaoMock,Mockito.times(1)).updateOrderProduct(orderId,productId,totalQuantity);

        assertEquals(mockedOrderProduct,resultOrderProduct);

    }
    @Test
    void addOrderProduct_WhenNotExistOrderProduct(){
        int orderId=8;
        int productId=5;
        BigDecimal quantity=BigDecimal.valueOf(7);

        //Pui stocul produsului mockuit mai mare ca si quantity ca sa nu ti intre metoda pe aruncarea exceptiei
        Product mockedProduct=new Product();
        mockedProduct.setName("coffee");
        mockedProduct.setPrice(BigDecimal.valueOf(10));
        mockedProduct.setImage("images/coffee.jpg");
        mockedProduct.setStock(50);


        when(productServiceMock.getProductById(productId)).thenReturn(mockedProduct);
        //dai return null ca sa nu-ti intre in if pt update
        when(orderProductDaoMock.findBy(orderId,productId)).thenReturn(null);
        doNothing().when(orderProductDaoMock).addOrderProduct(orderId,productId,quantity);
        doNothing().when(orderProductDaoMock).updateOrderProduct(orderId,productId,quantity);

        OrderProduct resultOrderProduct=orderProductServiceTest.addOrderProduct(orderId,productId,quantity);

        verify(productServiceMock,Mockito.times(1)).getProductById(productId);
        verify(orderProductDaoMock,Mockito.times(2)).findBy(orderId,productId);
        verify(orderProductDaoMock,Mockito.times(1)).addOrderProduct(orderId,productId,quantity);
        //pui 0 times pt ca metoda nu va intra in acel if
        verify(orderProductDaoMock,Mockito.times(0)).updateOrderProduct(orderId,productId,quantity);

        assertNull(resultOrderProduct);



    }
    @Test
    void addOrderProduct_WhenThrowException(){
        int orderId=8;
        int productId=5;
        BigDecimal quantity=BigDecimal.valueOf(30);

        //Pui stocul produsului mockuit mai mic!!! ca sa-ti arunce exceptie
        Product mockedProduct=new Product();
        mockedProduct.setName("coffee");
        mockedProduct.setPrice(BigDecimal.valueOf(10));
        mockedProduct.setImage("images/coffee.jpg");
        mockedProduct.setStock(10);


        when(productServiceMock.getProductById(productId)).thenReturn(mockedProduct);
        when(orderProductDaoMock.findBy(orderId,productId)).thenReturn(null);
        doNothing().when(orderProductDaoMock).addOrderProduct(orderId,productId,quantity);
        doNothing().when(orderProductDaoMock).updateOrderProduct(orderId,productId,quantity);
        //faci asertia exceptiei dar si call la metoda subiect
        assertThrows(ValidationErrorException.class, ()->{
            OrderProduct resultOrderProduct=orderProductServiceTest.addOrderProduct(orderId,productId,quantity);
        });

        verify(productServiceMock,Mockito.times(1)).getProductById(productId);
        // pui times 0 la urmatoarele metode pt ca nu se va mai intra in ifuri
        verify(orderProductDaoMock,Mockito.times(0)).findBy(orderId,productId);
        verify(orderProductDaoMock,Mockito.times(0)).addOrderProduct(orderId,productId,quantity);
        verify(orderProductDaoMock,Mockito.times(0)).updateOrderProduct(orderId,productId,quantity);

    }
    @Test
    void deleteOrderProductBy(){
        int orderId=6;
        int productId=3;

        ArgumentCaptor<Integer>argumentCaptor1=ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer>argumentCaptor2=ArgumentCaptor.forClass(Integer.class);

        doNothing().when(orderProductDaoMock).deleteBy(argumentCaptor1.capture(),argumentCaptor2.capture());

        orderProductServiceTest.deleteOrderProductBy(orderId,productId);

        verify(orderProductDaoMock,Mockito.times(1)).deleteBy(argumentCaptor1.capture(),argumentCaptor2.capture());

        assertEquals(orderId,argumentCaptor1.getValue());
        assertEquals(productId,argumentCaptor2.getValue());
    }
    @Test
    void getOrderProductByOrderIdAndProductId(){
        int orderId=5;
        int productId=8;
        Product mockedProduct=new Product();
        mockedProduct.setName("paint cup");
        mockedProduct.setPrice(BigDecimal.valueOf(5));
        mockedProduct.setImage("images/paint_cup.jpg");
        OrderProduct expectedOrderProduct=new OrderProduct();
        expectedOrderProduct.setQuantity(BigDecimal.valueOf(10));
        expectedOrderProduct.setValue(BigDecimal.valueOf(50));
        expectedOrderProduct.setProduct(mockedProduct);

        when(orderProductDaoMock.getOrderProductByOrderIdAndProductId(orderId,productId)).thenReturn(expectedOrderProduct);

       OrderProduct resultOrderProduct=orderProductServiceTest.getOrderProductByOrderIdAndProductId(orderId,productId);

        verify(orderProductDaoMock,Mockito.times(1)).getOrderProductByOrderIdAndProductId(orderId,productId);

        assertEquals(expectedOrderProduct,resultOrderProduct);

    }
}