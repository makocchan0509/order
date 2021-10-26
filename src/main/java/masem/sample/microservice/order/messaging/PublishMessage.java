package masem.sample.microservice.order.messaging;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import masem.sample.microservice.order.exception.SystemException;

@Component
public class PublishMessage implements IPublishMessage{
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(PublishMessage.class);
	
	@Autowired
	KafkaTemplate<String,Object> kafkaTemplate;
	
	public void publishMessage(String topic,Object message) {
		try {
			kafkaTemplate.send(topic,message).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException();
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException();
		}
	}
}
