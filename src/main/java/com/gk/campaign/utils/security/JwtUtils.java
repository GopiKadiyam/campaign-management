package com.gk.campaign.utils.security;

import com.gk.campaign.service.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
//    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
//    @Value("${cm.app.jwtSecret}")
//    private String jwtSecret;

    @Value("${cm.app.jwtExpirationMs}")
    private int jwtExpirationMs;

//    private Key key() {
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//    }

//    public String generateJwtToken(Authentication authentication) {
//
//        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//
//        return Jwts.builder()
//                .setSubject((userPrincipal.getUsername()))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//                .signWith(key(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String getUserNameFromJwtToken(String token) {
//        return Jwts.parserBuilder().setSigningKey(key()).build()
//                .parseClaimsJws(token).getBody().getSubject();
//    }
//
//    public boolean validateJwtToken(String authToken) {
//        try {
////            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
//            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
//            return true;
//        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty: {}", e.getMessage());
//        }
//
//        return false;
//    }

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    //private final long expiration = 86400000; // 1 day

    /**
     * Generate a JWT token with username and role
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", userPrincipal.getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    /**
     * Validate JWT token
     */
    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    /**
     * Extract username from JWT token
     */
    public String getUserNameFromJwtToken(String token) {
        return validateToken(token).getBody().getSubject();
    }

    /**
     * Extract role from JWT token
     */
    public List<String> getUserRoleFromJwtToken(String token) {
        return validateToken(token).getBody().get("roles", List.class);
    }
}
