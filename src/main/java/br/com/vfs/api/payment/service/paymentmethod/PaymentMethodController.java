package br.com.vfs.api.payment.service.paymentmethod;

import br.com.vfs.api.payment.service.restaurant.RestaurantRepository;
import br.com.vfs.api.payment.service.paymentmethod.fraudster.PaymentFraudster;
import br.com.vfs.api.payment.service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final Collection<PaymentFraudster> paymentFraudsters;

    @GetMapping
    @Transactional
    public ResponseEntity<Set<PaymentMethodDetail>> findByUserAndRestaurant(
            @Valid final FindPaymentMethod findPaymentMethod) {
        final var user = userRepository.findById(findPaymentMethod.getIdUser()).orElseThrow();
        final var restaurant = restaurantRepository.findById(findPaymentMethod.getIdRestaurant()).orElseThrow();
        final var payments = user.filterPaymentMethods(restaurant, paymentFraudsters)
                .stream()
                .map(PaymentMethodDetail::new)
                .collect(Collectors.toSet());
        user.addVisit(restaurant);
        userRepository.save(user);
        if (user.isBestRestaurant(restaurant, 3L)){
            final var expiredTime = LocalDateTime.now().plusMinutes(30);
            return ResponseEntity.ok()
                    .header("Expired", expiredTime.toString())
                    .body(payments);
        }
        return ResponseEntity.ok().body(payments);
    }
}
