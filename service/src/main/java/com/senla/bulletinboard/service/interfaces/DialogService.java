package com.senla.bulletinboard.service.interfaces;

import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.exception.EntityAlreadyExistsException;

import java.util.List;

public interface DialogService extends CommonService<DialogDto, DialogEntity> {

    List<DialogDto> findDialogsByUserId(Long id);

    IdDto createDialog(DialogDto dialogDto) throws EntityAlreadyExistsException;
}
