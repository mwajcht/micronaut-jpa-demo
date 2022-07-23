package eu.espeo.micronautdemo.domain;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import eu.espeo.micronautdemo.db.ProductRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	public List<Product> findAll() {
		return productRepository.findAll().stream()
				.map(eu.espeo.micronautdemo.db.Product::toProduct)
				.collect(toList());
	}


	public Product findById(UUID businessId) {
		return productRepository.findByBusinessId(businessId)
				.map(eu.espeo.micronautdemo.db.Product::toProduct)
				.orElseThrow();
	}

	@Transactional
	public Product create(Product product) {
		return productRepository
				.save(eu.espeo.micronautdemo.db.Product.fromProduct(product))
				.toProduct();
	}

	@Transactional
	public void delete(UUID businessId) {
		productRepository.deleteByBusinessId(businessId);
	}
}
