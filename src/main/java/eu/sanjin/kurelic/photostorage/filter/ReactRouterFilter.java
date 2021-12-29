package eu.sanjin.kurelic.photostorage.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class ReactRouterFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    var uri = ((HttpServletRequest) servletRequest).getRequestURI();
    log.info(uri);

    servletRequest.getRequestDispatcher("/").forward(servletRequest, servletResponse);
  }
}
