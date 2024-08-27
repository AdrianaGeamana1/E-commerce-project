package siit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import siit.model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceIntegrationTest {
    OrderService orderServiceTest;
    @BeforeEach
    public void setUp(){
        orderServiceTest=new OrderService();
    }

    @Test
    void getAllOrdersBy() {
        int customerId=5;
        Order expectedOrder=new Order();
        expectedOrder.setId(1);
        expectedOrder.setNumber("FNR982774");
        expectedOrder.setValue(68.0);
        LocalDate localDate=LocalDate.of(1999,7,10);
        LocalTime localTime=LocalTime.of(10,20,30);
        expectedOrder.setPlaced(LocalDateTime.of(localDate,localTime).truncatedTo(ChronoUnit.SECONDS));

        List<Order>resulList=orderServiceTest.getAllOrdersBy(customerId);

        assertEquals(2,resulList.size());
        assertTrue(resulList.contains(expectedOrder));
    }
    @Test
    void getOrderBy(){
        int orderId=1;
        Order expectedOrder=new Order();
        expectedOrder.setId(1);
        expectedOrder.setNumber("FNR982774");
        expectedOrder.setValue(68.0);
        LocalDate localDate=LocalDate.of(1999,7,10);
        LocalTime localTime=LocalTime.of(10,20,30);
        expectedOrder.setPlaced(LocalDateTime.of(localDate,localTime).truncatedTo(ChronoUnit.SECONDS));

        Order resultOrder=orderServiceTest.gerOrderBy(orderId);

        assertEquals(expectedOrder,resultOrder);

    }
}