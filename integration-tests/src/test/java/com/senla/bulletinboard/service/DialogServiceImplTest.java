package com.senla.bulletinboard.service;

import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.mock.BulletinMock;
import com.senla.bulletinboard.mock.DialogMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.DialogRepository;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        final Long bulletinId = 2L;
        doReturn(Optional.empty()).when(bulletinRepository).findById(bulletinId);
        final EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> dialogService.checkOwner(1L, bulletinId));
        assertEquals(exception.getMessage(), translator.toLocale("bulletin-not-exists", bulletinId));
    }

    @Test
    public void checkDialogOwner_DialogNotExistsTest() {
        final Long dialogId = 1L;
        doReturn(Optional.empty()).when(dialogRepository).findById(dialogId);

        final EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> dialogService.checkDialogOwner(1L, dialogId));
        assertEquals(exception.getMessage(), translator.toLocale("dialog-not-exists", dialogId));
    }

    @Test
    public void checkDialogOwner_SellerOwnerTest() throws Exception {
        final Long userId = 1L;
        final Long dialogId = 1L;
        DialogEntity dialogEntity = new DialogMock().getEntityById(dialogId);
        BulletinEntity bulletinEntity = new BulletinMock().getEntityById(dialogEntity.getBulletinId());
        doReturn(Optional.of(dialogEntity)).when(dialogRepository).findById(dialogId);
        doReturn(Optional.of(bulletinEntity)).when(bulletinRepository).findById(dialogEntity.getBulletinId());

        assertTrue(dialogService.checkDialogOwner(userId, dialogId));
    }

    @Test
    public void checkDialogOwner_CustomerOwnerTest() throws Exception {
        final Long userId = 2L;
        final Long dialogId = 1L;
        DialogEntity dialogEntity = new DialogMock().getEntityById(dialogId);
        BulletinEntity bulletinEntity = new BulletinMock().getEntityById(dialogEntity.getBulletinId());
        doReturn(Optional.of(dialogEntity)).when(dialogRepository).findById(dialogId);
        doReturn(Optional.of(bulletinEntity)).when(bulletinRepository).findById(dialogEntity.getBulletinId());

        assertTrue(dialogService.checkDialogOwner(userId, dialogId));
    }

}
