package br.com.vfs.api.payment.service.transaction.online;

import br.com.vfs.api.payment.service.gateways.Gateway;
import br.com.vfs.api.payment.service.gateways.GatewayCalculate;
import br.com.vfs.api.payment.service.gateways.GatewayComparator;
import br.com.vfs.api.payment.service.gateways.GatewayInformation;
import br.com.vfs.api.payment.service.paymentmethod.CreditCardType;
import br.com.vfs.api.payment.service.transaction.Transaction;
import br.com.vfs.api.payment.service.transaction.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.vfs.api.payment.service.transaction.TransactionStatus.CONFIRM;

@Service
@RequiredArgsConstructor
public class PaymentGateway {

    private final List<Gateway> gateways;
    public Transaction execute(final Transaction transaction){
        //calculate order gateway's
        List<GatewayCalculate> gatewayCalculates = gateways.stream()
                .map(gateway -> gateway.calculate(new GatewayInformation(transaction.getValue(), CreditCardType.MASTER)))
                .filter(GatewayCalculate::isAccept)
                .sorted(new GatewayComparator())
                .collect(Collectors.toList());

        for (GatewayCalculate gatewayCalculate: gatewayCalculates){
            if(gatewayCalculate.getGateway().pay(transaction)){
                transaction.setStatus(CONFIRM);
                break;
            }
        }
        return transaction;
    }
}
