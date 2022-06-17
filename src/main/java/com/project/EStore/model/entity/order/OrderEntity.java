package com.project.EStore.model.entity.order;

import com.project.EStore.model.entity.base.BaseEntity;
import com.project.EStore.model.entity.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
public class OrderEntity extends BaseEntity {

    @ManyToOne(optional = false)
    private UserEntity user;
    @Column(nullable = false)
    private LocalDateTime created;
    @OneToMany(mappedBy = "orderNumber", cascade = CascadeType.ALL)
    private Set<OrderDetailEntity> orderDetails;

    @PrePersist
    public void onPersist(){
        this.created = LocalDateTime.now();
    }

    public OrderEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public OrderEntity setOrderDetails(Set<OrderDetailEntity> orderDetails) {
        this.orderDetails = orderDetails;
        return this;
    }
}
