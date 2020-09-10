package br.com.vfs.api.payment.service.shared.validators;

import br.com.vfs.api.payment.service.shared.annotations.ExistOrder;
import br.com.vfs.api.payment.service.transaction.Order;
import br.com.vfs.api.payment.service.transaction.OrderIntegration;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@RequiredArgsConstructor
public class ExistOrderValidator implements ConstraintValidator<ExistOrder, Long> {

    private final OrderIntegration orderIntegration;

    @Override
    public void initialize(ExistOrder constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(Objects.isNull(value)) return true;
        final var order = orderIntegration.findOrderId(value);
        return order.isPresent();
    }
}
