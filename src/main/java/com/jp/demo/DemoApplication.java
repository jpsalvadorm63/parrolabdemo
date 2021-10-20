package com.jp.demo;

import com.jp.demo.entities.repository.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jp.demo.entities.model.PaymentType;
import com.jp.demo.entities.model.Product;
import com.jp.demo.entities.model.ShippingAddress;
import com.jp.demo.entities.model.Customer;
import com.jp.demo.entities.model.Order;

import java.sql.Date;
import java.util.Optional;

@SpringBootApplication
@RestController
public class DemoApplication {
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@GetMapping("/about")
	public String hello(@RequestParam(value = "name", defaultValue = "Juan Salvador") String name) {
		return String.format("developer %s", name);
	}

	@Bean
	public CommandLineRunner fillPaymentTypes(PaymentTypeRepository rep) {
		return (args) -> {
			String[] types = {"cash", "credit card", "check", "other"};
			for(String type: types) {
				try {
					PaymentType paymentType = rep.findByTypeIsLikeIgnoreCase(type);
					if(paymentType == null) {
						PaymentType newPaymentType = rep.save(new PaymentType(type));
						log.info(
								String.format(
										"Payment type '%s' has been saved to DB, as: %s",
										newPaymentType.getType(),
										newPaymentType
								)
						);
					} else {
						log.info(
								String.format(
										"Payment type '%s' is already in DB as: %s",
										paymentType.getType(),
										paymentType
								)
						);
					}
				}
				catch (Exception e) {
					log.error(e.getLocalizedMessage());
				}
			}
		};
	}

	@Bean
	public CommandLineRunner fillProducts(ProductRepository rep) {
		return (args) -> {
			String[] products = {"bread", "milk", "butter", "cheese"};
			float[] prices = {0.15f, 1.8f, 2.5f, 0.95f};
			String[] weights = {"unit", "liter", "kilo", "ounce"};
			for(int i=0 ; i < products.length; i++) {
				String product = products[i];
				float price = prices[i];
				String weight = weights[i];
				try {
					Product mproduct = rep.findByDescription(product);
					if(mproduct == null) {
						Product newProduct = rep.save(new Product(product,price,weight));
						log.info(
								String.format(
										"Product '%s' has been saved to DB, as: %s",
										newProduct.getDescription(),
										newProduct
								)
						);
					} else {
						log.info(
								String.format(
										"Product '%s' is already in DB, as: %s",
										mproduct.getDescription(),
										mproduct
								)
						);
					}
				}
				catch (Exception e) {
					log.error(e.getLocalizedMessage());
				}
			}
		};
	}

	@Bean
	public CommandLineRunner fillDirections(ShippingAddressRepository sarep, CustomerRepository custrep) {
		return (args) -> {
			ShippingAddress sa = sarep.findByStreetAndNumber("America Av.",12);
			if( sa == null) {
				sa = sarep.save(new ShippingAddress("America Av.", 12, "OUI", "PI", "331121", "00-011", "EC"));
			    log.info(String.format("Customer has been saved to DB, as %s",sa));
			};
			String email = "jpsalvador@gmail.com";
			Customer customer = custrep.findByEmail(email);
			if(customer == null) {
				customer = custrep.save(new Customer("JSalvador","0969054256",email, sa));
				log.info(String.format("Customer has been saved to DB, as %s",customer));
			}
		};
	}

	@Bean
	public CommandLineRunner fillOrder(
			@Qualifier("customOrderRepository") OrderRepository orderRep,
			ShippingAddressRepository sarep,
			CustomerRepository custrep,
			PaymentTypeRepository ptrep,
			ProductRepository prorep,
			@Qualifier("customOrderItemRepository") OrderItemRepository oiRep
	) {
		return (args) -> {
			String orderCode = "2021-10-19 01";
			Optional<Order> optOrder = orderRep.findByCode(orderCode);
			Order order;
			if(optOrder.isPresent()) {
				order =  optOrder.get();
			} else {
				order = new Order(orderCode);
				order = orderRep.save(order);
			}
			order.setDate("2021-10-19 14:30:09");
			order.setCustomer(custrep.findByEmail("jpsalvador@gmail.com"));
			order.setShippingAddress(sarep.findByStreetAndNumber("America Av.",12));
			order.setPaymentType(ptrep.findByTypeIsLikeIgnoreCase("cash"));
			order = orderRep.save(order);
			oiRep.save(order.add(prorep.findByDescription("milk"), 2));
			oiRep.deleteById(525L);
//			oiRep.save(order.add(prorep.findByDescription("cheese"), 2));
		};
	}
}
