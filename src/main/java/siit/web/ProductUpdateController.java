package siit.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import siit.model.Customer;
import siit.model.Product;
import siit.service.ProductService;
import siit.validation.ValidationErrorException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/product_update")
public class ProductUpdateController extends HttpServlet {

    private static Logger logger;

    static {


        logger = Logger.getLogger(ProductUpdateController.class.getName());
    }
    ProductService productService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        productService=new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("productId"));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/product-update.jsp");


        Product product =productService.getProductById(productId);
        req.setAttribute("product", product);

        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("productId"));
        String name = req.getParameter("name");
        int price = Integer.parseInt(req.getParameter("price"));
        BigDecimal price2=BigDecimal.valueOf(price);
        int stock = Integer.parseInt(req.getParameter("stock"));

        Product updatedProduct=new Product();
        updatedProduct.setName(name);
        updatedProduct.setId(productId);
        updatedProduct.setPrice(price2);
        updatedProduct.setStock(stock);
        try {
            productService.updateProduct(updatedProduct);
            resp.sendRedirect("/static/products-edit.html");
        }catch (ValidationErrorException e){
            logger.log(Level.SEVERE,e.getMessage(),e);

            String error2=e.getMessage();
            req.setAttribute("error9",error2);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/product-update.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

}
