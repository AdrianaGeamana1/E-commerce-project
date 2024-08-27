package siit.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import siit.dao.ProductDao;
import siit.model.Customer;
import siit.service.CustomerService;
import siit.validation.ValidationErrorException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/customer_edit")
public class CustomerEditController extends HttpServlet {
    private static Logger logger5;

    static {


        logger5 = Logger.getLogger(CustomerEditController.class.getName());
    }
    private CustomerService customerService;

    public void init() {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int customerId = Integer.parseInt(req.getParameter("id"));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/customer-edit.jsp");
        // customer id

        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            req.setAttribute("error3", "Customer with id: " + customerId + " does not exist");
        } else {
            req.setAttribute("customer", customer);
        }
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int customerId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        LocalDate birthDate = LocalDate.parse(req.getParameter("date"));

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(customerId);
        updatedCustomer.setName(name);
        updatedCustomer.setPhone(phone);
        updatedCustomer.setBirthDate(birthDate);
        try {
            customerService.updateCustomer(updatedCustomer);
            resp.sendRedirect("/customer");
        }catch (ValidationErrorException e){
            logger5.log(Level.SEVERE,e.getMessage(),e);

            String error2=e.getMessage();
            req.setAttribute("error4",error2);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/customer-edit.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
