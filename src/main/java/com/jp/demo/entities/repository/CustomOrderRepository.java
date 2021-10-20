package com.jp.demo.entities.repository;

import com.jp.demo.entities.model.Customer;
import com.jp.demo.entities.model.Order;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("customOrderRepository")
public class CustomOrderRepository implements OrderRepository{
    private static final Logger log = LoggerFactory.getLogger(CustomOrderRepository.class);

    @Autowired
    @Qualifier("orderRepository")
    private OrderRepository orderRepository;

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @Override
    public <S extends Order> S save(S entity) {
        boolean isNewOrder = ((Order) entity).getId() == 0;
        Order order = orderRepository.save(entity);
        Customer customer = order.getCustomer();
        if(customer != null && isNewOrder) {
            customerRepository.save(customer);
        }
        return (S) order;
    }

    /**
     * Saves all given entities.
     *
     * @param entities must not be {@literal null} nor must it contain {@literal null}.
     * @return the saved entities; will never be {@literal null}. The returned {@literal Iterable} will have the same size
     * as the {@literal Iterable} passed as an argument.
     * @throws IllegalArgumentException in case the given {@link Iterable entities} or one of its entities is
     *                                  {@literal null}.
     */
    @Override
    public <S extends Order> Iterable<S> saveAll(Iterable<S> entities) {
        return orderRepository.saveAll(entities);
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param aLong must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Override
    public Optional<Order> findById(Long aLong) {
        return Optional.empty();
    }

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param aLong must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Override
    public boolean existsById(Long aLong) {
        return orderRepository.existsById(aLong);
    }

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    @Override
    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Returns all instances of the type {@code T} with the given IDs.
     * <p>
     * If some or all ids are not found, no entities are returned for these IDs.
     * <p>
     * Note that the order of elements in the result is not guaranteed.
     *
     * @param longs must not be {@literal null} nor contain any {@literal null} values.
     * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
     * {@literal ids}.
     * @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its items is {@literal null}.
     */
    @Override
    public Iterable<Order> findAllById(Iterable<Long> longs) {
        return orderRepository.findAllById(longs);
    }

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities.
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param aLong must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    @Override
    public void deleteById(Long aLong) {
        Optional<Order> order = orderRepository.findById(aLong);
        Customer customer = (order != null)?order.get().getCustomer() : null;
        if(customer != null) {
            customer.decrement();
            customerRepository.save(customer);
        }
        orderRepository.deleteById(aLong);
    }

    /**
     * Deletes a given entity.
     *
     * @param entity must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Override
    public void delete(Order entity) {

    }

    public Optional<Order> findByCode(String code) {
        return orderRepository.findByCode(code);
    }

    /**
     * Deletes all instances of the type {@code T} with the given IDs.
     *
     * @param longs must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal ids} or one of its elements is {@literal null}.
     * @since 2.5
     */
    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    /**
     * Deletes the given entities.
     *
     * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal entities} or one of its entities is {@literal null}.
     */
    @Override
    public void deleteAll(Iterable<? extends Order> entities) {

    }

    /**
     * Deletes all entities managed by the repository.
     */
    @Override
    public void deleteAll() {

    }
}
