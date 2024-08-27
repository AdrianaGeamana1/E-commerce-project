package siit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import siit.dao.CustomerDao;
import siit.dao.ProductDao;
import siit.model.Customer;
import siit.validation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.*;

class CustomerServiceTest {
    @Mock
    public CustomerDao customerDaoMock;
    @Mock
    public NameValidation nameValidationMock;
    @Mock
    public PhoneValidation phoneValidationMock;
    @Mock
    public EmailValidation emailValidationMock;
    @Mock
    public BirthdayValidation birthdayValidationMock;
    @Mock
    public PasswordValidation passwordValidationMock;
    @Mock
    public UsernameValidation usernameValidationMock;
    @InjectMocks
    CustomerService customerServiceTest;

    @BeforeEach
    public void setUp() {
        customerDaoMock = mock(CustomerDao.class);
        nameValidationMock = mock(NameValidation.class);
        phoneValidationMock = mock(PhoneValidation.class);
        emailValidationMock = mock(EmailValidation.class);
        birthdayValidationMock = mock(BirthdayValidation.class);
        passwordValidationMock = mock(PasswordValidation.class);
        usernameValidationMock = mock(UsernameValidation.class);
        customerServiceTest = new CustomerService(customerDaoMock, nameValidationMock, phoneValidationMock, emailValidationMock, birthdayValidationMock, passwordValidationMock, usernameValidationMock);
    }

    private List<Customer> createMockList() {
        Customer customer1 = new Customer();
        customer1.setId(7);
        customer1.setName("mircea");
        customer1.setPhone("0987654567");
        customer1.setEmail("mircea@yahoo.com");
        customer1.setBirthDate(LocalDate.of(1990, 9, 7));
        customer1.setUsername("mircea_78");
        customer1.setPassword("mircea12345");
        customer1.setRoles("user");
        Customer customer2 = new Customer();
        customer2.setId(3);
        customer2.setName("alexandra");
        customer2.setPhone("0987654567");
        customer2.setEmail("alexandra@yahoo.com");
        customer2.setBirthDate(LocalDate.of(1990, 6, 7));
        customer2.setUsername("alexandra_78");
        customer2.setPassword("alexandra12345");
        customer2.setRoles("user");
        Customer customer3 = new Customer();
        customer3.setId(1);
        customer3.setName("adriana");
        customer3.setPhone("0987654567");
        customer3.setEmail("adriana@yahoo.com");
        customer3.setBirthDate(LocalDate.of(2000, 6, 7));
        customer3.setUsername("adriana_78");
        customer3.setPassword("adriana12345");
        customer3.setRoles("admin");

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);

