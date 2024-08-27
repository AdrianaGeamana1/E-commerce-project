package siit.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import siit.model.Customer;
import siit.model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class OrderDaoTest {
    OrderDao orderDaoTest;
    OrderDao orderDaoMock;

    @BeforeEach
    public void init() {
        orderDaoTest = new OrderDao();
        orderDaoMock=mock(OrderDao.class);
    }
    @AfterEach
    public  void setDown(){
        orderDaoTest=null;
    }

    @Test
    void getOrdersBy() {
        int customerId=0;

        List<Order> actualOrderList=orderDaoTest.getOrdersBy(customerId);

        List<Order> expectedOrderList=new ArrayList<>();

        assertEquals(expectedOrderList,actualOrderList);
        assertTrue(actualOrderList.isEmpty());
    }
    @Test
    void insertTest(){
        ArgumentCaptor<Order> valueCapture = ArgumentCaptor.forClass(Order.class);

        doNothing().when(orderDaoMock).insert(any(Integer.class),valueCapture.capture());

        Order updateOrder=new Order();
        updateOrder.setId(7);
        updateOrder.setNumber("GHRDE12EDF");
        updateOrder.setValue(543.1);
        LocalDate localDate=LocalDate.of(1999,8,5);
        LocalTime localTime=LocalTime.now();
        updateOrder.setPlaced(LocalDateTime.of(localDate,localTime));

        orderDaoMock.insert(7,updateOrder);

        assertEquals(updateOrder, valueCapture.getValue());
    }
    @Test
    void deleteOrder(){
        ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);

        doNothing().when(orderDaoMock).delete(valueCapture.capture());
        orderDaoMock.delete(8);

        assertEquals(8,valueCapture.getValue());

    }
    @Test
    void getOrderBy(){
        int orderId=0;

        Order resultOrder=orderDaoTest.getOrderBy(orderId);

        assertNull(resultOrder);
    }
    @Test
    void getTotalOrderValue(){
        String orderNumber=null;

        double totalValueResult=orderDaoTest.getTotalOrderValue(orderNumber);

        assertEquals(0,totalValueResult);
    }
    @Test
    void getTotalOrderValueWithNumber(){
        String orderNumber="ABCDEFGH45";

        double totalValueResult=orderDaoTest.getTotalOrderValue(orderNumber);

        assertEquals(0,totalValueResult);
    }
}