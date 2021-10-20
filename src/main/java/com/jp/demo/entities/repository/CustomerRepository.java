package com.jp.demo.entities.repository;

import com.jp.demo.entities.model.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByPhone(String phone);

    Customer findByEmail(String email);

    List<Customer> findByNameLikeIgnoreCase(String name);

    @Modifying
    @Query("delete from Customer c where c.id = ?1 and c.refCounter <= 0")
    void deleteById(Long aLong);
}