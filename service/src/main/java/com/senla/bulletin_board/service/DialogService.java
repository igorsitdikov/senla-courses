package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.DialogDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.entity.DialogEntity;
import com.senla.bulletin_board.exception.EntityAlreadyExistsException;
import com.senla.bulletin_board.mapper.interfaces.DialogDtoEntityMapper;
import com.senla.bulletin_board.repository.DialogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DialogService extends AbstractService<DialogDto, DialogEntity, DialogRepository> {

    private final DialogDtoEntityMapper dialogDtoEntityMapper;

    private DialogService(final DialogDtoEntityMapper dtoEntityMapper,
                          final DialogRepository repository) {
        super(dtoEntityMapper, repository);
        dialogDtoEntityMapper = dtoEntityMapper;
    }

    public List<DialogDto> findDialogsByUserId(final Long id) {
        return repository.findAllByBulletin_SellerId(id)
            .stream()
            .map(dialogDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    public IdDto createDialog(final DialogDto dialogDto) throws EntityAlreadyExistsException {
        if (checkDialogExistence(dialogDto)) {
            final String message =
                String.format("Dialog with title %s and customer id %d already exists",
                              dialogDto.getTitle(),
                              dialogDto.getCustomerId());
            log.error(message);
            throw new EntityAlreadyExistsException(message);
        }
        return super.post(dialogDto);
    }

    public boolean checkDialogExistence(final DialogDto dialogDto) {
        return repository.existsByBulletin_TitleAndCustomerId(dialogDto.getTitle(), dialogDto.getCustomerId());
    }
}
