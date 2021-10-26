package masem.sample.microservice.order.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import masem.sample.microservice.order.dto.CustomerRequest;
import masem.sample.microservice.order.dto.Item;
import masem.sample.microservice.order.entity.Order;
import masem.sample.microservice.order.exception.SystemException;
import masem.sample.microservice.order.messaging.IPublishMessage;
import masem.sample.microservice.order.repository.OrderRepository;

@Service
public class OrderDomainServiceImpl implements OrderDomainService{
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	IPublishMessage publishMessage;
	
	@Override
	@Transactional
	public Order create(OrderCreateCmd input) {
				
		Order order = new Order();
		order.createOrder();
		List<Item> items = input.getItems();
		String itemsStr = "[";
		
		for(int i = 0;i < items.size();i++) {
			itemsStr = itemsStr.concat("{\"menuId\":\""+items.get(i).getMenuId()+"\",\"quantity\":"+items.get(i).getQuantity()+"}");
			if(i != items.size()-1) {
				itemsStr = itemsStr.concat(",");
			}
		}
		itemsStr = itemsStr.concat("]");
		System.out.println("items :"+ itemsStr);
		
		BeanUtils.copyProperties(input, order);
		order.setItems(itemsStr);
		
		Order entity = orderRepository.save(order);
		
		return entity;
	}

	@Override
	public Order statusUpdate(OrderUpdateCmd input) {
		
		Order order = getOrderOne(input.getOrderId());
		
		if(input.getResult().equals("true")) {
			order.progressStatus();
			order.setPrice(input.getPrice());
		}else {
			order.failStatus();
		}
		
		orderRepository.save(order);
		
		return order;
	}
	
	public Order getOrderOne(String orderId) {
		Optional<Order> record = Optional.ofNullable(orderRepository.findById(orderId)
				.orElseThrow(() -> new SystemException()));
		Order order = record.get();
		
		return order;
	}

}
