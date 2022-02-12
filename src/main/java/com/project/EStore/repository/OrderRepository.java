package com.project.EStore.repository;

import com.project.EStore.model.entity.OrderEntity;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity> {
}
