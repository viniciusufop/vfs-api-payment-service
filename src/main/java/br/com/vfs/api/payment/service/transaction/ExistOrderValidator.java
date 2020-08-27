package br.com.vfs.api.payment.service.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@RequiredArgsConstructor
public class ExistOrderValidator implements Validator {

    private final OrderIntegration orderIntegration;

    @Override
    public boolean supports(Class<?> clazz) {
        return NewOrder.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final var newOrder = (NewOrder) target;
        if(Objects.isNull(newOrder.getOrderId())) return;
        final var optional = orderIntegration.findOrderId(newOrder.getOrderId());
        if(optional.isEmpty()){
            errors.rejectValue("orderId", null, "Order not exist.");
        }
    }
}
