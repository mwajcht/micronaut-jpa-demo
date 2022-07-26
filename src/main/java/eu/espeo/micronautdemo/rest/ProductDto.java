package eu.espeo.micronautdemo.rest;

import java.math.BigDecimal;
import java.util.UUID;

import eu.espeo.micronautdemo.domain.Product;
import io.micronaut.core.annotation.Introspected;

@Introspected
public record ProductDto(
		UUID businessId,
		String name,
		BigDecimal price
) {
	public static ProductDto fromProduct(Product product) {
		return new ProductDto(
				product.getBusinessId(),
				product.getName(),
				product.getPrice());
	}

	public Product toProduct() {
		return Product.builder()
				.businessId(businessId)
				.price(price)
				.name(name)
				.build();
	}
}
