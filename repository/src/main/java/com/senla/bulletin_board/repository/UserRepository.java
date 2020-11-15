package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CommonRepository<UserEntity, Long> {

}
