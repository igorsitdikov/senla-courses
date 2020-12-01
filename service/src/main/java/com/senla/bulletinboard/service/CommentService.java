package com.senla.bulletinboard.service;

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
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class CommentService extends AbstractService<CommentDto, CommentEntity, CommentRepository> {

    private final BulletinRepository bulletinRepository;

    public CommentService(final CommentDtoEntityMapper commentDtoEntityMapper,
                          final CommentRepository commentRepository,
                          final BulletinRepository bulletinRepository) {
        super(commentDtoEntityMapper, commentRepository);
        this.bulletinRepository = bulletinRepository;
    }

    @PreAuthorize("authentication.principal.id == #commentDto.getAuthorId()")
    public IdDto createComment(final CommentDto commentDto)
        throws BulletinIsClosedException, EntityNotFoundException {
        final Optional<BulletinEntity> bulletinEntity = bulletinRepository.findById(commentDto.getBulletinId());
        if (bulletinEntity.isPresent() && bulletinEntity.get().getStatus() == BulletinStatus.CLOSE) {
            final String message = Translator.toLocale("bulletin-closed", bulletinEntity.get().getId());
            log.error(message);
            throw new BulletinIsClosedException(message);
        } else if (!bulletinEntity.isPresent()) {
            final String message = Translator.toLocale("bulletin-not-exists", commentDto.getBulletinId());
            log.error(message);
            throw new EntityNotFoundException(message);
        }
        return super.post(commentDto);
    }
}
