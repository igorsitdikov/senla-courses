package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.CommentDto;
import com.senla.bulletin_board.entity.CommentEntity;
import com.senla.bulletin_board.mapper.interfaces.CommentDtoEntityMapper;
import com.senla.bulletin_board.repository.CommentRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class CommentService extends AbstractService<CommentDto, CommentEntity, CommentRepository> {

    public CommentService(final CommentDtoEntityMapper commentDtoEntityMapper,
                          final CommentRepository commentRepository) {
        super(commentDtoEntityMapper, commentRepository);
    }

}
