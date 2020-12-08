package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.exception.EntityAlreadyExistsException;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.mapper.interfaces.DialogDtoEntityMapper;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.DialogRepository;
import com.senla.bulletinboard.service.interfaces.DialogService;
import com.senla.bulletinboard.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DialogServiceImpl extends AbstractService<DialogDto, DialogEntity, DialogRepository> implements
                                                                                                  DialogService {

    private final DialogDtoEntityMapper dialogDtoEntityMapper;
    private final BulletinRepository bulletinRepository;

    public DialogServiceImpl(final DialogDtoEntityMapper dtoEntityMapper,
                             final DialogRepository repository,
                             final BulletinRepository bulletinRepository) {
        super(dtoEntityMapper, repository);
        dialogDtoEntityMapper = dtoEntityMapper;
        this.bulletinRepository = bulletinRepository;
    }

    @Override
    @PreAuthorize("authentication.principal.id == #id")
    public List<DialogDto> findDialogsByUserId(final Long id) {
        return findDialogsBySellerIdOrCustomerId(id)
            .stream()
            .map(dialogDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("@dialogServiceImpl.checkDialogOwner(authentication.principal.id, #id)")
    public void delete(final Long id) {
        super.delete(id);
    }

    private List<DialogEntity> findDialogsBySellerIdOrCustomerId(final Long id) {
        return repository.findAllByBulletinSellerIdOrCustomerId(id, id);
    }

    @Override
    @PreAuthorize(
        "authentication.principal.id == #dialogDto.getCustomerId() and !@dialogServiceImpl.checkOwner(authentication.principal.id, #dialogDto.getBulletinId())")
    public IdDto createDialog(final DialogDto dialogDto) throws EntityAlreadyExistsException {
        if (checkDialogExistence(dialogDto)) {
            final String message = Translator.toLocale("dialog-already-exists",
                                                       dialogDto.getBulletinId(),
                                                       dialogDto.getCustomerId());
            log.error(message);
            throw new EntityAlreadyExistsException(message);
        }
        return super.post(dialogDto);
    }

    public boolean checkOwner(final Long userId, final Long bulletinId) throws EntityNotFoundException {
        BulletinEntity entity = bulletinRepository.findById(bulletinId).orElseThrow(() -> {
            final String message = Translator.toLocale("bulletin-not-exists", bulletinId);
            log.error(message);
            return new EntityNotFoundException(message);
        });
        return userId.equals(entity.getSeller().getId());
    }

    public boolean checkDialogOwner(final Long userId, final Long dialogId) throws EntityNotFoundException {
        DialogEntity entity = repository.findById(dialogId).orElseThrow(() -> {
            final String message = Translator.toLocale("dialog-not-exists", dialogId);
            log.error(message);
            return new EntityNotFoundException(message);
        });
        if (checkOwner(userId, entity.getBulletinId())) {
            return true;
        }
        return userId.equals(entity.getCustomerId());
    }

    public boolean checkDialogExistence(final DialogDto dialogDto) {
        return repository.existsByBulletinIdAndCustomerId(dialogDto.getBulletinId(), dialogDto.getCustomerId());
    }
}
