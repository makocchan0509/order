package masem.sample.microservice.order.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import masem.sample.microservice.order.domain.app.OrderAppService;
import masem.sample.microservice.order.domain.service.OrderCreateCmd;
import masem.sample.microservice.order.domain.service.OrderCreateOutput;
import masem.sample.microservice.order.domain.service.OrderDomainService;
import masem.sample.microservice.order.dto.OrderRequest;

@EnableWebMvc
@SpringBootTest
public class OrderControllerTest {

	private MockMvc mockMvc;

	private OrderRequest request;

	private OrderCreateCmd input;

	private OrderCreateOutput output;

	private String requestJson;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	OrderAppService service;

	@BeforeEach
	void beforeEach() throws JsonProcessingException {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		request = new OrderRequest();
		request.setService("order");
		request.setType("create");
		request.setCustomerId("0001");
		//request.setMenuId("0002");
		//request.setQuantity(2);
		request.setPaymentMethod(1);

		ObjectMapper mapper = new ObjectMapper();
		requestJson = mapper.writeValueAsString(request);

		output = new OrderCreateOutput();
		output.setOrderId("dummy");
		output.setResult(true);
	}

	@Test
	public void createTest() throws Exception {

		String jsonString = "{\"result\":true,\"service\":\"order\",\"type\":\"create\",\"orderId\":\"dummy\"}";

		when(service.createOrder(any())).thenReturn(output);
		mockMvc.perform(post("/order/create").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isOk()).andExpect(content().json(jsonString));
	}

}
