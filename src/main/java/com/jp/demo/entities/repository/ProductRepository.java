package com.jp.demo.entities.repository;

import com.jp.demo.entities.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {
    Product findByDescription(String description);

    /**
     * Deletes the entity with the given id.
     *
     * @param aLong must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    @Modifying
    @Query("delete from Product p where p.id = ?1 and p.refCounter <= 0")
    void deleteById(Long aLong);
}
