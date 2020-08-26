package br.com.vfs.api.payment.service.paymentmethod.fraudster;

import br.com.vfs.api.payment.service.paymentmethod.PaymentMethod;
import br.com.vfs.api.payment.service.user.User;

import javax.validation.constraints.NotNull;

public interface PaymentFraudster {

    boolean validPaymentMethods(@NotNull final PaymentMethod paymentMethod, final User user);
}
