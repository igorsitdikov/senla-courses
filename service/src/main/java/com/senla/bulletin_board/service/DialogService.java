package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.DialogDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.entity.DialogEntity;
import com.senla.bulletin_board.exception.EntityAlreadyExistsException;
import com.senla.bulletin_board.mapper.interfaces.DialogDtoEntityMapper;
import com.senla.bulletin_board.repository.DialogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
            throw new EntityAlreadyExistsException(
                String.format("Dialog with id %s already exists", dialogDto.getId()));
        }
        return super.post(dialogDto);
    }

    public boolean checkDialogExistence(final DialogDto dialogDto) {
        return repository.existsByBulletin_TitleAndCustomerId(dialogDto.getTitle(), dialogDto.getCustomerId());
    }
}
