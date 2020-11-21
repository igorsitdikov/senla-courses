package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.CommentDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.entity.BulletinEntity;
import com.senla.bulletin_board.entity.CommentEntity;
import com.senla.bulletin_board.enumerated.BulletinStatus;
import com.senla.bulletin_board.exception.BulletinIsClosedException;
import com.senla.bulletin_board.exception.EntityNotFoundException;
import com.senla.bulletin_board.exception.NoSuchUserException;
import com.senla.bulletin_board.mapper.interfaces.CommentDtoEntityMapper;
import com.senla.bulletin_board.repository.BulletinRepository;
import com.senla.bulletin_board.repository.CommentRepository;
import com.senla.bulletin_board.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class CommentService extends AbstractService<CommentDto, CommentEntity, CommentRepository> {

    private final BulletinRepository bulletinRepository;
    private final UserRepository userRepository;

    public CommentService(final CommentDtoEntityMapper commentDtoEntityMapper,
                          final CommentRepository commentRepository,
                          final BulletinRepository bulletinRepository,
                          final UserRepository userRepository
                         ) {
        super(commentDtoEntityMapper, commentRepository);
        this.bulletinRepository = bulletinRepository;
        this.userRepository = userRepository;
    }

    public IdDto createComment(final CommentDto commentDto)
        throws BulletinIsClosedException, NoSuchUserException, EntityNotFoundException {
        final Optional<BulletinEntity> bulletinEntity = bulletinRepository.findById(commentDto.getBulletinId());
        if (!userRepository.existsById(commentDto.getAuthorId())) {
            final String message = String.format("User with id %d does not exist",
                                                 commentDto.getAuthorId());
            log.error(message);
            throw new NoSuchUserException(message);
        }
        if (bulletinEntity.isPresent() && bulletinEntity.get().getStatus() == BulletinStatus.CLOSE) {
            final String message = String.format("Bulletin with id %d is closed",
                                                 bulletinEntity.get().getId());
            log.error(message);
            throw new BulletinIsClosedException(message);
        } else if (!bulletinEntity.isPresent()) {
            final String message = String.format("Bulletin with id %d does not exist",
                                                 commentDto.getBulletinId());
            log.error(message);
            throw new EntityNotFoundException(message);
        }
        return super.post(commentDto);
    }
}
