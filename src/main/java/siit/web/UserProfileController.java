package siit.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import siit.service.CustomerService;

import java.io.IOException;
@WebServlet("/user_profile")
public class UserProfileController extends HttpServlet {
    private CustomerService customerService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/customers-list.jsp");
        HttpSession session = req.getSession();
        String username= (String) session.getAttribute("logged_user");
        String password= (String) session.getAttribute("user_password");

        req.setAttribute("customers", customerService.getCustomersByUsernamePassword(username,password));
        requestDispatcher.forward(req, resp);
    }
}
