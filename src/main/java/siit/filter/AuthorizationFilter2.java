package siit.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/customer","/static/products-edit.html","/product_update"})
public class AuthorizationFilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
       HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;

       if(isAdmin(httpServletRequest)){
           filterChain.doFilter(servletRequest,servletResponse);
       }else{
           httpServletResponse.sendRedirect("/user_profile");
       }
    }
    private boolean isAdmin(HttpServletRequest request){
        return "admin".equals(request.getSession().getAttribute("role"));
    }
}
