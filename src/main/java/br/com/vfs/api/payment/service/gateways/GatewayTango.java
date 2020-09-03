package br.com.vfs.api.payment.service.gateways;

import br.com.vfs.api.payment.service.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class GatewayTango implements Gateway{

    private final static BigDecimal delimiter = new BigDecimal("100.00");
    private final static BigDecimal fix = new BigDecimal("4.00");
    private static final BigDecimal percents = new BigDecimal("0.05");
    @Override
    public GatewayCalculate calculate(GatewayInformation information) {
        //todo nao estou tratando localidade
        final var rate = rate(information);
        return new GatewayCalculate(rate, true, this);
    }

    @Override
    public boolean pay(Transaction transaction) {
        return false;
    }

    private BigDecimal rate(final GatewayInformation information) {
        if(delimiter.compareTo(information.getValue()) >= 0){
            return fix;
        }
        return information.getValue().multiply(percents).setScale(2, RoundingMode.DOWN);
    }
}
