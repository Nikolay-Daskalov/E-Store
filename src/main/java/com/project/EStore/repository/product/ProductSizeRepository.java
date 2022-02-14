package com.project.EStore.repository.product;

import com.project.EStore.model.entity.product.ProductSizeEntity;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSizeRepository extends BaseRepository<ProductSizeEntity> {

    Optional<ProductSizeEntity> findBySize(ProductSizeEnum size);
}
