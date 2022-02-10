package com.project.EStore.model.entity;

import com.project.EStore.model.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
public class OrderEntity extends BaseEntity {

    @ManyToOne(optional = false)
    private UserEntity user;
    @Column(nullable = false)
    private LocalDateTime created;

    public OrderEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public OrderEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }
}
