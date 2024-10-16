package com.stpp.movies.services.jwt;

import com.stpp.movies.dto.user.UserResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
public class JwtService {
  @Value("${security.jwt.secret-key}")
  private String secretKey;

  @Value("${security.jwt.expiration-time}")
  private long jwtExpiration;

  Logger logger = Logger.getLogger(JwtService.class.getName());

  public String extractUsername(String token) {
    String claims = extractClaim(token, Claims::getSubject);
    logger.severe("CLAIMS ARE AFTER APPLICATION:" + claims);
    return claims;
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    logger.severe("CLAIMS ARE:" + claims);
    return claimsResolver.apply(claims);
  }

  //  CIA KEICIA IS DETAILS I DTO
  public String generateToken(UserResponseDto userResponseDto) {
    return generateToken(new HashMap<>(), userResponseDto);
  }

  public String generateToken(Map<String, Object> extraClaims, UserResponseDto userResponseDto) {
    return buildToken(extraClaims, userResponseDto, jwtExpiration);
  }

  public long getExpirationTime() {
    return jwtExpiration;
  }

  private String buildToken(Map<String, Object> extraClaims, UserResponseDto userResponseDto, long expiration) {
    extraClaims.put("role", userResponseDto.getRole().name());
    extraClaims.put("status", userResponseDto.getStatus().name());

    return Jwts
      .builder()
      .setClaims(extraClaims)
      .setSubject(userResponseDto.getUsername())
      .setSubject(userResponseDto.getId().toString())
      .setSubject(userResponseDto.getStatus().name())
      .setSubject(userResponseDto.getRole().name())
      .setSubject(userResponseDto.getEmail())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + expiration))
      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(getSignInKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
