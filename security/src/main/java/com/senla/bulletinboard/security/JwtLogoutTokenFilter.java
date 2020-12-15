package com.senla.bulletinboard.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtLogoutTokenFilter extends OncePerRequestFilter {

    private static final String HEADER_STRING = "Authorization";
    private final TokenCacheService tokenCacheService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain)
        throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HEADER_STRING);

        if (tokenCacheService.checkToken(authorizationHeader)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }
}
