package com.project.EStore.repository;

import com.project.EStore.model.entity.UserEntity;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity> {
}
