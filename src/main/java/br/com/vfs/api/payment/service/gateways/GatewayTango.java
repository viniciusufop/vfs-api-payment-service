package br.com.vfs.api.payment.service.gateways;

import br.com.vfs.api.payment.service.gateways.request.TangoRequest;
import br.com.vfs.api.payment.service.transaction.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class GatewayTango implements Gateway{

    private static final BigDecimal delimiter = new BigDecimal("100.00");
    private static final BigDecimal fix = new BigDecimal("4.00");
    private static final BigDecimal percents = new BigDecimal("0.05");
    @Override
    public GatewayCalculate calculate(final GatewayInformation information) {
        final var rate = rate(information);
        return new GatewayCalculate(rate, true, this);
    }

    @Override
    public boolean pay(final Transaction transaction) {
        final var request = new TangoRequest(transaction);
        final var url = "http://localhost:8080/tango/processa";
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

    private BigDecimal rate(final GatewayInformation information) {
        if(delimiter.compareTo(information.getValue()) >= 0){
            return fix;
        }
        return information.getValue().multiply(percents).setScale(2, RoundingMode.DOWN);
    }
}
