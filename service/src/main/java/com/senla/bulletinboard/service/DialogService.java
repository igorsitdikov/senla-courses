package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.exception.EntityAlreadyExistsException;
import com.senla.bulletinboard.mapper.interfaces.DialogDtoEntityMapper;
import com.senla.bulletinboard.repository.DialogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DialogService extends AbstractService<DialogDto, DialogEntity, DialogRepository> {

    private final DialogDtoEntityMapper dialogDtoEntityMapper;

    public DialogService(final DialogDtoEntityMapper dtoEntityMapper,
                         final DialogRepository repository) {
        super(dtoEntityMapper, repository);
        dialogDtoEntityMapper = dtoEntityMapper;
    }

    @PreAuthorize("authentication.principal.id == #id")
    public List<DialogDto> findDialogsByUserId(final Long id) {
        return findDialogsBySellerIdOrCustomerId(id)
            .stream()
            .map(dialogDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    private List<DialogEntity> findDialogsBySellerIdOrCustomerId(final Long id) {
        return repository.findAllByBulletin_SellerIdOrCustomerId(id, id);
    }

    public IdDto createDialog(final DialogDto dialogDto) throws EntityAlreadyExistsException {
        if (checkDialogExistence(dialogDto)) {
            final String message =
                String.format("Dialog with user id %s and customer id %d already exists",
                              dialogDto.getBulletinId(),
                              dialogDto.getCustomerId());
            log.error(message);
            throw new EntityAlreadyExistsException(message);
        }
        return super.post(dialogDto);
    }

    public boolean checkDialogExistence(final DialogDto dialogDto) {
        return repository.existsByBulletin_IdAndCustomerId(dialogDto.getBulletinId(), dialogDto.getCustomerId());
    }
}
