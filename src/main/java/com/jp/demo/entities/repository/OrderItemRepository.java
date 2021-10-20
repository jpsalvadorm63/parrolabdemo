package com.jp.demo.entities.repository;

import com.jp.demo.entities.model.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    OrderItem findByOrder_IdAndProduct_Id(long order_id, long product_id);
}
