package ua.hav.ui.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by Юля on 12.08.2016.
 */
@WebFilter("/*")
public class CodingConvertor implements Filter {
    private static final String DESIRED_CODE = "utf-8";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(DESIRED_CODE);
        servletResponse.setCharacterEncoding(DESIRED_CODE);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
