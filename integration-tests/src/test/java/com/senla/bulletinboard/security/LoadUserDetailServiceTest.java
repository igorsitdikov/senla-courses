package com.senla.bulletinboard.security;

import com.senla.bulletinboard.BulletinBoardApplicationTest;
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class LoadUserDetailServiceTest extends BulletinBoardApplicationTest {

    @SpyBean
    private LoadUserDetailService loadUserDetailService;
    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private Translator translator;

    @Test
    public void loadUserByUsername_UsernameNotFoundExceptionTest() {
        final String email = "ivan.ivanov@mail.ru";
        doReturn(Optional.empty()).when(userRepository).findByEmail(email);
        final UsernameNotFoundException exception = assertThrows(
            UsernameNotFoundException.class,
            () -> loadUserDetailService.loadUserByUsername(email));
        assertEquals(exception.getMessage(), translator.toLocale("no-such-user", email));

    }
}
