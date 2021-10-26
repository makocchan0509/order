package masem.sample.microservice.order.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import masem.sample.microservice.order.domain.service.OrderCreateCmd;
import masem.sample.microservice.order.domain.service.OrderCreateOutput;
import masem.sample.microservice.order.domain.service.OrderDomainService;
import masem.sample.microservice.order.dto.CustomerRequest;
import masem.sample.microservice.order.entity.Order;
import masem.sample.microservice.order.messaging.PublishMessage;
import masem.sample.microservice.order.repository.OrderRepository;

@SpringBootTest
public class OrderDomainServiceImplTest {

	@Autowired
	OrderDomainService service;
	
	@MockBean
	OrderRepository orderRepository;
	
	@MockBean
	PublishMessage publishMessage;
	
	OrderCreateCmd input;
	
	OrderCreateOutput output;
	
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		
		input = new OrderCreateCmd();		
		input.setCustomerId("0001");
		//input.setMenuId("0002");
		input.setPaymentMethod(1);
		//input.setQuantity(2);
		
	}
	
	@Test
	public void createTest() {
		when(orderRepository.save(any())).thenReturn(null);
		doNothing().when(publishMessage).publishMessage(any(),any());
		
		Order result = service.create(input);
		
		verify(orderRepository).save(any());
		//verify(publishMessage).publishMessage(eq("CustomerVerified"), anyObject());
	}
	
	
}
