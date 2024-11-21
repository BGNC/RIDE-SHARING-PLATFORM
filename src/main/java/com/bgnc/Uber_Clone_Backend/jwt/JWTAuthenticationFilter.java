package com.bgnc.Uber_Clone_Backend.jwt;

import com.bgnc.Uber_Clone_Backend.exception.BaseException;
import com.bgnc.Uber_Clone_Backend.exception.ErrorMessage;
import com.bgnc.Uber_Clone_Backend.exception.MessageType;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
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

                if (userDetails != null && jwtService.isTokenValid(token)) {
                    // Set authentication context with user details and authorities
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(userDetails);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    throw new BaseException(new ErrorMessage(MessageType.INVALID_TOKEN, "Token is invalid"));
                }
            }

        } catch (ExpiredJwtException ex) {
            // Handle expired token with a more secure message
            throw new BaseException(new ErrorMessage(MessageType.TOKEN_IS_EXPIRED, "Token has expired"));
        } catch (Exception e) {
            // General exception handling with minimal exposure of error details
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Authentication failed"));
        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}