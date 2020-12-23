package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.CommentDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.CommentEntity;
import com.senla.bulletinboard.exception.BulletinIsClosedException;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CommentService extends CommonService<CommentDto, CommentEntity> {

    @PreAuthorize("authentication.principal.id == #commentDto.getAuthorId()")
    IdDto createComment(CommentDto commentDto)
        throws BulletinIsClosedException, EntityNotFoundException;
}
