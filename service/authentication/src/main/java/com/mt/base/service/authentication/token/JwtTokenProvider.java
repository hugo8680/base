package com.mt.base.service.authentication.token;

import com.mt.base.service.authentication.config.properties.TokenConfigProperties;
import com.mt.base.service.authentication.entity.Role;
import com.mt.base.service.authentication.service.AuthDetailsService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Custom Security Provider
 */
@Component
public class JwtTokenProvider implements InitializingBean {
    private TokenConfigProperties tokenConfigProperties;
    private AuthDetailsService authDetailsService;
    private Key key;

    public JwtTokenProvider(TokenConfigProperties properties, AuthDetailsService detailsService) {
        this.tokenConfigProperties = properties;
        this.authDetailsService = detailsService;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] encodedKey = Base64.getEncoder().encode(this.tokenConfigProperties.getSecret().getBytes());
        this.key = new SecretKeySpec(encodedKey, 0, encodedKey.length, SignatureAlgorithm.HS256.getJcaName());
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        Long now = new Date().getTime();
        Date validity = new Date(now + tokenConfigProperties.getExpiration());
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(this.tokenConfigProperties.getAuthorizeKey(), authorities)
                .signWith(this.key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String createTokenOld(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(this.tokenConfigProperties.getAuthorizeKey(), roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));
        Date now = new Date();
        Date validity = new Date(now.getTime() + this.tokenConfigProperties.getExpiration());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(this.tokenConfigProperties.getAuthorizeKey()).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Authentication getAuthenticationOld(String token) {
        UserDetails userDetails = this.authDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Key getSecretKey() {
        byte[] encoedKey = Base64.getEncoder().encode(this.tokenConfigProperties.getSecret().getBytes());
        return new SecretKeySpec(encoedKey, 0, encoedKey.length, SignatureAlgorithm.HS256.getJcaName());
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(this.tokenConfigProperties.getHeader());
        if (bearerToken != null && bearerToken.startsWith(this.tokenConfigProperties.getTokenHeader())) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateTokenOld(String token) {
        try {
            Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | io.jsonwebtoken.security.SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }
}
