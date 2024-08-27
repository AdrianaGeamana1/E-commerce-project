package siit.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebFilter(urlPatterns = {"/signup","/user_profile","/customer","/customer_edit","/static/customer-order-edit-rest.html","/order","/static/products-edit.html","/product_update"})
public class AuthorizationFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (!req.getServletPath().equals("/signup") && req.getSession(true).getAttribute("logged_user") == null) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/login");
        } else if (req.getServletPath().equals("/signup")&& req.getSession(true).getAttribute("logged_user") != null) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/customer");
        } else {
            // permitem requestului sa se duca la servleturi
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
