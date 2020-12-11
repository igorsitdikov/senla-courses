package com.senla.bulletinboard.security;


import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.utils.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class LoadUserDetailService implements UserDetailsService {

    private final Translator translator;
    private final UserRepository userRepository;
    private final static String ROLE_PREFIX = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (!userEntity.isPresent()) {
            final String message = translator.toLocale("no-such-user", email);
            log.error(message);
            throw new UsernameNotFoundException(message);
        } else {
            final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                ROLE_PREFIX + userEntity.get().getRole().name());
            return new AuthUser(email, userEntity.get().getPassword(), Collections.singleton(authority),
                                userEntity.get().getId());
        }
    }
}
