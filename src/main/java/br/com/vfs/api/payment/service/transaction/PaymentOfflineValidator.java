package br.com.vfs.api.payment.service.transaction;

import br.com.vfs.api.payment.service.paymentmethod.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@RequiredArgsConstructor
public class PaymentOfflineValidator implements Validator {
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return NewOrder.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final var newOrder = (NewOrder) target;
        if(Objects.isNull(newOrder.getPaymentMethodId())) return;
        final var optional = paymentMethodRepository.findById(newOrder.getPaymentMethodId());
        if(optional.isPresent() && optional.get().getType().isOnline()){
            errors.rejectValue("paymentMethodId", null, "Payment method type is online.");
        }
    }
}
