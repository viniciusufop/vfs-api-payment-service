package br.com.vfs.api.payment.service.gateways;

import br.com.vfs.api.payment.service.gateways.request.SaoriRequest;
import br.com.vfs.api.payment.service.paymentmethod.CreditCardType;
import br.com.vfs.api.payment.service.transaction.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Slf4j
@Service
public class GatewaySaori implements Gateway{
    private static final Set<CreditCardType> acceptCreditCardTypes =
            Set.of(CreditCardType.MASTER, CreditCardType.VISA);
    private static final BigDecimal percents = new BigDecimal("0.05");

    @Override
    public GatewayCalculate calculate(GatewayInformation information) {
        if (!acceptCreditCardTypes.contains(information.getCreditCardType()))
            return new GatewayCalculate(BigDecimal.ZERO, false, this);
        final var rate = information.getValue().multiply(percents).setScale(2, RoundingMode.DOWN);
        return new GatewayCalculate(rate, true, this);
    }

    @Override
    public boolean pay(Transaction transaction) {
        final var request = new SaoriRequest(transaction);
        final var url = "http://localhost:8080/saori/processa";
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
}
