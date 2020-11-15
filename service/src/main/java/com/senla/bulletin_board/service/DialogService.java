package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.DialogDto;
import com.senla.bulletin_board.entity.DialogEntity;
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

}
