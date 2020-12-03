package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.exception.EntityAlreadyExistsException;
import com.senla.bulletinboard.mapper.interfaces.DialogDtoEntityMapper;
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

    public DialogServiceImpl(final DialogDtoEntityMapper dtoEntityMapper,
                             final DialogRepository repository) {
        super(dtoEntityMapper, repository);
        dialogDtoEntityMapper = dtoEntityMapper;
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
        return repository.findAllByBulletin_SellerIdOrCustomerId(id, id);
    }

    @Override
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

    public boolean checkDialogExistence(final DialogDto dialogDto) {
        return repository.existsByBulletin_IdAndCustomerId(dialogDto.getBulletinId(), dialogDto.getCustomerId());
    }
}