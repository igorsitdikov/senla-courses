package com.senla.bulletinboard.mock;

import com.senla.bulletinboard.dto.SellerVoteDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.SellerVoteEntity;
import com.senla.bulletinboard.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

public class VoteMock {

    private static final Map<Long, SellerVoteEntity> voteEntityMap = new HashMap<Long, SellerVoteEntity>() {{
        put(1L, new SellerVoteEntity(5, new UserEntity(), 3L, new BulletinEntity(), 4L));
        put(2L, new SellerVoteEntity(5, new UserEntity(), 1L, new BulletinEntity(), 4L));
        put(3L, new SellerVoteEntity(5, new UserEntity(), 1L, new BulletinEntity(), 4L));
    }};

    private static final Map<Long, SellerVoteDto> voteDtoMap = new HashMap<Long, SellerVoteDto>() {{
        put(1L, new SellerVoteDto(1L, 3L, 4L, 5));
        put(2L, new SellerVoteDto(1L, 1L, 4L, 5));
        put(3L, new SellerVoteDto(1L, 3L, 3L, 5));
    }};

    public static SellerVoteEntity getEntityById(final Long id) {
        SellerVoteEntity bulletinEntity = voteEntityMap.get(id);
        bulletinEntity.setId(id);
        return bulletinEntity;
    }

    public static SellerVoteDto getById(final Long id) {
        return voteDtoMap.get(id);
    }
}
