package siit.service;

import org.apache.commons.lang3.RandomStringUtils;
import siit.dao.OrderDao;
import siit.model.Customer;
import siit.model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderService {
    private OrderDao orderDao;

    public OrderService() {
        this.orderDao = new OrderDao();
    }

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Order> getAllOrdersBy(int customerId) {
        return orderDao.getOrdersBy(customerId);
    }

    public void createOrder(int customerId) {
        String number = RandomStringUtils.randomAlphabetic(9);
        LocalDateTime placed = LocalDateTime.now();
        Order updatedOrder = new Order();
        updatedOrder.setNumber(number);
        updatedOrder.setPlaced(placed);

        orderDao.insert(customerId, updatedOrder);
    }

    public void delete(int orderId) {
        orderDao.delete(orderId);
    }

//    private void test() {
//        Customer customer = Customer.builder()
//                .id(12323)
//                .name("bla bla bla")
//                .email("bla bla bla")
//                .build();
//        customer.getId();
//        customer.setBirthDate(LocalDate.of(2024, 1, 1));
//
//        Customer customer2 = new Customer();
//    }

    public Order gerOrderBy(int orderId) {
        return orderDao.getOrderBy(orderId);
    }

}
