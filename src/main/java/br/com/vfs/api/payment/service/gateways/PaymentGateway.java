package br.com.vfs.api.payment.service.gateways;

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

    private final List<Gateway> gateways; // 1

    public Transaction execute(final Transaction transaction){
        //calculate order gateway's
        final var gatewayCalculates = gateways.stream()
                .map(gateway ->
                        gateway.calculate(new GatewayInformation(transaction.getValue(),
                                CreditCardType.MASTER))) //2
                .filter(GatewayCalculate::isAccept) // 1
                .sorted(new GatewayComparator()) // 1
                .collect(Collectors.toList()); // 1
        //execute pay by calculate order
        for (GatewayCalculate gatewayCalculate: gatewayCalculates){ // 1
            if(gatewayCalculate.getGateway().pay(transaction)){ // 1
                transaction.setStatus(CONFIRM);
                break;
            }
        }
        return transaction; // 1
    }
}
