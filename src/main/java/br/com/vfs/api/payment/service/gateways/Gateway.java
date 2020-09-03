package br.com.vfs.api.payment.service.gateways;

import br.com.vfs.api.payment.service.transaction.Transaction;

public interface Gateway {

    GatewayCalculate calculate(final GatewayInformation information);

    boolean pay(final Transaction transaction);
}
