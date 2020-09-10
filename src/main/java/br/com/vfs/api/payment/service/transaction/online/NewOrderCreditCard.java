package br.com.vfs.api.payment.service.transaction.online;

import br.com.vfs.api.payment.service.paymentmethod.PaymentMethod;
import br.com.vfs.api.payment.service.restaurant.Restaurant;
import br.com.vfs.api.payment.service.restaurant.RestaurantRepository;
import br.com.vfs.api.payment.service.shared.annotations.ExistElement;
import br.com.vfs.api.payment.service.shared.annotations.ExistOrder;
import br.com.vfs.api.payment.service.transaction.OrderIntegration;
import br.com.vfs.api.payment.service.transaction.Transaction;
import br.com.vfs.api.payment.service.transaction.TransactionStatus;
import br.com.vfs.api.payment.service.user.User;
import br.com.vfs.api.payment.service.user.UserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class NewOrderCreditCard implements Serializable {

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
    @CreditCardNumber
    @NotNull
    private String cardNumber;

    @Min(100)
    @Max(999)
    @NotNull
    private Integer ccv;

    public Transaction toTransaction(UserRepository userRepository, RestaurantRepository restaurantRepository, OrderIntegration orderIntegration) {
        final var user = userRepository.findById(userId).orElseThrow();
        final var restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        final var paymentMethod = user.getPaymentMethods().stream().filter(p -> p.getId().equals(paymentMethodId)).findFirst().orElseThrow();
        final var order = orderIntegration.findOrderId(orderId).orElseThrow();
        final Transaction transaction = new Transaction(orderId, order.getValue(), user, restaurant, TransactionStatus.CANCEL, paymentMethod);
        final Map<String, String> information = new HashMap<>();
        information.put("cardNumber", cardNumber);
        information.put("ccv", ccv.toString());
        transaction.setPaymentInformation(information);
        return transaction;
    }
}
