package masem.sample.microservice.order.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import masem.sample.microservice.order.exception.SystemException;
import masem.sample.microservice.order.util.GetTimestamp;
import masem.sample.microservice.order.util.OrderIdGenerator;

@Entity
@Table(name = "order_table")
public class Order {
	
	private static String CREATE_STATUS = "CREATE";
	
	private static String CUSTOMER_VERIFY_STATUS = "CSTMR_VRFY";
	
	private static String TICEKT_ISSUSED_STATUS = "TCKT_ISSUED";
	
	private static String PAYMENT_APPROVE_STATUS = "PAY_APPRV";
	
	private static String ORDER_COMPLETE_STATUS = "COMPLETE";
	
	private static String ORDER_CANCEL_STATUS = "CANCELLED";

	private static String CUSTOMER_VERIFY_ERROR = "CSTMR_VRFY_ERR";
	
	private static String TICKET_CREATE_ERROR = "TICKET_ERR";
	
	private static String PAYMENT_ERROR = "PAYMENT_ERR";
	
	private static String ERROR = "ERROR";
	
	@Id
	@Column(name="orderid")
	private String orderId;
	
	@Column(name="ordertime")
	private Timestamp orderTime;
	
	@Column(name="customerid")
	private String customerId;
	
	@Column(name="items")
	private String items;
	
	@Column(name="paymentmethod")
	private int paymentMethod;
	
	@Column(name="order_status")
	private String orderStatus;
	
	@Column(name="price")
	private int price;

	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	
	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void createOrder() {
		setOrderId(OrderIdGenerator.createOrderId());
		setOrderTime(GetTimestamp.getTimestamp());
		createStatus();
	}
	
	private void createStatus() {
		setOrderStatus(CREATE_STATUS);
	}
	
	public void progressStatus() {
		
		switch(getOrderStatus()) {
			case "CREATE":
				setOrderStatus(CUSTOMER_VERIFY_STATUS);
				break;
			case "CSTMR_VRFY":
				setOrderStatus(TICEKT_ISSUSED_STATUS);
				break;
			case "TCKT_ISSUED":
				setOrderStatus(ORDER_COMPLETE_STATUS);
				break;
			default:
				throw new SystemException();
		}
	}
	
	public void failStatus() {
		
		switch(getOrderStatus()) {
			case "CREATE":
				setOrderStatus(CUSTOMER_VERIFY_ERROR);
				break;
			case "CSTMR_VRFY":
				setOrderStatus(TICKET_CREATE_ERROR);
				break;
			case "TCKT_ISSUED":
				setOrderStatus(PAYMENT_ERROR);
				break;
			default:
				throw new SystemException();
		}
	}
	
}
