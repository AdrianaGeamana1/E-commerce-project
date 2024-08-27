package siit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import siit.dao.OrderProductDao;
import siit.model.OrderProduct;
import siit.model.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderProductServiceIntegrationTest {
    OrderProductService orderProductServiceTest;
    @BeforeEach
    public void setUp(){
        orderProductServiceTest=new OrderProductService();
    }

    @Test
    void getOrderProducts() {
        int customerId=5;
        int orderId=1;
        OrderProduct expectedOrderProduct1=new OrderProduct();
        expectedOrderProduct1.setQuantity(BigDecimal.valueOf(2));
        expectedOrderProduct1.setValue(BigDecimal.valueOf(24));
        Product product1=new Product();
        product1.setName("Hanlogen lights");
        product1.setPrice(BigDecimal.valueOf(12));
        product1.setImage("images/hologen_lights.jpg");
        expectedOrderProduct1.setProduct(product1);
        OrderProduct expectedOrderProduct2=new OrderProduct();
        expectedOrderProduct2.setQuantity(BigDecimal.valueOf(4));
        expectedOrderProduct2.setValue(BigDecimal.valueOf(44));
        Product product2=new Product();
        product2.setName("Roasted beans");
        product2.setPrice(BigDecimal.valueOf(11));
        product2.setImage("images/roasted_beans.jpeg");
        expectedOrderProduct2.setProduct(product2);

        List<OrderProduct>resultList=orderProductServiceTest.getOrderProducts(customerId,orderId);

        assertEquals(2,resultList.size());
        assertTrue(resultList.contains(expectedOrderProduct1));
        assertTrue(resultList.contains(expectedOrderProduct2));
    }

    @Test
    void addOrderProduct_WhenOrderProductExist() {
        int orderId=1;
        int productId=1;
        BigDecimal quantity=BigDecimal.valueOf(4);
        BigDecimal initialQuantity=BigDecimal.valueOf(2);
        BigDecimal expectedUpdatedQuantity=initialQuantity.add(quantity);

       OrderProduct orderProductResult= orderProductServiceTest.addOrderProduct(orderId,productId,quantity);

       assertEquals(expectedUpdatedQuantity,orderProductResult.getQuantity());

       this.updateQuantityBack(orderId,productId,initialQuantity);

    }
    @Test
    void addOrderProduct_WhenOrderProductNotExist() {
        int orderId=1;
        int productId=3;
        BigDecimal quantity=BigDecimal.valueOf(9);

        OrderProduct orderProductResult= orderProductServiceTest.addOrderProduct(orderId,productId,quantity);
        //pt ca acest order product nu exista si va creea unul nou astfel ca quantity va fi egal cu quantity orderProduc rezultat

        assertEquals(quantity,orderProductResult.getQuantity());

        this.deleteNewCreatedOrderProduct(orderId,productId);

    }


    @Test
    void getOrderProductByOrderIdAndProductId() {
        int orderId=1;
        int productId=2;

        OrderProduct expectedOrderProduct=new OrderProduct();
        expectedOrderProduct.setQuantity(BigDecimal.valueOf(4));
        expectedOrderProduct.setValue(BigDecimal.valueOf(44));
        Product product=new Product();
        product.setName("Roasted beans");
        product.setPrice(BigDecimal.valueOf(11));
        product.setImage("images/roasted_beans.jpeg");
        expectedOrderProduct.setProduct(product);

        OrderProduct orderProductResult=orderProductServiceTest.getOrderProductByOrderIdAndProductId(orderId,productId);

        assertEquals(expectedOrderProduct,orderProductResult);
    }
    private void updateQuantityBack(int orderId,int productId,BigDecimal quantity){
        OrderProductDao orderProductDao=new OrderProductDao();
        orderProductDao.updateOrderProduct(orderId,productId,quantity);

    }
    private void deleteNewCreatedOrderProduct(int orderId,int productId){
        orderProductServiceTest.deleteOrderProductBy(orderId,productId);
    }
}