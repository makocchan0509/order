package masem.sample.microservice.order.domain.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import masem.sample.microservice.order.domain.service.OrderCreateCmd;
import masem.sample.microservice.order.domain.service.OrderCreateOutput;
import masem.sample.microservice.order.domain.service.OrderDomainService;
import masem.sample.microservice.order.domain.service.OrderUpdateCmd;
import masem.sample.microservice.order.dto.CustomerRequest;
import masem.sample.microservice.order.dto.Item;
import masem.sample.microservice.order.dto.PaymentRequest;
import masem.sample.microservice.order.dto.TicketCancel;
import masem.sample.microservice.order.dto.TicketRequest;
import masem.sample.microservice.order.entity.Order;
import masem.sample.microservice.order.messaging.IPublishMessage;

@Component
public class OrderAppServiceImpl implements OrderAppService{
	
	
	Map<String,String> topicTable;
	
	@Autowired
	OrderDomainService domainService;
	
	@Autowired
	IPublishMessage publishMessage;
	
	public OrderAppServiceImpl() {
		topicTable = new HashMap<String,String>();
		topicTable.put("CREATE", "CustomerVerified");
		topicTable.put("CSTMR_VRFY", "CreateTicket");
		topicTable.put("TCKT_ISSUED", "PaymentRequest");
		topicTable.put("PAYMENT_ERR", "CancelSaga");
	}
	
	
	public String findNextDest(String currentStatus) {
		return topicTable.get(currentStatus);
	}
	
	@Override
	@Transactional
	public OrderCreateOutput createOrder(OrderCreateCmd input) {
		
		Order order = domainService.create(input);
		
		String nextTopic = findNextDest(order.getOrderStatus());
		
		CustomerRequest message = new CustomerRequest(input.getCustomerId());
		
		message.setService("customer");
		message.setType("verify");
		message.setOrderId(order.getOrderId());
		
		publishMessage.publishMessage(nextTopic, message);
		
		OrderCreateOutput output = new OrderCreateOutput();
		output.setResult(true);
		output.setOrderId(order.getOrderId());
		
		return output;
	}
	


	@Override
	@Transactional
	public void updateOrder(String message) {
		
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			OrderUpdateCmd cmd = mapper.readValue(message,OrderUpdateCmd.class);
			Order order = domainService.statusUpdate(cmd);
			String nextTopic = findNextDest(order.getOrderStatus());
			
			Object request = null;
			System.out.println("nextTopic:" +nextTopic);
			if (nextTopic != null){

				if(nextTopic.equals("CreateTicket")) {
					TicketRequest req = new TicketRequest();
					req.setService("kitchen");
					req.setType("create_ticket");
					req.setOrderId(order.getOrderId());
					
					List<Item> items = mappingItems(order.getItems());
					
					req.setItems(items);
					req.setCustomerId(order.getCustomerId());
					request = req;
					
				}else if(nextTopic.equals("PaymentRequest")){
					PaymentRequest preq = new PaymentRequest();
					preq.setService("payment");
					preq.setType("request");
					preq.setOrderId(order.getOrderId());
					preq.setCustomerId(order.getCustomerId());
					preq.setPaymentMethod(order.getPaymentMethod());
					preq.setPrice(order.getPrice());
					request = preq;
				}else if(nextTopic.equals("CancelSaga")) {
					TicketRequest tc = new TicketRequest();
					tc.setService("cancel");
					tc.setType("cancel");
					tc.setOrderId(order.getOrderId());
					request = tc;
				}
				
				publishMessage.publishMessage(nextTopic, request);
			}
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private List<Item> mappingItems(String itemsStr) throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		List<Item> items = mapper.readValue(itemsStr,new TypeReference<List<Item>>() {});
		
//		for(int i=0;i<items.size();i++) {
//			System.out.println("menuId:"+items.get(i).getMenuId()+"quantity:"+items.get(i).getQuantity());
//		}
		return items;
	}

}
