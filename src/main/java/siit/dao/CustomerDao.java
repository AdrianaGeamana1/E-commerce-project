package siit.dao;

import siit.config.DataBaseManager;
import siit.model.Customer;
import siit.web.ProductsEditController;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDao {

    private static Logger logger1;

    static {


        logger1 = Logger.getLogger(CustomerDao.class.getName());
    }
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM customers")
        ) {
            while (rs.next()) {
                customers.add(extractCustomer(rs));
            }

        } catch (SQLException e) {
            logger1.log(Level.SEVERE,e.getMessage(),e);
        }

        return customers;
    }

    private Customer extractCustomer(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String phone = rs.getString("phone");
        String email = rs.getString("email");
        LocalDate birthDate = rs.getDate("birthday").toLocalDate();
        String roles=rs.getString("roles");
        String username=rs.getString("username");
        String password=rs.getString("passwords");

        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setBirthDate(birthDate);
        customer.setRoles(roles);
        customer.setUsername(username);
        customer.setPassword(password);

        return customer;
    }

    public Customer getCustomer(int customerId) {

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
        ) {
            stmt.setInt(1, customerId);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            return extractCustomer(rs);


        } catch (SQLException e) {
            logger1.log(Level.SEVERE,e.getMessage(),e);
        }

        return null;
    }
    public Customer getCustomerExistence(String username,String password) {
        Customer customer=null;

        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM customers WHERE username=? AND passwords=?");
        ) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            rs.next();

           return customer= extractCustomer(rs);
        } catch (SQLException e) {
            logger1.log(Level.SEVERE,e.getMessage(),e);
        }
           return null;
    }
    public void update(Customer updatedCustomer) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("UPDATE customers SET name = ?, phone = ?, birthday = ? WHERE id = ?");
        ) {
            stmt.setString(1, updatedCustomer.getName());
            stmt.setString(2, updatedCustomer.getPhone());
            stmt.setDate(3, Date.valueOf(updatedCustomer.getBirthDate()));
            stmt.setInt(4, updatedCustomer.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger1.log(Level.SEVERE,e.getMessage(),e);
        }

    }

    public void createCustomer(String name, String phone, String email, LocalDate birthday, String username, String password) {
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO customers(name,phone,email,birthday,roles,username,passwords)VALUES (?,?,?,?,?,?,?)");
        ) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setDate(4, Date.valueOf(birthday));
            stmt.setString(5,"user");
            stmt.setString(6,username);
            stmt.setString(7,password);


            stmt.executeUpdate();

        } catch (SQLException e) {
            logger1.log(Level.SEVERE,e.getMessage(),e);
        }
    }

}
