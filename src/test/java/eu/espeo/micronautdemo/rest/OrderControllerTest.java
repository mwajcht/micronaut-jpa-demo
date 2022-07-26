package eu.espeo.micronautdemo.rest;

import eu.espeo.micronautdemo.domain.OrderService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@MicronautTest
class OrderControllerTest {
    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    private OrderService orderService;

    @Test
    void shouldListOrders() {
        // given
        given(orderService.findAll()).willReturn(Collections.emptyList());

        // when
        List<FullOrderDto> orders = client.toBlocking().retrieve(HttpRequest.GET("/orders"), List.class);

        // then
        then(orderService).should().findAll();
        and.then(orders).isNotNull();
    }

    @MockBean(OrderService.class)
    OrderService orderService() {
        return mock(OrderService.class);
    }
}