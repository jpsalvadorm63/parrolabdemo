package com.jp.demo.entities.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer", indexes = {
        @Index(name = "idx_customer", columnList = "shipping_address_id")
})
@RequiredArgsConstructor
@ToString
public class Customer {
    @Getter
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    @Column(name = "name", length = 64)
    private String name;

    @Getter
    @Setter
    @Column(unique = true, length = 32)
    private String phone;

    @Getter
    @Setter
    @Column(unique = true, length = 64)
    private String email;

    @Getter
    @Setter
    @OneToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;

    @Setter
    @Getter
    @Column(name = "refcounter")
    public Integer refCounter;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.DETACH, orphanRemoval = true)
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void increment() {
        this.setRefCounter(++this.refCounter);
    };

    public void decrement() {
        this.setRefCounter(--this.refCounter);
    };

    public Customer(String name, String phone, String email, ShippingAddress shippingAddress) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.refCounter = 0;
    }
}
