package siit.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import siit.model.OrderProduct;
import siit.model.Product;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderProductDaoTest {

    OrderProductDao orderProductDao;

    @BeforeEach
    public void init() {
        orderProductDao = new OrderProductDao();
    }
    @AfterEach
    public  void setDown(){
        orderProductDao=null;
    }
    private static Stream<Arguments> provideArgumentsForGetOrderProducts() {
        return Stream.of(
                Arguments.of(0,0),
                Arguments.of(Integer.MIN_VALUE,Integer.MIN_VALUE),
                Arguments.of(Integer.MAX_VALUE,Integer.MAX_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForGetOrderProducts")
    void getOrderProducts(int customerId, int orderId) {

        List<OrderProduct> result = orderProductDao.getOrderProducts(customerId, orderId);
        List<OrderProduct> expectedResult = new ArrayList<>();

        assertEquals(expectedResult, result);
        assertTrue(result.isEmpty());
    }
    private static Stream<Arguments> provideArgumentsForFindBy() {
        return Stream.of(
                Arguments.of(0,0),
                Arguments.of(Integer.MIN_VALUE,Integer.MIN_VALUE),
                Arguments.of(Integer.MAX_VALUE,Integer.MAX_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindBy")
    void findByTest(int orderId,int productId) {

        OrderProduct orderProductResult = orderProductDao.findBy(orderId, productId);

        assertNull(orderProductResult);
    }

    @Test
    void addOrderProductTest() {
        OrderProductDao orderProductDao1 = mock(OrderProductDao.class);
        ArgumentCaptor<BigDecimal> valueCapture = ArgumentCaptor.forClass(BigDecimal.class);
        doNothing().when(orderProductDao1).addOrderProduct(any(Integer.class), any(Integer.class), valueCapture.capture());

        orderProductDao1.addOrderProduct(0, 0, BigDecimal.valueOf(0));

        assertEquals(BigDecimal.valueOf(0), valueCapture.getValue());
    }

    @Test
    void updateOrderProductTest() {
        OrderProductDao orderProductDao1 = mock(OrderProductDao.class);
        ArgumentCaptor<BigDecimal> valueCapture = ArgumentCaptor.forClass(BigDecimal.class);
        doNothing().when(orderProductDao1).updateOrderProduct(any(Integer.class), any(Integer.class), valueCapture.capture());

        orderProductDao1.updateOrderProduct(3, 7, BigDecimal.valueOf(9));

        assertEquals(BigDecimal.valueOf(9), valueCapture.getValue());
    }

    @Test
    void deleteByTest() {
        OrderProductDao orderProductDao1 = mock(OrderProductDao.class);
        ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(orderProductDao1).deleteBy(any(Integer.class), valueCapture.capture());

        orderProductDao1.deleteBy(3, 7);

        assertEquals(7, valueCapture.getValue());
    }

    @Test
    void getOrderProductByOrderIdAndProductIdTest() {
        int orderId = 0;
        int productId = 0;

        OrderProduct orderProductResult = orderProductDao.getOrderProductByOrderIdAndProductId(orderId, productId);

        assertNull(orderProductResult);

    }
}