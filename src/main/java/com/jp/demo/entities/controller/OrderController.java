package com.jp.demo.entities.controller;

import com.jp.demo.entities.repository.CustomerRepository;
import com.jp.demo.entities.repository.OrderRepository;
//import com.jp.demo.entities.model.*;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    protected final OrderRepository orderRepository;
    protected final CustomerRepository customerRepository;

    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

//    @PostMapping("/order/add")
//    public Order addOrder(
//            @RequestParam Customer customer,
//            @RequestParam ShippingAddress shippingAddress,
//            @RequestParam PaymentType paymentType
//    ) {
//
//        Order order = new Order();
//        Order newOrder = null;
//        order.setCustomer(customer);
//        order.setShippingAddress(shippingAddress);
//        order.setPaymentType(paymentType);
//
//        try {
//            newOrder = orderRepository.save(order);
//            customer.setRefCounter(customer.getRefCounter()+1);
//            customerRepository.save(customer);
//        } catch (Exception e) {
//            newOrder = null;
//        }
//        return newOrder;
//    }

}
