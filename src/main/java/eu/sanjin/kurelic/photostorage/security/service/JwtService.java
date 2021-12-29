package eu.sanjin.kurelic.photostorage.security.service;

import eu.sanjin.kurelic.photostorage.security.configuration.JwtConfig;
import eu.sanjin.kurelic.photostorage.security.model.UserDetailsModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtConfig config;

  public String generateJwtToken(Authentication authentication) {
    var userPrincipal = (UserDetailsModel) authentication.getPrincipal();

    return Jwts.builder()
      .setSubject((userPrincipal.getUsername()))
      .setIssuedAt(new Date())
      .setExpiration(DateUtils.addMinutes(new Date(), config.getJwtExpiration()))
      .signWith(SignatureAlgorithm.HS512, config.getJwtSecret())
      .compact();
  }

  public String getEmailFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(config.getJwtSecret()).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    if (StringUtils.isEmpty(authToken)) {
      return false;
    }

    try {
      Jwts.parser().setSigningKey(config.getJwtSecret()).parseClaimsJws(authToken);
      return true;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

    return false;
  }
}
