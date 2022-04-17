package com.project.EStore.repository.product;

import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity> {

    @Query(value = "SELECT DISTINCT pe.brand FROM ProductEntity AS pe")
    List<String> getAllBrands();
}
