package net.mw.system.utils;



import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import net.mw.system.config.jwt.JwtSecurityProperties;
import net.mw.system.constant.UserRoleConstants;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenUtils implements InitializingBean {

    private final JwtSecurityProperties jwtSecurityProperties;
    private static final String AUTHORITIES_KEY = "auth";
    private Key key;

    public JwtTokenUtils(JwtSecurityProperties jwtSecurityProperties) {
        this.jwtSecurityProperties = jwtSecurityProperties;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecurityProperties.getBase64Secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public  String createToken (Map<String,Object> map, List<String> roles) {
        return Jwts.builder()
        		.claim(AUTHORITIES_KEY, map)
                .claim("role", roles)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtSecurityProperties.getTokenValidityInSeconds()))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        List<String> roles = (List<String>)claims.get("role");
        List<SimpleGrantedAuthority> authorities =
                Objects.isNull(roles) ? Collections.singletonList(new SimpleGrantedAuthority(UserRoleConstants.ROLE_EDITOR)) :
                        roles.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());

        HashMap map =(HashMap) claims.get("auth");

        User principal = new User(map.get("user").toString(), map.get("password").toString(), authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            e.printStackTrace();
        }
        return false;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}


