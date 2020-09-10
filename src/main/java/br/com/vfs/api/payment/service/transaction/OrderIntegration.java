package br.com.vfs.api.payment.service.transaction;

import br.com.vfs.api.payment.service.shared.filter.OrderCache;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
public class OrderIntegration {

    public Optional<Order> findOrderId(final Long orderId) {
        if(OrderCache.getOrder() != null) return Optional.of(OrderCache.getOrder());
        final var url = String .format("http://localhost:8080/api/pedidos/%d", orderId);
        final var restTemplate = new RestTemplate();
        try {
            ResponseEntity<OrderResponse> response = restTemplate.getForEntity(url, OrderResponse.class);
            if(Objects.isNull(response.getBody())) return Optional.empty();
            final var order = new Order(orderId, response.getBody());
            OrderCache.setOrder(order);
            return Optional.ofNullable(order);
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            return Optional.empty();
        }
    }
}