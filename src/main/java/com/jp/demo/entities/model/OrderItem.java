package com.jp.demo.entities.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "orderitem")
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class OrderItem {
    @Getter
    @ToString.Include
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    @ToString.Include
    @Column(name = "quantity")
    private Integer quantity;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderItem(Product product, Integer quantity, Order order) {
        this.quantity = quantity;
        this.product = product;
        this.order = order;
    }
}
