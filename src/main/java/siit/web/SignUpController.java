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
import siit.validation.ValidationErrorException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/signup")
public class SignUpController extends HttpServlet {

    private static Logger logger;

    static {


        logger = Logger.getLogger(SignUpController.class.getName());
    }
    public static final String LOGIN_JSP_PATH2 = "WEB-INF/signup.jsp";
    private CustomerService customerService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_JSP_PATH2);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name=req.getParameter("name");
        String phone=String.valueOf(req.getParameter("phone"));
        String email=req.getParameter("email");
        LocalDate birthday= LocalDate.parse(req.getParameter("birthday"));
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmedPassword=req.getParameter("password2");
        if(customerService.getCustomerExistence(username,password)!=null){
            String error1="This account already exist!";
            req.setAttribute("error1",error1);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_JSP_PATH2);
            requestDispatcher.forward(req, resp);
        }
        else{
            try {
                customerService.createNewCustomer(name, phone, email, birthday, username, password, confirmedPassword);
                Customer newCustomer=customerService.getCustomerExistence(username,password);
                HttpSession httpSession=req.getSession();
                httpSession.setAttribute("logged_user",newCustomer.getUsername());
                httpSession.setAttribute("user_password",newCustomer.getPassword());
                httpSession.setAttribute("role",newCustomer.getRoles());
                resp.sendRedirect("/success_signup");
            }catch (ValidationErrorException e){
                logger.log(Level.SEVERE,e.getMessage(),e);

                String error2=e.getMessage();
                req.setAttribute("error1",error2);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_JSP_PATH2);
                requestDispatcher.forward(req, resp);
            }
        }
    }
}
