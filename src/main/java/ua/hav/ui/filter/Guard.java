package ua.hav.ui.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


/**
 * Created by Юля on 24.08.2016.
 */
@WebFilter("/*")
public class Guard implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String requestURI = ((HttpServletRequest) request).getRequestURI();
//        System.out.println("requestURI = " + requestURI);
//        System.out.println("request = " + request);
//        System.out.println("response = " + response);
//        System.out.println(((HttpServletRequest) request).getQueryString());
//        System.out.println("((HttpServletRequest) request).isRequestedSessionIdValid() = " + ((HttpServletRequest) request).isRequestedSessionIdValid());
//        System.out.println("((HttpServletRequest) request).getRemoteUser() = " + ((HttpServletRequest) request).getRemoteUser());
//        System.out.println("((HttpServletRequest) request).getHeaderNames(). = " + ((HttpServletRequest) request).getHeaderNames());
//
//        if (requestURI.equals("/Library")){
//            System.out.println("Guard /Library");
//        }
//        if (request.getAttribute("logged") == null) {
//            System.out.println("Guard null");
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//            return;
//        }
//        System.out.println("Guard ok");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
