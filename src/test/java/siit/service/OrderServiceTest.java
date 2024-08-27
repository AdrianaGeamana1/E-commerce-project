package siit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import siit.dao.OrderDao;
import siit.model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    OrderDao orderDaoMock;
    @InjectMocks
    OrderService orderServiceTest;
    @BeforeEach
    public void setUp(){
        orderDaoMock=mock(OrderDao.class);
        orderServiceTest=new OrderService(orderDaoMock);
    }
    private List<Order> createMockList(){
        Order order1=new Order();
        order1.setId(3);
        order1.setNumber("ABCDEFG55");
        order1.setValue(76.5);
        LocalDate localDate=LocalDate.of(1999,8,6);
        LocalTime localTime=LocalTime.of(20,9,34);
        order1.setPlaced(LocalDateTime.of(localDate,localTime));
        Order order2=new Order();
        order2.setId(3);
        order2.setNumber("ABCunf45");
        order2.setValue(96.5);
        LocalDate localDate2=LocalDate.of(1990,8,7);
        LocalTime localTime2=LocalTime.of(23,9,34);
        order2.setPlaced(LocalDateTime.of(localDate2,localTime2));
        Order order3=new Order();
        order3.setId(3);
        order3.setNumber("GHJCunf45");
        order3.setValue(100.5);
        LocalDate localDate3=LocalDate.of(2000,8,7);
        LocalTime localTime3=LocalTime.of(17,9,34);
        order3.setPlaced(LocalDateTime.of(localDate3,localTime3));

        return new ArrayList<>(List.of(order1,order2,order3));
    }

    @Test
    void getAllOrdersBy() {
        int customerId=9;
        ArgumentCaptor<Integer>argumentCaptor=ArgumentCaptor.forClass(Integer.class);

        when(orderDaoMock.getOrdersBy(argumentCaptor.capture())).thenReturn(this.createMockList());

        List<Order> resultList=orderServiceTest.getAllOrdersBy(customerId);

        verify(orderDaoMock, Mockito.times(1)).getOrdersBy(argumentCaptor.capture());

        assertEquals(this.createMockList(),resultList);
        assertEquals(customerId,argumentCaptor.getValue());
    }
    @Test
    void createOrder(){
        int customerId=9;

        ArgumentCaptor<Integer>argumentCaptor1=ArgumentCaptor.forClass(Integer.class);

        doNothing().when(orderDaoMock).insert(argumentCaptor1.capture(),any(Order.class));

        orderServiceTest.createOrder(customerId);

        verify(orderDaoMock,Mockito.times(1)).insert(argumentCaptor1.capture(),any(Order.class));

        assertEquals(customerId,argumentCaptor1.getValue());
    }
    @Test
    void delete(){
        int orderId=7;

        ArgumentCaptor<Integer>argumentCaptor=ArgumentCaptor.forClass(Integer.class);

        doNothing().when(orderDaoMock).delete(argumentCaptor.capture());

        orderServiceTest.delete(orderId);

        verify(orderDaoMock,Mockito.times(1)).delete(argumentCaptor.capture());

        assertEquals(orderId,argumentCaptor.getValue());
    }
    @Test
    void getOrderBy(){
        int orderId=9;
        Order mockedOrder=new Order();
        mockedOrder.setId(3);
        mockedOrder.setNumber("ABCDEFG55");
        mockedOrder.setValue(76.5);
        LocalDate localDate=LocalDate.of(1999,8,6);
        LocalTime localTime=LocalTime.of(20,9,34);
        mockedOrder.setPlaced(LocalDateTime.of(localDate,localTime));

        ArgumentCaptor<Integer>argumentCaptor=ArgumentCaptor.forClass(Integer.class);

        when(orderDaoMock.getOrderBy(argumentCaptor.capture())).thenReturn(mockedOrder);

        Order resultOrder=orderServiceTest.gerOrderBy(orderId);

        verify(orderDaoMock,Mockito.times(1)).getOrderBy(argumentCaptor.capture());

        assertEquals(mockedOrder,resultOrder);
        assertEquals(orderId,argumentCaptor.getValue());
    }

}