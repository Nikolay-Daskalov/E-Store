package com.project.EStore.repository.user;

import com.project.EStore.model.entity.enums.RoleEnum;
import com.project.EStore.model.entity.user.RoleEntity;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity> {

    Optional<RoleEntity> findByRole(RoleEnum role);
}
