package siit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import siit.config.DataBaseManager;
import siit.dao.ProductDao;
import siit.model.Customer;
import siit.validation.ValidationErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CustomerServiceIntegrationTest {
     CustomerService customerServiceTest;
    @BeforeEach
    public void init() {
        customerServiceTest = new CustomerService();
    }
    @Test
    void getCustomers() {
        List<Customer> resultList=customerServiceTest.getCustomers();
        int expectedFirstId=1;
        int expectedLastId=51;

        assertEquals(8,resultList.size());

        for(Customer customer:resultList){
            assertNotEquals("admin",customer.getRoles());
        }
        assertEquals(expectedFirstId,resultList.get(0).getId());
        assertEquals(expectedLastId,resultList.get(7).getId());
    }
    @Test
    void getCustomersByUsernameAndPassword(){
        String username="clau_100";
        String password="claudiu12";
        Customer expectedResult=new Customer();
        expectedResult.setName("claudiu nelcus");
        expectedResult.setPhone("0975432786");
        expectedResult.setEmail("clau.n@yahoo.out.rs");
        expectedResult.setBirthDate(LocalDate.of(2008,9,8));
        expectedResult.setUsername("clau_100");
        expectedResult.setPassword("claudiu12");
        expectedResult.setRoles("user");

        List<Customer> resultList=customerServiceTest.getCustomersByUsernamePassword(username,password);

        assertEquals(1,resultList.size());
        assertEquals(expectedResult,resultList.get(0));
    }
    @Test
    void getCustomerById(){
        int customerId=51;
        Customer expectedCustomer=new Customer();
        expectedCustomer.setName("claudiu nelcus");
        expectedCustomer.setPhone("0975432786");
        expectedCustomer.setEmail("clau.n@yahoo.out.rs");
        expectedCustomer.setBirthDate(LocalDate.of(2008,9,8));
        expectedCustomer.setUsername("clau_100");
        expectedCustomer.setPassword("claudiu12");
        expectedCustomer.setRoles("user");

        Customer result=customerServiceTest.getCustomerById(customerId);

        assertEquals(expectedCustomer,result);
    }
    @Test
    void getCustomerById_GetNull(){
        int customerId= -7;

        Customer result=customerServiceTest.getCustomerById(customerId);

        assertNull(result);
    }
    @Test
    void updateCustomer(){
        Customer updateCustomer=new Customer();
        updateCustomer.setId(51);
        updateCustomer.setName("claudiu nelcus update");
        updateCustomer.setPhone("0975432786");
        updateCustomer.setEmail("clau.n@yahoo.out.rs");
        updateCustomer.setBirthDate(LocalDate.of(2008,9,8));
        updateCustomer.setUsername("clau_100");
        updateCustomer.setPassword("claudiu12");
        updateCustomer.setRoles("user");

        customerServiceTest.updateCustomer(updateCustomer);

        Customer resultUpdatedCustomer=customerServiceTest.getCustomerById(updateCustomer.getId());

        assertEquals(updateCustomer.getName(),resultUpdatedCustomer.getName());

        Customer backCustomer=new Customer();
        backCustomer.setId(51);
        backCustomer.setName("claudiu nelcus");
        backCustomer.setPhone("0975432786");
        backCustomer.setEmail("clau.n@yahoo.out.rs");
        backCustomer.setBirthDate(LocalDate.of(2008,9,8));
        backCustomer.setUsername("clau_100");
        backCustomer.setPassword("claudiu12");
        backCustomer.setRoles("user");

        this.updateBackCustomer(backCustomer);
    }
    @Test
    void updateCustomer_WithNameException(){
        Customer updateCustomer=new Customer();
        updateCustomer.setId(51);
        updateCustomer.setName("claudiu nelcus_$$$$$$$$");
        updateCustomer.setPhone("0975432786");
        updateCustomer.setEmail("clau.n@yahoo.out.rs");
        updateCustomer.setBirthDate(LocalDate.of(2008,9,8));
        updateCustomer.setUsername("clau_100");
        updateCustomer.setPassword("claudiu12");
        updateCustomer.setRoles("user");

        assertThrows(ValidationErrorException.class, ()->{
            customerServiceTest.updateCustomer(updateCustomer);
        });

        Customer resultUpdatedCustomer=customerServiceTest.getCustomerById(updateCustomer.getId());

        assertNotEquals(updateCustomer.getName(),resultUpdatedCustomer.getName());
    }
    @Test
    void updateCustomer_WithPhoneException(){
        Customer updateCustomer=new Customer();
        updateCustomer.setId(51);
        updateCustomer.setName("claudiu nelcus");
        updateCustomer.setPhone("");
        updateCustomer.setEmail("clau.n@yahoo.out.rs");
        updateCustomer.setBirthDate(LocalDate.of(2008,9,8));
        updateCustomer.setUsername("clau_100");
        updateCustomer.setPassword("claudiu12");
        updateCustomer.setRoles("user");

        assertThrows(ValidationErrorException.class, ()->{
            customerServiceTest.updateCustomer(updateCustomer);
        });

        Customer resultUpdatedCustomer=customerServiceTest.getCustomerById(updateCustomer.getId());

        assertNotEquals(updateCustomer.getPhone(),resultUpdatedCustomer.getPhone());
    }
    @Test
    void updateCustomer_WithBirthdateException(){
        Customer updateCustomer=new Customer();
        updateCustomer.setId(51);
        updateCustomer.setName("claudiu nelcus");
        updateCustomer.setPhone("");
        updateCustomer.setEmail("clau.n@yahoo.out.rs");
        updateCustomer.setBirthDate(LocalDate.of(1000,9,8));
        updateCustomer.setUsername("clau_100");
        updateCustomer.setPassword("claudiu12");
        updateCustomer.setRoles("user");

        assertThrows(ValidationErrorException.class, ()->{
            customerServiceTest.updateCustomer(updateCustomer);
        });

        Customer resultUpdatedCustomer=customerServiceTest.getCustomerById(updateCustomer.getId());

        assertNotEquals(updateCustomer.getBirthDate(),resultUpdatedCustomer.getBirthDate());
    }
    @Test
    void createCustomer(){
        String name="Eric Miclea";
        String phone="0768123456";
        String email="eric.miclea@yahoo.com";
        LocalDate birthDate=LocalDate.of(1990,7,5);
        String username="eric_78";
        String password="eric1234";
        String password2="eric1234";

        customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,password2);

        List<Customer>result=customerServiceTest.getCustomersByUsernamePassword(username,password);
        Customer createdCustomerResult=result.get(0);

        assertEquals(name,createdCustomerResult.getName());
        assertEquals(email,createdCustomerResult.getEmail());
        assertEquals(password,createdCustomerResult.getPassword());

        this.deleteCustomer(username,password);
    }
    @Test
    void createCustomer_WithNameException(){
        String name="";
        String phone="0768123456";
        String email="eric.miclea@yahoo.com";
        LocalDate birthDate=LocalDate.of(1990,7,5);
        String username="eric_78";
        String password="eric1234";
        String password2="eric1234";

        assertThrows(ValidationErrorException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,password2);
        });

        List<Customer>resultListCreatedCustomer=customerServiceTest.getCustomersByUsernamePassword(username,password);

       assertTrue(resultListCreatedCustomer.isEmpty());
    }
    @Test
    void createCustomer_WithPhoneException(){
        String name="eric miclea";
        String phone="0768123456.........+%";
        String email="eric.miclea@yahoo.com";
        LocalDate birthDate=LocalDate.of(1990,7,5);
        String username="eric_78";
        String password="eric1234";
        String password2="eric1234";

        assertThrows(ValidationErrorException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,password2);
        });

        List<Customer>resultListCreatedCustomer=customerServiceTest.getCustomersByUsernamePassword(username,password);

        assertTrue(resultListCreatedCustomer.isEmpty());
    }
    @Test
    void createCustomer_WithEmailException(){
        String name="eric miclea";
        String phone="0768123456";
        String email="eric.miclea.yahoo.com";
        LocalDate birthDate=LocalDate.of(1990,7,5);
        String username="eric_78";
        String password="eric1234";
        String password2="eric1234";

        assertThrows(ValidationErrorException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,password2);
        });

        List<Customer>resultListCreatedCustomer=customerServiceTest.getCustomersByUsernamePassword(username,password);

        assertTrue(resultListCreatedCustomer.isEmpty());
    }
    @Test
    void createCustomer_WithBirthdateException(){
        String name="eric miclea";
        String phone="0768123456";
        String email="eric.miclea@yahoo.com";
        LocalDate birthDate=LocalDate.of(1000,7,5);
        String username="eric_78";
        String password="eric1234";
        String password2="eric1234";

        assertThrows(ValidationErrorException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,password2);
        });

        List<Customer>resultListCreatedCustomer=customerServiceTest.getCustomersByUsernamePassword(username,password);

        assertTrue(resultListCreatedCustomer.isEmpty());
    }
    @Test
    void createCustomer_WithUsernameException(){
        String name="eric miclea";
        String phone="0768123456";
        String email="eric.miclea@yahoo.com";
        LocalDate birthDate=LocalDate.of(1000,7,5);
        String username="";
        String password="eric1234";
        String password2="eric1234";

        assertThrows(ValidationErrorException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,password2);
        });

        List<Customer>resultListCreatedCustomer=customerServiceTest.getCustomersByUsernamePassword(username,password);

        assertTrue(resultListCreatedCustomer.isEmpty());
    }
    @Test
    void createCustomer_WithPasswordException(){
        String name="eric miclea";
        String phone="0768123456";
        String email="eric.miclea@yahoo.com";
        LocalDate birthDate=LocalDate.of(1000,7,5);
        String username="";
        String password="eric1234";
        String password2="eric1234$$$$$$$$$$$";

        assertThrows(ValidationErrorException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,password2);
        });

        List<Customer>resultListCreatedCustomer=customerServiceTest.getCustomersByUsernamePassword(username,password);

        assertTrue(resultListCreatedCustomer.isEmpty());
    }
    @Test
    void getCustomerExistence(){
        String username="clau_100";
        String password="claudiu12";
        Customer expectedResult=new Customer();
        expectedResult.setName("claudiu nelcus");
        expectedResult.setPhone("0975432786");
        expectedResult.setEmail("clau.n@yahoo.out.rs");
        expectedResult.setBirthDate(LocalDate.of(2008,9,8));
        expectedResult.setUsername("clau_100");
        expectedResult.setPassword("claudiu12");
        expectedResult.setRoles("user");

        Customer customerResult=customerServiceTest.getCustomerExistence(username,password);

        assertEquals(expectedResult,customerResult);

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
    private void updateBackCustomer(Customer backCustomer){
        customerServiceTest.updateCustomer(backCustomer);
    }
}