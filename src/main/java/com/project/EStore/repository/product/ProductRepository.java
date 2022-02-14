package com.project.EStore.repository.product;

import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity> {
}
