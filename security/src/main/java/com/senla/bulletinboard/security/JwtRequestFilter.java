package com.senla.bulletinboard.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final LoadUserDetailService userDetailsService;
    private final JwtUtil jwtUtil;
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain)
        throws ServletException, IOException {
        try {
            final String authorizationHeader = request.getHeader(HEADER_STRING);

            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
                jwt = authorizationHeader.replace(TOKEN_PREFIX, "");
                username = jwtUtil.extractUsername(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

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
        } catch (ExpiredJwtException exception) {
            final String message = String.format("Security exception for user %s - %s",
                                                 exception.getClaims().getSubject(),
                                                 exception.getMessage());
            log.info(message);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setHeader("Content-Type", "application/json");
//            response.getOutputStream().print(String.format("{\"errorMessage\":\"%s\"}", message));
//            response.flushBuffer();
        }
    }
}
