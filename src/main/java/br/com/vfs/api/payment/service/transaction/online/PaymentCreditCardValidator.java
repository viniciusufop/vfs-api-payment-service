package br.com.vfs.api.payment.service.transaction.online;

import br.com.vfs.api.payment.service.paymentmethod.PaymentMethodRepository;
import br.com.vfs.api.payment.service.transaction.NewOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@RequiredArgsConstructor
public class PaymentCreditCardValidator implements Validator {
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return NewOrderCreditCard.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final var newOrderCreditCard = (NewOrderCreditCard) target;
        if(Objects.isNull(newOrderCreditCard.getPaymentMethodId())) return;
        final var optional = paymentMethodRepository.findById(newOrderCreditCard.getPaymentMethodId());
        if(optional.isPresent()){
            if(optional.get().getType().isOffline()){
                errors.rejectValue("paymentMethodId", null, "Payment method type is offline.");
            }
            if(optional.get().getType().notIsCreditCard()){
                errors.rejectValue("paymentMethodId", null, "Payment method type not is credit card type.");
            }
        }
    }
}
