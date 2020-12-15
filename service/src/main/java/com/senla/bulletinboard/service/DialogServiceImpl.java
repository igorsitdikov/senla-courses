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
import com.senla.bulletinboard.repository.specification.DialogExistsSpecification;
import com.senla.bulletinboard.service.interfaces.DialogService;
import com.senla.bulletinboard.utils.Translator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DialogServiceImpl extends AbstractService<DialogDto, DialogEntity, DialogRepository> implements
                                                                                                  DialogService {

    private final DialogDtoEntityMapper dialogDtoEntityMapper;
    private final BulletinRepository bulletinRepository;

    public DialogServiceImpl(final DialogDtoEntityMapper dtoEntityMapper,
                             final DialogRepository repository,
                             final BulletinRepository bulletinRepository,
                             final Translator translator) {
        super(dtoEntityMapper, repository, translator);
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

    private List<DialogEntity> findDialogsBySellerIdOrCustomerId(final Long id) {
        return repository.findAllByBulletinSellerIdOrCustomerId(id, id);
    }

    @Override
    @PreAuthorize("@dialogServiceImpl.checkDialogOwner(authentication.principal.id, #id)")
    public void delete(final Long id) {
        super.delete(id);
    }

    @Cacheable(value = "dialogOwner", key = "{#userId, #dialogId}")
    public boolean checkDialogOwner(final Long userId, final Long dialogId) {
        DialogExistsSpecification specification = new DialogExistsSpecification(dialogId, userId);
        return repository.findOne(specification).isPresent();
    }

    @Override
    @PreAuthorize("@dialogServiceImpl.checkOwner(authentication.principal.id, #dialogDto)")
    public IdDto createDialog(final DialogDto dialogDto) throws EntityAlreadyExistsException {
        if (checkDialogExistence(dialogDto)) {
            final String message = translator.toLocale("dialog-already-exists",
                                                       dialogDto.getBulletinId(),
                                                       dialogDto.getCustomerId());
            throw new EntityAlreadyExistsException(message);
        }
        return super.post(dialogDto);
    }

    private boolean checkDialogExistence(final DialogDto dialogDto) {
        return repository.existsByBulletinIdAndCustomerId(dialogDto.getBulletinId(), dialogDto.getCustomerId());
    }

    @Cacheable(value = "bulletinOwner", key = "#bulletinId")
    public boolean checkOwner(final Long userId, final DialogDto dialogDto) throws EntityNotFoundException {
        final BulletinEntity entity = bulletinRepository.findById(dialogDto.getBulletinId()).orElseThrow(() -> {
            final String message = translator.toLocale("bulletin-not-exists", dialogDto.getBulletinId());
            return new EntityNotFoundException(message);
        });
        return dialogDto.getCustomerId().equals(userId) && !userId.equals(entity.getSeller().getId());
    }
}
