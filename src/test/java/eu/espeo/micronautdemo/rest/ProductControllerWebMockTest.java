package eu.espeo.micronautdemo.rest;

import eu.espeo.micronautdemo.domain.Product;
import eu.espeo.micronautdemo.domain.ProductService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@MicronautTest
class ProductControllerWebMockTest {
    @Inject
    @Client("/")
    private HttpClient client;

    @Inject
    private ProductService productService;

    @Test
    void shouldSaveAndRetrieveProduct() {
        // given
        var productBusinessId = UUID.randomUUID();
        var productName = "Apple MacBook";
        var price = BigDecimal.valueOf(11499.90);
        given(productService.create(any())).willAnswer(answer -> answer.getArgument(0));
        given(productService.findByBusinessId(productBusinessId)).willReturn(Product.builder()
                .businessId(productBusinessId)
                .name(productName)
                .price(price)
                .build());

        // when
        var createResponse = client.toBlocking()
                .exchange(HttpRequest.POST("/products", new ProductDto(productBusinessId, productName, price)));
        HttpStatus createStatus = createResponse.getStatus();
        then((CharSequence) createStatus).isEqualTo(HttpStatus.OK);
        ProductDto product = client.toBlocking()
                .retrieve(HttpRequest.GET("/products/" + productBusinessId), ProductDto.class);

        // then
        then(product).isNotNull();
        then(product.businessId()).isEqualTo(productBusinessId);
        then(product.name()).isEqualTo(productName);
        then(product.price()).isEqualByComparingTo(price);
    }

    @MockBean(ProductService.class)
    ProductService productService() {
        return mock(ProductService.class);
    }
}
