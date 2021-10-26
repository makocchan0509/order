package masem.sample.microservice.order.messaging;

public interface IPublishMessage {
	public void publishMessage(String topic,Object message);
}
