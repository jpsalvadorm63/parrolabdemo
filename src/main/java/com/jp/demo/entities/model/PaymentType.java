package com.jp.demo.entities.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "paymenttype")
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long id;

    @Column(name = "type", unique = true, length = 16)
    @Getter
    @Setter
    private String type;

    public PaymentType() {}

    public PaymentType(String type) {
        this.type = type;
    }
}
