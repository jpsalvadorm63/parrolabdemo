package com.jp.demo.entities.repository;

import com.jp.demo.entities.model.ShippingAddress;
import org.springframework.data.repository.CrudRepository;

public interface ShippingAddressRepository extends CrudRepository<ShippingAddress, Long> {
    ShippingAddress findByStreetAndNumber(String street, Integer number);
}