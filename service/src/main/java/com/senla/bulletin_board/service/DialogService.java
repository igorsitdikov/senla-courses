package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.DialogDto;
import com.senla.bulletin_board.entity.DialogEntity;
import com.senla.bulletin_board.mapper.interfaces.DialogDtoEntityMapper;
import com.senla.bulletin_board.repository.DialogRepository;
import org.springframework.stereotype.Service;

@Service
public class DialogService extends AbstractService<DialogDto, DialogEntity, DialogRepository> {

    private DialogService(final DialogDtoEntityMapper dtoEntityMapper,
                          final DialogRepository repository) {
        super(dtoEntityMapper, repository);
    }
}
