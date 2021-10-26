package masem.sample.microservice.order.domain.service;

import masem.sample.microservice.order.entity.Order;

public interface OrderDomainService {
	
	public Order create(OrderCreateCmd input);
	
	public Order statusUpdate(OrderUpdateCmd input);
	
}
