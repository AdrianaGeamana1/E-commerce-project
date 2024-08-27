package siit.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import siit.model.Order;
import siit.service.OrderService;
import siit.utility.JsonUtil;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/order_edit")
public class OrderEditController extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        Order order = orderService.gerOrderBy(orderId);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonObject = JsonUtil.convertToJsonString(order);

        out.print(jsonObject);
        out.flush();
    }
}
