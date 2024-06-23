package com.project.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
//validating the jwt token when request is hit


public class JwtConfigValidator extends OncePerRequestFilter {

    public static final String SECRET_KEY = "cjanrygfakdirh vvsmtocfswbvcjh moqjdxhgcgtrbdjm";
    public static final String header = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // access the token coming from request header
        String authHeader = request.getHeader(header);
        System.out.println("auth header ---->  "+authHeader);

        if(authHeader!=null){
            authHeader = authHeader.substring(7);

            try{
                SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(authHeader).getBody();

                String username = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            catch(Exception e){
                throw new BadCredentialsException("Invalid token");
            }
        }

        filterChain.doFilter(request,response);

    }
}
