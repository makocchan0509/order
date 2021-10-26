package masem.sample.microservice.order.domain.app;

import masem.sample.microservice.order.domain.service.OrderCreateCmd;
import masem.sample.microservice.order.domain.service.OrderCreateOutput;

public interface OrderAppService {
	
	public OrderCreateOutput createOrder(OrderCreateCmd input);
	
	public void updateOrder(String message);
}
