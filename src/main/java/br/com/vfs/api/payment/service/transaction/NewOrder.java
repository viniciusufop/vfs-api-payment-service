package br.com.vfs.api.payment.service.transaction;

import br.com.vfs.api.payment.service.paymentmethod.PaymentMethod;
import br.com.vfs.api.payment.service.restaurant.Restaurant;
import br.com.vfs.api.payment.service.restaurant.RestaurantRepository;
import br.com.vfs.api.payment.service.shared.annotations.ExistElement;
import br.com.vfs.api.payment.service.shared.annotations.ExistOrder;
import br.com.vfs.api.payment.service.user.User;
import br.com.vfs.api.payment.service.user.UserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class NewOrder {
    @NotNull
    @ExistOrder
    private Long orderId;
    @ExistElement(domainClass = PaymentMethod.class)
    @NotNull
    private Long paymentMethodId;
    @ExistElement(domainClass = Restaurant.class)
    @NotNull
    private Long restaurantId;
    @ExistElement(domainClass = User.class)
    @NotNull
    private Long userId;

    public Transaction toTransaction(final UserRepository userRepository,
                                     final RestaurantRepository restaurantRepository,
                                     final OrderIntegration orderIntegration) {
        final var user = userRepository.findById(userId).orElseThrow();
        final var restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        final var paymentMethod = user.getPaymentMethods().stream().filter(p -> p.getId().equals(paymentMethodId)).findFirst().orElseThrow();
        final var order = orderIntegration.findOrderId(orderId).orElseThrow();
        return new Transaction(orderId, order.getValue(), user, restaurant, TransactionStatus.PENDING, paymentMethod);
    }
}
