package com.jp.demo.entities.repository;

//import com.jp.demo.demoApplication;
import com.jp.demo.entities.model.PaymentType;
import org.springframework.data.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface PaymentTypeRepository extends CrudRepository<PaymentType, Long> {

    static final Logger log = LoggerFactory.getLogger(PaymentTypeRepository.class);

    PaymentType findByTypeIsLikeIgnoreCase(String type);

}