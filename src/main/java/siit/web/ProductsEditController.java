package siit.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import siit.model.OrderProduct;
import siit.model.Product;
import siit.service.ProductService;
import siit.utility.JsonUtil;
import siit.validation.ValidationErrorException;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/products_edit")
public class ProductsEditController extends HttpServlet {

    private static Logger logger;

    static {


        logger = Logger.getLogger(ProductsEditController.class.getName());
    }
private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        productService=new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> productList = productService.getProductsList();

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonObject = JsonUtil.convertToJsonString(productList);

        out.print(jsonObject);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject payload = JsonUtil.getJsonObjFrom(req);

        String name = payload.getString("name");
        BigDecimal weight = payload.getBigDecimal("weight");
        BigDecimal price = payload.getBigDecimal("price");
        String image = payload.getString("image");
        int stock = payload.getInt("stock");

        try {
            Product newProduct = productService.addNewProduct(name,weight,price,image,stock);
            String jsonResponse = JsonUtil.convertToJsonString(newProduct);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            out.print(jsonResponse);
            out.flush();

        }catch (ValidationErrorException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);

            var error9 = """
                    """ + e.getMessage() + """
                    """;

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(505);

            out.print(error9);
            out.flush();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("productId"));

        productService.deleteProductBy(productId);
    }
}
