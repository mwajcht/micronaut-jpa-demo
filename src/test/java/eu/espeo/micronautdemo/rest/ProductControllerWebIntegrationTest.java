package eu.espeo.micronautdemo.rest;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

@MicronautTest
class ProductControllerWebIntegrationTest {

    @Inject
    @Client("/")
    private HttpClient client;

    @Test
    void shouldSaveAndRetrieveProduct() {
        // given
        var productBusinessId = UUID.randomUUID();
        var productName = "Apple MacBook";
        var price = BigDecimal.valueOf(11499.90);

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
}
