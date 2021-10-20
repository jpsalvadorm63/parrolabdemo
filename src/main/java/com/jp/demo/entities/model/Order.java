package com.jp.demo.entities.model;

import com.jp.demo.entities.repository.CustomerRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;
//import java.util.Set;

@Entity
@Table(name = "torder")
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Order {
    @ToString.Include
    @Getter
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ToString.Include
    @Setter
    @Getter
    @Column(name = "code", nullable = false, unique = true, updatable = false, length = 24)
    private String code;

    @ToString.Include
    @Setter
    @Getter
    @Column(name = "date", length = 24)
    private String date;

    @Getter
    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public void setCustomer(Customer customer) {
        customer.increment();
        this.customer = customer;
    }
    @ManyToOne(cascade = CascadeType.DETACH)
    @Setter
    @Getter
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;

    @ManyToOne(cascade = CascadeType.DETACH)
    @Getter
    @Setter
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

    @ToString.Include
    @Setter
    @Getter
    @Column(name = "ordervalue")
    private Float orderValue;

    @Setter
    @Getter
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> items;

    public Order(String code) {
        this.code = code;
        this.orderValue = 0F;
    }

    public OrderItem add(Product product, Integer quantity) {
        if(product != null) {
            product.increment();
            OrderItem oi = new OrderItem(product, quantity, this);
            return oi;
        } else
            return null;
    }
}
