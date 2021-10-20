package com.jp.demo.entities.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product")
@RequiredArgsConstructor
public class Product {
    @Getter
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    @Column(name = "description", unique = true, length = 64)
    private String description;

    @Getter
    @Setter
    @Column(name = "price")
    private Float price;

    @Getter
    @Setter
    @Column(name = "weight", length = 16)
    private String weight;

    @Getter
    @Setter
    @Column(name = "refcounter")
    public Integer refCounter;

    public void increment() {
        this.setRefCounter(++this.refCounter);
    };

    public void decrement() {
        this.setRefCounter(--this.refCounter);
    };

    public Product(String description, Float price, String weight) {
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.refCounter = 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", weight='" + weight + '\'' +
                ", refCounter=" + refCounter +
                '}';
    }
}
