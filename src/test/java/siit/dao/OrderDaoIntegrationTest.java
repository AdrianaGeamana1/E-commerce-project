package siit.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import siit.config.DataBaseManager;
import siit.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class OrderDaoIntegrationTest {
    OrderDao orderDaoTest;

    @BeforeEach
    public void init() {
        orderDaoTest = new OrderDao();
    }
    @AfterEach
    public  void setDown(){
        orderDaoTest=null;
    }

    @Test
    void getOrdersBy() {
        int customerId=51;

        List<Order> resultList=orderDaoTest.getOrdersBy(customerId);

        Order expectedOrder=new Order();
        expectedOrder.setId(53);
        expectedOrder.setNumber("BRelTLPaA");
        LocalDate localDate=LocalDate.of(2024,2,28);
        LocalTime localTime=LocalTime.of(15,2,30);
        expectedOrder.setPlaced(LocalDateTime.of(localDate,localTime));
        expectedOrder.setValue(1268.0);

        List<Order> expectedList=new ArrayList<>(List.of(expectedOrder));

        assertEquals(expectedList.size(),resultList.size());
        assertEquals(expectedList.get(0),resultList.get(0));
    }
    @Test
    void insertTest(){
        int customerId=51;
        Order newOrder=new Order();
        newOrder.setNumber("ADRIANA23");
        newOrder.setPlaced(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        newOrder.setValue(0.0);

        orderDaoTest.insert(customerId,newOrder);
        List<Order> resultList=orderDaoTest.getOrdersBy(customerId);

        assertTrue(resultList.contains(newOrder));

        this.deleteOrder(newOrder.getNumber());

    }
    @Test
    void deleteTest(){
        int orderId=55;

        orderDaoTest.delete(orderId);
        Order deleteOrder=orderDaoTest.getOrderBy(orderId);

        assertNull(deleteOrder);
    }
    @Test
    void getOrderBy(){
        int orderId=6;

        Order resultOrder=orderDaoTest.getOrderBy(orderId);

        Order expectedOrder=new Order();
        expectedOrder.setId(orderId);
        expectedOrder.setValue(141.0);
        expectedOrder.setNumber("FNR98M772");
        LocalDate localDate=LocalDate.of(1999,7,12);
        LocalTime localTime=LocalTime.of(12,20,30);
        expectedOrder.setPlaced(LocalDateTime.of(localDate,localTime));

         assertTrue(resultOrder.equals(expectedOrder));
    }
    @Test
    void getTotalOrderValue(){
        String orderNumber="FNR98M772";

        double resultValue=orderDaoTest.getTotalOrderValue(orderNumber);

        assertEquals(141.0,resultValue);
    }
    private void deleteOrder(String orderNumber){
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM orders WHERE number = ?");
        ) {
            stmt.setString(1, orderNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            //oops
        }
    }
}