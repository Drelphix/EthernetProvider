package by.training.ethernetprovider.controller.filter;

import by.training.ethernetprovider.controller.LocaleHolder;
import by.training.ethernetprovider.controller.command.AttributeAndParameter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(urlPatterns = "/pages/*", dispatcherTypes = {DispatcherType.FORWARD,
                                                        DispatcherType.INCLUDE,
                                                        DispatcherType.REQUEST})
public class LocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(request.getSession(false) != null && request.getSession(false).getAttribute(AttributeAndParameter.LOCALE) == null){
            request.getSession().setAttribute(AttributeAndParameter.LOCALE, LocaleHolder.DEFAULT);
        }
        filterChain.doFilter(request,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
