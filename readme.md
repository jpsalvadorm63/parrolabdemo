# JSalvador test

. Please go to HAL Explorer for API documentation

. `http://localhost:8080/`

Next is a complete order transacion:

```

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
```