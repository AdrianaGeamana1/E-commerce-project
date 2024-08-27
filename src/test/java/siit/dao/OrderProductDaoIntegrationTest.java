package siit.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import siit.model.OrderProduct;
import siit.model.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderProductDaoIntegrationTest {
    OrderProductDao orderProductDao;

    @BeforeEach
    public void init() {
        orderProductDao = new OrderProductDao();
    }
    @AfterEach
    public  void setDown(){
        orderProductDao=null;
    }

    @Test
    void getOrderProducts() {
        int customerId=5;
        int orderId=2;
        OrderProduct expectedOrderProduct=new OrderProduct();
        expectedOrderProduct.setQuantity(BigDecimal.valueOf(1));
        expectedOrderProduct.setValue(BigDecimal.valueOf(5));
        expectedOrderProduct.setId(2);
        Product expectedProduct=new Product();
        expectedProduct.setName("Cat food");
        expectedProduct.setId(3);
        expectedProduct.setWeight(BigDecimal.valueOf(1000));
        expectedProduct.setPrice(BigDecimal.valueOf(5));
        expectedProduct.setImage("images/cat_food.jpeg");
        expectedProduct.setStock(48);
        expectedOrderProduct.setProduct(expectedProduct);

        List<OrderProduct> resultList=orderProductDao.getOrderProducts(customerId,orderId);
        OrderProduct resultOrderProduct=resultList.get(0);

        assertEquals(1,resultList.size());
        assertEquals(expectedOrderProduct,resultOrderProduct);

    }
    @Test
    void findBy(){
        int orderId=3;
        int productId=2;

        OrderProduct actualOrderProduct=orderProductDao.findBy(orderId,productId);

        OrderProduct expectedOrderProduct=new OrderProduct();
        expectedOrderProduct.setQuantity(BigDecimal.valueOf(13));
        expectedOrderProduct.setValue(BigDecimal.valueOf(143));
        expectedOrderProduct.setId(3);
        Product expectedProduct=new Product();
        expectedProduct.setName("Roasted beans");
        expectedProduct.setId(2);
        expectedProduct.setWeight(BigDecimal.valueOf(600));
        expectedProduct.setPrice(BigDecimal.valueOf(11));
        expectedProduct.setImage("images/roasted_beans.jpeg");
        expectedProduct.setStock(17);
        expectedOrderProduct.setProduct(expectedProduct);

        assertEquals(expectedOrderProduct,actualOrderProduct);
    }
    @Test
    void addOrderProduct(){
        int orderId=1;
        int productId=5;
        BigDecimal quantity=BigDecimal.valueOf(7);

        orderProductDao.addOrderProduct(orderId,productId,quantity);
        OrderProduct addOrderProduct=orderProductDao.findBy(orderId,productId);

        assertEquals(orderId,addOrderProduct.getId());
        assertEquals(productId,addOrderProduct.getProduct().getId());
        assertEquals(quantity,addOrderProduct.getQuantity());
    }
    @Test
    void updateOrderProduct(){
        int orderId=1;
        int productId=5;
        BigDecimal totalQuantity=BigDecimal.valueOf(20);

        orderProductDao.updateOrderProduct(orderId,productId,totalQuantity);
        OrderProduct updateOrderProduct=orderProductDao.findBy(orderId,productId);

        assertEquals(orderId,updateOrderProduct.getId());
        assertEquals(productId,updateOrderProduct.getProduct().getId());
        assertEquals(totalQuantity,updateOrderProduct.getQuantity());

    }
    @Test
    void deleteBy(){
        int orderId=1;
        int productId=5;

        orderProductDao.deleteBy(orderId,productId);
        OrderProduct deleteOrderProduct=orderProductDao.findBy(orderId,productId);

        assertNull(deleteOrderProduct);
    }
}