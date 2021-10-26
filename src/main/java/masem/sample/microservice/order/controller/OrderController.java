package masem.sample.microservice.order.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import masem.sample.microservice.order.domain.app.OrderAppService;
import masem.sample.microservice.order.domain.service.OrderCreateCmd;
import masem.sample.microservice.order.domain.service.OrderCreateOutput;
import masem.sample.microservice.order.dto.OrderRequest;
import masem.sample.microservice.order.dto.OrderResponse;

@RestController
public class OrderController {
	
	@Autowired
	OrderAppService appService;

	@RequestMapping(value = "/order/create", method = RequestMethod.POST, consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public OrderResponse create(@RequestBody OrderRequest request) {

		OrderCreateCmd input = new OrderCreateCmd();

		BeanUtils.copyProperties(request, input);
		
		OrderCreateOutput output = appService.createOrder(input);

		OrderResponse response = new OrderResponse();
		BeanUtils.copyProperties(request, response);
		BeanUtils.copyProperties(output, response);
		

		return response;
	}

}
