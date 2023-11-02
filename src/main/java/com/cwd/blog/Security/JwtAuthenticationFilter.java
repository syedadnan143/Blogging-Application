package com.cwd.blog.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get token
        String requestToken = request.getHeader("Authorization");

        // Bearer 2352523sdgsg
        System.out.println(requestToken);
        String username = null;
        String token = null;

        if (requestToken!= null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);
            System.out.println("Received JWT token: " +token);
            
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
                System.out.println("Username from token: " + username);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JwtToken");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT token has expired");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid jwt");
            }
        } else {
            System.out.println("Jwt token does not begin with bearer");
            
        }
        

        // Once we get the token, now validate
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                // Everything is good
                // Authenticate
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("Invalid Jwt Token");
            }
         } else {
            System.out.println("User name is null or context is null");
        }


        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
