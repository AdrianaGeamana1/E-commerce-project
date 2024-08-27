package siit.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import siit.model.OrderProduct;
import siit.model.Product;
import siit.service.OrderProductService;
import siit.service.ProductService;
import siit.utility.JsonUtil;
import siit.validation.ValidationErrorException;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/order_products")
public class OrderProductController extends HttpServlet {

    private static Logger logger5;

    static {


        logger5 = Logger.getLogger(OrderProductController.class.getName());
    }

    private OrderProductService orderProductService;
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        orderProductService = new OrderProductService();
        productService=new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int customerId = Integer.parseInt(req.getParameter("customerId"));
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        List<OrderProduct> orderProducts = orderProductService.getOrderProducts(customerId, orderId);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonObject = JsonUtil.convertToJsonString(orderProducts);

        out.print(jsonObject);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var test =  """
                {
                    "product": {
                            "id": 23,
                            "name": "product_name/product_number"   
                    },
                    "quantity": 222,
                    "value": 311
                }
                """;

        JSONObject payload = JsonUtil.getJsonObjFrom(req);

        int orderId = Integer.parseInt(req.getParameter("orderId"));
        int productId = payload.getJSONObject("product").getInt("id");
        BigDecimal quantity = payload.getBigDecimal("quantity");

        try {
            OrderProduct orderProduct = orderProductService.addOrderProduct(orderId, productId, quantity);
            String jsonResponse = JsonUtil.convertToJsonString(orderProduct);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            out.print(jsonResponse);
            out.flush();
            productService.updateStockWhenAdd(productId, quantity);
        }catch (ValidationErrorException e){
            logger5.log(Level.SEVERE,e.getMessage(),e);

            var error =  """
                """ +e.getMessage()+"""
                    """;

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(500);

            out.print(error);
            out.flush();
        }

    }



    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        int productId = Integer.parseInt(req.getParameter("productId"));
        OrderProduct orderProduct=orderProductService.getOrderProductByOrderIdAndProductId(orderId,productId);
        BigDecimal quantity=orderProduct.getQuantity();


        orderProductService.deleteOrderProductBy(orderId, productId);
        productService.updateStockWhenDelete(productId,quantity);
    }
}