        return customerList;
    }

    @Test
    void getCustomers() {
        //  System.out.println(this.createMockList().get(0).getRoles());

        when(customerDaoMock.getAllCustomers()).thenReturn(this.createMockList());


        List<Customer> resultList = customerServiceTest.getCustomers();

        Mockito.verify(customerDaoMock, Mockito.times(1)).getAllCustomers();

        for (Customer customer : resultList) {
            assertTrue("user".equals(customer.getRoles()));
        }

        assertEquals(2, resultList.size());
        assertEquals(3, resultList.get(0).getId());
        assertEquals(7, resultList.get(1).getId());
    }

    @Test
    void getCustomersByUsernameAndPassword() {
        String username = "mircea_78";
        String password = "mircea12345";
        when(customerDaoMock.getAllCustomers()).thenReturn(this.createMockList());

        List<Customer> resultList = customerServiceTest.getCustomersByUsernamePassword(username, password);

        Mockito.verify(customerDaoMock, Mockito.times(1)).getAllCustomers();

        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(7);
        expectedCustomer.setName("mircea");
        expectedCustomer.setPhone("0987654567");
        expectedCustomer.setEmail("mircea@yahoo.com");
        expectedCustomer.setBirthDate(LocalDate.of(1990, 9, 7));
        expectedCustomer.setUsername("mircea_78");
        expectedCustomer.setPassword("mircea12345");
        expectedCustomer.setRoles("user");

        assertEquals(1, resultList.size());
        assertEquals(expectedCustomer, resultList.get(0));
    }

    @Test
    void getCustomerById() {
        int customerId = 3;
        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(3);
        expectedCustomer.setName("alexandra");
        expectedCustomer.setPhone("0987654567");
        expectedCustomer.setEmail("alexandra@yahoo.com");
        expectedCustomer.setBirthDate(LocalDate.of(1990, 6, 7));
        expectedCustomer.setUsername("alexandra_78");
        expectedCustomer.setPassword("alexandra12345");
        expectedCustomer.setRoles("user");

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);

        when(customerDaoMock.getCustomer(argumentCaptor.capture())).thenReturn(expectedCustomer);

        Customer customerResult = customerServiceTest.getCustomerById(customerId);

        verify(customerDaoMock, Mockito.times(1)).getCustomer(customerId);

        assertEquals(expectedCustomer, customerResult);
        assertEquals(customerId, argumentCaptor.getValue());

        Customer customerResult2 = customerServiceTest.getCustomerById(-7);

        assertNull(customerResult2);
    }

    @Test
    void updateCustomer() {
        Customer updateCustomer = new Customer();
        updateCustomer.setId(7);
        updateCustomer.setName("mirceaUpdated");
        updateCustomer.setPhone("0987654567");
        updateCustomer.setEmail("mircea@yahoo.com");
        updateCustomer.setBirthDate(LocalDate.of(1990, 9, 7));
        updateCustomer.setUsername("mircea_78");
        updateCustomer.setPassword("mircea12345");
        updateCustomer.setRoles("user");

        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        doNothing().when(nameValidationMock).nameIsEmptyOrNull(updateCustomer.getName());
        doNothing().when(nameValidationMock).verifyNameFormat(updateCustomer.getName());
        doNothing().when(nameValidationMock).verifyNameLength(updateCustomer.getName());
        doNothing().when(phoneValidationMock).phoneIsEmptyOrNull(updateCustomer.getPhone());
        doNothing().when(phoneValidationMock).verifyPhoneFormat(updateCustomer.getPhone());
        doNothing().when(birthdayValidationMock).isBirthdayFormat(updateCustomer.getBirthDate());
        doNothing().when(birthdayValidationMock).verifyValidAge(updateCustomer.getBirthDate());

        doNothing().when(customerDaoMock).update(argumentCaptor.capture());

        customerServiceTest.updateCustomer(updateCustomer);

        verify(nameValidationMock, Mockito.times(1)).nameIsEmptyOrNull(updateCustomer.getName());
        verify(nameValidationMock, Mockito.times(1)).verifyNameFormat(updateCustomer.getName());
        verify(nameValidationMock, Mockito.times(1)).verifyNameLength(updateCustomer.getName());
        verify(phoneValidationMock, Mockito.times(1)).phoneIsEmptyOrNull(updateCustomer.getPhone());
        verify(phoneValidationMock, Mockito.times(1)).verifyPhoneFormat(updateCustomer.getPhone());
        verify(birthdayValidationMock, Mockito.times(1)).isBirthdayFormat(updateCustomer.getBirthDate());
        verify(birthdayValidationMock, Mockito.times(1)).verifyValidAge(updateCustomer.getBirthDate());
        verify(customerDaoMock, Mockito.times(1)).update(argumentCaptor.capture());

        assertEquals(updateCustomer, argumentCaptor.getValue());
    }

    @Test
    void updateCustomer_WithNameException() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(7);
        updatedCustomer.setName("mirceaUpdated");
        updatedCustomer.setPhone("0987654567");
        updatedCustomer.setEmail("mircea@yahoo.com");
        updatedCustomer.setBirthDate(LocalDate.of(1990, 9, 7));
        updatedCustomer.setUsername("mircea_78");
        updatedCustomer.setPassword("mircea12345");
        updatedCustomer.setRoles("user");

        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        doNothing().when(nameValidationMock).nameIsEmptyOrNull(updatedCustomer.getName());
        doThrow(new IllegalArgumentException("Mocked exception message")).when(nameValidationMock).verifyNameFormat(updatedCustomer.getName());
        doNothing().when(nameValidationMock).verifyNameLength(updatedCustomer.getName());
        doNothing().when(phoneValidationMock).phoneIsEmptyOrNull(updatedCustomer.getPhone());
        doNothing().when(phoneValidationMock).verifyPhoneFormat(updatedCustomer.getPhone());
        doNothing().when(birthdayValidationMock).isBirthdayFormat(updatedCustomer.getBirthDate());
        doNothing().when(birthdayValidationMock).verifyValidAge(updatedCustomer.getBirthDate());

        doNothing().when(customerDaoMock).update(argumentCaptor.capture());

        assertThrows(IllegalArgumentException.class, () -> {
            customerServiceTest.updateCustomer(updatedCustomer);
        });

        verify(nameValidationMock, Mockito.times(1)).nameIsEmptyOrNull(updatedCustomer.getName());
        verify(nameValidationMock, Mockito.times(1)).verifyNameFormat(updatedCustomer.getName());
        verify(nameValidationMock, Mockito.times(0)).verifyNameLength(updatedCustomer.getName());
        verify(phoneValidationMock, Mockito.times(0)).phoneIsEmptyOrNull(updatedCustomer.getPhone());
        verify(phoneValidationMock, Mockito.times(0)).verifyPhoneFormat(updatedCustomer.getPhone());
        verify(birthdayValidationMock, Mockito.times(0)).isBirthdayFormat(updatedCustomer.getBirthDate());
        verify(birthdayValidationMock, Mockito.times(0)).verifyValidAge(updatedCustomer.getBirthDate());
        verify(customerDaoMock, Mockito.times(0)).update(argumentCaptor.capture());

    }

    @Test
    void updateCustomer_WithPhoneException() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(7);
        updatedCustomer.setName("mirceaUpdated");
        updatedCustomer.setPhone("0987654567");
        updatedCustomer.setEmail("mircea@yahoo.com");
        updatedCustomer.setBirthDate(LocalDate.of(1990, 9, 7));
        updatedCustomer.setUsername("mircea_78");
        updatedCustomer.setPassword("mircea12345");
        updatedCustomer.setRoles("user");

        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        doNothing().when(nameValidationMock).nameIsEmptyOrNull(updatedCustomer.getName());
        doNothing().when(nameValidationMock).verifyNameFormat(updatedCustomer.getName());
        doNothing().when(nameValidationMock).verifyNameLength(updatedCustomer.getName());
        doNothing().when(phoneValidationMock).phoneIsEmptyOrNull(updatedCustomer.getPhone());
        doThrow(new IllegalArgumentException("Mocked exception message")).when(phoneValidationMock).verifyPhoneFormat(updatedCustomer.getPhone());
        doNothing().when(birthdayValidationMock).isBirthdayFormat(updatedCustomer.getBirthDate());
        doNothing().when(birthdayValidationMock).verifyValidAge(updatedCustomer.getBirthDate());

        doNothing().when(customerDaoMock).update(argumentCaptor.capture());

        assertThrows(IllegalArgumentException.class, () -> {
            customerServiceTest.updateCustomer(updatedCustomer);
        });

        verify(nameValidationMock, Mockito.times(1)).nameIsEmptyOrNull(updatedCustomer.getName());
        verify(nameValidationMock, Mockito.times(1)).verifyNameFormat(updatedCustomer.getName());
        verify(nameValidationMock, Mockito.times(1)).verifyNameLength(updatedCustomer.getName());
        verify(phoneValidationMock, Mockito.times(1)).phoneIsEmptyOrNull(updatedCustomer.getPhone());
        verify(phoneValidationMock, Mockito.times(1)).verifyPhoneFormat(updatedCustomer.getPhone());
        verify(birthdayValidationMock, Mockito.times(0)).isBirthdayFormat(updatedCustomer.getBirthDate());
        verify(birthdayValidationMock, Mockito.times(0)).verifyValidAge(updatedCustomer.getBirthDate());
        verify(customerDaoMock, Mockito.times(0)).update(argumentCaptor.capture());

    }

    @Test
    void updateCustomer_WithBirthDateException() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(7);
        updatedCustomer.setName("mirceaUpdated");
        updatedCustomer.setPhone("0987654567");
        updatedCustomer.setEmail("mircea@yahoo.com");
        updatedCustomer.setBirthDate(LocalDate.of(1990, 9, 7));
        updatedCustomer.setUsername("mircea_78");
        updatedCustomer.setPassword("mircea12345");
        updatedCustomer.setRoles("user");

        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        doNothing().when(nameValidationMock).nameIsEmptyOrNull(updatedCustomer.getName());
        doNothing().when(nameValidationMock).verifyNameFormat(updatedCustomer.getName());
        doNothing().when(nameValidationMock).verifyNameLength(updatedCustomer.getName());
        doNothing().when(phoneValidationMock).phoneIsEmptyOrNull(updatedCustomer.getPhone());
        doNothing().when(phoneValidationMock).verifyPhoneFormat(updatedCustomer.getPhone());
        doThrow(new IllegalArgumentException("Mocked exception message")).when(birthdayValidationMock).isBirthdayFormat(updatedCustomer.getBirthDate());
        doNothing().when(birthdayValidationMock).verifyValidAge(updatedCustomer.getBirthDate());

        doNothing().when(customerDaoMock).update(argumentCaptor.capture());

        assertThrows(IllegalArgumentException.class, () -> {
            customerServiceTest.updateCustomer(updatedCustomer);
        });

        verify(nameValidationMock, Mockito.times(1)).nameIsEmptyOrNull(updatedCustomer.getName());
        verify(nameValidationMock, Mockito.times(1)).verifyNameFormat(updatedCustomer.getName());
        verify(nameValidationMock, Mockito.times(1)).verifyNameLength(updatedCustomer.getName());
        verify(phoneValidationMock, Mockito.times(1)).phoneIsEmptyOrNull(updatedCustomer.getPhone());
        verify(phoneValidationMock, Mockito.times(1)).verifyPhoneFormat(updatedCustomer.getPhone());
        verify(birthdayValidationMock, Mockito.times(1)).isBirthdayFormat(updatedCustomer.getBirthDate());
        verify(birthdayValidationMock, Mockito.times(0)).verifyValidAge(updatedCustomer.getBirthDate());
        verify(customerDaoMock, Mockito.times(0)).update(argumentCaptor.capture());

    }

    @Test
    void createNewCustomer() {
        String name="mircea";
        String phone="0987654567";
        String email="mircea@yahoo.com";
        LocalDate birthDate=LocalDate.of(1990, 9, 7);
        String username="mircea_78";
        String password="mircea12345";
        String confirmedPassword="mircea12345";

      ArgumentCaptor<LocalDate>argumentCaptor=ArgumentCaptor.forClass(LocalDate.class);
      ArgumentCaptor<String>argumentCaptor2=ArgumentCaptor.forClass(String.class);
      ArgumentCaptor<String>argumentCaptor3=ArgumentCaptor.forClass(String.class);
      ArgumentCaptor<String>argumentCaptor4=ArgumentCaptor.forClass(String.class);
      ArgumentCaptor<String>argumentCaptor5=ArgumentCaptor.forClass(String.class);
      ArgumentCaptor<String>argumentCaptor6=ArgumentCaptor.forClass(String.class);

        doNothing().when(nameValidationMock).nameIsEmptyOrNull(name);
        doNothing().when(nameValidationMock).verifyNameFormat(name);
        doNothing().when(nameValidationMock).verifyNameLength(name);
        doNothing().when(phoneValidationMock).phoneIsEmptyOrNull(phone);
        doNothing().when(phoneValidationMock).verifyPhoneFormat(phone);
        doNothing().when(emailValidationMock).emailIsEmptyOrNull(email);
        doNothing().when(emailValidationMock).verifyEmailFormat(email);
        doNothing().when(birthdayValidationMock).isBirthdayFormat(birthDate);
        doNothing().when(birthdayValidationMock).verifyValidAge(birthDate);
        doNothing().when(usernameValidationMock).usernameIsEmptyOrNull(username);
        doNothing().when(usernameValidationMock).verifyUsernameFormat(username);
        doNothing().when(passwordValidationMock).passwordIsEmptyOrNull(password);
        doNothing().when(passwordValidationMock).password2IsEmptyOrNull(confirmedPassword);
        doNothing().when(passwordValidationMock).verifyPasswordFormat(password);
        doNothing().when(passwordValidationMock).verifyEqualsPassword(password,confirmedPassword);

        doNothing().when(customerDaoMock).createCustomer(name,phone,email,birthDate,username,password);

        customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,confirmedPassword);

        verify(nameValidationMock, Mockito.times(1)).nameIsEmptyOrNull(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameFormat(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameLength(name);
        verify(phoneValidationMock, Mockito.times(1)).phoneIsEmptyOrNull(phone);
        verify(phoneValidationMock, Mockito.times(1)).verifyPhoneFormat(phone);
        verify(emailValidationMock, Mockito.times(1)).emailIsEmptyOrNull(email);
        verify(emailValidationMock, Mockito.times(1)).verifyEmailFormat(email);
        verify(birthdayValidationMock, Mockito.times(1)).isBirthdayFormat(birthDate);
        verify(birthdayValidationMock, Mockito.times(1)).verifyValidAge(birthDate);
        verify(usernameValidationMock, Mockito.times(1)).usernameIsEmptyOrNull(username);
        verify(usernameValidationMock, Mockito.times(1)).verifyUsernameFormat(username);
        verify(passwordValidationMock, Mockito.times(1)).passwordIsEmptyOrNull(password);
        verify(passwordValidationMock, Mockito.times(1)).password2IsEmptyOrNull(confirmedPassword);
        verify(passwordValidationMock, Mockito.times(1)).verifyPasswordFormat(password);
        verify(passwordValidationMock, Mockito.times(1)).verifyEqualsPassword(password,confirmedPassword);
        verify(customerDaoMock, Mockito.times(1)).createCustomer(argumentCaptor2.capture(),argumentCaptor3.capture(),argumentCaptor4.capture(),argumentCaptor.capture(),argumentCaptor5.capture(),argumentCaptor6.capture());

        assertEquals(name,argumentCaptor2.getValue());
        assertEquals(phone,argumentCaptor3.getValue());
        assertEquals(email,argumentCaptor4.getValue());
        assertEquals(birthDate,argumentCaptor.getValue());
        assertEquals(username,argumentCaptor5.getValue());
        assertEquals(password,argumentCaptor6.getValue());
    }
    @Test
    void createNewCustomer_WithNameException() {
        String name = "mircea";
        String phone = "0987654567";
        String email = "mircea@yahoo.com";
        LocalDate birthDate = LocalDate.of(1990, 9, 7);
        String username = "mircea_78";
        String password = "mircea12345";
        String confirmedPassword = "mircea12345";

        doNothing().when(nameValidationMock).nameIsEmptyOrNull(name);
        doThrow(new IllegalArgumentException("Mocked exception message")).when(nameValidationMock).verifyNameFormat(name);
        doNothing().when(nameValidationMock).verifyNameLength(name);
        doNothing().when(phoneValidationMock).phoneIsEmptyOrNull(phone);
        doNothing().when(phoneValidationMock).verifyPhoneFormat(phone);
        doNothing().when(emailValidationMock).emailIsEmptyOrNull(email);
        doNothing().when(emailValidationMock).verifyEmailFormat(email);
        doNothing().when(birthdayValidationMock).isBirthdayFormat(birthDate);
        doNothing().when(birthdayValidationMock).verifyValidAge(birthDate);
        doNothing().when(usernameValidationMock).usernameIsEmptyOrNull(username);
        doNothing().when(usernameValidationMock).verifyUsernameFormat(username);
        doNothing().when(passwordValidationMock).passwordIsEmptyOrNull(password);
        doNothing().when(passwordValidationMock).password2IsEmptyOrNull(confirmedPassword);
        doNothing().when(passwordValidationMock).verifyPasswordFormat(password);
        doNothing().when(passwordValidationMock).verifyEqualsPassword(password, confirmedPassword);

        doNothing().when(customerDaoMock).createCustomer(name, phone, email, birthDate, username, password);

        assertThrows(IllegalArgumentException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,confirmedPassword);
        });

        verify(nameValidationMock, Mockito.times(1)).nameIsEmptyOrNull(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameFormat(name);
        verify(nameValidationMock, Mockito.times(0)).verifyNameLength(name);
        verify(phoneValidationMock, Mockito.times(0)).phoneIsEmptyOrNull(phone);
        verify(phoneValidationMock, Mockito.times(0)).verifyPhoneFormat(phone);
        verify(emailValidationMock, Mockito.times(0)).emailIsEmptyOrNull(email);
        verify(emailValidationMock, Mockito.times(0)).verifyEmailFormat(email);
        verify(birthdayValidationMock, Mockito.times(0)).isBirthdayFormat(birthDate);
        verify(birthdayValidationMock, Mockito.times(0)).verifyValidAge(birthDate);
        verify(usernameValidationMock, Mockito.times(0)).usernameIsEmptyOrNull(username);
        verify(usernameValidationMock, Mockito.times(0)).verifyUsernameFormat(username);
        verify(passwordValidationMock, Mockito.times(0)).passwordIsEmptyOrNull(password);
        verify(passwordValidationMock, Mockito.times(0)).password2IsEmptyOrNull(confirmedPassword);
        verify(passwordValidationMock, Mockito.times(0)).verifyPasswordFormat(password);
        verify(passwordValidationMock, Mockito.times(0)).verifyEqualsPassword(password, confirmedPassword);
        verify(customerDaoMock, Mockito.times(0)).createCustomer(name,phone,email,birthDate,username,password);
      }
    @Test
    void createNewCustomer_WithPhoneException() {
        String name = "mircea";
        String phone = "0987654567";
        String email = "mircea@yahoo.com";
        LocalDate birthDate = LocalDate.of(1990, 9, 7);
        String username = "mircea_78";
        String password = "mircea12345";
        String confirmedPassword = "mircea12345";

        doNothing().when(nameValidationMock).nameIsEmptyOrNull(name);
        doNothing().when(nameValidationMock).verifyNameFormat(name);
        doNothing().when(nameValidationMock).verifyNameLength(name);
        doNothing().when(phoneValidationMock).phoneIsEmptyOrNull(phone);
        doThrow(new IllegalArgumentException("Mocked exception message")).when(phoneValidationMock).verifyPhoneFormat(phone);
        doNothing().when(emailValidationMock).emailIsEmptyOrNull(email);
        doNothing().when(emailValidationMock).verifyEmailFormat(email);
        doNothing().when(birthdayValidationMock).isBirthdayFormat(birthDate);
        doNothing().when(birthdayValidationMock).verifyValidAge(birthDate);
        doNothing().when(usernameValidationMock).usernameIsEmptyOrNull(username);
        doNothing().when(usernameValidationMock).verifyUsernameFormat(username);
        doNothing().when(passwordValidationMock).passwordIsEmptyOrNull(password);
        doNothing().when(passwordValidationMock).password2IsEmptyOrNull(confirmedPassword);
        doNothing().when(passwordValidationMock).verifyPasswordFormat(password);
        doNothing().when(passwordValidationMock).verifyEqualsPassword(password, confirmedPassword);

        doNothing().when(customerDaoMock).createCustomer(name, phone, email, birthDate, username, password);

        assertThrows(IllegalArgumentException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,confirmedPassword);
        });

        verify(nameValidationMock, Mockito.times(1)).nameIsEmptyOrNull(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameFormat(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameLength(name);
        verify(phoneValidationMock, Mockito.times(1)).phoneIsEmptyOrNull(phone);
        verify(phoneValidationMock, Mockito.times(1)).verifyPhoneFormat(phone);
        verify(emailValidationMock, Mockito.times(0)).emailIsEmptyOrNull(email);
        verify(emailValidationMock, Mockito.times(0)).verifyEmailFormat(email);
        verify(birthdayValidationMock, Mockito.times(0)).isBirthdayFormat(birthDate);
        verify(birthdayValidationMock, Mockito.times(0)).verifyValidAge(birthDate);
        verify(usernameValidationMock, Mockito.times(0)).usernameIsEmptyOrNull(username);
        verify(usernameValidationMock, Mockito.times(0)).verifyUsernameFormat(username);
        verify(passwordValidationMock, Mockito.times(0)).passwordIsEmptyOrNull(password);
        verify(passwordValidationMock, Mockito.times(0)).password2IsEmptyOrNull(confirmedPassword);
        verify(passwordValidationMock, Mockito.times(0)).verifyPasswordFormat(password);
        verify(passwordValidationMock, Mockito.times(0)).verifyEqualsPassword(password, confirmedPassword);
        verify(customerDaoMock, Mockito.times(0)).createCustomer(name,phone,email,birthDate,username,password);
    }
    @Test
    void createNewCustomer_WithEmailException() {
        String name = "mircea";
        String phone = "0987654567";
        String email = "mircea@yahoo.com";
        LocalDate birthDate = LocalDate.of(1990, 9, 7);
        String username = "mircea_78";
        String password = "mircea12345";
        String confirmedPassword = "mircea12345";

        doNothing().when(nameValidationMock).nameIsEmptyOrNull(name);
        doNothing().when(nameValidationMock).verifyNameFormat(name);
        doNothing().when(nameValidationMock).verifyNameLength(name);
        doNothing().when(phoneValidationMock).phoneIsEmptyOrNull(phone);
        doNothing().when(phoneValidationMock).verifyPhoneFormat(phone);
        doNothing().when(emailValidationMock).emailIsEmptyOrNull(email);
        doThrow(new IllegalArgumentException("Mocked exception message")).when(emailValidationMock).verifyEmailFormat(email);
        doNothing().when(birthdayValidationMock).isBirthdayFormat(birthDate);
        doNothing().when(birthdayValidationMock).verifyValidAge(birthDate);
        doNothing().when(usernameValidationMock).usernameIsEmptyOrNull(username);
        doNothing().when(usernameValidationMock).verifyUsernameFormat(username);
        doNothing().when(passwordValidationMock).passwordIsEmptyOrNull(password);
        doNothing().when(passwordValidationMock).password2IsEmptyOrNull(confirmedPassword);
        doNothing().when(passwordValidationMock).verifyPasswordFormat(password);
        doNothing().when(passwordValidationMock).verifyEqualsPassword(password, confirmedPassword);

        doNothing().when(customerDaoMock).createCustomer(name, phone, email, birthDate, username, password);

        assertThrows(IllegalArgumentException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,confirmedPassword);
        });

        verify(nameValidationMock, Mockito.times(1)).nameIsEmptyOrNull(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameFormat(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameLength(name);
        verify(phoneValidationMock, Mockito.times(1)).phoneIsEmptyOrNull(phone);
        verify(phoneValidationMock, Mockito.times(1)).verifyPhoneFormat(phone);
        verify(emailValidationMock, Mockito.times(1)).emailIsEmptyOrNull(email);
        verify(emailValidationMock, Mockito.times(1)).verifyEmailFormat(email);
        verify(birthdayValidationMock, Mockito.times(0)).isBirthdayFormat(birthDate);
        verify(birthdayValidationMock, Mockito.times(0)).verifyValidAge(birthDate);
        verify(usernameValidationMock, Mockito.times(0)).usernameIsEmptyOrNull(username);
        verify(usernameValidationMock, Mockito.times(0)).verifyUsernameFormat(username);
        verify(passwordValidationMock, Mockito.times(0)).passwordIsEmptyOrNull(password);
        verify(passwordValidationMock, Mockito.times(0)).password2IsEmptyOrNull(confirmedPassword);
        verify(passwordValidationMock, Mockito.times(0)).verifyPasswordFormat(password);
        verify(passwordValidationMock, Mockito.times(0)).verifyEqualsPassword(password, confirmedPassword);
        verify(customerDaoMock, Mockito.times(0)).createCustomer(name,phone,email,birthDate,username,password);
    }
    @Test
    void createNewCustomer_WithBirthDateException() {
        String name = "mircea";
        String phone = "0987654567";
        String email = "mircea@yahoo.com";
        LocalDate birthDate = LocalDate.of(1990, 9, 7);
        String username = "mircea_78";
        String password = "mircea12345";
        String confirmedPassword = "mircea12345";

        doNothing().when(nameValidationMock).nameIsEmptyOrNull(name);
        doNothing().when(nameValidationMock).verifyNameFormat(name);
        doNothing().when(nameValidationMock).verifyNameLength(name);
        doNothing().when(phoneValidationMock).phoneIsEmptyOrNull(phone);
        doNothing().when(phoneValidationMock).verifyPhoneFormat(phone);
        doNothing().when(emailValidationMock).emailIsEmptyOrNull(email);
        doNothing().when(emailValidationMock).verifyEmailFormat(email);
        doNothing().when(birthdayValidationMock).isBirthdayFormat(birthDate);
        doThrow(new IllegalArgumentException("Mocked exception message")).when(birthdayValidationMock).verifyValidAge(birthDate);
        doNothing().when(usernameValidationMock).usernameIsEmptyOrNull(username);
        doNothing().when(usernameValidationMock).verifyUsernameFormat(username);
        doNothing().when(passwordValidationMock).passwordIsEmptyOrNull(password);
        doNothing().when(passwordValidationMock).password2IsEmptyOrNull(confirmedPassword);
        doNothing().when(passwordValidationMock).verifyPasswordFormat(password);
        doNothing().when(passwordValidationMock).verifyEqualsPassword(password, confirmedPassword);

        doNothing().when(customerDaoMock).createCustomer(name, phone, email, birthDate, username, password);

        assertThrows(IllegalArgumentException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,confirmedPassword);
        });

        verify(nameValidationMock, Mockito.times(1)).nameIsEmptyOrNull(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameFormat(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameLength(name);
        verify(phoneValidationMock, Mockito.times(1)).phoneIsEmptyOrNull(phone);
        verify(phoneValidationMock, Mockito.times(1)).verifyPhoneFormat(phone);
        verify(emailValidationMock, Mockito.times(1)).emailIsEmptyOrNull(email);
        verify(emailValidationMock, Mockito.times(1)).verifyEmailFormat(email);
        verify(birthdayValidationMock, Mockito.times(1)).isBirthdayFormat(birthDate);
        verify(birthdayValidationMock, Mockito.times(1)).verifyValidAge(birthDate);
        verify(usernameValidationMock, Mockito.times(0)).usernameIsEmptyOrNull(username);
        verify(usernameValidationMock, Mockito.times(0)).verifyUsernameFormat(username);
        verify(passwordValidationMock, Mockito.times(0)).passwordIsEmptyOrNull(password);
        verify(passwordValidationMock, Mockito.times(0)).password2IsEmptyOrNull(confirmedPassword);
        verify(passwordValidationMock, Mockito.times(0)).verifyPasswordFormat(password);
        verify(passwordValidationMock, Mockito.times(0)).verifyEqualsPassword(password, confirmedPassword);
        verify(customerDaoMock, Mockito.times(0)).createCustomer(name,phone,email,birthDate,username,password);
    }
    @Test
    void createNewCustomer_WithUsernameException() {
        String name = "mircea";
        String phone = "0987654567";
        String email = "mircea@yahoo.com";
        LocalDate birthDate = LocalDate.of(1990, 9, 7);
        String username = "mircea_78";
        String password = "mircea12345";
        String confirmedPassword = "mircea12345";

        doNothing().when(nameValidationMock).nameIsEmptyOrNull(name);
        doNothing().when(nameValidationMock).verifyNameFormat(name);
        doNothing().when(nameValidationMock).verifyNameLength(name);
        doNothing().when(phoneValidationMock).phoneIsEmptyOrNull(phone);
        doNothing().when(phoneValidationMock).verifyPhoneFormat(phone);
        doNothing().when(emailValidationMock).emailIsEmptyOrNull(email);
        doNothing().when(emailValidationMock).verifyEmailFormat(email);
        doNothing().when(birthdayValidationMock).isBirthdayFormat(birthDate);
        doNothing().when(birthdayValidationMock).verifyValidAge(birthDate);
        doNothing().when(usernameValidationMock).usernameIsEmptyOrNull(username);
        doThrow(new IllegalArgumentException("Mocked exception message")).when(usernameValidationMock).verifyUsernameFormat(username);
        doNothing().when(passwordValidationMock).passwordIsEmptyOrNull(password);
        doNothing().when(passwordValidationMock).password2IsEmptyOrNull(confirmedPassword);
        doNothing().when(passwordValidationMock).verifyPasswordFormat(password);
        doNothing().when(passwordValidationMock).verifyEqualsPassword(password, confirmedPassword);

        doNothing().when(customerDaoMock).createCustomer(name, phone, email, birthDate, username, password);

        assertThrows(IllegalArgumentException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,confirmedPassword);
        });

        verify(nameValidationMock, Mockito.times(1)).nameIsEmptyOrNull(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameFormat(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameLength(name);
        verify(phoneValidationMock, Mockito.times(1)).phoneIsEmptyOrNull(phone);
        verify(phoneValidationMock, Mockito.times(1)).verifyPhoneFormat(phone);
        verify(emailValidationMock, Mockito.times(1)).emailIsEmptyOrNull(email);
        verify(emailValidationMock, Mockito.times(1)).verifyEmailFormat(email);
        verify(birthdayValidationMock, Mockito.times(1)).isBirthdayFormat(birthDate);
        verify(birthdayValidationMock, Mockito.times(1)).verifyValidAge(birthDate);
        verify(usernameValidationMock, Mockito.times(1)).usernameIsEmptyOrNull(username);
        verify(usernameValidationMock, Mockito.times(1)).verifyUsernameFormat(username);
        verify(passwordValidationMock, Mockito.times(0)).passwordIsEmptyOrNull(password);
        verify(passwordValidationMock, Mockito.times(0)).password2IsEmptyOrNull(confirmedPassword);
        verify(passwordValidationMock, Mockito.times(0)).verifyPasswordFormat(password);
        verify(passwordValidationMock, Mockito.times(0)).verifyEqualsPassword(password, confirmedPassword);
        verify(customerDaoMock, Mockito.times(0)).createCustomer(name,phone,email,birthDate,username,password);
    }
    @Test
    void createNewCustomer_WithPasswordException() {
        String name = "mircea";
        String phone = "0987654567";
        String email = "mircea@yahoo.com";
        LocalDate birthDate = LocalDate.of(1990, 9, 7);
        String username = "mircea_78";
        String password = "mircea12345";
        String confirmedPassword = "mircea12345";

        doNothing().when(nameValidationMock).nameIsEmptyOrNull(name);
        doNothing().when(nameValidationMock).verifyNameFormat(name);
        doNothing().when(nameValidationMock).verifyNameLength(name);
        doNothing().when(phoneValidationMock).phoneIsEmptyOrNull(phone);
        doNothing().when(phoneValidationMock).verifyPhoneFormat(phone);
        doNothing().when(emailValidationMock).emailIsEmptyOrNull(email);
        doNothing().when(emailValidationMock).verifyEmailFormat(email);
        doNothing().when(birthdayValidationMock).isBirthdayFormat(birthDate);
        doNothing().when(birthdayValidationMock).verifyValidAge(birthDate);
        doNothing().when(usernameValidationMock).usernameIsEmptyOrNull(username);
        doNothing().when(usernameValidationMock).verifyUsernameFormat(username);
        doNothing().when(passwordValidationMock).passwordIsEmptyOrNull(password);
        doNothing().when(passwordValidationMock).password2IsEmptyOrNull(confirmedPassword);
        doNothing().when(passwordValidationMock).verifyPasswordFormat(password);
        doThrow(new IllegalArgumentException("Mocked exception message")).when(passwordValidationMock).verifyEqualsPassword(password, confirmedPassword);

        doNothing().when(customerDaoMock).createCustomer(name, phone, email, birthDate, username, password);

        assertThrows(IllegalArgumentException.class, ()->{
            customerServiceTest.createNewCustomer(name,phone,email,birthDate,username,password,confirmedPassword);
        });

        verify(nameValidationMock, Mockito.times(1)).nameIsEmptyOrNull(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameFormat(name);
        verify(nameValidationMock, Mockito.times(1)).verifyNameLength(name);
        verify(phoneValidationMock, Mockito.times(1)).phoneIsEmptyOrNull(phone);
        verify(phoneValidationMock, Mockito.times(1)).verifyPhoneFormat(phone);
        verify(emailValidationMock, Mockito.times(1)).emailIsEmptyOrNull(email);
        verify(emailValidationMock, Mockito.times(1)).verifyEmailFormat(email);
        verify(birthdayValidationMock, Mockito.times(1)).isBirthdayFormat(birthDate);
        verify(birthdayValidationMock, Mockito.times(1)).verifyValidAge(birthDate);
        verify(usernameValidationMock, Mockito.times(1)).usernameIsEmptyOrNull(username);
        verify(usernameValidationMock, Mockito.times(1)).verifyUsernameFormat(username);
        verify(passwordValidationMock, Mockito.times(1)).passwordIsEmptyOrNull(password);
        verify(passwordValidationMock, Mockito.times(1)).password2IsEmptyOrNull(confirmedPassword);
        verify(passwordValidationMock, Mockito.times(1)).verifyPasswordFormat(password);
        verify(passwordValidationMock, Mockito.times(1)).verifyEqualsPassword(password, confirmedPassword);
        verify(customerDaoMock, Mockito.times(0)).createCustomer(name,phone,email,birthDate,username,password);
    }
@Test
    void getCustomerExistence(){
        String username="adriana";
         String password="adriana12345";
    Customer expectedCustomer = new Customer();
    expectedCustomer.setId(7);
    expectedCustomer.setName("mirceaUpdated");
    expectedCustomer.setPhone("0987654567");
    expectedCustomer.setEmail("mircea@yahoo.com");
    expectedCustomer.setBirthDate(LocalDate.of(1990, 9, 7));
    expectedCustomer.setUsername("mircea_78");
    expectedCustomer.setPassword("mircea12345");
    expectedCustomer.setRoles("user");

    when(customerDaoMock.getCustomerExistence(username,password)).thenReturn(expectedCustomer);

    Customer resultCustomer=customerServiceTest.getCustomerExistence(username,password);

    verify(customerDaoMock,Mockito.times(1)).getCustomerExistence(username,password);

    assertEquals(expectedCustomer,resultCustomer);
  }
}