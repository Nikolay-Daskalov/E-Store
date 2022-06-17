package com.project.EStore.repository.user;

import com.project.EStore.model.entity.user.UserEntity;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<UserEntity> {

    Optional<UserEntity> findByUsername (String username);

    void deleteByUsername(String username);
}
