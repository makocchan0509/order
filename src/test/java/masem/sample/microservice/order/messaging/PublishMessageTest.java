package masem.sample.microservice.order.messaging;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import masem.sample.microservice.order.dto.CustomerRequest;

@SpringBootTest
public class PublishMessageTest {

	@Autowired
	PublishMessage target;
	
	@Autowired
	KafkaTemplate<String,Object> kafkaTemplate;
	
	CustomerRequest message;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		message = new CustomerRequest("0002");
		message.setService("customer");
		message.setType("verify");
	}
	
	@Test
	public void successTest() throws InterruptedException, ExecutionException {
		String topic = "CustomerVerified";
		target.publishMessage(topic, message);
		//verify(kafkaTemplate).send(topic, message).get();
	}
}
