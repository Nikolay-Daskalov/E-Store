package com.project.EStore.repository.product;

import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity> {

    @Query(value = "SELECT DISTINCT pe.brand FROM ProductEntity AS pe")
    List<String> getAllBrands();

    Page<ProductEntity> findAllByBrandAndTypeAndSupply_PriceBetween(
            String brand, ProductTypeEnum type,
            BigDecimal supply_price, BigDecimal supply_price2, Pageable pageable
    );
}
