package siit.service;

import siit.dao.CustomerDao;
import siit.model.Customer;
import siit.validation.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerService {
    private final CustomerDao customerDao;
    private final NameValidation nameValidation;
    private final PhoneValidation phoneValidation;
    private final EmailValidation emailValidation;
    private final BirthdayValidation birthdayValidation;
    private final PasswordValidation passwordValidation;
    private final UsernameValidation usernameValidation;

    public CustomerService() {
        this.customerDao = new CustomerDao();
        this.nameValidation=new NameValidation();
        this.phoneValidation=new PhoneValidation();
        this.emailValidation=new EmailValidation();
        this.birthdayValidation=new BirthdayValidation();
        this.passwordValidation=new PasswordValidation();
        this.usernameValidation=new UsernameValidation();
    }

    public CustomerService(CustomerDao customerDao, NameValidation nameValidation, PhoneValidation phoneValidation, EmailValidation emailValidation, BirthdayValidation birthdayValidation, PasswordValidation passwordValidation, UsernameValidation usernameValidation) {
        this.customerDao = customerDao;
        this.nameValidation = nameValidation;
        this.phoneValidation = phoneValidation;
        this.emailValidation = emailValidation;
        this.birthdayValidation = birthdayValidation;
        this.passwordValidation = passwordValidation;
        this.usernameValidation = usernameValidation;
    }

    public List<Customer> getCustomers() {
        return customerDao.getAllCustomers().stream()
                .filter(customer->customer.getRoles().equals("user"))
                .sorted(Comparator.comparing(Customer::getId))
                .collect(Collectors.toList());
    }
    public List<Customer>getCustomersByUsernamePassword(String username,String password){
        return customerDao.getAllCustomers().stream()
                .filter(customer->customer.getUsername().equals(username)&&customer.getPassword().equals(password))
                .collect(Collectors.toList());
    }

    public Customer getCustomerById(int customerId) {
        if (customerId <= 0) {
            return null;
        }

        return customerDao.getCustomer(customerId);
    }

    public void updateCustomer(Customer updatedCustomer) throws ValidationErrorException{
        nameValidation.nameIsEmptyOrNull(updatedCustomer.getName());
        nameValidation.verifyNameFormat(updatedCustomer.getName());
        nameValidation.verifyNameLength(updatedCustomer.getName());
        phoneValidation.phoneIsEmptyOrNull(updatedCustomer.getPhone());
        phoneValidation.verifyPhoneFormat(updatedCustomer.getPhone());
        birthdayValidation.isBirthdayFormat(updatedCustomer.getBirthDate());
        birthdayValidation.verifyValidAge(updatedCustomer.getBirthDate());

        customerDao.update(updatedCustomer);
    }

    public void createNewCustomer(String name, String phone, String email, LocalDate birthday, String username, String password,String confirmedPassword)throws ValidationErrorException {
            nameValidation.nameIsEmptyOrNull(name);
            nameValidation.verifyNameFormat(name);
            nameValidation.verifyNameLength(name);
            phoneValidation.phoneIsEmptyOrNull(phone);
            phoneValidation.verifyPhoneFormat(phone);
            emailValidation.emailIsEmptyOrNull(email);
            emailValidation.verifyEmailFormat(email);
            birthdayValidation.isBirthdayFormat(birthday);
            birthdayValidation.verifyValidAge(birthday);
            usernameValidation.usernameIsEmptyOrNull(username);
            usernameValidation.verifyUsernameFormat(username);
            passwordValidation.passwordIsEmptyOrNull(password);
            passwordValidation.password2IsEmptyOrNull(confirmedPassword);
            passwordValidation.verifyPasswordFormat(password);
            passwordValidation.verifyEqualsPassword(password,confirmedPassword);

            customerDao.createCustomer(name,phone,email,birthday,username,password);
    }

    public Customer getCustomerExistence(String username, String password) {
        return customerDao.getCustomerExistence(username,password);
    }

}
