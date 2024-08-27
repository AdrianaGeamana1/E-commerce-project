package siit.dao;

import siit.config.DataBaseManager;
import siit.model.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDao {
    private static Logger logger2;

    static {


        logger2 = Logger.getLogger(OrderDao.class.getName());
    }
    public List<Order> getOrdersBy(int customerId) {
        List<Order> orders = new ArrayList<>();

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM orders WHERE customer_id = ?");
        ) {
            stmt.setInt(1, customerId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(extractOrder(rs));
            }

        } catch (SQLException e) {
            logger2.log(Level.SEVERE,e.getMessage(),e);
        }

        return orders;
    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String number = rs.getString("number");
        LocalDateTime placed = rs.getTimestamp("placed").toLocalDateTime();

        Order order = new Order();
        order.setId(id);
        order.setValue(this.getTotalOrderValue(number));
        order.setNumber(number);
        order.setPlaced(placed);

        return order;
    }

    public void insert(int customerId, Order updatedOrder) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO orders (number, placed, customer_id) VALUES (?,?,?)");
        ) {
            stmt.setString(1, updatedOrder.getNumber());
            stmt.setTimestamp(2, Timestamp.valueOf(updatedOrder.getPlaced().truncatedTo(ChronoUnit.SECONDS)));
            stmt.setInt(3, customerId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            logger2.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    public void delete(int orderId) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM orders WHERE id = ?");
        ) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger2.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    public Order getOrderBy(int orderId) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM orders WHERE id = ?");
        ) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return extractOrder(rs);
        } catch (SQLException e) {
            logger2.log(Level.SEVERE,e.getMessage(),e);
        }

        return null;
    }

    public Double getTotalOrderValue(String orderNumber){
        double totalValue=0;
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT op.quantity,p.name,p.price FROM orders_products op JOIN orders o ON o.id=op.order_id JOIN products p ON p.id=op.product_id WHERE o.number=?");
        ) {
            stmt.setString(1, orderNumber);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double price=rs.getDouble("price");
                double quantity=rs.getDouble("quantity");
                double partialValue=quantity*price;
                totalValue=totalValue+(partialValue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalValue;
    }

}
