package com.project.EStore.repository.product;

import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity> {

    @Query(value = "SELECT DISTINCT pe.brand FROM ProductEntity AS pe")
    Set<String> findAllBrands();

//    @Query(value = "SELECT pe FROM ProductEntity AS pe JOIN pe.supply AS sup WHERE (pe.brand IN (:brands) OR :nullBrand IS NULL) " +
//            "AND (pe.type IN (:typeParams) OR (:typeParams) IS NULL) AND ((sup.price BETWEEN :lowest AND :highest) OR (:highest) IS NULL)")
    Page<ProductEntity> findAllByBrandInAndTypeInAndSupply_PriceBetween(
            Collection<String> brand, Collection<ProductTypeEnum> type,
            BigDecimal supply_price, BigDecimal supply_price2, Pageable pageable);
//            @Param("brands") Collection<String> brands,
//            @Param("typeParams") Collection<ProductTypeEnum> types,
//            @Param("lowest") BigDecimal lowest, @Param("highest") BigDecimal highest, Pageable pageable

}
