package com.project.EStore.service.domain.order.impl;

import com.project.EStore.model.entity.order.OrderDetailEntity;
import com.project.EStore.model.entity.order.OrderEntity;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.model.service.order.OrderServiceModel;
import com.project.EStore.repository.order.OrderDetailRepository;
import com.project.EStore.service.domain.order.OrderDetailService;
import com.project.EStore.service.domain.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, ProductService productService, ModelMapper modelMapper) {
        this.orderDetailRepository = orderDetailRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void placeOrderWithDetails(Map<String, String> productsByIdAndCount, OrderServiceModel orderServiceModel) {
        for (Map.Entry<String, String> entry : productsByIdAndCount.entrySet()) {
            this.orderDetailRepository.save(
                    new OrderDetailEntity()
                            .setQuantity(Short.valueOf(entry.getValue()))
                            .setProduct(this.modelMapper.map(this.productService.findProductById(Integer.parseInt(entry.getKey())), ProductEntity.class))
                            .setOrderNumber(this.modelMapper.map(orderServiceModel, OrderEntity.class))
            );
        }
    }
}
