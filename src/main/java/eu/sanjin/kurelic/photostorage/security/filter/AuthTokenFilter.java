package eu.sanjin.kurelic.photostorage.security.filter;

import eu.sanjin.kurelic.photostorage.security.service.JwtService;
import eu.sanjin.kurelic.photostorage.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

  private final JwtService service;
  private final UserDetailsServiceImpl userDetailsService;

  private static final String AUTHORIZATION_BEARER = "Bearer ";
  private static final String AUTHORIZATION_QUERY = "auth=";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      var jwt = parseJwt(request);
      if (service.validateJwtToken(jwt)) {
        var username = service.getEmailFromJwtToken(jwt);

        var userDetails = userDetailsService.loadUserByUsername(username);
        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(AUTHORIZATION_BEARER)) {
      return headerAuth.substring(AUTHORIZATION_BEARER.length());
    }

    // Get auth from URL request - this option is needed while downloading image (should be replaced with fetch-blob mechanism)
    if (StringUtils.hasText(request.getQueryString()) && request.getQueryString().contains(AUTHORIZATION_QUERY)) {
      var queryString = request.getQueryString();

      var authQuery = queryString.substring(queryString.indexOf(AUTHORIZATION_QUERY) + AUTHORIZATION_QUERY.length());
      var endOfAuthQuery = authQuery.indexOf("&");
      if (endOfAuthQuery == -1) {
        endOfAuthQuery = authQuery.length();
      }
      var auth = URLDecoder.decode(authQuery.substring(0, endOfAuthQuery), StandardCharsets.UTF_8);

      return StringUtils.hasText(auth) ? auth : null;
    }

    return null;
  }
}
