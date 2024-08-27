package siit.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import siit.model.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerDaoTest {
    CustomerDao customerDaoTest;
    CustomerDao customerDaoMock;

    @BeforeEach
    public void init() {
        customerDaoTest = new CustomerDao();
        customerDaoMock=mock(CustomerDao.class);
    }
    @AfterEach
    public  void setDown(){
        customerDaoTest=null;
    }

    @Test
    void getAllCustomers() {

        Customer customer1=new Customer();
        customer1.setId(8);
        customer1.setName("Marius");
        customer1.setPhone("0987345234");
        customer1.setEmail("marius.d@gmail.com");
        customer1.setBirthDate(LocalDate.of(2000,9,5));
        customer1.setRoles("user");
        customer1.setUsername("marius_55");
        customer1.setPassword("marius123456");
        List<Customer> expectedCustomerList=new ArrayList<>(List.of(customer1));

        when(customerDaoMock.getAllCustomers()).thenReturn(expectedCustomerList);

        List<Customer> resultList=customerDaoMock.getAllCustomers();

        assertEquals(expectedCustomerList.size(),resultList.size());
        assertEquals(expectedCustomerList.get(0),resultList.get(0));
    }
    @Test
    void getCustomerTest(){
        int customerId=0;

        Customer customerResult=customerDaoTest.getCustomer(customerId);

        assertNull(customerResult);
    }
    @Test
    void getCustomerExistenceTest(){
        String username=null;
        String password=null;

        Customer customerResult=customerDaoTest.getCustomerExistence(username,password);

        assertNull(customerResult);
    }
    @Test
    void updateCustomerTest(){
        ArgumentCaptor<Customer> valueCapture = ArgumentCaptor.forClass(Customer.class);

        doNothing().when(customerDaoMock).update(valueCapture.capture());

        Customer updateCustomer=new Customer();
        updateCustomer.setId(9);
        updateCustomer.setName("Adrian");
        updateCustomer.setPhone("7865432678");
        updateCustomer.setEmail("adrian@yahoo.com");
        updateCustomer.setUsername("adi_99");
        updateCustomer.setPassword("adrian12345");
        updateCustomer.setBirthDate(LocalDate.of(2003,7,3));

        customerDaoMock.update(updateCustomer);

        assertEquals(updateCustomer, valueCapture.getValue());
    }
    @Test
    void createCustomerTest(){
        ArgumentCaptor<LocalDate> valueCapture = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<String> valueCapture2 = ArgumentCaptor.forClass(String.class);

        doNothing().when(customerDaoMock).createCustomer(valueCapture2.capture(),any(String.class),any(String.class),valueCapture.capture(),any(String.class),any(String.class));

       String name="Elisabeta";
       String phone="7865432345";
       String email="eli.c@gmail.com";
       LocalDate birthDate=LocalDate.of(1987,7,8);
       String username="eli_78";
       String password="elisabeta@23";

       customerDaoMock.createCustomer(name,phone,email,birthDate,username,password);

        assertEquals(birthDate, valueCapture.getValue());
        assertEquals(name, valueCapture2.getValue());
    }
}