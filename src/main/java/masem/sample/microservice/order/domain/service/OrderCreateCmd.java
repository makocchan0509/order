package masem.sample.microservice.order.domain.service;

import java.util.List;

import masem.sample.microservice.order.dto.Item;

public class OrderCreateCmd {
	
	private String customerId;
	
	private List<Item> items;
	
	private int paymentMethod;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}
