package com.senla.bulletinboard.service.interfaces;

import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.exception.EntityAlreadyExistsException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface DialogService extends CommonService<DialogDto, DialogEntity> {

    @PreAuthorize("authentication.principal.id == #id")
    List<DialogDto> findDialogsByUserId(Long id);

    IdDto createDialog(DialogDto dialogDto) throws EntityAlreadyExistsException;
}
