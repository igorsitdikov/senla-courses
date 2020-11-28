package com.senla.bulletinboard.mapper;

import com.senla.bulletinboard.dto.MessageDto;
import com.senla.bulletinboard.entity.MessageEntity;
import com.senla.bulletinboard.mapper.interfaces.MessageDtoEntityMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MessageDtoEntityMapperImpl implements MessageDtoEntityMapper {

//    private final UserDtoEntityMapper userDtoEntityMapper;

    @Override
    public MessageEntity sourceToDestination(final MessageDto source) {
        MessageEntity destination = new MessageEntity();
        destination.setText(source.getMessage());
        destination.setDialogId(source.getDialogId());
        destination.setRecipientId(source.getRecipientId());
        destination.setSenderId(source.getSenderId());
        return destination;
    }

    @Override
    public MessageDto destinationToSource(final MessageEntity destination) {
        MessageDto source = new MessageDto();
        source.setCreatedAt(destination.getCreatedAt());
        source.setMessage(destination.getText());
        source.setDialogId(destination.getDialogId());
        source.setRecipientId(destination.getRecipientId());
        source.setSenderId(destination.getSenderId());
        return source;
    }
}
