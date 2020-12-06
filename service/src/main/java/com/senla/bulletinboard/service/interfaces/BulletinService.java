package com.senla.bulletinboard.service.interfaces;

import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.enumerated.SortBulletin;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.NoSuchUserException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface BulletinService extends CommonService<BulletinDto, BulletinEntity> {

    @PreAuthorize("authentication.principal.id == #id")
    List<BulletinBaseDto> findBulletinsByUserId(Long id) throws NoSuchUserException;

    BulletinDto findBulletinById(Long id) throws EntityNotFoundException;

    @PreAuthorize("authentication.principal.id == #bulletinDto.getSellerId()")
    IdDto createBulletin(BulletinDto bulletinDto);

    @PreAuthorize("authentication.principal.id == #bulletinDto.getSellerId()")
    BulletinDto updateBulletin(Long id, BulletinDto bulletinDto) throws EntityNotFoundException;

    @PreAuthorize("hasRole('ROLE_ADMIN') or @bulletinServiceImpl.checkOwner(authentication.principal.id, #id)")
    void deleteBulletin(Long id) throws EntityNotFoundException;

    List<BulletinBaseDto> findAllBulletins(String[] filters, SortBulletin sort);
}
