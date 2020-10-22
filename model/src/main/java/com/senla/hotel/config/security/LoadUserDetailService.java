package com.senla.hotel.config.security;

import com.senla.hotel.dao.interfaces.UserDao;
import com.senla.hotel.entity.UserEntity;
import com.senla.hotel.exceptions.PersistException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class LoadUserDetailService implements UserDetailsService {

    private static final String ROLE = "ROLE_";
    private final UserDao userDao;

    private LoadUserDetailService(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        try {
            final UserEntity userEntity = userDao.findByEmail(email);
            final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                ROLE + userEntity.getRole().name());
            return new User(email, userEntity.getPassword(), Collections.singletonList(authority));
        } catch (PersistException e) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }
    }
}
