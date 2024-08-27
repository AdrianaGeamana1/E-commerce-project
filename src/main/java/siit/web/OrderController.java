package siit.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import siit.service.CustomerService;
import siit.service.OrderService;
import siit.utility.JsonUtil;

import java.io.IOException;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    private CustomerService customerService;
    private OrderService orderService;


    public void init() {
        customerService = new CustomerService();
        orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int customerId = Integer.parseInt(req.getParameter("id"));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/customer-orders.jsp");

        req.setAttribute("customer", customerService.getCustomerById(customerId));
        req.setAttribute("orders", orderService.getAllOrdersBy(customerId));
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json = JsonUtil.getJsonObjFrom(req);

        int customerId = json.getInt("id");
        orderService.createOrder(customerId);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json = JsonUtil.getJsonObjFrom(req);

        int orderId = json.getInt("orderId");
        orderService.delete(orderId);
    }
}

