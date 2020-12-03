package com.senla.bulletinboard.security;

import com.senla.bulletinboard.entity.TokenBlacklistEntity;
import com.senla.bulletinboard.repository.TokenBlacklistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtLogoutTokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HEADER_STRING);

        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            jwt = authorizationHeader.replace(TOKEN_PREFIX, "");
        }

        Optional<TokenBlacklistEntity> tokenBlacklistEntity = tokenBlacklistRepository.findByToken(TOKEN_PREFIX + jwt);
        if (tokenBlacklistEntity.isPresent()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }
}
