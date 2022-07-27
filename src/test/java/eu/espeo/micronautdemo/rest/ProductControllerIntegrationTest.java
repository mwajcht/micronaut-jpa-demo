package eu.espeo.micronautdemo.rest;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

@MicronautTest
class ProductControllerIntegrationTest {

    @Inject
    private ProductController sut;

    @Test
    void shouldSaveAndRetrieveProduct() {
        // given
        var productBusinessId = UUID.randomUUID();
        var productName = "NewProduct";
        var price = BigDecimal.valueOf(10.5);

        // when
        sut.create(new ProductDto(productBusinessId, productName, price));
        var product = sut.getProduct(productBusinessId.toString());

        // then
        then(product).isNotNull();
        then(product.businessId()).isEqualTo(productBusinessId);
        then(product.name()).isEqualTo(productName);
        then(product.price()).isEqualByComparingTo(price);
    }
}
