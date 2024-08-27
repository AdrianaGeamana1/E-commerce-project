package siit.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import siit.service.CustomerService;

import java.io.IOException;

@WebServlet("/customer")
public class CustomerController extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/customers-list.jsp");

        req.setAttribute("customers", customerService.getCustomers());
        requestDispatcher.forward(req, resp);
    }
}
