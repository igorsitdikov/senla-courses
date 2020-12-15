package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.mock.DialogMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.DialogRepository;
import com.senla.bulletinboard.repository.specification.DialogExistsSpecification;
import com.senla.bulletinboard.utils.Translator;
import liquibase.pro.packaged.D;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class DialogServiceImplTest {

    @SpyBean
    private DialogServiceImpl dialogService;
    @MockBean
    private BulletinRepository bulletinRepository;
    @MockBean
    private DialogRepository dialogRepository;
    @SpyBean
    private Translator translator;

    @Test
    public void checkOwner_BulletinNotExistsTest() {
        final Long userId = 1L;
        final Long bulletinId = 2L;
        DialogDto dialogDto = new DialogDto();
        dialogDto.setBulletinId(bulletinId);
        dialogDto.setCustomerId(userId);
        doReturn(Optional.empty()).when(bulletinRepository).findById(bulletinId);
        final EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> dialogService.checkOwner(userId, dialogDto));
        assertEquals(exception.getMessage(), translator.toLocale("bulletin-not-exists", bulletinId));
    }

    @Test
    public void checkDialogOwner_SellerOwnerTest() {
        final Long userId = 1L;
        final Long dialogId = 1L;
        DialogEntity dialogEntity = new DialogMock().getEntityById(dialogId);
        doReturn(Optional.of(dialogEntity)).when(dialogRepository).findOne(any(DialogExistsSpecification.class));

        assertTrue(dialogService.checkDialogOwner(userId, dialogId));
    }

    @Test
    public void checkDialogOwner_CustomerOwnerTest() {
        final Long userId = 2L;
        final Long dialogId = 1L;
        DialogEntity dialogEntity = new DialogMock().getEntityById(dialogId);
        doReturn(Optional.of(dialogEntity)).when(dialogRepository).findOne(any(DialogExistsSpecification.class));

        assertTrue(dialogService.checkDialogOwner(userId, dialogId));
    }

}
