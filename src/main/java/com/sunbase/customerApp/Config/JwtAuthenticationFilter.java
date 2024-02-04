package com.sunbase.customerApp.Config;

import com.sunbase.customerApp.Config.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 * Here we want every time to filter request send by the user
 * and do all the job that we want to do.
 * we can directly implement the Filter chain,
 * But we go with the OncePerRequestFilter i.e provided by the spring
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
//        validate the request header, is the header contains the valid JWT or not
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
//        Now extract the JWT token
        jwt = authHeader.substring(7);
//      extreact the user email from JWT token
        userEmail = jwtService.extractUsername(jwt);
//      if user is not authenticated
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            if user is present in the database or not
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//            if the token is valid or not
            if(jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
//                update our SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
//        send the request to dispatcherServlet
        filterChain.doFilter(request, response);
    }
}
