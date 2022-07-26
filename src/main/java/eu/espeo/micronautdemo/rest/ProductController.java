package eu.espeo.micronautdemo.rest;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import eu.espeo.micronautdemo.domain.ProductService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;

@Controller(value = "/products", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@Get
	public List<ProductDto> listProducts() {
		return productService.findAll().stream()
				.map(ProductDto::fromProduct)
				.collect(toList());
	}

	@Get(value = "/{productId}")
	public ProductDto getProduct(@PathVariable("productId") String productId) {
		return ProductDto.fromProduct(productService.findByBusinessId(UUID.fromString(productId)));
	}

	@Post
	public ProductDto create(@Body ProductDto productDto) {
		return ProductDto.fromProduct(productService.create(productDto.toProduct()));
	}

	@Delete("/{productId}")
	public void delete(@PathVariable("productId") String productId) {
		productService.delete(UUID.fromString(productId));
	}
}
