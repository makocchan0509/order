package masem.sample.microservice.order.domain.service;

public class OrderCreateOutput {
	
	private Boolean result;
	
	private String orderId;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
