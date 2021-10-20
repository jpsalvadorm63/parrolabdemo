package com.jp.demo.entities.repository;

import com.jp.demo.entities.model.Order;
import com.jp.demo.entities.model.OrderItem;
import com.jp.demo.entities.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("customOrderItemRepository")
public class CustomOrderItemRepository  implements OrderItemRepository{
    private static final Logger log = LoggerFactory.getLogger(CustomOrderItemRepository.class);

    @Autowired
    @Qualifier("orderItemRepository")
    private OrderItemRepository orderItemRepository;

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    @Autowired
    @Qualifier("orderRepository")
    private OrderRepository orderRepository;

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @Override
    public <S extends OrderItem> S save(S entity) {
        OrderItem orderItem = orderItemRepository.save(entity);
        Product product = orderItem.getProduct();

        productRepository.save(product);
        Integer quantity = orderItem.getQuantity();
        Float price = orderItem.getProduct().getPrice();
        float value = (quantity!=null?quantity:0)*(price!=null?price:0);
        Optional<Order> order = orderRepository.findById(orderItem.getOrder().getId());
        if(order.isPresent()) {
            order.get().setOrderValue(order.get().getOrderValue()+value);
            orderRepository.save(order.get());
        }

        return (S) orderItem;
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
    public <S extends OrderItem> Iterable<S> saveAll(Iterable<S> entities) {
        return orderItemRepository.saveAll(entities);
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param aLong must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Override
    public Optional<OrderItem> findById(Long aLong) {
        return orderItemRepository.findById(aLong);
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
        return false;
    }

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    @Override
    public Iterable<OrderItem> findAll() {
        return null;
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
    public Iterable<OrderItem> findAllById(Iterable<Long> longs) {
        return null;
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
//        Optional<OrderItem> optOrderItem = orderItemRepository.findById(aLong);
//        if(optOrderItem.isPresent()) {
//            OrderItem orderItem = optOrderItem.get();
//            log.info("===> "+orderItem.getProduct().getId());
//            orderItem.getProduct().decrement();
//            productRepository.save(orderItem.getProduct());
//            float value = orderItem.getQuantity() * orderItem.getProduct().getPrice();
//            orderItem.getOrder().setOrderValue(orderItem.getOrder().getOrderValue()-value);
//            orderRepository.save(orderItem.getOrder());
//            orderItemRepository.delete(orderItem);
//        }
    }

    /**
     * Deletes a given entity.
     *
     * @param entity must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Override
    public void delete(OrderItem entity) {

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
    public void deleteAll(Iterable<? extends OrderItem> entities) {

    }

    /**
     * Deletes all entities managed by the repository.
     */
    @Override
    public void deleteAll() {

    }

    @Override
    public OrderItem findByOrder_IdAndProduct_Id(long order_id, long product_id) {
        return orderItemRepository.findByOrder_IdAndProduct_Id(order_id,product_id);
    }
}
