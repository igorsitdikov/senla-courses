package com.senla.bulletin_board.security;


import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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

    private final UserRepository userRepository;
    private final static String ROLE_PREFIX = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (!userEntity.isPresent()) {
            final String message = String.format("User with email: %s not found", email);
            log.error(message);
            throw new UsernameNotFoundException(message);
        } else {
            final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                ROLE_PREFIX + userEntity.get().getRole().name());
            return new User(email, userEntity.get().getPassword(), Collections.singleton(authority));
        }
    }
}
