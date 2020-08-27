package br.com.vfs.api.payment.service.transaction;

import br.com.vfs.api.payment.service.paymentmethod.PaymentMethod;
import br.com.vfs.api.payment.service.paymentmethod.PaymentMethodRepository;
import br.com.vfs.api.payment.service.restaurant.RestaurantRepository;
import br.com.vfs.api.payment.service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class PaymentValidByUserAndRestaurantValidator implements Validator {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;


    @Override
    public boolean supports(Class<?> clazz) {
        return NewOrder.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final var newOrder = (NewOrder) target;
        if(Objects.isNull(newOrder.getPaymentMethodId())
                || Objects.isNull(newOrder.getUserId())
                || Objects.isNull(newOrder.getRestaurantId())) return;
        final var optionalUser = userRepository.findById(newOrder.getUserId());
        final var optionalRestaurant = restaurantRepository.findById(newOrder.getRestaurantId());
        if(optionalUser.isPresent() && optionalRestaurant.isPresent()){
            if(notContainsPayment(optionalUser.get().getPaymentMethods(), newOrder.getPaymentMethodId())
            || notContainsPayment(optionalRestaurant.get().getPaymentMethods(), newOrder.getPaymentMethodId()) )
            errors.rejectValue("paymentMethodId", null, "Payment method type not accept.");
        }
    }

    private boolean notContainsPayment(final Set<PaymentMethod> paymentMethods, final Long id){
        return paymentMethods.stream().map(PaymentMethod::getId).noneMatch(id::equals);
    }
}
