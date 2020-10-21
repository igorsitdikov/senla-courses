package com.senla.hotel.config.security;

import com.senla.hotel.entity.TokenEntity;
import com.senla.hotel.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Integer BEARER_LENGTH = 7;
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final LoadUserDetailService userDetailsService;
    private final JwtUtil jwtUtil;
    private final TokenChecker tokenChecker;

    private JwtRequestFilter(final LoadUserDetailService userDetailsService, final JwtUtil jwtUtil, final TokenChecker tokenChecker) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.tokenChecker = tokenChecker;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            jwt = authorizationHeader.substring(BEARER_LENGTH);
            username = jwtUtil.extractUsername(jwt);
        }

        TokenEntity tokenEntity = tokenChecker.validate(jwt);

        if (tokenEntity == null && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
