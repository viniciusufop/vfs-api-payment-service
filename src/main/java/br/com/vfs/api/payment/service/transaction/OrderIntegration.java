package br.com.vfs.api.payment.service.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OrderIntegration {

    public Optional<Order> findOrderId(final Long orderId) {
        final var url = String .format("http://localhost:8080/api/pedidos/%d", orderId);
        final var restTemplate = new RestTemplate();
        try {
            ResponseEntity<Order> response = restTemplate.getForEntity(url, Order.class);
            return Optional.ofNullable(response.getBody());
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            return Optional.empty();
        }
    }
}
