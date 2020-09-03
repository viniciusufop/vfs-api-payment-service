package br.com.vfs.api.payment.service.gateways;

import br.com.vfs.api.payment.service.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GatewaySeya implements Gateway{
    @Override
    public GatewayCalculate calculate(GatewayInformation information) {
        //todo nao estou tratando localidade
        return new GatewayCalculate(new BigDecimal("6.00"), true, this);
    }

    @Override
    public boolean pay(Transaction transaction) {
        return false;
    }
}
