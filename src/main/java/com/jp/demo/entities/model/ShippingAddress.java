package com.jp.demo.entities.model;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "shippingaddress")
@RequiredArgsConstructor
@ToString
public class ShippingAddress {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "street", length = 64)
    private String street;

    @Column(name = "number")
    private Integer number;

    @Column(name = "city", length = 32)
    private String city;

    @Column(name = "state", length = 4)
    private String state;

    @Column(name = "zip", length = 8)
    private String zip;

    @Column(name = "code", length = 16)
    private String code;

    @Column(name = "country", length = 64)
    private String country;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public ShippingAddress(String street, Integer number, String city, String state, String zip, String code, String country) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.code = code;
        this.country = country;
    }
}
