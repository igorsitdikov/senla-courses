package com.senla.bulletinboard.service.impl;

import com.senla.bulletinboard.dto.CommentDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.CommentEntity;
import com.senla.bulletinboard.enumerated.BulletinStatus;
import com.senla.bulletinboard.exception.BulletinIsClosedException;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.mapper.interfaces.CommentDtoEntityMapper;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.CommentRepository;
import com.senla.bulletinboard.service.CommentService;
import com.senla.bulletinboard.utils.Translator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl extends AbstractService<CommentDto, CommentEntity, CommentRepository> implements
                                                                                                      CommentService {

    private final BulletinRepository bulletinRepository;

    public CommentServiceImpl(final CommentDtoEntityMapper commentDtoEntityMapper,
                              final CommentRepository commentRepository,
                              final BulletinRepository bulletinRepository,
                              final Translator translator) {
        super(commentDtoEntityMapper, commentRepository, translator);
        this.bulletinRepository = bulletinRepository;
    }

    @Override
    @PreAuthorize("authentication.principal.id == #commentDto.getAuthorId()")
    public IdDto createComment(final CommentDto commentDto)
        throws BulletinIsClosedException, EntityNotFoundException {
        final Optional<BulletinEntity> bulletinEntity = bulletinRepository.findById(commentDto.getBulletinId());
        if (bulletinEntity.isPresent() && bulletinEntity.get().getStatus() == BulletinStatus.CLOSE) {
            final String message = translator.toLocale("bulletin-closed", bulletinEntity.get().getId());
            throw new BulletinIsClosedException(message);
        } else if (!bulletinEntity.isPresent()) {
            final String message = translator.toLocale("bulletin-not-exists", commentDto.getBulletinId());
            throw new EntityNotFoundException(message);
        }
        return super.post(commentDto);
    }
}
