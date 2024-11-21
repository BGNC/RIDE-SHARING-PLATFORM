package com.bgnc.Uber_Clone_Backend.jwt;

import com.bgnc.Uber_Clone_Backend.enums.Role;
import com.bgnc.Uber_Clone_Backend.exception.BaseException;
import com.bgnc.Uber_Clone_Backend.exception.ErrorMessage;
import com.bgnc.Uber_Clone_Backend.exception.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;


@RequiredArgsConstructor
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;




    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        String username = null;

        try {
            // Extract username from token
            username = jwtService.getUsernameByToken(token);

            // If username is valid and no authentication is set in the context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details from the database
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate token and roles
                if (userDetails != null && jwtService.isTokenValid(token)) {
                    Set<Role> roles = jwtService.getRolesByToken(token);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(userDetails);



                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    throw new BaseException(new ErrorMessage(MessageType.INVALID_TOKEN, "Token is invalid"));
                }
            }

        } catch (ExpiredJwtException ex) {
            throw new BaseException(new ErrorMessage(MessageType.TOKEN_IS_EXPIRED, "Token has expired"));
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Authentication failed"));
        }

        filterChain.doFilter(request, response);
    }
}