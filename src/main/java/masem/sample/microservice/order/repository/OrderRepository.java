package masem.sample.microservice.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import masem.sample.microservice.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order,String>{

}
