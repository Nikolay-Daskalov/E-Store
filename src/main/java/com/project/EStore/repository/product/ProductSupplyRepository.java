package com.project.EStore.repository.product;

import com.project.EStore.model.entity.product.ProductSupplyEntity;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSupplyRepository extends BaseRepository<ProductSupplyEntity> {

    @Modifying
    @Query("UPDATE ProductSupplyEntity AS ps SET ps.quantity = (ps.quantity - :quantity) WHERE ps.product.id = :productId")
    void decrementQuantityById(Short quantity, Integer productId);
}
