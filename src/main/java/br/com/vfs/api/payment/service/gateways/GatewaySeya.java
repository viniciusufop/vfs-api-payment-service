package br.com.vfs.api.payment.service.gateways;

import br.com.vfs.api.payment.service.gateways.request.SeyaCardRequest;
import br.com.vfs.api.payment.service.gateways.request.SeyaPurchaseRequest;
import br.com.vfs.api.payment.service.gateways.request.TangoRequest;
import br.com.vfs.api.payment.service.transaction.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class GatewaySeya implements Gateway{
    @Override
    public GatewayCalculate calculate(GatewayInformation information) {
        //todo nao estou tratando localidade
        return new GatewayCalculate(new BigDecimal("6.00"), true, this);
    }

    @Override
    public boolean pay(Transaction transaction) {
        final var optionalOperation = verifyCard(transaction);

        if(optionalOperation.isEmpty()) return false;

        final var request = new SeyaPurchaseRequest(transaction);
        final var url = String.format("http://localhost:8080/seya/processa/%d",optionalOperation.get());
        final var restTemplate = new RestTemplate();
        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);
            if(response.getStatusCode().is2xxSuccessful()){
                return true;
            }
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            log.warn("M=pay, connector error from tango gateway");
        }
        return false;
    }

    private Optional<Long> verifyCard(Transaction transaction) {
        final var request = new SeyaCardRequest(transaction);
        final var url = "http://localhost:8080/seya/verifica";
        final var restTemplate = new RestTemplate();
        try {
            ResponseEntity<Long> response = restTemplate.postForEntity(url, request, Long.class);
            if(response.getStatusCode().is2xxSuccessful()){
                return Optional.ofNullable(response.getBody());
            }
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            log.warn("M=verifyCard, connector error from tango gateway");
        }
        return Optional.empty();
    }
}
