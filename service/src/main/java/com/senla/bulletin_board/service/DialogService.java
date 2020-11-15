package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.DialogDto;
import com.senla.bulletin_board.entity.DialogEntity;
import com.senla.bulletin_board.mapper.interfaces.DtoEntityMapper;
import com.senla.bulletin_board.repository.DialogRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

//@Data
@Service
public class DialogService extends AbstractService<DialogDto, DialogEntity, DialogRepository> {

    private DialogService(final DtoEntityMapper<DialogDto, DialogEntity> dtoEntityMapper,
                          final DialogRepository repository) {
        super(dtoEntityMapper, repository);
    }
}
