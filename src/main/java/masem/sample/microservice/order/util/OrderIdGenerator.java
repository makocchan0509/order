package masem.sample.microservice.order.util;

import java.util.UUID;

public class OrderIdGenerator {
	
	public static String createOrderId() {
		UUID orderId = UUID.randomUUID();
		return orderId.toString();
	}
}
