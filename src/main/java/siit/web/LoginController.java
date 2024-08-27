package siit.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import siit.dao.CustomerDao;
import siit.model.Customer;
import siit.service.CustomerService;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    public static final String LOGIN_JSP_PATH = "WEB-INF/login.jsp";
    private int numberOfLoginAttempts = 0;
    private CustomerService customerService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_JSP_PATH);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var username = req.getParameter("username");
        var password = req.getParameter("password");
        Customer customer = customerService.getCustomerExistence(username, password);

          if (customer !=null) {
            // redirect la dashboard
            String role= customer.getRoles();
            HttpSession session = req.getSession();
            session.setAttribute("logged_user", username);
            session.setAttribute("user_password",password);
            session.setAttribute("role",role);
            resp.sendRedirect("/customer");
          } else {
            // create an error message and assign it to the error variable
            numberOfLoginAttempts++;
            String error1 = "Incorrect Username or password ! Number of attempts: " + numberOfLoginAttempts;
            req.setAttribute("error", error1);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_JSP_PATH);
            requestDispatcher.forward(req, resp);
        }

    }
}
