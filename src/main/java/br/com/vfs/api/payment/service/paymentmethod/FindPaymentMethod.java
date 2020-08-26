package br.com.vfs.api.payment.service.paymentmethod;

import br.com.vfs.api.payment.service.restaurant.Restaurant;
import br.com.vfs.api.payment.service.restaurant.RestaurantRepository;
import br.com.vfs.api.payment.service.shared.annotations.ExistElement;
import br.com.vfs.api.payment.service.paymentmethod.fraudster.PaymentFraudster;
import br.com.vfs.api.payment.service.user.User;
import br.com.vfs.api.payment.service.user.UserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class FindPaymentMethod implements Serializable {

    @NotNull
    @ExistElement(domainClass = User.class)
    private Long idUser;
    @NotNull
    @ExistElement(domainClass = Restaurant.class)
    private Long idRestaurant;

    public Set<PaymentMethodDetail> find(final UserRepository userRepository,
                                         final RestaurantRepository restaurantRepository,
                                         final Collection<PaymentFraudster> paymentFraudsters){
        final var user = userRepository.findById(idUser).orElseThrow();
        final var restaurant = restaurantRepository.findById(idRestaurant).orElseThrow();
        return user.filterPaymentMethods(restaurant, paymentFraudsters)
                .stream()
                .map(PaymentMethodDetail::new)
                .collect(Collectors.toSet());
    }

}
