package com.jp.demo.entities.repository;

import com.jp.demo.entities.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByCode(String code);
}
