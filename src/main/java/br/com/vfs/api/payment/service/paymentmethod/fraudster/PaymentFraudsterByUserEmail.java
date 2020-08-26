package br.com.vfs.api.payment.service.paymentmethod.fraudster;

import br.com.vfs.api.payment.service.paymentmethod.PaymentMethod;
import br.com.vfs.api.payment.service.user.User;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Component
public class PaymentFraudsterByUserEmail implements PaymentFraudster{

    private final Set<String> fraudsters = Set.of("vinicius@mock.com", "jose@mock.com");

    @Override
    public boolean validPaymentMethods(@NotNull final PaymentMethod paymentMethod, final User user) {
        return paymentMethod.getType().isOffline()  || !fraudsters.contains(user.getEmail());
    }
}
