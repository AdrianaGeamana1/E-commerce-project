package siit.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import siit.config.DataBaseManager;
import siit.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoIntegrationTest {
    CustomerDao customerDao;

    @BeforeEach
    public void init() {
        customerDao= new CustomerDao();
    }
    @AfterEach
    public  void setDown(){
        customerDao=null;
    }

    @Test
    void getAllCustomers() {

        List<Customer> resultList=customerDao.getAllCustomers();

        assertEquals(9,resultList.size());

       Customer expectedCustomer=new Customer();
       expectedCustomer.setName("claudiu nelcus");
       expectedCustomer.setPhone("0975432786");
       expectedCustomer.setEmail("clau.n@yahoo.out.rs");
       expectedCustomer.setBirthDate(LocalDate.of(2008,9,8));
       expectedCustomer.setRoles("user");
       expectedCustomer.setUsername("clau_100");
       expectedCustomer.setPassword("claudiu12");

       assertTrue(resultList.contains(expectedCustomer));
//
    }
    @Test
    void getCustomerExistenceTest(){
        String username="leodom";
        String password="123456";

        Customer customerResult=customerDao.getCustomerExistence(username,password);

        Customer expectedCustomer=new Customer();
        expectedCustomer.setId(2);
        expectedCustomer.setName("Leopold Domay");
        expectedCustomer.setPhone("+0341346253");
        expectedCustomer.setEmail("domay.leo@gmail.com");
        expectedCustomer.setBirthDate(LocalDate.of(1887,11,10));
        expectedCustomer.setUsername(username);
        expectedCustomer.setPassword(password);

        assertEquals(expectedCustomer,customerResult);

    }
    @Test
    void updateCustomer(){
        Customer updateCustomer=new Customer();
        updateCustomer.setId(22);//la fel ca in baza de date
        updateCustomer.setName("mioara borto2");
        updateCustomer.setPhone("0678123456");
        updateCustomer.setBirthDate(LocalDate.of(1987,9,5));

        customerDao.update(updateCustomer);

        Customer resultCustomer=customerDao.getCustomerExistence("miora45","mioara");

        assertEquals(resultCustomer.getName(),updateCustomer.getName());
        assertEquals(resultCustomer.getPhone(),updateCustomer.getPhone());
        assertEquals(resultCustomer.getBirthDate(),updateCustomer.getBirthDate());
    }
    @Test
    void createNewCustomer(){
        String name="Vasile Borod";
        String phone="0756884221";
        String email="vasile.borod@yahoo.com";
        LocalDate birthDate=LocalDate.of(1977,8,7);
        String username="vasile21";
        String password="vasile@12345";

        customerDao.createCustomer(name,phone,email,birthDate,username,password);

        Customer resultCustomer=customerDao.getCustomerExistence(username,password);

        assertEquals(name,resultCustomer.getName());
        assertEquals(phone,resultCustomer.getPhone());
        assertEquals(email,resultCustomer.getEmail());
        assertEquals(birthDate,resultCustomer.getBirthDate());

        this.deleteCustomer(username,password);
        Customer resultCustomer2=customerDao.getCustomerExistence(username,password);

        assertNull(resultCustomer2);

    }
    private void deleteCustomer(String username,String password){
        try (
                Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM customers WHERE username = ? AND passwords=?");
        ) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            //oops
        }
    }
}