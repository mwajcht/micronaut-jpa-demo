package eu.espeo.micronautdemo.rest;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import eu.espeo.micronautdemo.domain.OrderService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;

@Controller(value = "/orders", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@Get
	public List<FullOrderDto> listOrders() {
		return orderService.findAll().stream()
				.map(FullOrderDto::fromOrder)
				.collect(toList());
	}

	@Get(value = "/{orderId}")
	public FullOrderDto getOrder(@PathVariable("orderId") String orderId) {
		return FullOrderDto.fromOrder(orderService.findByBusinessId(UUID.fromString(orderId)));
	}

	@Post
	public OrderDto create(@Body OrderDto orderDto) {
		return OrderDto.fromOrder(orderService.create(orderDto.toOrder()));
	}

	@Delete("/{orderId}")
	public void delete(@PathVariable("orderId") String orderId) {
		orderService.delete(UUID.fromString(orderId));
	}
}
