package com.project.EStore.repository.order;

import com.project.EStore.model.entity.order.OrderEntity;
import com.project.EStore.model.entity.user.UserEntity;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity> {

    List<OrderEntity> findAllByUser(UserEntity user);
}
