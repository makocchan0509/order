package masem.sample.microservice.order.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import masem.sample.microservice.order.domain.app.OrderAppService;

@Component
public class ConsumeMessage {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(ConsumeMessage.class);
	
	@Autowired
	OrderAppService appService;
	
	
	@KafkaListener(topics = "OrderSagaReply",groupId="OrderSagaReply")
	public void replyingListener(ConsumerRecord<?, ?> cr) {
		
		logger.info(cr.toString());
		appService.updateOrder((String) cr.value());
	}
}
