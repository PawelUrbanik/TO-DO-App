package pl.pawel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LoggerFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(LoggerFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            if (servletRequest instanceof HttpServletRequest) {
                var httpReq = (HttpServletRequest) servletRequest;
                logger.info("msg: [doFilter] " + httpReq.getMethod() + " " + httpReq.getRequestURI());
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } finally {
            logger.info("msg: [doFilter] " + "finished");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
