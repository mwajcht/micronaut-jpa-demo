package eu.espeo.micronautdemo.domain;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import eu.espeo.micronautdemo.db.BuyerRepository;
import eu.espeo.micronautdemo.db.OrderRepository;
import eu.espeo.micronautdemo.db.ProductRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final BuyerRepository buyerRepository;
	private final ProductRepository productRepository;

	public List<Order> findAll() {
		return orderRepository.findAll().stream()
				.map(eu.espeo.micronautdemo.db.Order::toOrder)
				.collect(toList());
	}


	public Order findById(UUID businessId) {
		return orderRepository.findByBusinessId(businessId).toOrder();
	}

	@Transactional
	public Order create(Order order) {
		var orderToBeSaved = eu.espeo.micronautdemo.db.Order.fromOrder(order);
		orderToBeSaved.setBuyer(buyerRepository.findByBusinessId(order.getBuyer().getBusinessId()).orElseThrow());
		orderToBeSaved.setProducts(order.getProducts().stream()
				.map(product -> productRepository.findByBusinessId(product.getBusinessId())
						.orElseThrow())
				.collect(toList()));
		return orderRepository.save(orderToBeSaved).toOrder();
	}

	@Transactional
	public void delete(UUID businessId) {
		orderRepository.deleteByBusinessId(businessId);
	}
}
