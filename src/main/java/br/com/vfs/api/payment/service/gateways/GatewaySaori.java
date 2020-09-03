package br.com.vfs.api.payment.service.gateways;

import br.com.vfs.api.payment.service.paymentmethod.CreditCardType;
import br.com.vfs.api.payment.service.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Service
public class GatewaySaori implements Gateway{
    private static final Set<CreditCardType> acceptCreditCardTypes =
            Set.of(CreditCardType.MASTER, CreditCardType.VISA);
    private static final BigDecimal percents = new BigDecimal("0.05");

    @Override
    public GatewayCalculate calculate(GatewayInformation information) {
        //todo nao estou tratando localidade
        if (!acceptCreditCardTypes.contains(information.getCreditCardType()))
            return new GatewayCalculate(BigDecimal.ZERO, false, this);
        final var rate = information.getValue().multiply(percents).setScale(2, RoundingMode.DOWN);
        return new GatewayCalculate(rate, true, this);
    }

    @Override
    public boolean pay(Transaction transaction) {
        return false;
    }
}
