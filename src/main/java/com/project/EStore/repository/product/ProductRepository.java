package com.project.EStore.repository.product;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity> {

    Optional<ProductEntity> findByIdAndCategory(Integer id, ProductCategoryEnum category);

    @Query(value = "SELECT DISTINCT p.brand FROM ProductEntity AS p WHERE p.category = :category")
    Set<String> findAllBrandsByProductCategory(@Param("category") ProductCategoryEnum categoryEnum);

    Page<ProductEntity> findAllByBrandInAndTypeInAndCategoryAndIsDeletedFalseAndSupply_PriceGreaterThanEqualAndSupply_PriceLessThanEqual(
            Collection<String> brand, Collection<ProductTypeEnum> type, ProductCategoryEnum category, BigDecimal supply_price, BigDecimal supply_price2, Pageable pageable);

    List<ProductEntity> findAllByIdIn(Collection<Integer> id);
}